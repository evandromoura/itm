package br.com.trixti.itm.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.trixti.itm.service.cliente.ClienteService;


@javax.ws.rs.Path(value="/data")
@javax.enterprise.context.RequestScoped
public class ItmRESTService {
	
	
	private @Inject ClienteService clienteService;
	
	@GET
	@Path("/cliente")
	@Produces(MediaType.APPLICATION_JSON)
	public String dataCliente() throws Exception {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(clienteService.listar());
	}
	
}