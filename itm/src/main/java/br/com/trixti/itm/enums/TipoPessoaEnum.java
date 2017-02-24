package br.com.trixti.itm.enums;

public enum TipoPessoaEnum {

	FISICA("label.global.pessoafisica"),
	JURIDICA("label.global.pessoajuridica"); 
	
	
	private TipoPessoaEnum(String key){
		setKey(key);
	}
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getName(){
		return name();
	}
	
}
