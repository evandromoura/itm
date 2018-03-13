package br.com.trixti.itm.entity;

public enum TecnologiaEnum {

	
	xDSL("A","xDSL"),
	Cable_Modem("B","Cable Modem"),
	Spread_Spectrum("C","Spread Spectrum"),
	FWA("D","FWA"),
	MMDS("E","MMDS"),
	DTH("F","DTH"),
	SATELITE("G","SATELITE"),
	Fibra("H","Fibra"),
	PLC("I","PLC"),
	HFC("J","HFC"),
	WIMAX("K","WIMAX"),
	LTE("L","LTE"),
	ETHERNET("M","ETHERNET"),
	FR("N","FR"),
	ATM("O","ATM");
	
	private TecnologiaEnum(String item,String descricao){
		setItem(item);
		setDescricao(descricao);
		
	}
	
	private String item;
	private String descricao;
	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	
}
