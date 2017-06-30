package br.com.trixti.itm.service.contrato;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contrato.ContratoDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoGrupo;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.contratoautenticacao.ContratoAutenticacaoService;
import br.com.trixti.itm.service.contratoequipamento.ContratoEquipamentoService;
import br.com.trixti.itm.service.contratogrupo.ContratoGrupoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;

@Stateless
public class ContratoService extends AbstractService<Contrato> {
	
	private @Inject ContratoDAO contratoDAO;
	
	private @Inject ContratoProdutoService contratoProdutoService;
	private @Inject ContratoGrupoService contratoGrupoService;
	private @Inject ContratoEquipamentoService contratoEquipamentoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject ContratoAutenticacaoService contratoAutenticacaoService;
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
	
	
	@Override
	public void alterar(Contrato entidade) {
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
	
	
}
