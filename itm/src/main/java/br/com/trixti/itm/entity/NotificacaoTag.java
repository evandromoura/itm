package br.com.trixti.itm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "itm_notificacao_tag", schema = "public")
public class NotificacaoTag implements java.io.Serializable {

	private static final long serialVersionUID = 22775385501094990L;
	
	@Id
	@SequenceGenerator(name="NOTIFICACAO_TAG_ID_GENERATOR", sequenceName="itm_notificacao_tag_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOTIFICACAO_TAG_ID_GENERATOR") 
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="id_notificacao")
	private Notificacao notificacao;
	
	@ManyToOne
	@JoinColumn(name="id_tag")
	private Tag tag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	
	public NotificacaoTag() {
	}
	
	public NotificacaoTag(Notificacao notificacao, Tag tag){
		setNotificacao(notificacao);
		setTag(tag);
	}
	

}
