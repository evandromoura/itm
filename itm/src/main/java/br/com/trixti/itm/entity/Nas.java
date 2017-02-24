package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Nas generated by hbm2java
 */
@Entity
@Table(name = "nas", schema = "public")
public class Nas implements java.io.Serializable {

	private int id;
	private String nasname;
	private String shortname;
	private String type;
	private Integer ports;
	private String secret;
	private String server;
	private String community;
	private String description;

	public Nas() {
	}

	public Nas(int id, String nasname, String shortname, String type, String secret) {
		this.id = id;
		this.nasname = nasname;
		this.shortname = shortname;
		this.type = type;
		this.secret = secret;
	}

	public Nas(int id, String nasname, String shortname, String type, Integer ports, String secret, String server,
			String community, String description) {
		this.id = id;
		this.nasname = nasname;
		this.shortname = shortname;
		this.type = type;
		this.ports = ports;
		this.secret = secret;
		this.server = server;
		this.community = community;
		this.description = description;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nasname", nullable = false)
	public String getNasname() {
		return this.nasname;
	}

	public void setNasname(String nasname) {
		this.nasname = nasname;
	}

	@Column(name = "shortname", nullable = false)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "type", nullable = false)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ports")
	public Integer getPorts() {
		return this.ports;
	}

	public void setPorts(Integer ports) {
		this.ports = ports;
	}

	@Column(name = "secret", nullable = false)
	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(name = "server")
	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Column(name = "community")
	public String getCommunity() {
		return this.community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
