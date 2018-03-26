package br.com.trixti.itm.to;

import java.util.List;

public class TagWS {

	private String nome;
	private Integer qtdNotificacao;
	private List<NotificacaoWSTO> notificacoes;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public TagWS(String nome){
		setNome(nome);
	}

	public List<NotificacaoWSTO> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<NotificacaoWSTO> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public Integer getQtdNotificacao() {
		return qtdNotificacao;
	}

	public void setQtdNotificacao(Integer qtdNotificacao) {
		this.qtdNotificacao = qtdNotificacao;
	}
	
	
}
