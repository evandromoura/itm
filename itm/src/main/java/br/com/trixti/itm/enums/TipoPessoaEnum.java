package br.com.trixti.itm.enums;

public enum TipoPessoaEnum {

	FISICA("label.global.pessoafisica","F"),
	JURIDICA("label.global.pessoajuridica","J"); 
	
	
	private TipoPessoaEnum(String key,String sigla){
		setKey(key);
		setSigla(sigla);
	}
	
	private String key;
	private String sigla;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getName(){
		return name();
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
