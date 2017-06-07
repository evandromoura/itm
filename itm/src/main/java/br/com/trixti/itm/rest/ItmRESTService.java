package br.com.trixti.itm.rest;

import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.trixti.itm.service.snmp.SnmpService;
import br.com.trixti.itm.to.SnmpTO;

/**
 * @author Evandro Moura
 */

@Path("/services")
@RequestScoped
public class ItmRESTService {


	private @Inject SnmpService snmpService;
	
	@GET
	@Path("/snmp/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public SnmpTO snmp(@PathParam(value = "username") String username) throws Exception {
		
		SnmpTO snmpTO = snmpService.snmpWalkDownload(username);
//		SnmpTO snmpTO = new SnmpTO();
//		Random rn = new Random();
//		Integer download = rn.nextInt() * 1024;
//		if(download < 0){
//			download = download *(-1);
//		}
//		snmpTO.setDownload(String.valueOf(download));
//		snmpTO.setUpload(String.valueOf(rn.nextInt() * 1024));
//		
//		System.out.println("Download = "+snmpTO.getDownload());
//		System.out.println("Upload = "+snmpTO.getUpload());
		
		
		return snmpTO;
	}
	
}
