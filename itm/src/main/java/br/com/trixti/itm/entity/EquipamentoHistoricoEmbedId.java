package br.com.trixti.itm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EquipamentoHistoricoEmbedId implements Serializable {

	private static final long serialVersionUID = -4172007160140460237L;
	
	private Integer id;
	private Integer rev;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRev() {
		return rev;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	
	@Override
	public int hashCode() {
		return getRev() + getId();
	}


	@Override
	public boolean equals(Object obj) {
		return (((EquipamentoHistoricoEmbedId)obj).getId().equals(this.getId())) && 
			((EquipamentoHistoricoEmbedId)obj).getRev().equals(this.getRev());
	}
	
	
}
