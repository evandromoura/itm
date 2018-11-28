package br.com.trixti.itm.service.snmp;
import java.io.IOException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.parametro.ParametroService;

@Named
public class Snmpwalk {
	
	private int maxRepetitions = 10;

	private int maxSizeResponsePDU = 65535;
	
	private Address targetAddress;

	private String communityString = "ppp-snmp";
	
	private Snmp snmp;
	
	private @Inject ParametroService parametroService;
	
	
	public void build(){
		try{
			Parametro parametro = parametroService.recuperarParametro();
			targetAddress = GenericAddress.parse("udp:"+parametro.getSnmpHost()+"/" + parametro.getPortaSmtp());
	        TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
	        snmp = new Snmp(transport);
	        transport.listen();
		}catch(Exception e){
			e.printStackTrace();
		} 
	}
	
	public Snmpwalk build(String host,Integer porta){
		try{
			targetAddress = GenericAddress.parse("udp:"+host+"/" + porta);
	        TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
	        snmp = new Snmp(transport);
	        transport.listen();
		}catch(Exception e){
			e.printStackTrace();
		} 
		return this;
	}

	public HashMap<String, String> snmpWalk(int version,int pduVar, String startOid)throws IOException{
	    //String startOid = "1.3.6.1.4.1.9.9.46.1.3.1.1.4.1"; 
	    String oid = startOid;
	    HashMap<String, String> varBindings = new HashMap<String, String>();

	    while (oid.startsWith(startOid)) {
	        PDU pdu = getVariableBinding(version, new OID(oid), pduVar);
	        if (pdu == null || pdu.size() == 0) return varBindings;

	        for (int i=0; i<pdu.size(); i++) {
	            VariableBinding var = pdu.get(i);
	            if (var == null) return varBindings;

	            oid = var.getOid().toString();
	            if (oid.startsWith(startOid)) {
	                varBindings.put(oid, var.getVariable().toString());
	            } else {
	            	varBindings.put(oid, var.getVariable().toString());
//	                return varBindings;
	            }
	        }
	    }
	    return varBindings;
	}

	/**
	 * Method which takes a single OID and returns the response from the agent
	 * as a String.
	 * 
	 * @param oid
	 * @return
	 * @throws IOException
	 */
	public PDU getVariableBinding(int version,OID oid, int type) throws IOException {
	    ResponseEvent event = get(version,new OID[] { oid }, type);

	    if (event == null || event.getResponse() == null) {
//	        warn(oid);
	        return null;
	    }

	    return event.getResponse();
	}

	public ResponseEvent get(int version, OID oids[], int type) throws IOException {
	    PDU pdu = new PDU();
	    for (OID oid : oids) {
	        pdu.add(new VariableBinding(oid));
	    }
	    pdu.setType(type);

	    pdu.setMaxRepetitions(maxRepetitions); // This makes GETBULK work as expected

	    ResponseEvent event = snmp.send(pdu, getTarget(version), null);
	    if (event != null) {
	        return event;
	    }
	    throw new RuntimeException("GET timed out");
	}

	private Target getTarget(int version) {
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString(communityString));
	    target.setAddress(targetAddress);
	    target.setRetries(2);
	    target.setTimeout(3000);
	    target.setVersion(version);
	    target.setMaxSizeRequestPDU(maxSizeResponsePDU); // This makes GETBULK work as expected
	    return target;
	}

	
}