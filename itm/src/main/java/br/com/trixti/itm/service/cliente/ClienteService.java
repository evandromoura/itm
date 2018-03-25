package br.com.trixti.itm.service.cliente;

import java.io.Serializable;
import java.math.BigDecimal;
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
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.TecnologiaEnum;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.clientetag.ClienteTagService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.to.ClienteWSTO;

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
	private @Inject ClienteTagService clienteTagService;
	
	@Override
	public AbstractDAO<Cliente> getDAO() {
		return clienteDAO;
	}
 
	public List<Cliente> pesquisar(Cliente clientePesquisa) {
		return clienteDAO.pesquisar(clientePesquisa);
	}
	
	public List<Cliente> listarPorBoletoAtrasado(){
//		return comporBoletoAtrasado(clienteDAO.listarPorBoletoAtrasado());
		return clienteDAO.listarPorBoletoAtrasado();
	}
	

	@Override
	public void incluir(Cliente entidade) {
		entidade.setDataCriacao(new Date());
		super.incluir(entidade);
//		atualizarTags(entidade);
	}
	
	
	@Override
	public void alterar(Cliente entidade) {
		super.alterar(entidade);
//		atualizarTags(entidade);
	}

	private void atualizarTags(Cliente entidade){
		clienteTagService.excluirPorCliente(entidade);
		clienteTagService.incluirLista(entidade.getClienteTags());
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
					for(ContratoAutenticacao contratoAutenticacao:contratoCompleto.getAutenticacoes()){
						freeRadiusService.excluirPorUsername(contratoAutenticacao.getUsername());
					}	
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
	
	
	public ClienteWSTO recuperarPorCpfWS(String cpf){
		try{
			return clienteDAO.recuperarPorCpfWS(cpf);
		}catch(Exception e){
			return null;
		}	
	}
	
	
	public Cliente recuperarPorCpfCnpj(String cpf){
		try{
			return clienteDAO.recuperarPorCpfCnpj(cpf);
		}catch(Exception e){
			return null;
		}	
	}
	

	public Integer qtdClienteAtivo() {
		return clienteDAO.qtdClienteAtivo();
	}
	
	public Integer qtdClienteUfTipoPessoaIntervaloDownload(Uf uf,TipoPessoaEnum tipoPessoa, Integer minimoDownload,Integer maximoDownload) {
		Integer retorno = clienteDAO.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, minimoDownload, maximoDownload);
		return retorno != null?retorno:0;
	}
	
	public BigDecimal somaClienteUfTipoPessoaIntervaloDownload(Uf uf,TipoPessoaEnum tipoPessoa, Integer minimoDownload,Integer maximoDownload) {
		BigDecimal retorno = clienteDAO.somaClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, minimoDownload, maximoDownload);
		return retorno != null?retorno:BigDecimal.ZERO;
	}
	
	public Integer qtdClientePorTecnologia(TecnologiaEnum tecnologiaEnum) {
		return clienteDAO.qtdClientePorTecnologia(tecnologiaEnum);
	}
	
	public Integer qtdDownloadPorTecnologia(TecnologiaEnum tecnologiaEnum) {
		return clienteDAO.qtdDownloadPorTecnologia(tecnologiaEnum);
	}
	
	public Integer qtdClientePorTecnologiaIntervaloDownload(TecnologiaEnum tecnologiaEnum,Integer minimoDownload,Integer maximoDownload) {
		return clienteDAO.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum, minimoDownload, maximoDownload);
	}
	
	public BigDecimal menorPrecoUfTipoPessoaIntervaloDownload(Uf uf,TipoPessoaEnum tipoPessoa, Integer minimoDownload,Integer maximoDownload,boolean dedicado) {
		return clienteDAO.menorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, minimoDownload, maximoDownload, dedicado);
	}
	
	public BigDecimal maiorPrecoUfTipoPessoaIntervaloDownload(Uf uf,TipoPessoaEnum tipoPessoa, Integer minimoDownload,Integer maximoDownload,boolean dedicado) {
		return clienteDAO.maiorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, minimoDownload, maximoDownload, dedicado);
	}
	
	public Integer qtdClienteTipoPessoa(TipoPessoaEnum tipoPessoa) {
		return clienteDAO.qtdClienteTipoPessoa(tipoPessoa);
	}
	
	public BigDecimal somarTodosContratos(){
		return clienteDAO.somarTodosContratos();
	}
	
	public Integer somarTodosDownloadContratos() {
		return clienteDAO.somarTodosDownloadContratos();
	}
	
	public void desbloquearClienteTemporariamente(Cliente cliente){
		if(cliente != null && cliente.getContratos() != null){
			for(Contrato contrato:cliente.getContratos()){
				if(contrato.getStatus().equals(StatusContrato.BLOQUEADO)){
					contratoService.desbloquearContratoTemporariamente(contratoService.recuperarCompleto(contrato.getId()));
				}
			}
		}
	}

}
