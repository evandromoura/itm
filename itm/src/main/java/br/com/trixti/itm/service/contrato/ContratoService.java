package br.com.trixti.itm.service.contrato;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contrato.ContratoDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.contratoautenticacao.ContratoAutenticacaoService;
import br.com.trixti.itm.service.contratoequipamento.ContratoEquipamentoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratonotificacao.ContratoNotificacaoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class ContratoService extends AbstractService<Contrato> {
	
	private @Inject ContratoDAO contratoDAO;
	
	private @Inject ContratoProdutoService contratoProdutoService;
	private @Inject ContratoEquipamentoService contratoEquipamentoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject ContratoAutenticacaoService contratoAutenticacaoService;
	private @Inject ContratoNotificacaoService contratoNotificacaoService;
	private @Inject FreeRadiusService freeRadiusService;
	private @Inject BoletoService boletoService;
	
	
	@Override
	public AbstractDAO<Contrato> getDAO() {
		return contratoDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Contrato recuperarCompleto(Integer id){
		Contrato contrato =  contratoDAO.recuperarCompleto(id);
		contrato.setLancamentos(contratoLancamentoService.pesquisarLancamentoAberto(contrato));
		contrato.setNotificacoes(contratoNotificacaoService.pesquisarUltimasNotificacoes(contrato));
		return contrato;
	}

	
	
	@Override
	public void incluir(Contrato entidade) {
		entidade.setDataCriacao(new Date());
		entidade.setStatus(StatusContrato.ATIVO);
		super.incluir(entidade);
//		contratoProdutoService.incluirLista(entidade.getContratoProdutos());
//		contratoGrupoService.incluirLista(entidade.getContratoGrupos());
//		contratoEquipamentoService.incluirLista(entidade.getContratoEquipamentos());
		
		freeRadiusService.sincronizarContrato(entidade);
	}
	
	private void criarLancamentoCredito(Contrato contrato){
		if(contrato.getDataCreditoInicial() == null){
			UtilData utilData = new UtilData();
			ContratoLancamento contratoLancamento = new ContratoLancamento();
			contratoLancamento.setContrato(contrato);
			contratoLancamento.setDataLancamento(new Date());
			contratoLancamento.setStatus(StatusLancamentoEnum.PENDENTE);
			contratoLancamento.setDescricao("Desconto por utilização parcial do serviço");
			contratoLancamento.setTipoLancamento(TipoLancamentoEnum.CREDITO);
			Integer dia = new Long(utilData.getDia(contrato.getDataCriacao())).intValue();
			BigDecimal totalContrato = BigDecimal.ZERO;
			for(ContratoProduto produto:contrato.getContratoProdutos()){
				totalContrato = totalContrato.add(produto.getValor());
			}
			BigDecimal totalCredito = new BigDecimal(totalContrato.doubleValue() - (BigDecimal.valueOf(totalContrato.doubleValue()).doubleValue()*(30-dia))/30).setScale(2,BigDecimal.ROUND_HALF_UP);
			if(totalCredito.intValue() > 0){
				contratoLancamento.setValor(totalCredito);
				contratoLancamentoService.incluir(contratoLancamento);
			}	
			contrato.setDataCreditoInicial(new Date());
		}
	}
	
	
	@Override
	public void alterar(Contrato entidade) {
		if(entidade.isCriarLancamentoCredito()){
			criarLancamentoCredito(entidade);
		}	
		
		super.alterar(entidade);
		
		for(ContratoProduto contratoProduto:entidade.getContratoProdutos()){
			if(contratoProduto.getId() == null){
				contratoProdutoService.incluir(contratoProduto);
			}else{
				contratoProdutoService.alterar(contratoProduto);
				
			}	
		}
//		for(ContratoGrupo contratoGrupo:entidade.getContratoGrupos()){
//			if(contratoGrupo.getId() == null){
//				contratoGrupoService.incluir(contratoGrupo);
//			}
//			else{
//				contratoGrupoService.alterar(contratoGrupo);
//			}	
//		}
		
		for(ContratoEquipamento contratoEquipamento:entidade.getContratoEquipamentos()){
			if(contratoEquipamento.getId() == null){
				contratoEquipamentoService.incluir(contratoEquipamento);
			}else{
				contratoEquipamentoService.alterar(contratoEquipamento);
			}	
		}
		
		for(ContratoAutenticacao autenticacao:entidade.getAutenticacoes()){
			if(autenticacao.getId() == null){
				contratoAutenticacaoService.incluir(autenticacao);
			}else{
				contratoAutenticacaoService.alterar(autenticacao);
			}	
		}
		
		freeRadiusService.sincronizarContrato(entidade);
		
	}
	
	public List<Contrato> listarAtivo() {
		return contratoDAO.listarAtivo();
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Contrato recuperar(Serializable id) {
		Contrato contrato =  super.recuperar(id);
		contrato.setLancamentos(contratoLancamentoService.pesquisarLancamentoAberto(contrato));
		return contrato;
	}

	public List<Contrato> pesquisarPorCliente(Cliente cliente) {
		return contratoDAO.pesquisarPorCliente(cliente);
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void excluir(Contrato entidade) {
		boletoService.excluirPorContrato(entidade);
		contratoLancamentoService.excluirPorContrato(entidade);
		if(entidade.getAutenticacoes() != null && !entidade.getAutenticacoes().isEmpty()){
			freeRadiusService.excluirPorUsername(entidade.getAutenticacoes().get(0).getUsername());
		}	
		super.excluir(entidade);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void cancelar(Contrato entidade) {
		entidade.setStatus(StatusContrato.CANCELADO);
		entidade.setDataCancelamento(new Date());
		
		if(entidade.getAutenticacoes() != null && !entidade.getAutenticacoes().isEmpty()){
			freeRadiusService.excluirPorUsername(entidade.getAutenticacoes().get(0).getUsername());
		}
		contratoDAO.alterar(entidade);
		if(entidade.isGeraMultaCancelamento()){
			criarMultaContratoLancamento(entidade);
		}	
	}
	
	private void criarMultaContratoLancamento(Contrato contrato){
		UtilData utilData = new UtilData();
		ContratoLancamento contratoLancamento = new ContratoLancamento();
		contratoLancamento.setContrato(contrato);
		contratoLancamento.setDataLancamento(new Date());

		BigDecimal totalProdutos = BigDecimal.ZERO;
		for(ContratoProduto contratoProduto:contrato.getContratoProdutos()){
			totalProdutos = totalProdutos.add(contratoProduto.getValor());
		}
		
		Double total = totalProdutos.doubleValue() * (12 -utilData.getDiferencaMes(new Date(), contrato.getDataCriacao()));
		BigDecimal valorFinal = BigDecimal.valueOf(total).multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
		
		contratoLancamento.setValor(valorFinal.setScale(2,RoundingMode.HALF_UP));
		
		contratoLancamento.setTipoLancamento(TipoLancamentoEnum.DEBITO);
		contratoLancamento.setStatus(StatusLancamentoEnum.PENDENTE);
		contratoLancamento.setDescricao("Multa por cancelamento");
		
		Boleto boleto = new Boleto();
		boleto.setContrato(contrato);
		boleto.setDataCriacao(new Date());
		boleto.setDataVencimento(new Date());
		boleto.setValor(valorFinal.setScale(2,RoundingMode.HALF_UP));
		List<BoletoLancamento> listaBoletoLancamento = new ArrayList<BoletoLancamento>();
		BoletoLancamento boletoLancamento = new BoletoLancamento();
		boletoLancamento.setBoleto(boleto);
		contratoLancamentoService.incluir(contratoLancamento);
		boletoLancamento.setContratoLancamento(contratoLancamento);
		listaBoletoLancamento.add(boletoLancamento);
		boleto.setLancamentos(listaBoletoLancamento);
		boleto.setStatus(StatusBoletoEnum.ABERTO);
		BigInteger nossoNumero = boletoService.recuperarNossoNumero();
		boleto.setNumeroDocumento(nossoNumero.toString());
		boleto.setNossoNumero(nossoNumero.toString());
		boleto.setDigitoNossoNumero(String.valueOf(new CalculaBase10().getMod10(nossoNumero.toString())));
		boleto.setNossoNumeroCompleto(boleto.getNossoNumero()+boleto.getDigitoNossoNumero());
		boletoService.incluir(boleto);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void promessaPagamento(Contrato contrato) {
		UtilData utilData = new UtilData();
		contrato.setDataParaBloqueio(utilData.adicionaDias(new Date(), 5));
		super.alterar(contrato);
	}
	
	public List<Contrato> listarRelatorio(){
		return contratoDAO.listarRelatorio();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void desbloquear(Contrato entidade) {
		entidade.setStatus(StatusContrato.ATIVO);
		entidade.setDataCancelamento(null);
		
		if(entidade.getAutenticacoes() != null && !entidade.getAutenticacoes().isEmpty()){
			freeRadiusService.sincronizarContrato(entidade);
		}
		contratoDAO.alterar(entidade);
	}
	
}
