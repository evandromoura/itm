package br.com.trixti.itm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "itm_equipamento", schema = "public")
public class Equipamento implements java.io.Serializable {

	private static final long serialVersionUID = -2127519180048329065L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="id_marca")
	private EquipamentoMarca marca;
	
	@ManyToOne
	@JoinColumn(name="id_modelo")
	private EquipamentoMarcaModelo modelo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipamento")
	private List<ContratoEquipamento> contratoEquipamentos;

	public Equipamento() {
	}

	public Equipamento(Integer id) {
		this.id = id;
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

	public EquipamentoMarca getMarca() {
		return marca;
	}

	public void setMarca(EquipamentoMarca marca) {
		this.marca = marca;
	}

	public EquipamentoMarcaModelo getModelo() {
		return modelo;
	}

	public void setModelo(EquipamentoMarcaModelo modelo) {
		this.modelo = modelo;
	}

	public List<ContratoEquipamento> getContratoEquipamentos() {
		return contratoEquipamentos;
	}

	public void setContratoEquipamentos(List<ContratoEquipamento> contratoEquipamentos) {
		this.contratoEquipamentos = contratoEquipamentos;
	}

}
