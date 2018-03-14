package br.com.trixti.itm.entity;

public enum TipoServico {
	_045("045","Serviço de Comunicação Multimídia");
	
	private TipoServico(String codigo,String descricao){
		setCodigo(codigo);
		setDescricao(descricao);
	}
	private String descricao;
	private String codigo;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	

}
