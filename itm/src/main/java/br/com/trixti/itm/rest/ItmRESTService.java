package br.com.trixti.itm.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteTag;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.dashboard.DashboardService;
import br.com.trixti.itm.to.BoletoLancamentoWS;
import br.com.trixti.itm.to.BoletoWS;
import br.com.trixti.itm.to.ClienteWSTO;
import br.com.trixti.itm.to.ContratoWSTO;
import br.com.trixti.itm.to.DashboardWSTO;
import br.com.trixti.itm.to.TagWS;


@javax.ws.rs.Path(value="/data")
@javax.enterprise.context.RequestScoped
public class ItmRESTService {
	
	
	private @Inject ClienteService clienteService;
	private @Inject BoletoService boletoService;
	private @Inject DashboardService dashboardService;
	
	@GET
	@Path("/cliente")
	@Produces(MediaType.APPLICATION_JSON)
	public String dataCliente() throws Exception {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(clienteService.listar());
	}
	
	
	
	@GET
	@POST
	@Path("/integracao/cliente")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteWSTO integracaoCliente(@QueryParam("cpf") String cpf) throws Exception {
		ClienteWSTO cliente = clienteService.recuperarPorCpfWS(cpf);
		return cliente;
	}
	
	
	@GET
	@POST
	@Path("/integracao/cliente/situacao")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteWSTO situacaoCliente(@QueryParam("cpf") String cpf) throws Exception {
		ClienteWSTO cliente1 = clienteService.recuperarPorCpfWS(cpf);
		Cliente cliente = clienteService.recuperar(cliente1.getId());
		Integer qtdBoletosEmAberto = 0;
		if(cliente.getContratos() != null){
			for(Contrato contrato:cliente.getContratos()){
				List<Boleto> boletosEmAbertos = boletoService.pesquisarBoletoEmAbertoContratoComAviso(contrato);
				if(boletosEmAbertos != null){
					qtdBoletosEmAberto += boletosEmAbertos.size();
				}	
			}
		}	
		cliente1.setQtdBoletoEmAtraso(qtdBoletosEmAberto);
		return cliente1;
	}
	
	@GET
	@POST
	@Path("/integracao/cliente/informacoes")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteWSTO boletoAtrasadoCliente(@QueryParam("cpf") String cpf) throws Exception {
		
		ClienteWSTO cliente1 = clienteService.recuperarPorCpfWS(cpf);
		Cliente cliente = clienteService.recuperar(cliente1.getId());
		cliente1.setPromessa(true);
		List<BoletoWS> listaBoleto = new ArrayList<BoletoWS>();
		if(cliente != null && cliente.getContratos() != null){
			List<ContratoWSTO> contratosWSTO = new ArrayList<ContratoWSTO>();
			for(Contrato contrato:cliente.getContratos()){
				if(contrato.getDataParaBloqueio() != null){
					cliente1.setPromessa(false);
				}
				contratosWSTO.add(new ContratoWSTO(contrato));
				List<Boleto> boletosEmAbertos = boletoService.pesquisarBoletoEmAtraso(contrato);
				if(boletosEmAbertos != null){
					for(Boleto boleto:boletosEmAbertos){
						List<BoletoLancamentoWS> lancamentosWs = new ArrayList<BoletoLancamentoWS>();
						if(boleto.getLancamentos() != null){
							for(BoletoLancamento boletoLancamento:boleto.getLancamentos()){
								lancamentosWs.add(new BoletoLancamentoWS(boletoLancamento.getContratoLancamento().getDescricao()));
							}
						}
						listaBoleto.add(new BoletoWS(boleto.getId(),boleto.getDataVencimento(), boleto.getValor(),lancamentosWs));
					}
				}	
			}
			cliente1.setContratos(contratosWSTO);
		}	
		if(cliente != null && cliente.getClienteTags() != null){
			List<TagWS> tags = new ArrayList<TagWS>();
			for(ClienteTag clienteTag:cliente.getClienteTags()){
				tags.add(new TagWS(clienteTag.getTag().getNome()));
			}
			cliente1.setQtdTag(tags.size());
			cliente1.setTags(tags);
		}
		
		cliente1.setQtdBoletoEmAtraso(listaBoleto.size());
		cliente1.setBoletos(listaBoleto);
		cliente1.setResultado(true);
		return cliente1;
	}
	
	
	@GET
	@POST
	@Path("/integracao/cliente/boleto/segundavia")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteWSTO enviarBoletoCliente(@QueryParam("idBoleto") String idBoleto) throws Exception {
		Boleto boleto = boletoService.recuperar(Integer.valueOf(idBoleto));
		boletoService.enviarSegundaVia(boleto);
		ClienteWSTO clienteRetorno = new ClienteWSTO();
		clienteRetorno.setResultado(true);
		return clienteRetorno;
	}
	
	
	@GET
	@POST
	@Path("/integracao/cliente/promessapagamento")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteWSTO promessaPagamentoCliente(@QueryParam("cpf") String cpf) throws Exception {
		ClienteWSTO cliente1 = clienteService.recuperarPorCpfWS(cpf);
		Cliente cliente = clienteService.recuperar(cliente1.getId());
		clienteService.desbloquearClienteTemporariamente(cliente);
		ClienteWSTO clienteRetorno = new ClienteWSTO();
		clienteRetorno.setResultado(true);
		return clienteRetorno;
	}
	
	
	
	
	@GET
	@Path("/dashboard")
	@Produces(MediaType.APPLICATION_JSON)
	public DashboardWSTO dashboard() throws Exception {
		return dashboardService.dashboard();
	}
	
	
}