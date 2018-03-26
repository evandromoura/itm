package br.com.trixti.itm.to;

import java.util.Date;

import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.util.UtilData;

public class ContratoWSTO {
	
	private Integer id;
	private String identificador;
	private Date dataCadastro;
	private StatusContrato status;
	
	public ContratoWSTO(Contrato contrato){
		setId(contrato.getId());
		setIdentificador(contrato.getIdentificador());
		setDataCadastro(contrato.getDataCriacao());
		setStatus(contrato.getStatus());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDataCadastro() {
		UtilData utilData = new UtilData();
		return utilData.formatDate(this.dataCadastro, "dd/MM/yyyy");
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public StatusContrato getStatus() {
		return status;
	}

	public void setStatus(StatusContrato status) {
		this.status = status;
	}
	

}
