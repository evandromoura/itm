package br.com.trixti.itm.service.uploadarquivo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.entity.ArquivoSici;
import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.entity.ServicoLocal;
import br.com.trixti.itm.entity.TecnologiaEnum;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.entity.TipoMovimentacaoFinanceira;
import br.com.trixti.itm.entity.TipoServico;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.jaxb.sici.Conteudo;
import br.com.trixti.itm.jaxb.sici.Indicador;
import br.com.trixti.itm.jaxb.sici.Municipio;
import br.com.trixti.itm.jaxb.sici.Outorga;
import br.com.trixti.itm.jaxb.sici.Pessoa;
import br.com.trixti.itm.jaxb.sici.Tecnologia;
import br.com.trixti.itm.jaxb.sici.UploadSICI;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.custofixo.CustoFixoService;
import br.com.trixti.itm.service.movimentacaofinanceira.MovimentacaoFinanceiraService;
import br.com.trixti.itm.service.servico.ServicoService;
import br.com.trixti.itm.util.UtilEnum;
import br.com.trixti.itm.util.UtilJaxb;

@Stateless
public class UploadArquivoService {
	
	private @Inject ServicoService servicoService;
	private @Inject ClienteService clienteService;
	private @Inject CustoFixoService custoFixoService;
	private @Inject MovimentacaoFinanceiraService movimentacaoFinanceiraService;

	public String gerarXml(ArquivoSici arquivoSici) {
		UtilJaxb utilJaxb = new UtilJaxb();
		return utilJaxb.marshal(comporUploadArquivoSici());

	}

	private UploadSICI comporUploadArquivoSici() {
		UploadSICI uploadSICI = new UploadSICI();
		uploadSICI.setAno(2018);
		uploadSICI.setMes(3);
		uploadSICI.getOutorga().addAll(comporListaOutorga());
		return uploadSICI;

	}
	
	private List<Outorga> comporListaOutorga(){
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
			
			outorga.getIndicador().addAll(comporListIndicadores(servico,listaUf,listaCidade));
			listaOutorga.add(outorga);
		}
		
		return listaOutorga;
	}

	private List<Indicador> comporListIndicadores(Servico servico,List<Uf> listaUf,List<Cidade> listaCidade) {
		List<Indicador> listaIndicadores = new ArrayList<Indicador>();
		if(servico.getTipo().equals(TipoServico._045)){
			listaIndicadores.add(comporIndicadorIEM9(servico,listaUf));
			listaIndicadores.add(comporIndicadorIEM10(servico,listaUf));
			listaIndicadores.add(comporIndicadorIPL3(servico,listaCidade));
			listaIndicadores.add(comporIndicadorIEM6());
			listaIndicadores.add(comporIndicadorIEM7());
			listaIndicadores.add(comporIndicadorIEM8());
		}
		return listaIndicadores;
	}
	
	
	private Indicador comporIndicadorIEM9(Servico servico,List<Uf> listaUf){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM9");
		comporIndicadorIEM9Pessoa(TipoPessoaEnum.FISICA, listaUf,indicador);
		comporIndicadorIEM9Pessoa(TipoPessoaEnum.JURIDICA, listaUf,indicador);
		return indicador;
	
	}
	
	
	private Indicador comporIndicadorIEM10(Servico servico,List<Uf> listaUf){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM10");
		comporIndicadorIEM10Pessoa(TipoPessoaEnum.FISICA, listaUf,indicador);
		comporIndicadorIEM10Pessoa(TipoPessoaEnum.JURIDICA, listaUf,indicador);
		return indicador;
		
	}

	private Indicador comporIndicadorIPL3(Servico servico,List<Cidade> listaCidade){
		Indicador indicador = new Indicador();
		indicador.setSigla("IPL3");
		comporIndicadorIPL3Pessoa(listaCidade,indicador);
		return indicador;
		
	}
	private Indicador comporIndicadorQAIPL4SM(Servico servico,List<Cidade> listaCidade){
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
		conteudo.setValor(total.toString());
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
		conteudo.setValor(total.toString());
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
			conteudo.setValor(totalCustoFixo.toString());
		}else{
			conteudo.setValor("0");
		}	
		indicador.getConteudo().add(conteudo);
		
		
		BigDecimal totalCustoOperacao = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,TipoCentroCusto.OPERACAO_MANUTENCAO,new Date()));
		conteudo = new Conteudo();
		conteudo.setItem("b");
		if(totalCustoOperacao != null){
			conteudo.setValor(totalCustoOperacao.toString());
		}else{
			conteudo.setValor("0");
		}	
		indicador.getConteudo().add(conteudo);
		
		
		
		BigDecimal totalCustoPublicidade = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,TipoCentroCusto.PUBLICIDADE,new Date()));
		conteudo = new Conteudo();
		conteudo.setItem("c");
		if(totalCustoPublicidade != null){
			conteudo.setValor(totalCustoPublicidade.toString());
		}else{
			conteudo.setValor("0");
		}	
		indicador.getConteudo().add(conteudo);
		
		
		BigDecimal totalCustoVendas = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,TipoCentroCusto.VENDAS,new Date()));
		conteudo = new Conteudo();
		conteudo.setItem("d");
		if(totalCustoVendas != null){
			conteudo.setValor(totalCustoVendas.toString());
		}else{
			conteudo.setValor("0");
		}	
		indicador.getConteudo().add(conteudo);
		
		
		BigDecimal totalCustoInterconexao = (movimentacaoFinanceiraService.somar(TipoMovimentacaoFinanceira.DEBITO,TipoCentroCusto.INTERCONEXAO,new Date()));
		conteudo = new Conteudo();
		conteudo.setItem("e");
		if(totalCustoInterconexao != null){
			conteudo.setValor(totalCustoInterconexao.toString());
		}else{
			conteudo.setValor("0");
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
			conteudoPessoa.setValor(clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 0, 512).toString());
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("b");
			conteudoPessoa.setUf(uf.getSigla());
			conteudoPessoa.setValor(clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 513, 2048).toString());
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("c");
			conteudoPessoa.setUf(uf.getSigla());
			conteudoPessoa.setValor(clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 2049, 12288).toString());
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("d");
			conteudoPessoa.setUf(uf.getSigla());
			conteudoPessoa.setValor(clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 12289, 34816).toString());
			pessoa.getConteudo().add(conteudoPessoa);
		}
		
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("e");
			conteudoPessoa.setUf(uf.getSigla());
			conteudoPessoa.setValor(clienteService.qtdClienteUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 34817, 999999999).toString());
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
				conteudoPessoa.setValor(valor.divide(BigDecimal.valueOf(10)).toString());
			}else{
				conteudoPessoa.setValor("0");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("b");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.menorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,true);
			if(valor != null){
				conteudoPessoa.setValor(valor.divide(BigDecimal.valueOf(10)).toString());
			}else{
				conteudoPessoa.setValor("0");
			}	
			pessoa.getConteudo().add(conteudoPessoa);
		}
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("c");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.maiorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,false);
			if(valor != null){
				conteudoPessoa.setValor(valor.divide(BigDecimal.valueOf(10)).toString());
			}else{
				conteudoPessoa.setValor("0");
			}
			pessoa.getConteudo().add(conteudoPessoa);
		}
		for(Uf uf:listaUf){
			conteudoPessoa = new Conteudo();
			conteudoPessoa.setItem("d");
			conteudoPessoa.setUf(uf.getSigla());
			BigDecimal valor = clienteService.maiorPrecoUfTipoPessoaIntervaloDownload(uf, tipoPessoa, 10000, 11000,true);
			if(valor != null){
				conteudoPessoa.setValor(valor.divide(BigDecimal.valueOf(10)).toString());
			}else{
				conteudoPessoa.setValor("0");
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
			pessoa.getConteudo().add(conteudoPessoa);
			
			municipio.getPessoa().add(pessoa);
			
			Pessoa pessoaJ = new Pessoa();
			Conteudo conteudoPessoaJ = new Conteudo();
			conteudoPessoaJ.setItem("b");
			conteudoPessoaJ.setValor(clienteService.qtdClienteTipoPessoa(TipoPessoaEnum.JURIDICA).toString());
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
		conteudo.setValor(tecnologiaEnum.getDescricao());
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
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,513,2048).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(17);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,2049,12288).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(18);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,12289,34816).toString());
		tecnologia.getConteudo().add(conteudo);
		
		
		conteudo = new Conteudo();
		conteudo.setFaixa(19);
		conteudo.setValor(clienteService.qtdClientePorTecnologiaIntervaloDownload(tecnologiaEnum,34816,999999999).toString());
		tecnologia.getConteudo().add(conteudo);
		return tecnologia;
	}	

}
