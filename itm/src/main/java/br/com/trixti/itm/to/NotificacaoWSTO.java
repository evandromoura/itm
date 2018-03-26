package br.com.trixti.itm.to;

import java.util.Date;

import br.com.trixti.itm.entity.Notificacao;
import br.com.trixti.itm.util.UtilData;

public class NotificacaoWSTO {

	
	private Integer id;
	private String nome;
	private Date dataInicio;
	private Date dataFim;
	private String mensagem;
	
	public NotificacaoWSTO(Notificacao notificacao){
		setId(notificacao.getId());
		setNome(notificacao.getNome());
		setDataInicio(notificacao.getDataInicio());
		setDataFim(notificacao.getDataFim());
		setMensagem(notificacao.getMensagem());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataInicio() {
		UtilData utilData = new UtilData();
		return utilData.formatDate(this.dataInicio, "dd/MM/yyyy HH:mm:ss");
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public String getDataFim() {
		UtilData utilData = new UtilData();
		return utilData.formatDate(this.dataFim, "dd/MM/yyyy HH:mm:ss");
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
