package br.com.trixti.itm.service.contrato;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.contratoanexo.ContratoAnexoService;
import br.com.trixti.itm.service.contratoautenticacao.ContratoAutenticacaoService;
import br.com.trixti.itm.service.contratoequipamento.ContratoEquipamentoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratonotificacao.ContratoNotificacaoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.to.PeriodoTO;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class ContratoService extends AbstractService<Contrato> {
	
	private @Inject ContratoDAO contratoDAO;
	
	private @Inject ContratoProdutoService contratoProdutoService;
//	private @Inject ContratoEquipamentoService contratoEquipamentoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject ContratoAutenticacaoService contratoAutenticacaoService;
	private @Inject ContratoNotificacaoService contratoNotificacaoService;
	private @Inject FreeRadiusService freeRadiusService;
	private @Inject BoletoService boletoService;
	private @Inject ParametroService parametroService;
	private @Inject ContratoAnexoService contratoAnexoService;
	
	
	@Override
	public AbstractDAO<Contrato> getDAO() {
		return contratoDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Contrato recuperarCompleto(Integer id){
		Contrato contrato =  contratoDAO.recuperarCompleto(id);
		contrato.setLancamentos(contratoLancamentoService.pesquisarLancamentoAberto(contrato));
		contrato.setNotificacoes(contratoNotificacaoService.pesquisarUltimasNotificacoes(contrato));
		contrato.setAnexos(contratoAnexoService.pesquisarPorContrato(contrato));
		return contrato;
	}
	
	@Override
	public void incluir(Contrato entidade) {
		entidade.setDataCriacao(new Date());
		entidade.setStatus(StatusContrato.ATIVO);
		super.incluir(entidade);
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
			Integer dia = 0;
			BigDecimal totalContrato = BigDecimal.ZERO;
			for(ContratoProduto produto:contrato.getContratoProdutos()){
				totalContrato = totalContrato.add(produto.getValor().multiply(new BigDecimal(produto.getQtd())));
				if(produto.isVigente()){
					dia = new Long(utilData.getDia(produto.getDataInicio())).intValue();
					if(dia > 30){
						dia = 30;
					}
				}
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
//		for(ContratoEquipamento contratoEquipamento:entidade.getContratoEquipamentos()){
//			if(contratoEquipamento.getId() == null){
//				contratoEquipamentoService.incluir(contratoEquipamento);
//			}else{
//				contratoEquipamentoService.alterar(contratoEquipamento);
//			}	
//		}
		
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
	
	public List<Contrato> pesquisarPorPeriodo(PeriodoTO periodoTO){
		return contratoDAO.pesquisarPorPeriodo(periodoTO);
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void excluir(Contrato entidade) {
		boletoService.excluirPorContrato(entidade);
		contratoLancamentoService.excluirPorContrato(entidade);
		if(entidade.getAutenticacoes() != null && !entidade.getAutenticacoes().isEmpty()){
			for(ContratoAutenticacao contratoAutenticacao:entidade.getAutenticacoes()){
				freeRadiusService.excluirPorUsername(contratoAutenticacao.getUsername());
			}	
		}	
		super.excluir(entidade);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void cancelar(Contrato entidade) {
		entidade.setStatus(StatusContrato.CANCELADO);
		entidade.setDataCancelamento(new Date());
		
		if(entidade.getAutenticacoes() != null && !entidade.getAutenticacoes().isEmpty()){
			for(ContratoAutenticacao contratoAutenticacao:entidade.getAutenticacoes()){
				freeRadiusService.excluirPorUsername(contratoAutenticacao.getUsername());
			}	
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
			totalProdutos = totalProdutos.add(contratoProduto.getValor().multiply(new BigDecimal(contratoProduto.getQtd())));
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
			freeRadiusService.removerSuspensaoContrato(entidade);
			freeRadiusService.sincronizarContrato(entidade);
		}
		contratoDAO.alterar(entidade);
	}
	
	public Integer qtdContratoAtivo() {
		return contratoDAO.qtdContratoAtivo();
	}
	
	public BigDecimal valorTotalFixo(){
		BigDecimal total = BigDecimal.ZERO;
		List<ContratoProduto> listaContratoProduto = contratoProdutoService.listarAtivos();
		for(ContratoProduto contratoProduto:listaContratoProduto){
			if(contratoProduto.getValor() != null){
				total = total.add(contratoProduto.getValor().multiply(new BigDecimal(contratoProduto.getQtd())));
			}	
		}
		return total;
	}
	
	public BigDecimal valorTotalAtrasado(){
		BigDecimal total = boletoService.somarBoletoEmAtraso();
		System.out.println("VALOR = "+total);
		return total!=null?total:BigDecimal.ZERO;
	}
	
	public Integer qtdContratoCriadoPeriodo(PeriodoTO periodoTO) {
		return contratoDAO.qtdContratoCriadoPeriodo(periodoTO);
	}
	
	public Integer qtdContratoCanceladoPeriodo(PeriodoTO periodoTO) {
		return contratoDAO.qtdContratoCanceladoPeriodo(periodoTO);
	}
	
	
	public Integer qtdContratoCriadoPeriodoPagantes(PeriodoTO periodoTO) {
		return contratoDAO.qtdContratoCriadoPeriodoPagantes(periodoTO);
	}
	
	public Integer qtdContratoCanceladoPeriodoPagantes(PeriodoTO periodoTO) {
		return contratoDAO.qtdContratoCanceladoPeriodoPagantes(periodoTO);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void desbloquearContratoTemporariamente(Contrato contrato){
		UtilData utilData = new UtilData();
		Parametro parametro = parametroService.recuperarParametro();
		contrato.setDataParaBloqueio(utilData.adicionaDias(new Date(), parametro.getQtdDiasDesbloqueioTemporario()));
		desbloquear(contrato);
	}
	
}
