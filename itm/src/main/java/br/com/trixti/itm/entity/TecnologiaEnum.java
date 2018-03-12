package br.com.trixti.itm.entity;

public enum TecnologiaEnum {

	
	xDSL("xDSL"),Cable_Modem("Cable Modem"),Spread_Spectrum("Spread Spectrum"),
	FWA("FWA"),MMDS("MMDS"),DTH("DTH"),SATELITE("SATELITE"),Fibra("Fibra"),
	PLC("PLC"),HFC("HFC"),WIMAX("WIMAX"),LTE("LTE"),ETHERNET("ETHERNET"),FR("FR"),ATM("ATM");
	
	private TecnologiaEnum(String descricao){
		setDescricao(descricao);
	}
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
