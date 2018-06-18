package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.trixti.itm.entity.Notificacao;
import br.com.trixti.itm.entity.Tag;

public class NotificacaoTO {

	private Notificacao notificacao;
	private List<Notificacao> notificacoes;
	private Notificacao notificacaoPesquisa;
	private List<Tag> tags;
	private Date dataAtual;
	
	
	public Notificacao getNotificacao() {
		if (notificacao == null) {
			notificacao = new Notificacao();
		}
		return notificacao;
	}
	
	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}
	
	public List<Notificacao> getNotificacoes() {
		if (notificacoes == null) {
			notificacoes = new ArrayList<Notificacao>();
		}

		return notificacoes;
	}
	
	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public Notificacao getNotificacaoPesquisa() {
		if (notificacaoPesquisa == null) {
			notificacaoPesquisa = new Notificacao();
		}
		return notificacaoPesquisa;
	}

	public void setNotificacaoPesquisa(Notificacao notificacaoPesquisa) {
		this.notificacaoPesquisa = notificacaoPesquisa;
	}
	
	public List<Tag> getTags() {
		if (tags == null) {
			tags = new ArrayList<Tag>();
		}
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

}