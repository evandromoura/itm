package br.com.trixti.itm.to;

import java.util.Date;

import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.util.UtilData;

public class AutenticacaoWSTO implements java.io.Serializable {

	private static final long serialVersionUID = 5309792107580965495L;

	private Integer id;
	private String username;
	private String senha;
	private String ip;
	private String grupo;
	private Date dataCriacao;
	
	public AutenticacaoWSTO(ContratoAutenticacao contratoAutenticacao){
		setId(contratoAutenticacao.getId());
		setUsername(contratoAutenticacao.getUsername());
		setSenha(contratoAutenticacao.getSenha());
		setIp(contratoAutenticacao.getIp());
		setGrupo(contratoAutenticacao.getGrupo());
		setDataCriacao(contratoAutenticacao.getDataCriacao());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		String retorno = "";
		if(username != null){
			for(int i=0;i<username.length();i++){
				retorno += username.toCharArray()[i];
				if(i+1 < username.length()){
					retorno += ".";
				}
			}
		}	
		return retorno;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSenha() {
		String retorno = "";
		if(senha != null){
			for(int i=0;i<senha.length();i++){
				retorno += senha.toCharArray()[i];
				if(i+1 < senha.length()){
					retorno += ".";
				}
			}
		}	
		return retorno;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public String getDataCriacao() {
		UtilData utilData = new UtilData();
		return utilData.formatDate(this.dataCriacao, "dd/MM/yyyy");
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
