package br.com.trixti.itm.service.uploadarquivo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.entity.ServicoLocal;
import br.com.trixti.itm.entity.TecnologiaEnum;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.entity.TipoMovimentacaoFinanceira;
import br.com.trixti.itm.entity.TipoServico;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.enums.SiglaTemplateSiciEnum;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.jaxb.sici.Conteudo;
import br.com.trixti.itm.jaxb.sici.Indicador;
import br.com.trixti.itm.jaxb.sici.Municipio;
import br.com.trixti.itm.jaxb.sici.Outorga;
import br.com.trixti.itm.jaxb.sici.Pessoa;
import br.com.trixti.itm.jaxb.sici.Root;
import br.com.trixti.itm.jaxb.sici.Tecnologia;
import br.com.trixti.itm.jaxb.sici.UploadSICI;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.custofixo.CustoFixoService;
import br.com.trixti.itm.service.movimentacaofinanceira.MovimentacaoFinanceiraService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.servico.ServicoService;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilEnum;
import br.com.trixti.itm.util.UtilJaxb;

@Stateless
public class UploadArquivoService {
	
	private @Inject ServicoService servicoService;
	private @Inject ClienteService clienteService;
	private @Inject CustoFixoService custoFixoService;
	private @Inject MovimentacaoFinanceiraService movimentacaoFinanceiraService;
	private @Inject ValidadorTemplateSici validador;
	private @Inject ParametroService parametroService;

	public String gerarXml(Date data) {
		UtilJaxb utilJaxb = new UtilJaxb();
		Root root = new Root();
		root.setUploadSICI(comporUploadArquivoSici(data));
		return utilJaxb.marshal(root);

	}
	
	private UploadSICI comporUploadArquivoSici(Date data) {
		UtilData utilData = new UtilData();
		data = utilData.subtrairMeses(data, 1);
		UploadSICI uploadSICI = new UploadSICI();
		uploadSICI.setAno(utilData.getAno(data));
		uploadSICI.setMes(new Long(utilData.getMes(data)).intValue());
		uploadSICI.getOutorga().addAll(comporListaOutorga(data));
		return uploadSICI;

	}
	
	private List<Outorga> comporListaOutorga(Date data){
		List<Outorga> listaOutorga = new ArrayList<Outorga>();
		List<Servico> listaServico = servicoService.listar();
		
		for(Servico servico:listaServico){
			
			List<ServicoLocal> listaServicoLocal = servico.getLocais();
			List<Uf> listaUf = new ArrayList<Uf>();
			List<Cidade> listaCidade = new ArrayList<Cidade>();
			
			for(ServicoLocal servicoLocal:listaServicoLocal){
				if(!listaUf.contains(servicoLocal.getUf())){
					listaUf.add(servicoLocal.getUf());
				}
				if(!listaCidade.contains(servicoLocal.getCidade())){
					listaCidade.add(servicoLocal.getCidade());
				}
			}
			
			Outorga outorga = new Outorga();
			outorga.setFistel(servico.getFistel());
			
			outorga.getIndicador().addAll(comporListIndicadores(data,servico,listaUf,listaCidade));
			listaOutorga.add(outorga);
		}
		
		return listaOutorga;
	}

	private List<Indicador> comporListIndicadores(Date data,Servico servico,List<Uf> listaUf,List<Cidade> listaCidade) {
		List<Indicador> listaIndicadores = new ArrayList<Indicador>();
		UtilData utilData = new UtilData();
		String mes = utilData.getMesCorrente(data);
		
		if(servico.getTipo().equals(TipoServico._045)){
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM4)){
				listaIndicadores.add(comporIndicadorIEM4(listaUf));
			}
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM5)){
				listaIndicadores.add(comporIndicadorIEM5(listaUf));
			}
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM9)){
				listaIndicadores.add(comporIndicadorIEM9(listaUf));
			}
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM10)){
				listaIndicadores.add(comporIndicadorIEM10(listaUf));
			}
			if(validador.validar(mes, SiglaTemplateSiciEnum.IPL3)){
				listaIndicadores.add(comporIndicadorIPL3(listaCidade));
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.QAIPL4SM)){
				listaIndicadores.add(comporIndicadorQAIPL4SM(listaCidade));
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IPL6IM)){
				listaIndicadores.add(comporIndicadorIPL6IM(listaCidade));
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IAU1)){
				listaIndicadores.add(comporIndicadorIAU1());
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IPL1)){
				listaIndicadores.add(comporIndicadorIPL1());
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IPL2)){
				listaIndicadores.add(comporIndicadorIPL2());
			}
			
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM1)){
				listaIndicadores.add(comporIndicadorIEM1(data,validador.isAnual(mes)));
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM2)){
				listaIndicadores.add(comporIndicadorIEM2(data,validador.isAnual(mes)));
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM3)){
				listaIndicadores.add(comporIndicadorIEM3(data,validador.isAnual(mes)));
			}
			
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM6)){
				listaIndicadores.add(comporIndicadorIEM6());
			}
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM7)){
				listaIndicadores.add(comporIndicadorIEM7());
			}
			if(validador.validar(mes, SiglaTemplateSiciEnum.IEM8)){
				listaIndicadores.add(comporIndicadorIEM8());
			}
		}
		return listaIndicadores;
	}
	
	private Indicador comporIndicadorIEM1(Date data, boolean anual){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM1");
		Conteudo conteudo = new Conteudo();
		conteudo.setItem("a");
		BigDecimal valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual,TipoCentroCusto.INVESTIMENTO_PLANTA);
		BigDecimal valorCustoFixo = custoFixoService.somar(TipoCentroCusto.PUBLICIDADE);
		conteudo.setValor(valor.add(valorCustoFixo).toString());
		indicador.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setItem("g");
	    valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual, TipoCentroCusto.PUBLICIDADE);
	    valorCustoFixo = custoFixoService.somar(TipoCentroCusto.PUBLICIDADE);
	    conteudo.setValor(valor.add(valorCustoFixo).toString());
	    
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("c");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual, TipoCentroCusto.EQUIPAMENTOS);
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.EQUIPAMENTOS);
		conteudo.setValor(valor.add(valorCustoFixo).toString());
		
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("d");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual, TipoCentroCusto.SOFTWARE);
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.SOFTWARE);
		conteudo.setValor(valor.add(valorCustoFixo).toString());
		indicador.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setItem("h");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual, TipoCentroCusto.INVESTIMENTO_PD);
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.INVESTIMENTO_PD);
		conteudo.setValor(valor.add(valorCustoFixo).toString());
		indicador.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setItem("f");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual, TipoCentroCusto.SERVICOS);
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.SERVICOS);
		conteudo.setValor(valor.add(valorCustoFixo).toString());
		indicador.getConteudo().add(conteudo);

		conteudo = new Conteudo();
		conteudo.setItem("i");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO, data,anual, TipoCentroCusto.CALL_CENTER);
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.CALL_CENTER);
		conteudo.setValor(valor.add(valorCustoFixo).toString());
		indicador.getConteudo().add(conteudo);

		return indicador;
		
	}
	
	private Indicador comporIndicadorIEM2(Date data, boolean anual){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM2");
		Conteudo conteudo = new Conteudo();
		conteudo.setItem("a");
		
		BigDecimal valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.CREDITO, data,anual, TipoCentroCusto.SERVICOS);
		conteudo.setValor(valor != null?valor.toString():"0");
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("b");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.CREDITO, data,anual, TipoCentroCusto.EXPLORACAO_INDUSTRIAL);
		conteudo.setValor(valor != null?valor.toString():"0");
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("c");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.CREDITO, data,anual, TipoCentroCusto.SERVICOS_ADICIONAIS);
		conteudo.setValor(valor != null?valor.toString():"0");
		indicador.getConteudo().add(conteudo);
		
		return indicador;
	}
	
	private Indicador comporIndicadorIEM3(Date data,boolean anual){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM3");
		Conteudo conteudo = new Conteudo();
		conteudo.setItem("a");
		
		BigDecimal valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.CREDITO, data,anual, TipoCentroCusto.SERVICOS);
		conteudo.setValor(valor != null?valor.toString():"0");
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("b");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.CREDITO, data,anual, TipoCentroCusto.EXPLORACAO_INDUSTRIAL);
		conteudo.setValor(valor != null?valor.toString():"0");
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("c");
		valor = movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.CREDITO, data,anual, TipoCentroCusto.SERVICOS_ADICIONAIS);
		conteudo.setValor(valor != null?valor.toString():"0");
		indicador.getConteudo().add(conteudo);
		
		return indicador;
	}
	
	private Indicador comporIndicadorIEM4(List<Uf> listaUf){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM4");
		Parametro parametro = parametroService.recuperarParametro();
		for(Uf uf:listaUf){
			Conteudo conteudo = new Conteudo();
			conteudo.setItem("a");
			conteudo.setUf(uf.getSigla());
			conteudo.setValor(parametro.getQtdEmpregadoContratado().toString());
			indicador.getConteudo().add(conteudo);
		}	
		return indicador;
	}
	
	private Indicador comporIndicadorIEM5(List<Uf> listaUf){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM5");
		Parametro parametro = parametroService.recuperarParametro();
		for(Uf uf:listaUf){
			Conteudo conteudo = new Conteudo();
			conteudo.setItem("a");
			conteudo.setUf(uf.getSigla());
			conteudo.setValor(parametro.getQtdEmpregadoTerceirizado().toString());
			indicador.getConteudo().add(conteudo);
		}	
		return indicador;
	}
	
	
	private Indicador comporIndicadorIEM9(List<Uf> listaUf){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM9");
		comporIndicadorIEM9Pessoa(TipoPessoaEnum.FISICA, listaUf,indicador);
		comporIndicadorIEM9Pessoa(TipoPessoaEnum.JURIDICA, listaUf,indicador);
		return indicador;
	
	}
	
	
	
	private Indicador comporIndicadorIEM10(List<Uf> listaUf){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM10");
		comporIndicadorIEM10Pessoa(TipoPessoaEnum.FISICA, listaUf,indicador);
		comporIndicadorIEM10Pessoa(TipoPessoaEnum.JURIDICA, listaUf,indicador);
		return indicador;
		
	}

	private Indicador comporIndicadorIPL3(List<Cidade> listaCidade){
		Indicador indicador = new Indicador();
		indicador.setSigla("IPL3");
		
		comporIndicadorIPL3Pessoa(listaCidade,indicador);
		return indicador;
		
	}
	
	private Indicador comporIndicadorIAU1(){
		Parametro parametro = parametroService.recuperarParametro();
		Indicador indicador = new Indicador();
		indicador.setSigla("IAU1");
		Conteudo conteudo = new Conteudo();
		conteudo.setValor(parametro.getNumeroAtendimentoTelefonico());
		indicador.getConteudo().add(conteudo);
		return indicador;
	}
	
	private Indicador comporIndicadorIPL1(){
		Parametro parametro = parametroService.recuperarParametro();
		Indicador indicador = new Indicador();
		indicador.setSigla("IPL1");
		Conteudo conteudo = new Conteudo();
		conteudo.setItem("a");
		conteudo.setValor(parametro.getTotalFibraKM().toString());
		indicador.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setItem("b");
		conteudo.setValor(parametro.getTotalFibraTerceiroKM().toString());
		indicador.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setItem("c");
		conteudo.setValor(parametro.getTotalFibraPrevistaKM().toString());
		indicador.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setItem("d");
		conteudo.setValor(parametro.getTotalFibraTerceiroPrevistaKM().toString());
		indicador.getConteudo().add(conteudo);
		return indicador;
	}
	
	private Indicador comporIndicadorIPL2(){
		Parametro parametro = parametroService.recuperarParametro();
		Indicador indicador = new Indicador();
		indicador.setSigla("IPL2");
		Conteudo conteudo = new Conteudo();
		conteudo.setItem("a");
		conteudo.setValor(parametro.getTotalFibraImplantadaKM().toString());
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("b");
		conteudo.setValor(parametro.getTotalFibraImplantadaTerceiroKM().toString());
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("c");
		conteudo.setValor(parametro.getTotalFibraImplantadaPrevistaKM().toString());
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("d");
		conteudo.setValor(parametro.getTotalFibraImplantadaTerceiroPrevistaKM().toString());
		indicador.getConteudo().add(conteudo);
		return indicador;
	}
	
	
	private Indicador comporIndicadorIPL6IM(List<Cidade> listaCidade){
		Indicador indicador = new Indicador();
		indicador.setSigla("IPL6IM");
		Conteudo conteudo;
		for(Cidade cidade:listaCidade){
			conteudo = new Conteudo();
			conteudo.setItem("a");
			conteudo.setCodmunicipio(cidade.getId().toString());
			conteudo.setValor(clienteService.somarTodosDownloadContratos().toString());
			indicador.getConteudo().add(conteudo);
		}
		return indicador;
		
	}
	private Indicador comporIndicadorQAIPL4SM(List<Cidade> listaCidade){
		Indicador indicador = new Indicador();
		indicador.setSigla("QAIPL4SM");
		comporIndicadorQAIPL4SMTecnologia(listaCidade, indicador);
		return indicador;
		
	}
	private Indicador comporIndicadorIEM6(){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM6");
		Conteudo conteudo = new Conteudo();
		BigDecimal total = clienteService.somarTodosContratos();
		conteudo.setItem("a");
		conteudo.setValor(total.toString().replace(".", ","));
		indicador.getConteudo().add(conteudo);
		return indicador;
	}
	
	private Indicador comporIndicadorIEM7(){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM7");
		Conteudo conteudo = new Conteudo();
		BigDecimal total = clienteService.somarTodosContratos();
		total = total.subtract(total.multiply(BigDecimal.valueOf(0.10)));
		conteudo.setItem("a");
		conteudo.setValor(total.toString().replace(".", ","));
		indicador.getConteudo().add(conteudo);
		return indicador;
	}
	
	private Indicador comporIndicadorIEM8(){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM8");
		BigDecimal totalCustoFixo = custoFixoService.somarTodos();
		
		Conteudo conteudo = new Conteudo();
		conteudo.setItem("a");
		if(totalCustoFixo != null){
			conteudo.setValor(totalCustoFixo.toString().replace(".", ","));
		}else{
			conteudo.setValor("0,00");
		}	
		indicador.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setItem("b");
		BigDecimal totalCustoOperacao = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,new Date(),false,TipoCentroCusto.OPERACAO_MANUTENCAO));
		BigDecimal valorCustoFixo = custoFixoService.somar(TipoCentroCusto.SERVICOS);
		BigDecimal totalSoma = totalCustoOperacao.add(valorCustoFixo);
		
		if(!totalSoma.equals(BigDecimal.ZERO)){
			conteudo.setValor(totalSoma.toString().replace(".", ","));
		}else{
			conteudo.setValor("0,00");
		}	
			
		indicador.getConteudo().add(conteudo);
		
		
		
		BigDecimal totalCustoPublicidade = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,new Date(),false,TipoCentroCusto.PUBLICIDADE));
		conteudo = new Conteudo();
		conteudo.setItem("c");
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.PUBLICIDADE);
		totalSoma = totalCustoPublicidade.add(valorCustoFixo);
		if(!totalSoma.equals(BigDecimal.ZERO)){
			conteudo.setValor(totalSoma.toString().replace(".", ","));
		}else{
			conteudo.setValor("0,00");
		}
		
		
		indicador.getConteudo().add(conteudo);
		
		
		BigDecimal totalCustoVendas = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,new Date(),false,TipoCentroCusto.VENDAS));
		conteudo = new Conteudo();
		conteudo.setItem("d");
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.VENDAS);
		totalSoma = totalCustoVendas.add(valorCustoFixo);
		if(!totalSoma.equals(BigDecimal.ZERO)){
			conteudo.setValor(totalSoma.toString().replace(".", ","));
		}else{
			conteudo.setValor("0,00");
		}	
		indicador.getConteudo().add(conteudo);
		
		
		BigDecimal totalCustoInterconexao = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,new Date(),false,TipoCentroCusto.INTERCONEXAO));
		conteudo = new Conteudo();
		conteudo.setItem("e");
		valorCustoFixo = custoFixoService.somar(TipoCentroCusto.INTERCONEXAO);
		totalSoma = totalCustoInterconexao.add(valorCustoFixo);
		if(!totalSoma.equals(BigDecimal.ZERO)){
			conteudo.setValor(totalSoma.toString().replace(".", ","));
		}else{
			conteudo.setValor("0,00");
		}
		indicador.getConteudo().add(conteudo);
		return indicador;
	}

	private void comporIndicadorIEM9Pessoa(TipoPessoaEnum tipoPessoa, List<Uf> listaUf,Indicador indicador) {
		Pessoa pessoa = new Pessoa();
		pessoa.setItem(tipoPessoa.getSigla());
		Conteudo conteudoPessoa;
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("a");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal soma = clienteService.somaClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 0, 512);
			Integer qtd =  clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 0, 512);
			if(!soma.equals(BigDecimal.ZERO) && !qtd.equals(BigDecimal.ZERO)){
				BigDecimal valor = soma.divide(BigDecimal.valueOf(qtd),RoundingMode.HALF_UP);
				conteudoPessoa.setValor(valor.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			} 
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("b");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal soma = clienteService.somaClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 512, 2048);
			Integer qtd =  clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 512, 2048);
			if(!soma.equals(BigDecimal.ZERO) && !qtd.equals(BigDecimal.ZERO)){
				BigDecimal valor = soma.divide(BigDecimal.valueOf(qtd),RoundingMode.HALF_UP);
				conteudoPessoa.setValor(valor.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			} 
			
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("c");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal soma = clienteService.somaClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 2048, 12288);
			Integer qtd =  clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 2048, 12288);
			if(!soma.equals(BigDecimal.ZERO) && !qtd.equals(BigDecimal.ZERO)){
				BigDecimal valor = soma.divide(BigDecimal.valueOf(qtd),RoundingMode.HALF_UP);
				conteudoPessoa.setValor(valor.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("d");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal soma = clienteService.somaClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 12288, 34816);
			Integer qtd =  clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 12288, 34816);
			if(!soma.equals(BigDecimal.ZERO) && !qtd.equals(BigDecimal.ZERO)){
				BigDecimal valor = soma.divide(BigDecimal.valueOf(qtd),RoundingMode.HALF_UP);
				conteudoPessoa.setValor(valor.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("e");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal soma = clienteService.somaClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 34816, 999999999);
			Integer qtd =  clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 34816, 999999999);
			if(!soma.equals(BigDecimal.ZERO) && !qtd.equals(BigDecimal.ZERO)){
				BigDecimal valor = soma.divide(BigDecimal.valueOf(qtd),RoundingMode.HALF_UP);
				conteudoPessoa.setValor(valor.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		indicador.getPessoa().add(pessoa);
	}
	
	private void comporIndicadorIEM10Pessoa(TipoPessoaEnum tipoPessoa, List<Uf> listaUf,Indicador indicador) {
		Pessoa pessoa = new Pessoa();
		pessoa.setItem(tipoPessoa.getSigla());
		Conteudo conteudoPessoa;
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("a");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.menorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,false);
			if(valor != null){
				BigDecimal valorr = valor.divide(BigDecimal.valueOf(10));
				conteudoPessoa.setValor(valorr.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("b");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.menorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,true);
			if(valor != null){
				BigDecimal valorr  = valor.divide(BigDecimal.valueOf(10));
				conteudoPessoa.setValor(valorr.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}	
			pessoa.getConteudo().add(conteudoPessoa);
		}
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("c");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.maiorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,false);
			if(valor != null){
				BigDecimal valorr = valor.divide(BigDecimal.valueOf(10));
				conteudoPessoa.setValor(valorr.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("d");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.maiorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,true);
			if(valor != null){
				BigDecimal valorr = valor.divide(BigDecimal.valueOf(10));
				conteudoPessoa.setValor(valorr.toString().replace(".", ","));
			}else{
				conteudoPessoa.setValor("0,00");
			}	
			pessoa.getConteudo().add(conteudoPessoa);
		}
		indicador.getPessoa().add(pessoa);
	}
	
	
	private void comporIndicadorIPL3Pessoa(List<Cidade> listaCidade,Indicador indicador) {
		List<Municipio> listaMunicipio = new ArrayList<Municipio>();
		for(Cidade cidade:listaCidade){
			Municipio municipio = new Municipio();
			municipio.setCodmunicipio(cidade.getId().toString());
			Pessoa pessoa = new Pessoa();
			Conteudo conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("a");
			conteudoPessoa.setValor(clienteService.qtdClienteTipoPessoa(TipoPessoaEnum.FISICA).toString());
			pessoa.setItem(TipoPessoaEnum.FISICA.getSigla());
			pessoa.getConteudo().add(conteudoPessoa);
			
			municipio.getPessoa().add(pessoa);
			
			Pessoa pessoaJ = new Pessoa();
			Conteudo conteudoPessoaJ = new Conteudo();
			conteudoPessoaJ.setItem("a");
			conteudoPessoaJ.setValor(clienteService.qtdClienteTipoPessoa(TipoPessoaEnum.JURIDICA).toString());
			pessoaJ.setItem(TipoPessoaEnum.JURIDICA.getSigla());
			pessoaJ.getConteudo().add(conteudoPessoaJ);
			
			municipio.getPessoa().add(pessoaJ);
			
			
			listaMunicipio.add(municipio);
		}
		indicador.getMunicipio().addAll(listaMunicipio);
	}	
	
	
	private void comporIndicadorQAIPL4SMTecnologia(List<Cidade> listaCidade,Indicador indicador) {
		List<Municipio> listaMunicipio = new ArrayList<Municipio>();
		for(Cidade cidade:listaCidade){
			Municipio municipio = new Municipio();
			municipio.setCodmunicipio(cidade.getId().toString());
			
			String[] items = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
			for(String item:items){
				Tecnologia tecnologia = comporTecnologia(item);
				municipio.getTecnologia().add(tecnologia);
			}
			listaMunicipio.add(municipio);
		}
		indicador.getMunicipio().addAll(listaMunicipio);
	}

	private Tecnologia comporTecnologia(String itemm) {
		UtilEnum<TecnologiaEnum> utilEnum = new UtilEnum<TecnologiaEnum>();
		
		TecnologiaEnum tecnologiaEnum = utilEnum.obterValorEnum(TecnologiaEnum.values(), "item", itemm);
		
		Tecnologia tecnologia = new Tecnologia();
		tecnologia.setItem(itemm);
		Conteudo conteudo = new Conteudo();
		conteudo.setNome("QAIPL5SM");
		Integer totalDownload = clienteService.qtdDownloadPorTecnologia(tecnologiaEnum);
		if(totalDownload != null && totalDownload > 0){
			conteudo.setValor(Integer.valueOf(totalDownload/1024).toString());
		}else{
			conteudo.setValor("0");
		}
		tecnologia.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setNome("total");
		conteudo.setValor(clienteService.qtdClientePorTecnologia(tecnologiaEnum).toString());
		tecnologia.getConteudo().add(conteudo);
		
		conteudo = new Conteudo();
		conteudo.setFaixa(15);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,0,512).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(16);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,512,2048).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(17);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,2048,12288).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(18);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,12288,34816).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(19);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,34816,999999999).toString());
		tecnologia.getConteudo().add(conteudo);
		return tecnologia;
	}	

}
