package br.com.trixti.itm.infra.security.annotations;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@URLMappings(mappings={
		
		  @URLMapping(id = "login", pattern = "/login", viewId = "/login.jsf"),
		  @URLMapping(id = "esquecisenha", pattern = "/esquecisenha", viewId = "/pages/public/esquecisenha.jsf"),
		  @URLMapping(id = "alterarsenha", pattern = "/alterarsenha", viewId = "/pages/public/alterarsenha.jsf"),
		  @URLMapping(id = "novoacesso", pattern = "/novoacesso"	, viewId = "/pages/public/novoacesso.jsf"),
		  @URLMapping(id = "home", pattern = "/home", viewId = "/pages/home/home.jsf"),

		  @URLMapping(id = "snmp",      pattern = "/snmp", viewId = "/pages/snmp/snmp.jsf"),
		  @URLMapping(id = "snmp_view", pattern = "/snmp/view/#{parametro}", viewId = "/pages/snmp/snmp_view.jsf"),
		
		  @URLMapping(id = "cliente_list", pattern = "/cliente", viewId = "/pages/cliente/cliente_list.jsf"),
		  @URLMapping(id = "cliente_view", pattern = "/cliente/view/#{parametro}", viewId = "/pages/cliente/cliente_view.jsf"),
		  @URLMapping(id = "cliente_form_new", pattern = "/cliente/#{acao}", viewId = "/pages/cliente/cliente_form.jsf"),
		  @URLMapping(id = "cliente_form_edit", pattern = "/cliente/#{acao}/#{parametro}", viewId = "/pages/cliente/cliente_form.jsf"),
		 
		  @URLMapping(id = "contrato_form", pattern = "/contrato", viewId = "/pages/contrato/contrato_form.jsf"),
		  @URLMapping(id = "contrato_view", pattern = "/contrato/view/#{parametro}", viewId = "/pages/contrato/contrato_view.jsf"),
		  @URLMapping(id = "contrato_form_edit", pattern = "/contrato/#{acao}/#{id}", viewId = "/pages/contrato/contrato_form.jsf"),
		  
		  
		  @URLMapping(id = "parametro_form", pattern = "/parametro/#{acao}/#{parametro}", viewId = "/pages/parametro/parametro_form.jsf"),

		  @URLMapping(id = "usuario_form", pattern = "/usuario/#{acao}/#{parametro}", viewId = "/pages/usuario/usuario_form.jsf"),
		  @URLMapping(id = "usuario_list", pattern = "/usuario", viewId = "/pages/usuario/usuario_list.jsf"),
		  @URLMapping(id = "usuario_form_view", pattern = "/usuario/#{acao}", viewId = "/pages/usuario/usuario_form.jsf"),
		  
		  @URLMapping(id = "log_list", pattern = "/log", viewId = "/pages/log/log_list.jsf"),
		 	  
		  @URLMapping(id = "produto_form", pattern = "/produto/#{acao}/#{parametro}", viewId = "/pages/produto/produto_form.jsf"),
		  @URLMapping(id = "produto_list", pattern = "/produto", viewId = "/pages/produto/produto_list.jsf"),
		  @URLMapping(id = "produto_form_view", pattern = "/produto/#{acao}", viewId = "/pages/produto/produto_form.jsf"),
		  
		  
		  @URLMapping(id = "equipamento_list", 		pattern = "/equipamento", viewId = "/pages/equipamento/equipamento_list.jsf"),
		  @URLMapping(id = "equipamento_form_new",  pattern = "/equipamento/#{acao}", viewId = "/pages/equipamento/equipamento_form.jsf"),
		  @URLMapping(id = "equipamento_view",      pattern = "/equipamento/view/#{parametro}", viewId = "/pages/equipamento/equipamento_view.jsf"),
		  @URLMapping(id = "equipamento_form_edit", pattern = "/equipamento/#{acao}/#{parametro}", viewId = "/pages/equipamento/equipamento_form.jsf"),
		  
		  
		  
		  @URLMapping(id = "notificacao_form", pattern = "/notificacao/#{acao}/#{parametro}", viewId = "/pages/notificacao/notificacao_form.jsf"),
		  @URLMapping(id = "notificacao_list", pattern = "/notificacao", viewId = "/pages/notificacao/notificacao_list.jsf"),
		  @URLMapping(id = "notificacao_form_view", pattern = "/notificacao/#{acao}", viewId = "/pages/notificacao/notificacao_form.jsf"),
		  
		  @URLMapping(id = "arquivosici_view", pattern = "/arquivosici/view/#{parametro}", viewId = "/pages/arquivosici/arquivosici_view.jsf"),
		  @URLMapping(id = "arquivosici_form", pattern = "/arquivosici/#{acao}/#{parametro}", viewId = "/pages/arquivosici/arquivosici_form.jsf"),
		  @URLMapping(id = "arquivosici_list", pattern = "/arquivosici", viewId = "/pages/arquivosici/arquivosici_list.jsf"),

		  @URLMapping(id = "nfe_view", pattern = "/nfe/view/#{parametro}", viewId = "/pages/nfe/nfe_view.jsf"),
		  @URLMapping(id = "nfe_form", pattern = "/nfe/#{acao}/#{parametro}", viewId = "/pages/nfe/nfe_form.jsf"),
		  @URLMapping(id = "nfe_list", pattern = "/nfe", viewId = "/pages/nfe/nfe_list.jsf"),
		  
		  @URLMapping(id = "servico_form", pattern = "/servico/#{acao}/#{parametro}", viewId = "/pages/servico/servico_form.jsf"),
		  @URLMapping(id = "servico_list", pattern = "/servico", viewId = "/pages/servico/servico_list.jsf"),
		  @URLMapping(id = "servico_form_view", pattern = "/servico/#{acao}", viewId = "/pages/servico/servico_form.jsf"),
		  
		  @URLMapping(id = "centrocusto_form", pattern = "/centrocusto/#{acao}/#{parametro}", viewId = "/pages/centrocusto/centro_custo_form.jsf"),
		  @URLMapping(id = "centrocusto_list", pattern = "/centrocusto", viewId = "/pages/centrocusto/centro_custo_list.jsf"),
		  @URLMapping(id = "centrocusto_form_view", pattern = "/centrocusto/#{acao}", viewId = "/pages/centrocusto/centro_custo_form.jsf"),
		  
		  @URLMapping(id = "custofixo_form", pattern = "/custofixo/#{acao}/#{parametro}", viewId = "/pages/custofixo/custo_fixo_form.jsf"),
		  @URLMapping(id = "custofixo_list", pattern = "/custofixo", viewId = "/pages/custofixo/custo_fixo_list.jsf"),
		  @URLMapping(id = "custofixo_form_view", pattern = "/custofixo/#{acao}", viewId = "/pages/custofixo/custo_fixo_form.jsf"),

		  @URLMapping(id = "movimentacaofinanceira_form", pattern = "/movimentacaofinanceira/#{acao}/#{parametro}", viewId = "/pages/movimentacaofinanceira/movimentacao_financeira_form.jsf"),
		  @URLMapping(id = "movimentacaofinanceira_list", pattern = "/movimentacaofinanceira", viewId = "/pages/movimentacaofinanceira/movimentacao_financeira_list.jsf"),
		  @URLMapping(id = "movimentacaofinanceira_form_view", pattern = "/movimentacaofinanceira/#{acao}", viewId = "/pages/movimentacaofinanceira/movimentacao_financeira_form.jsf"),
		  
		  @URLMapping(id = "tag_form", pattern = "/tag/#{acao}/#{parametro}", viewId = "/pages/tag/tag_form.jsf"),
		  @URLMapping(id = "tag_list", pattern = "/tag", viewId = "/pages/tag/tag_list.jsf"),
		  @URLMapping(id = "tag_form_view", pattern = "/tag/#{acao}", viewId = "/pages/tag/tag_form.jsf"),
		  
		  @URLMapping(id = "remessa_list", pattern = "/remessa", viewId = "/pages/remessa/remessa_list.jsf"),
		  @URLMapping(id = "remessa_view", pattern = "/remessa/view/#{parametro}", viewId = "/pages/remessa/remessa_view.jsf"),

		  @URLMapping(id = "retorno_list", pattern = "/retorno", viewId = "/pages/retorno/retorno_list.jsf"),
		  @URLMapping(id = "retorno_view", pattern = "/retorno/view/#{parametro}", viewId = "/pages/retorno/retorno_view.jsf"),
		  
		  @URLMapping(id = "contacorrente_list", pattern = "/contacorrente", viewId = "/pages/contacorrente/contacorrente_list.jsf"),
		  @URLMapping(id = "contacorrente_form", pattern = "/contacorrente/#{acao}/#{parametro}", viewId = "/pages/contacorrente/contacorrente_form.jsf"),
		  
		  @URLMapping(id = "grupo_list", pattern = "/grupo", viewId = "/pages/grupo/grupo_list.jsf"),
		  @URLMapping(id = "grupo_form_view", pattern = "/grupo/#{acao}", viewId = "/pages/grupo/grupo_form.jsf"),
		  @URLMapping(id = "grupo_form", pattern = "/grupo/#{acao}/#{parametro}", viewId = "/pages/grupo/grupo_form.jsf"),
		  
		  @URLMapping(id = "thread", pattern = "/thread", viewId = "/pages/thread/thread.jsf"),
		  
		  
		  @URLMapping(id = "relatorio_contrato", pattern = "/relatorio/contrato", viewId = "/pages/relatorio/contrato/relatorio_contrato.jsf"),
		  @URLMapping(id = "relatorio_cliente_inadimplente", pattern = "/relatorio/cliente/inadimplente", viewId = "/pages/relatorio/cliente/inadimplente/relatorio_cliente_inadimplente.jsf"),
		  
		  
		})
public class ItmView {
	

}
