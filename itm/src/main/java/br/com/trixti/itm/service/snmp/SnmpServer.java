  package br.com.trixti.itm.service.snmp;
   
  import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
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

import br.com.trixti.itm.to.SnmpTO;
   
  @Stateless
  @ServerEndpoint("/snmpserver")
  public class SnmpServer {
	  
	  
	  		private @Inject SnmpService snmpService;
	  		private static Map<Session,String> mapaQueue = new ConcurrentHashMap<Session,String>();

           @OnMessage
           public void recebeMensagem(String message, Session session) {
        	   	 mapaQueue.put(session, message);
           }
           
           
           @OnOpen
           public void open(Session session) {
        	mapaQueue.put(session, "");   
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
           
           @Schedule(info="Exec-Snmp",second="*/5", minute = "*", hour = "*", persistent = false)
           public void tarefa(){
        	   try {
	        	   for(Session session:mapaQueue.keySet()){
	        		   SnmpTO snmpTO =  snmpService.snmpWalkDownloadUpload(mapaQueue.get(session));
//	        		    SnmpTO snmpTO = comporMock();
	        		   	String msg = "{\"download\":\""+snmpTO.getDownload()+"\",\"upload\":\""+snmpTO.getUpload()+"\"}";
						session.getBasicRemote().sendText(msg);
	        	   }
        	   } catch (IOException e) {
        		   e.printStackTrace();
        	   }
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