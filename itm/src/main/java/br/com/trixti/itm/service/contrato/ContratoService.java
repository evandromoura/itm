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
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoGrupo;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.contratoequipamento.ContratoEquipamentoService;
import br.com.trixti.itm.service.contratogrupo.ContratoGrupoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;

@Stateless
public class ContratoService extends AbstractService<Contrato> {
	
	private @Inject ContratoDAO contratoDAO;
	
	private @Inject ContratoProdutoService contratoProdutoService;
	private @Inject ContratoGrupoService contratoGrupoService;
	private @Inject ContratoEquipamentoService contratoEquipamentoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	
	
	@Override
	public AbstractDAO<Contrato> getDAO() {
		return contratoDAO;
	}
	
	public Contrato recuperarCompleto(Integer id){
		Contrato contrato =  contratoDAO.recuperarCompleto(id);
		contrato.setLancamentos(contratoLancamentoService.pesquisarLancamentoAberto(contrato));
		return contrato;
	}

	
	
	@Override
	public void incluir(Contrato entidade) {
		entidade.setDataCriacao(new Date());
		super.incluir(entidade);
//		contratoProdutoService.incluirLista(entidade.getContratoProdutos());
//		contratoGrupoService.incluirLista(entidade.getContratoGrupos());
//		contratoEquipamentoService.incluirLista(entidade.getContratoEquipamentos());
		/**
		 * TODO: Desenvolver a ligação com o Radius  (radcheck, radgroupcheck)
		 */
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
		for(ContratoGrupo contratoGrupo:entidade.getContratoGrupos()){
			if(contratoGrupo.getId() == null){
				contratoGrupoService.incluir(contratoGrupo);
			}else{
				contratoGrupoService.alterar(contratoGrupo);
			}	
		}
		
		for(ContratoEquipamento contratoEquipamento:entidade.getContratoEquipamentos()){
			if(contratoEquipamento.getId() == null){
				contratoEquipamentoService.incluir(contratoEquipamento);
			}else{
				contratoEquipamentoService.alterar(contratoEquipamento);
			}	
		}
		
		
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
	
	
	
	
}
