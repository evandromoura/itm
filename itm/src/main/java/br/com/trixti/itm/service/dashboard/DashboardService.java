package br.com.trixti.itm.service.dashboard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.tag.TagService;
import br.com.trixti.itm.to.DashboardWSTO;
import br.com.trixti.itm.to.PeriodoTO;
import br.com.trixti.itm.to.TagContratoWSTO;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class DashboardService {

	private @Inject ClienteService clienteService;
	private @Inject ContratoService contratoService;
	private @Inject TagService tagService;
	private @Inject ContratoProdutoService contratoProdutoService;
	
	
	public DashboardWSTO dashboard(){
		DashboardWSTO dashboardWSTO = new DashboardWSTO();
		dashboardWSTO.setQtdCliente(clienteService.qtdClienteAtivo());
		dashboardWSTO.setQtdContrato(contratoService.qtdContratoAtivo());
		dashboardWSTO.setTotalFixoMensal(contratoService.valorTotalFixo());
		dashboardWSTO.setTotalAtrasado(contratoService.valorTotalAtrasado());
		comporDashboardNovosContratos(dashboardWSTO);
		comporDashboardTags(dashboardWSTO);
		return dashboardWSTO;
	}
	
	private void comporDashboardNovosContratos(DashboardWSTO dashboardWSTO){
		UtilData ud = new UtilData();
		Date hoje = new Date();
		for(int i=0;i<=12;i++){
			Date dataAjustada = ud.subtrairMeses(hoje, i);
			Date primeiro = ud.ajustarPrimeiroDiaMes(dataAjustada);
			Date ultimo = ud.ajustarUltimoDiaMes(dataAjustada);
			dashboardWSTO.getNovosContratos().getMapaContratosNovos().put(ud.getAnoCorrente(primeiro)+" - "+ ud.obterNomeMes(Integer.valueOf(ud.getMesCorrente(primeiro))),
					contratoService.qtdContratoCriadoPeriodoPagantes(new PeriodoTO(primeiro,ultimo)));
			
			dashboardWSTO.getNovosContratos().getMapaContratosCancelados().put(ud.getAnoCorrente(primeiro)+" - "+ ud.obterNomeMes(Integer.valueOf(ud.getMesCorrente(primeiro))),
					contratoService.qtdContratoCanceladoPeriodoPagantes(new PeriodoTO(primeiro,ultimo)));
		}
	}
	
	private void comporDashboardTags(DashboardWSTO dashboardWSTO){
		BigDecimal valorDosValores = BigDecimal.ZERO;
		List<Tag> tags = tagService.listar();
		BigDecimal valorTotal = BigDecimal.ZERO;
		Integer qtdPagantes = 0;
		Integer qtdNaoPagantes = 0;
		for(Tag tag:tags){
			valorTotal = BigDecimal.ZERO;
			qtdPagantes = 0;
			qtdNaoPagantes = 0;
			List<ContratoProduto> listaContratoProduto = contratoProdutoService.pesquisarPorTag(tag);
			for(ContratoProduto contratoProduto:listaContratoProduto){
				if(contratoProduto.getValor() == null || contratoProduto.getValor().compareTo(BigDecimal.ZERO)==0){
					qtdNaoPagantes++;
				}else{
					valorDosValores= valorDosValores.add(contratoProduto.getValor().multiply(new BigDecimal(contratoProduto.getQtd())));
					valorTotal = valorTotal.add(contratoProduto.getValor());
					qtdPagantes++;
				}
			}
			dashboardWSTO.getListaTags().add(new TagContratoWSTO(tag.getNome(),qtdPagantes, qtdNaoPagantes, valorTotal));
		}
		valorTotal = BigDecimal.ZERO;
		qtdPagantes = 0;
		qtdNaoPagantes = 0;
		List<ContratoProduto> listaContratoProdutoSemTag = contratoProdutoService.listarSemTag();
		for(ContratoProduto contratoProduto:listaContratoProdutoSemTag){
			if(contratoProduto.getValor() == null || contratoProduto.getValor().compareTo(BigDecimal.ZERO)==0){
				qtdNaoPagantes++;
			}else{
				valorTotal = valorTotal.add(contratoProduto.getValor());
				qtdPagantes++;
				valorDosValores= valorDosValores.add(contratoProduto.getValor());
			}
		}
		
		dashboardWSTO.getListaTags().add(new TagContratoWSTO("Sem TAG",qtdPagantes, qtdNaoPagantes, valorTotal));
		System.out.println("VALOR DOS VALORES = "+valorDosValores);
	}
}
