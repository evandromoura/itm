package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FiltroTO<T> {

	private Integer inicio;

	private Integer tamanho;

	private Integer totalRegistros;
	
	private Integer pagina;

	private String ordenacao;

	private String campoOrdenacao;
	
	private Date dataInicio;
	
	private Date dataFim;

	private T objeto;

	private List<T> lista;

	private Class<T> tipoObjeto;

	public FiltroTO(Class<T> tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	
	public FiltroTO() {
	}

	public Class<T> getTipoObjeto() {
		return tipoObjeto;
	}

	public Integer getInicio() {
		return inicio;
	}

	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public String getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(String ordenacao) {
		this.ordenacao = ordenacao;
	}

	public String getCampoOrdenacao() {
		return campoOrdenacao;
	}

	public void setCampoOrdenacao(String campoOrdenacao) {
		this.campoOrdenacao = campoOrdenacao;
	}

	public T getObjeto() throws Exception {
		if (objeto == null) {
			objeto = tipoObjeto.newInstance();
		}
		return objeto;
	}

	public void setObjeto(T t) {
		this.objeto = t;
	}

	public List<T> getLista() {
		if (lista == null) {
			lista = new ArrayList<>();
		}
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

}