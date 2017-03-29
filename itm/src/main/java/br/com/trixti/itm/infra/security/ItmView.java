package br.com.trixti.itm.infra.security;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@URLMappings(mappings={
		
		  @URLMapping(id = "login", pattern = "/login", viewId = "/login.jsf"),
		
		  @URLMapping(id = "cliente_list", pattern = "/cliente", viewId = "/pages/cliente/cliente_list.jsf"),
		  @URLMapping(id = "cliente_view", pattern = "/cliente/view/#{parametro}", viewId = "/pages/cliente/cliente_view.jsf"),
		  @URLMapping(id = "cliente_form_new", pattern = "/cliente/#{acao}", viewId = "/pages/cliente/cliente_form.jsf"),
		  @URLMapping(id = "cliente_form_edit", pattern = "/cliente/#{acao}/#{parametro}", viewId = "/pages/cliente/cliente_form.jsf"),
		 
		  @URLMapping(id = "contrato_form", pattern = "/contrato", viewId = "/pages/contrato/contrato_form.jsf"),
		  @URLMapping(id = "contrato_view", pattern = "/contrato/view/#{parametro}", viewId = "/pages/contrato/contrato_view.jsf")
		  
		  
		})
public class ItmView {
	

}
