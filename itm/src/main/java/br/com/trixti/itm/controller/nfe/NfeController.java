package br.com.trixti.itm.controller.nfe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Nfe;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.nfe.NfeService;
import br.com.trixti.itm.to.NfeTO;
import br.com.trixti.itm.util.UtilArquivo;

@Named
@ViewScoped
@Admin
public class NfeController extends AbstractController<Nfe> implements Serializable {

	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject NfeService nfeService;
	private NfeTO nfeTO;

	@PostConstruct
	private void init() {
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		if (acao != null && parametro != null) {
			if (acao.equals("editar")) {
				inicializarAlterar(Integer.valueOf(parametro));
			}
			if (acao.equals("novo")) {
				inicializarIncluir();
			}

		} else if (acao != null && parametro == null) {
			inicializarIncluir();
		} else if (acao == null && parametro != null) {
			inicializarAlterar(Integer.valueOf(parametro));
		} else {
			pesquisar();
		}
	}

	public void pesquisar() {
		getNfeTO().setNfes(nfeService.listar());
	}

	public void download(Nfe nfe) throws Exception {
		File arquivoNfe = null;
		try {
			nfe = nfeService.recuperarCompleto(nfe.getId());
			UtilArquivo utilArquivo = new UtilArquivo();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			arquivoNfe = utilArquivo.getFileFromBytes(nfe.getArquivos().get(0).getConteudo().getBytes(), nfe.getNome());
			utilArquivo.convertFileToByteArrayOutputStream(arquivoNfe, byteArrayOutputStream);
			download(byteArrayOutputStream, arquivoNfe.getName());
		} catch (Exception e) {
			getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} finally {
			if (arquivoNfe != null) {
				arquivoNfe.delete();
			}
		}
	}

	public String gravar() {
		if (getNfeTO().getNfe().getId() == null) {
			nfeService.incluir(getNfeTO().getNfe().getMes(), getNfeTO().getNfe().getAno());
		} else {
			nfeService.alterar(getNfeTO().getNfe());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso",
				"O Registro foi incluido na base"));
		return "sucesso";
	}

	private void inicializarAlterar(Integer id) {
		getNfeTO().setNfe(nfeService.recuperarCompleto(id));
	}

	private void inicializarIncluir() {
		getNfeTO().setNfe(new Nfe());
	}

	public NfeTO getNfeTO() {
		if (nfeTO == null) {
			nfeTO = new NfeTO();
		}
		return nfeTO;
	}

	public void setNfeTO(NfeTO nfeTO) {
		this.nfeTO = nfeTO;
	}

	public void excluir(Nfe nfe) {
		nfeService.excluir(nfe);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso",
				"O Registro foi incluido na base"));
		pesquisar();
	}

}
