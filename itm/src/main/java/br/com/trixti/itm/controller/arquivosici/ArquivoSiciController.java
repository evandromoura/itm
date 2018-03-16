package br.com.trixti.itm.controller.arquivosici;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.ArquivoSici;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.arquivosici.ArquivoSiciService;
import br.com.trixti.itm.to.ArquivoSiciTO;
import br.com.trixti.itm.util.UtilArquivo;



@Named
@ViewScoped
@Admin
public class ArquivoSiciController extends AbstractController<ArquivoSici> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject ArquivoSiciService arquivoSiciService;
	private ArquivoSiciTO arquivoSiciTO;
	
	
	@PostConstruct
	private void init(){
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		if(acao != null && parametro != null){
			if(acao.equals("editar")){
				inicializarAlterar(Integer.valueOf(parametro));
			}
			if(acao.equals("novo")){
				inicializarIncluir();
			}	
			
		}else if(acao != null && parametro == null){
				inicializarIncluir();
		}else if(acao == null && parametro != null){
			inicializarAlterar(Integer.valueOf(parametro));
		}else{
			pesquisar();
		}
	}
	
	public void pesquisar(){
		getArquivoSiciTO().setArquivoSicis(arquivoSiciService.listar());
	}
	
	public void download(ArquivoSici arquivoSici) throws Exception {
		File arquivoBoleto = null;
		try {
				UtilArquivo utilArquivo = new UtilArquivo();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				arquivoBoleto = utilArquivo.getFileFromBytes(arquivoSici.getXml().getBytes(), arquivoSici.getNomeArquivo());
				utilArquivo.convertFileToByteArrayOutputStream(arquivoBoleto, byteArrayOutputStream);
				download(byteArrayOutputStream, arquivoBoleto.getName());
		} catch (Exception e) {
			getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}finally {
			if(arquivoBoleto != null){
				arquivoBoleto.delete();
			}		
		}	
	}	
	
	public String gravar(){
		if(getArquivoSiciTO().getArquivoSici().getId() == null){
			arquivoSiciService.incluir(getArquivoSiciTO().getArquivoSici().getMes(),getArquivoSiciTO().getArquivoSici().getAno());
		}else{
			arquivoSiciService.alterar(getArquivoSiciTO().getArquivoSici());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getArquivoSiciTO().setArquivoSici(arquivoSiciService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getArquivoSiciTO().setArquivoSici(new ArquivoSici());
	}
	

	public ArquivoSiciTO getArquivoSiciTO() {
		if (arquivoSiciTO == null) {
			arquivoSiciTO = new ArquivoSiciTO();
		}
		return arquivoSiciTO;
	}

	public void setArquivoSiciTO(ArquivoSiciTO arquivoSiciTO) {
		this.arquivoSiciTO = arquivoSiciTO;
	}
	
	
	
	public void excluir(ArquivoSici arquivoSici){
		arquivoSiciService.excluir(arquivoSici);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


