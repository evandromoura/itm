package br.com.trixti.itm.entity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "itm_cliente_tag", schema = "public")
public class ClienteTag implements java.io.Serializable {

	private static final long serialVersionUID = -7925742180350414970L;

	@Id
	@SequenceGenerator(name="CLIENTE_TAG_ID_GENERATOR", sequenceName="itm_cliente_tag_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_TAG_ID_GENERATOR")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tag")
	private Tag tag;
	

	public ClienteTag() {
	}
	
	public ClienteTag(Cliente cliente, Tag tag){
		setCliente(cliente);
		setTag(tag);
	}

	public ClienteTag(Integer id) {
		this.id = id;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag itmTag) {
		this.tag = itmTag;
	}

}
