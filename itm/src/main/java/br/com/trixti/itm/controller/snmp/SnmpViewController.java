package br.com.trixti.itm.controller.snmp;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.infra.security.annotations.SuporteNivel1;
import br.com.trixti.itm.to.SnmpViewTO;


@Named
@ViewScoped
@SuporteNivel1
public class SnmpViewController extends AbstractController<Contrato> implements Serializable {

	private static final long serialVersionUID = 5020034670979433901L;
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
