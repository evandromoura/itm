package br.com.trixti.itm.controller.remessa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.to.RemessaTO;
import br.com.trixti.itm.util.Base64Utils;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilString;


@Named
@ViewScoped
@Financeiro
public class RemessaController extends AbstractController<Remessa> implements Serializable {
	
	private static final long serialVersionUID = -5859579938447357382L;
	
	private @Inject RemessaService remessaService;
	private RemessaTO remessaTO;
	
	@PostConstruct
	private void init(){
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		
		if(acao != null && parametro != null){
			if(acao.equals("editar")){
				inicializarAlterar(Integer.valueOf(parametro));
			}
		}else if(acao != null && parametro == null){
				inicializarIncluir();
		}else{
			pesquisar();
		}
	}
	
	public void pesquisar(){
		getRemessaTO().setRemessas(
				remessaService.pesquisar(getRemessaTO().getRemessaPesquisa()));
	}
	
	public void gravar(){
		if(getRemessaTO().getRemessa().getId() == null){
			remessaService.incluir(getRemessaTO().getRemessa());
		}else{
			remessaService.alterar(getRemessaTO().getRemessa());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
	}
	
	private void inicializarAlterar(Integer id){
		getRemessaTO().setRemessa(remessaService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getRemessaTO().setRemessa(new Remessa());
	}
	
	public void download(Remessa remessa){
		File arquivo = null;
		try{
			UtilString utilString = new UtilString();
			Remessa remessaCompleta = remessaService.recuperarCompleto(remessa.getId());
			UtilArquivo utilArquivo = new UtilArquivo();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String nomeArquivo = "CB"+utilString.completaComZerosAEsquerda(remessaCompleta.getId().toString(), 6)+".rem";
			if(remessaCompleta.getArquivo() != null){
				arquivo = utilArquivo.getFileFromBytes(Base64Utils.base64Decode(remessaCompleta.getArquivo()), nomeArquivo);
				utilArquivo.convertFileToByteArrayOutputStream(arquivo,
						byteArrayOutputStream);
				download(byteArrayOutputStream, nomeArquivo);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(arquivo != null){
				arquivo.delete();
			}	
		}	
	}
	
	public void executarManutencaoRemessa(){
		remessaService.executarManutencao();
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Executado com Sucesso", "Executado com Sucesso"));
		pesquisar();
	}

	public RemessaTO getRemessaTO() {
		if (remessaTO == null) {
			remessaTO = new RemessaTO();
		}
		return remessaTO;
	}

	public void setRemessaTO(RemessaTO remessaTO) {
		this.remessaTO = remessaTO;
	}
	
	public void marcarComoEnviada(Remessa remessa){
		remessaService.enviarRemessa(remessa);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado com sucesso!", "O Registro foi incluido na base"));
		pesquisar();
	}
	
	public void excluir(Remessa remessa){
		try{
			remessaService.excluir(remessa);
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
			pesquisar();
		}catch(Exception e){
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), e.getMessage()));
		}	
	}
	
	public void excluir() {
		try {
			remessaService.excluir(getRemessaTO().getRemessa());
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso",
					"O Registro foi excluido na base"));
			pesquisar();
		} catch (Exception e) {
			getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), e.getMessage()));
		}
	}
}
