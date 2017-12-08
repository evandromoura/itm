package br.com.trixti.itm.rest;

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
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.to.ClienteWSTO;


@javax.ws.rs.Path(value="/data")
@javax.enterprise.context.RequestScoped
public class ItmRESTService {
	
	
	private @Inject ClienteService clienteService;
	private @Inject BoletoService boletoService;
	
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
		ClienteWSTO cliente = clienteService.recuperarPorCpf(cpf);
		return cliente;
	}
	
	
	@GET
	@POST
	@Path("/integracao/cliente/situacao")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteWSTO situacaoCliente(@QueryParam("cpf") String cpf) throws Exception {
		ClienteWSTO cliente1 = clienteService.recuperarPorCpf(cpf);
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
	
	
}