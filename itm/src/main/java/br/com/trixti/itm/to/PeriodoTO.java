package br.com.trixti.itm.to;

import java.util.Date;

public class PeriodoTO {
	
	private Date dataInicio;
	private Date dataFim;
	
	public PeriodoTO(){
		
	}
	
	public PeriodoTO(Date dataInicio,Date dataFim){
		setDataInicio(dataInicio);
		setDataFim(dataFim);
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
