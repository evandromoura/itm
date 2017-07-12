package br.com.trixti.itm.controller.contrato;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoGrupo;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratoautenticacao.ContratoAutenticacaoService;
import br.com.trixti.itm.service.contratogrupo.ContratoGrupoService;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.service.grupo.GrupoService;
import br.com.trixti.itm.service.produto.ProdutoService;
import br.com.trixti.itm.to.ContratoTO;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilRandomString;

@ViewScoped
@ManagedBean
public class ContratoController extends AbstractController<Contrato> {
	
	
	private @Inject ContratoService contratoService;
	private @Inject ProdutoService produtoService;
	private @Inject EquipamentoService equipamentoService;
	private @Inject GrupoService grupoService;
	private @Inject ContratoAutenticacaoService contratoAutenticacaoService;
	private @Inject CustomIdentity customIdentity;
	private @Inject ContratoGrupoService contratoGrupoService;
	
	private ContratoTO contratoTO;
	
	
	@PostConstruct
	private void init(){
		String id = getRequest().getParameter("id");
		getContratoTO().setContrato(contratoService.recuperarCompleto(Integer.valueOf(id)));
		getContratoTO().getContratoAutenticacao().setSenha(new UtilRandomString(6).nextString());
	}
	
	
	public void adicionarProduto(){
		ContratoProduto contratoProduto = new ContratoProduto();
		contratoProduto.setContrato(getContratoTO().getContrato());
		Produto produto = produtoService.recuperar(getContratoTO().getProduto().getId());
		contratoProduto.setValor(produto.getValor());
		contratoProduto.setValorBase(produto.getValor());
		contratoProduto.setProduto(produto);
		
		Date dataLocal = new Date();
		contratoProduto.setDataCriacao(dataLocal);
		contratoProduto.setDataInicio(dataLocal);
		UtilData utilData = new UtilData();
		contratoProduto.setDataFim(utilData.adicionarAnos(dataLocal, 1));
		getContratoTO().getContrato().getContratoProdutos().add(contratoProduto);
	}
	
	public void removerProduto(ContratoProduto contratoProduto){
		getContratoTO().getContrato().getContratoProdutos().remove(contratoProduto);
	}
	
	
	public void adicionarEquipamento(){
		ContratoEquipamento contratoEquipamento = new ContratoEquipamento();
		contratoEquipamento.setContrato(getContratoTO().getContrato());
		Equipamento equipamento = equipamentoService.recuperar(getContratoTO().getEquipamento().getId());
		Date dataLocal = new Date();
		contratoEquipamento.setDataCriacao(dataLocal);
		contratoEquipamento.setEquipamento(equipamento);
		getContratoTO().getContrato().getContratoEquipamentos().add(contratoEquipamento);
	}
	
	public void removerEquipamento(ContratoEquipamento contratoEquipamento){
		getContratoTO().getContrato().getContratoEquipamentos().remove(contratoEquipamento);
	}
	
	
	public void adicionarGrupo(){
		ContratoGrupo contratoGrupo = new ContratoGrupo();
		contratoGrupo.setContrato(getContratoTO().getContrato()); 
		Grupo grupo = grupoService.recuperar(getContratoTO().getGrupo().getId());
		Date dataLocal = new Date();
		contratoGrupo.setDataCriacao(dataLocal);
		contratoGrupo.setGrupo(grupo);
		getContratoTO().getContrato().getContratoGrupos().add(contratoGrupo);
	}
	
	public void adicionarContratoAutenticacao(){
		if(getContratoTO().getContrato().getAutenticacoes() == null || 
				getContratoTO().getContrato().getAutenticacoes().isEmpty() || 
					getContratoTO().getContrato().getAutenticacoes().size() == 0){
			getContratoTO().getContratoAutenticacao().setContrato(getContratoTO().getContrato());
			getContratoTO().getContratoAutenticacao().setDataCriacao(new Date());
			getContratoTO().getContrato().getAutenticacoes().add(getContratoTO().getContratoAutenticacao());
			getContratoTO().setContratoAutenticacao(null);
		}	
	}
	
	public void removerGrupo(ContratoGrupo contratoGrupo){
		getContratoTO().getContrato().getContratoGrupos().remove(contratoGrupo);
//		contratoGrupoService.excluir(contratoGrupo);
	}
	
	public void removerAutenticacao(ContratoAutenticacao contratoAutenticacao){
		contratoAutenticacaoService.excluir(contratoAutenticacao);
		init();
	}
	
	public void ativarDesativarContratoProduto(ContratoProduto contratoProduto){
		if(contratoProduto.getDataExclusao() != null){
			contratoProduto.setDataExclusao(null);
		}else{
			contratoProduto.setDataExclusao(new Date());
		}	
	}
	
	public String gravar(){
		getContratoTO().getContrato().setUsuarioAlteracao(customIdentity.getUsuario());
		if(getContratoTO().getContrato().getId() == null){
			 contratoService.incluir(getContratoTO().getContrato());
			 String mensagem = getMessage("label.global.cadastrosucesso");
			 getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			 getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
			 return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
		}else{
			contratoService.alterar(getContratoTO().getContrato());
			String mensagem = getMessage("label.global.alterarsucesso");
			 getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
			return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
		}
	}
	
	public String cancelar(){
		getContratoTO().setContrato(null);
		return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
	}


	public ContratoTO getContratoTO() {
		if (contratoTO == null) {
			contratoTO = new ContratoTO();
		}
		return contratoTO;
	}


	public void setContratoTO(ContratoTO contratoTO) {
		this.contratoTO = contratoTO;
	}
	
}