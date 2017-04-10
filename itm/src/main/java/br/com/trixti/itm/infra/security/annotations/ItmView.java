package br.com.trixti.itm.infra.security.annotations;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@URLMappings(mappings={
		
		  @URLMapping(id = "login", pattern = "/login", viewId = "/login.jsf"),
		  @URLMapping(id = "home", pattern = "/home", viewId = "/pages/home/home.jsf"),
		
		  @URLMapping(id = "cliente_list", pattern = "/cliente", viewId = "/pages/cliente/cliente_list.jsf"),
		  @URLMapping(id = "cliente_view", pattern = "/cliente/view/#{parametro}", viewId = "/pages/cliente/cliente_view.jsf"),
		  @URLMapping(id = "cliente_form_new", pattern = "/cliente/#{acao}", viewId = "/pages/cliente/cliente_form.jsf"),
		  @URLMapping(id = "cliente_form_edit", pattern = "/cliente/#{acao}/#{parametro}", viewId = "/pages/cliente/cliente_form.jsf"),
		 
		  @URLMapping(id = "contrato_form", pattern = "/contrato", viewId = "/pages/contrato/contrato_form.jsf"),
		  @URLMapping(id = "contrato_view", pattern = "/contrato/view/#{parametro}", viewId = "/pages/contrato/contrato_view.jsf"),
		  @URLMapping(id = "contrato_form_edit", pattern = "/contrato/#{acao}/#{id}", viewId = "/pages/contrato/contrato_form.jsf"),
		  
		  @URLMapping(id = "contacorrente_list", pattern = "/contacorrente", viewId = "/pages/contacorrente/contacorrente_list.jsf"),
		  @URLMapping(id = "contacorrente_form", pattern = "/contacorrente/#{acao}/#{parametro}", viewId = "/pages/contacorrente/contacorrente_form.jsf"),
		  
		  @URLMapping(id = "grupo_list", pattern = "/grupo", viewId = "/pages/grupo/grupo_list.jsf"),
		  @URLMapping(id = "grupo_form_view", pattern = "/grupo/#{acao}", viewId = "/pages/grupo/grupo_form.jsf"),
		  @URLMapping(id = "grupo_form", pattern = "/grupo/#{acao}/#{parametro}", viewId = "/pages/grupo/grupo_form.jsf")
		  
		  
		})
public class ItmView {
	

}
