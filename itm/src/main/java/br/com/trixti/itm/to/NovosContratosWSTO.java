package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovosContratosWSTO {
	
	Map<String,Integer> mapaContratosNovos;
	Map<String,Integer> mapaContratosCancelados;
	

	public Map<String, Integer> getMapaContratosNovos() {
		if (mapaContratosNovos == null) {
			mapaContratosNovos = new HashMap<String, Integer>();
		}
		return mapaContratosNovos;
	}

	public void setMapaContratosNovos(Map<String, Integer> mapaContrato) {
		this.mapaContratosNovos = mapaContrato;
	}
	
	
	public Map<String, Integer> getMapaContratosCancelados() {
		if (mapaContratosCancelados == null) {
			mapaContratosCancelados = new HashMap<String, Integer>();
		}
		return mapaContratosCancelados;
	}

	public void setMapaContratosCancelados(Map<String, Integer> mapaContrato) {
		this.mapaContratosCancelados = mapaContrato;
	}
	
	public List<String> getMeses(){
		List<String> lista  = new ArrayList<String>(getMapaContratosNovos().keySet());
		Collections.sort(lista);
		return lista; 
	}
	
	public List<Integer> getQtdContratosNovos(){
		List<String> lista = getMeses();
		List<Integer> listaReturn = new ArrayList<Integer>();
		for(String mes:lista){
			listaReturn.add(getMapaContratosNovos().get(mes));
		}
		return listaReturn;
	}
	
	public List<Integer> getQtdContratosCancelados(){
		List<String> lista = getMeses();
		List<Integer> listaReturn = new ArrayList<Integer>();
		for(String mes:lista){
			listaReturn.add(getMapaContratosCancelados().get(mes));
		}
		return listaReturn;
	}
	

}
