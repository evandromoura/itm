package br.com.trixti.itm.service.snmp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.snmp4j.PDU;
import org.snmp4j.mp.SnmpConstants;

import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.to.SnmpTO;
import br.com.trixti.itm.util.UtilString;

@Named
public class SnmpService {

	private Snmpwalk snmpwalk;
	private @Inject ParametroService parametroService;
	
	@PostConstruct
	private void init(){
		Parametro parametro = parametroService.recuperarParametro();
		snmpwalk =  new Snmpwalk().build(parametro.getSnmpHost(),parametro.getSnmpPorta());
	}
	
	
	
	public SnmpTO snmpWalkDownloadUpload(Map<String, String> mapa,String username) {
		String oidPrincipal = "";
		SnmpTO retorno = new SnmpTO();
		retorno.setDownload("");
		retorno.setUpload("");
		LIST_SNMP:for (String key : mapa.keySet()) {
			if (mapa.get(key).contains(username)) {
				oidPrincipal = key.substring(key.length() + UtilString.indexOf(key, ".", -2) + 1, key.length());
				System.out.println("CHAVEES = "+"1.3.6.1.2.1.31.1.1.1.10."+oidPrincipal);
//				Map<String, String> mapa2 = snmpWalk(SnmpConstants.version1,PDU.GETNEXT,username, "1.3.6.1.2.1.31.1.1.1.10."+oidPrincipal);
				Map<String, String> mapa2 = snmpWalk(SnmpConstants.version2c,PDU.GETBULK,username, "1.3.6.1.2.1.31.1.1.1.10");
//				Map<String, String> mapa2 = snmpWalk(username, "1.3.6.1.2.1.31.1.1.1.10");
				DOWNLOAD:for (String key2 : mapa2.keySet()){
					if (key2.contains(oidPrincipal)) {
						retorno.setDownload(mapa2.get(key2));
						break DOWNLOAD;
					}
				}
////				Map<String, String> mapa3 = snmpWalk(SnmpConstants.version1,PDU.GET,username, "1.3.6.1.2.1.31.1.1.1.6."+oidPrincipal);
//				Map<String, String> mapa3 = snmpWalk(SnmpConstants.version2c,PDU.GETBULK,username, "1.3.6.1.2.1.31.1.1.1.6");
////				Map<String, String> mapa3 = snmpWalk(username, "1.3.6.1.2.1.31.1.1.1.6");
//				UPLOAD:for (String key3 : mapa3.keySet()){
//					if (key3.contains(oidPrincipal)) {
//						retorno.setUpload(mapa3.get(key3));
//						break UPLOAD;
//					}
//				}
				break LIST_SNMP;
			}
		}
		return retorno;
	}
	
	public Map<String,String> snmpWalk(int version, int pdu,String username,String oid){
		Map<String,String> listaRetorno = new HashMap<String,String>();
		try{
			Map<String,String> mapaSnmp =snmpwalk.snmpWalk(version,pdu, oid);
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
	
	public static void main(String[] args) {
		try{
			System.out.println("SEM PONTO2");
			Map<String,String> mapaSnmp = new Snmpwalk().build("177.190.167.78",161).snmpWalk(SnmpConstants.version1,PDU.GET,"1.3.6.1.2.1.31.1.1.1.6");
			for(String key:mapaSnmp.keySet()){
				System.out.println(key +" = "+mapaSnmp.get(key));
				//15728676
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	
	
	

}
