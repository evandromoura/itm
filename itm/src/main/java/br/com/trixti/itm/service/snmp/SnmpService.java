package br.com.trixti.itm.service.snmp;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.to.SnmpTO;
import br.com.trixti.itm.util.UtilString;

@Named
public class SnmpService {
	private @Inject ParametroService parametroService;
	
	public Map<String,String> snmpWalk(String username,String oid){
		Map<String,String> listaRetorno = new HashMap<String,String>();
		try{
			Parametro parametro = parametroService.recuperarParametro();
			Map<String,String> mapaSnmp = new Snmpwalk().build(parametro.getSnmpHost(),parametro.getSnmpPorta()).snmpWalk(oid);
			for(String key:mapaSnmp.keySet()){
//				if(mapaSnmp.get(key).contains(contrato.getAutenticacoes().get(0).getUsername())){
					listaRetorno.put(key,mapaSnmp.get(key));
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return listaRetorno;
	}
	
	
	public SnmpTO snmpWalkDownload(String username) {
		Map<String, String> mapa = snmpWalk(username, "1.3.6.1.2.1.2.2.1.2");
//		Map<String, String> mapa = snmpWalk(username, "1.3.6.1.2.1.31.1.1.1.6");
		String oidPrincipal = "";
		SnmpTO retorno = new SnmpTO();
		LIST_SNMP:for (String key : mapa.keySet()) {
			if (mapa.get(key).contains(username)) {
				oidPrincipal = key.substring(key.length() + UtilString.indexOf(key, ".", -2) + 1, key.length());
				Map<String, String> mapa2 = snmpWalk(username, "1.3.6.1.2.1.31.1.1.1.6");
				DOWNLOAD:for (String key2 : mapa2.keySet()){
					if (key2.contains(oidPrincipal)) {
						retorno.setDownload(mapa2.get(key2));
						break DOWNLOAD;
					}
				}
				Map<String, String> mapa3 = snmpWalk(username, "1.3.6.1.2.1.31.1.1.1.10");
				UPLOAD:for (String key3 : mapa3.keySet()){
					if (key3.contains(oidPrincipal)) {
						retorno.setUpload(mapa3.get(key3));
						break UPLOAD;
					}
				}
				break LIST_SNMP;
			}
		}
		return retorno;
	}
	
	public static void main(String[] args) {
		String var = "1.3.6.1.2.1.31.1.1.1.6.15734623";
		System.out.println();
	}
	
	
	
	

}
