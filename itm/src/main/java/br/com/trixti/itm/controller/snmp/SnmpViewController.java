package br.com.trixti.itm.controller.snmp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.to.SnmpViewTO;

@ViewScoped
@ManagedBean
public class SnmpViewController extends AbstractController<Contrato> {

	private SnmpViewTO snmpViewTO;
	

	@PostConstruct
	public void init() {
		inicializar();
		
	}

	private void inicializar() {
		String parametro = getRequest().getParameter("parametro");
		getSnmpViewTO().setUsername(parametro);
	}

	
	

	public SnmpViewTO getSnmpViewTO() {
		if (snmpViewTO == null) {
			snmpViewTO = new SnmpViewTO();
		}
		return snmpViewTO;
	}

	public void setSnmpViewTO(SnmpViewTO snmpViewTO) {
		this.snmpViewTO = snmpViewTO;
	}

}
