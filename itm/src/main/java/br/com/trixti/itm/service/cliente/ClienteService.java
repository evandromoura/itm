package br.com.trixti.itm.service.cliente;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.cliente.ClienteDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;

/**
 * Classe que aplica a regra de negocio do caso de uso (Cliente)
 * @author evandro
 *
 *  RN01 - PESSOA FISICA : 
 *  	 - Ao criar um cliente, cadastrar nas respectivas tabelas do Free Radius (radcheck, radgroupcheck)
 */
@Stateless
public class ClienteService extends AbstractService<Cliente> {
	
	private @Inject ClienteDAO clienteDAO;
	private @Inject ContratoService contratoService;
	private @Inject FreeRadiusService freeRadiusService;
	private @Inject BoletoService boletoService;
	
	@Override
	public AbstractDAO<Cliente> getDAO() {
		return clienteDAO;
	}
 
	public List<Cliente> pesquisar(Cliente clientePesquisa) {
		return clienteDAO.pesquisar(clientePesquisa);
	}

	@Override
	public void incluir(Cliente entidade) {
		entidade.setDataCriacao(new Date());
		super.incluir(entidade);
	}


	public List<Cliente> listarAtivo() {
		return clienteDAO.listarAtivo();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Cliente recuperar(Serializable id) {
		Cliente cliente =  super.recuperar(id);
//		cliente.setContratos(contratoService.pesquisarPorCliente(cliente));
//		cliente.setLancamentos(contratoLancamentoService.pesquisarLancamentoAberto(cliente));
		return cliente;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void excluir(Cliente entidade) {
		if(entidade.getContratos() != null){
			for(Contrato contrato:entidade.getContratos()){
				Contrato contratoCompleto = contratoService.recuperarCompleto(contrato.getId());
				contratoService.excluir(contratoCompleto);
				if(contratoCompleto.getAutenticacoes() != null && contratoCompleto.getAutenticacoes().size() > 0){
					freeRadiusService.excluirPorUsername(contratoCompleto.getAutenticacoes().get(0).getUsername());
				}	
			}
		}	
		super.excluir(entidade);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Cliente recuperarPorAutenticacao(String username,String senha){
		try{
			Cliente cliente =clienteDAO.recuperarPorAutenticacao(username, senha);
			for(Contrato contrato:cliente.getContratos()){
				contrato.setBoletos(boletoService.pesquisarBoletoEmAbertoContratoComAviso(contrato));
			}
			return cliente;
		}catch(Exception e){
			return null;
		}	
	}
	public Cliente recuperarPorEmail(String email){
		try{
			return clienteDAO.recuperarPorEmail(email);
		}catch(Exception e){
			return null;
		}	
	}
	
	
	

}
