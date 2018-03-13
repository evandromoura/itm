package br.com.trixti.itm.util;

public class UtilEnum<T> {

	
	public T obterValorEnum(T[] listaValoresEnum,String nomeChave,Integer nomeCampoValor){
		T retorno = null;
		try {
			UtilReflection utilReflection = new UtilReflection();
			for(T objEnum:listaValoresEnum){
					if(((Integer)utilReflection.getMethod(objEnum, nomeChave).invoke(objEnum, null)).equals(nomeCampoValor)){
						retorno = objEnum;
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public T obterValorEnum(T[] listaValoresEnum,String nomeChave,String nomeCampoValor){
		T retorno = null;
		try {
			UtilReflection utilReflection = new UtilReflection();
			for(T objEnum:listaValoresEnum){
					if(((String)utilReflection.getMethod(objEnum, nomeChave).invoke(objEnum, null)).equals(nomeCampoValor)){
						retorno = objEnum;
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
}