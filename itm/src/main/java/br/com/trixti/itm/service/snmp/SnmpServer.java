  package br.com.trixti.itm.service.snmp;
   
  import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.snmp4j.PDU;
import org.snmp4j.mp.SnmpConstants;

import com.google.gson.Gson;

import br.com.trixti.itm.to.SnmpTO;
import br.com.trixti.itm.util.UtilCollection;
import br.com.trixti.itm.util.UtilString;
   
  @Stateless
  @ServerEndpoint("/snmpserver")
  public class SnmpServer {
	  
	  
	  		private @Inject SnmpService snmpService;
	  		private static Queue<Session> mapaQueue = new ConcurrentLinkedQueue<Session>();
	  		private static List<SnmpTO> listaSnmpAnterior = new ArrayList<SnmpTO>();
	  		private static Map<String,String> mapaItens = new HashMap<String,String>();

           @OnMessage
           public void recebeMensagem(String message, Session session) {
        	   	 //mapaQueue.add(session);
           }
           
           
           @OnOpen
           public void open(Session session) {
        	mapaQueue.add(session);   
            System.out.println("New session opened: "+session.getId());
           }
           
            @OnError
           public void error(Session session, Throwable t) {
            mapaQueue.remove(session);
            System.err.println("Error on session "+session.getId());  
           }
            
            
           @OnClose
           public void closedConnection(Session session) { 
            mapaQueue.remove(session);
            System.out.println("session closed: "+session.getId());
           }
           
           @Schedule(info="Exec-Snmp-Update",second="*/10", minute = "*", hour = "*", persistent = false)
           public void atualizar(){
        	   mapaItens = snmpService.snmpWalk(SnmpConstants.version1,PDU.GETBULK,null, "1.3.6.1.2.1.2.2.1.2");
        	   System.out.println(mapaItens);
           }
           
           
           @Schedule(info="Exec-Snmp",second="*", minute = "*", hour = "*", persistent = false)
           public void tarefa(){
        	   try {
        		   if(mapaQueue.size() > 0){
	        		    List<SnmpTO> listaRetorno= comporTabelaSnmp();
	        		    Gson gson = new Gson();
	   					UtilCollection<SnmpTO> utilCollection = new UtilCollection<SnmpTO>();
	   					utilCollection.ordenarListaDesc(listaRetorno, "taxaDownload");
		        	   for(Session session:mapaQueue){
		        		   session.getBasicRemote().sendText(gson.toJson(listaRetorno));
		        	   }
		        	   listaSnmpAnterior = listaRetorno;
        		   }   
        	   } catch (Exception e) {
        		   e.printStackTrace();
        	   }
           }
           
        private SnmpTO recuperarSnmp(String id,List<SnmpTO> listaSnmpTO){
        	for(SnmpTO snmoTO:listaSnmpTO){
        		if(snmoTO.getId().equals(id)){
        			return snmoTO;
        		}
        	}
        	return null;
        	
        }   
           
        public List<SnmpTO> comporTabelaSnmp(){
        	
        	
        	List<SnmpTO> listaSnmpTO = new ArrayList<SnmpTO>();
        	
        	for(String key:mapaItens.keySet()){
        		String suffix = key.substring(key.length() + UtilString.indexOf(key, ".", -2) + 1, key.length());
        		listaSnmpTO.add(new SnmpTO(suffix,mapaItens.get(key), "0", "0"));
        	}
        	
        	Map<String,String> mapaDownload = snmpService.snmpWalk(SnmpConstants.version2c,PDU.GETBULK,null, "1.3.6.1.2.1.31.1.1.1.10");
        	for(String key:mapaDownload.keySet()){
        		String suffix = key.substring(key.length() + UtilString.indexOf(key, ".", -2) + 1, key.length());
        		SnmpTO snmpAnteriorTO = recuperarSnmp(suffix, listaSnmpAnterior);
        		SnmpTO snmpTO = recuperarSnmp(suffix, listaSnmpTO);
        		if(listaSnmpAnterior.size() > 0 && snmpTO != null && snmpTO.getDownload() != null && snmpAnteriorTO != null && snmpAnteriorTO.getDownload() != null){
        			snmpTO.setDownload(mapaDownload.get(key));
        			if(Double.valueOf(snmpAnteriorTO.getDownload()) > 0){
        				Double download = ((Double.valueOf(snmpTO.getDownload()) - Double.valueOf(snmpAnteriorTO.getDownload())) * 8/1024)/1024;
        				snmpTO.setTaxaDownload(download);
        			}else{
        				snmpTO.setTaxaDownload(new Double(0));
        			}	
        		}else if(snmpTO != null){
        			snmpTO.setDownload(String.valueOf(0));
	        		snmpTO.setTaxaDownload(Double.valueOf(0));
        		}
        	}
        	Map<String,String> mapaUpload = snmpService.snmpWalk(SnmpConstants.version2c,PDU.GETBULK, null, "1.3.6.1.2.1.31.1.1.1.6");
        	
        	for(String key:mapaUpload.keySet()){
        		String suffix = key.substring(key.length() + UtilString.indexOf(key, ".", -2) + 1, key.length());
        		SnmpTO snmpAnteriorTO = recuperarSnmp(suffix, listaSnmpAnterior);
        		SnmpTO snmpTO = recuperarSnmp(suffix, listaSnmpTO);
        		if(listaSnmpAnterior.size() > 0 && snmpTO != null && snmpTO.getUpload() != null && snmpAnteriorTO != null && snmpAnteriorTO.getUpload() != null){
        			snmpTO.setUpload(mapaUpload.get(key));
        			if(Double.valueOf(snmpAnteriorTO.getUpload()) > 0){
        				Double upload = ((Double.valueOf(snmpTO.getUpload()) - Double.valueOf(snmpAnteriorTO.getUpload())) * 8/1024)/1024;
        				snmpTO.setTaxaUpload(upload);
        			}else{
        				snmpTO.setTaxaUpload(new Double(0));
        			}
        				
        		}else if(snmpTO != null){
        			snmpTO.setUpload(String.valueOf(0));
	        		snmpTO.setTaxaUpload(Double.valueOf(0));
        		}
        	}
        	return listaSnmpTO;
        	
        	
        }   


		private SnmpTO comporMock() {
			SnmpTO snmpTO =  new SnmpTO();
			Integer download = ThreadLocalRandom.current().nextInt(0, 1000);
			Integer upload = ThreadLocalRandom.current().nextInt(0, 1000);
			download = download < 0?download*(-1):download;
			upload   = upload   < 0?upload*(-1):upload;
			snmpTO.setDownload(String.valueOf(download));
			snmpTO.setUpload(String.valueOf(upload));
			return snmpTO;
		}
           
  }