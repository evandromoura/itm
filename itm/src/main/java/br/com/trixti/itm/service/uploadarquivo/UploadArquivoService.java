package br.com.trixti.itm.service.uploadarquivo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.entity.ArquivoSici;
import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.entity.TipoServico;
import br.com.trixti.itm.jaxb.sici.Conteudo;
import br.com.trixti.itm.jaxb.sici.Indicador;
import br.com.trixti.itm.jaxb.sici.Outorga;
import br.com.trixti.itm.jaxb.sici.Pessoa;
import br.com.trixti.itm.jaxb.sici.UploadSICI;
import br.com.trixti.itm.service.servico.ServicoService;
import br.com.trixti.itm.util.UtilJaxb;

@Stateless
public class UploadArquivoService {
	
	private @Inject ServicoService servicoService;

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
			Outorga outorga = new Outorga();
			outorga.setFistel(servico.getFistel());
			outorga.getIndicador().addAll(comporListIndicadores(servico));
			listaOutorga.add(outorga);
		}
		
		return listaOutorga;
	}

	private List<Indicador> comporListIndicadores(Servico servico) {
		List<Indicador> listaIndicadores = new ArrayList<Indicador>();
		if(servico.getTipo().equals(TipoServico._045)){
			listaIndicadores.add(comporIndicadorIEM9());
		}
		return listaIndicadores;
	}
	private Indicador comporIndicadorIEM9(){
		Indicador indicador = new Indicador();
		indicador.setSigla("IEM9");
		Pessoa pessoaFisica = new Pessoa();
		pessoaFisica.setItem("F");
		
		
		
		Conteudo conteudoPessoaFisica = new Conteudo();
		conteudoPessoaFisica.setItem("a");
		conteudoPessoaFisica.setUf("DF");
		conteudoPessoaFisica.setValor("452,00");
		pessoaFisica.getConteudo().add(conteudoPessoaFisica);
		
		
		
		
		
		conteudoPessoaFisica = new Conteudo();
		conteudoPessoaFisica.setItem("b");
		conteudoPessoaFisica.setUf("DF");
		conteudoPessoaFisica.setValor("452,00");
		pessoaFisica.getConteudo().add(conteudoPessoaFisica);
		
		conteudoPessoaFisica = new Conteudo();
		conteudoPessoaFisica.setItem("c");
		conteudoPessoaFisica.setUf("DF");
		conteudoPessoaFisica.setValor("452,00");
		pessoaFisica.getConteudo().add(conteudoPessoaFisica);
		
		conteudoPessoaFisica = new Conteudo();
		conteudoPessoaFisica.setItem("d");
		conteudoPessoaFisica.setUf("DF");
		conteudoPessoaFisica.setValor("452,00");
		pessoaFisica.getConteudo().add(conteudoPessoaFisica);
		
		conteudoPessoaFisica = new Conteudo();
		conteudoPessoaFisica.setItem("e");
		conteudoPessoaFisica.setUf("DF");
		conteudoPessoaFisica.setValor("452,00");
		pessoaFisica.getConteudo().add(conteudoPessoaFisica);
		indicador.getPessoa().add(pessoaFisica);
		
		
		Pessoa pessoaJuridica = new Pessoa();
		pessoaJuridica.setItem("J");
		indicador.getPessoa().add(pessoaJuridica);
		
		
		return indicador;
	
	}

}
