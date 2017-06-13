package br.com.trixti.itm.util;

import java.lang.reflect.Method;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;

/**
 * Classe utilit�ria para m�todos de Reflex�o.
 * 
 * @author Evandro Moura
 */
public class UtilReflection {

	/**
	 * M�todo que recupera o m�todo get do objeto.
	 * 
	 * @param object Object
	 * @param atributo String
	 * @return Method
	 */
	public Method getMethod(Object object, String atributo)throws Exception {
		Method method = null;
		try {
			method = object.getClass().getMethod("get".concat(atributo.substring(0, 1).toUpperCase()).concat(atributo.substring(1)), null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw e;
		}
		return method;
	}
	
	/**
	 * M�todo que recupera o m�todo get do objeto.
	 * 
	 * @param object Object
	 * @param method String
	 * @return Method
	 */
	public Method getMethodSimple(Object object, String method)throws Exception {
		Method methodRetorno = null;
		try {
			methodRetorno = object.getClass().getMethod(method, null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw e;
		}
		return methodRetorno;
	}
	
	/**
	 * M�todo que recupera o m�todo get do objeto.
	 * 
	 * @param object Object
	 * @param atributo String
	 * @return Method
	 */
	@SuppressWarnings("rawtypes")
	public Method getMethodSet(Object object, String atributo,Class classe) throws Exception{
		Method method = null;
		try {
			method = object.getClass().getMethod(
					"set".concat(
							atributo.substring(0, 1).toUpperCase()).concat(
									atributo.substring(1)), classe);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw e;
		}
		return method;
	}
	
	/**
	 * Retorna um objeto.
	 * 
	 * @param object Object
	 * @param param String 
	 * @return Object
	 */
	public Object getObject(Object object, String param) {
		Object valor = null;
		try {
			JXPathContext context = JXPathContext.newContext(object);
			valor = context.getValue(param.replace('.', '/'));
			
		} catch (JXPathException jpe) {
			System.out.println(jpe.getMessage());
		}
		return valor;
	}
	
}