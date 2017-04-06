package br.com.trixti.itm.service.freeradius;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.GrupoParametro;
import br.com.trixti.itm.entity.Radcheck;
import br.com.trixti.itm.entity.Radgroupreply;
import br.com.trixti.itm.entity.Radusergroup;
import br.com.trixti.itm.service.radcheck.RadcheckService;
import br.com.trixti.itm.service.radgroupreply.RadgroupreplyService;
import br.com.trixti.itm.service.radusergroup.RadusergroupService;

@Stateless
public class FreeRadiusService {

	private @Inject RadcheckService radcheckService;
	private @Inject RadusergroupService radusergroupService;
	private @Inject RadgroupreplyService radgroupreplyService;
	
	public void sincronizarContrato(Contrato contrato){
		if(contrato.getAutenticacoes() != null 
					&& !contrato.getAutenticacoes().isEmpty()){	
			sincronizarRadCheck(contrato);
			sincronizarUserGroup(contrato);
		}	
		
	}

	private void sincronizarUserGroup(Contrato contrato) {
		if(contrato.getContratoGrupos() != null && !contrato.getContratoGrupos().isEmpty()){
			radusergroupService.excluirPorUsername(contrato.getAutenticacoes().get(0).getUsername());
			Radusergroup radusergroup  = new Radusergroup();
			radusergroup.setGroupname(contrato.getContratoGrupos().get(0).getGrupo().getNome());
			radusergroup.setUsername(contrato.getAutenticacoes().get(0).getUsername());
			radusergroup.setPriority(1);
			radusergroupService.incluir(radusergroup);
		}	
	}


	private void sincronizarRadCheck(Contrato contrato) {
		radcheckService.excluirPorUsername(contrato.getAutenticacoes().get(0).getUsername());
		List<Radcheck> listaRadCheck = new ArrayList<Radcheck>();
		Radcheck radcheckLogin = new Radcheck();
		radcheckLogin.setAttribute("Cleartext-Password");
		radcheckLogin.setOp(":=");
		radcheckLogin.setValue(contrato.getAutenticacoes().get(0).getSenha());
		radcheckLogin.setUsername(contrato.getAutenticacoes().get(0).getUsername());
		listaRadCheck.add(radcheckLogin);
		Radcheck radcheckLoginSimultaneo = new Radcheck();
		radcheckLoginSimultaneo.setAttribute("Simultaneous-Use");
		radcheckLoginSimultaneo.setOp(":=");
		radcheckLoginSimultaneo.setValue("1");
		radcheckLoginSimultaneo.setUsername(contrato.getAutenticacoes().get(0).getUsername());
		listaRadCheck.add(radcheckLoginSimultaneo);
		if(contrato.getAutenticacoes().get(0).getIp() != null && !contrato.getAutenticacoes().get(0).getIp().equals("")){
//			Radcheck radcheckIP = new Radcheck();
//			radcheckIP.setAttribute("Framed-IP-Address");
//			radcheckIP.setOp("=");
//			radcheckIP.setValue(contrato.getAutenticacoes().get(0).getIp());
//			radcheckIP.setUsername(contrato.getAutenticacoes().get(0).getUsername());
//			listaRadCheck.add(radcheckIP);
		}
		radcheckService.incluirLista(listaRadCheck);
	}


	public void sincronizarGrupo(Grupo grupo) {
		radgroupreplyService.excluirPorGroupname(grupo.getNome());
		List<Radgroupreply> listaRadgroupreply = new ArrayList<Radgroupreply>();
		if(grupo.getGrupoParametros() != null){
			for(GrupoParametro parametro:grupo.getGrupoParametros()){
				Radgroupreply radgroupreply = new Radgroupreply();
				radgroupreply.setAttribute(parametro.getPropriedade());
				radgroupreply.setOp(parametro.getOperador());
				radgroupreply.setValue(parametro.getValor());
				radgroupreply.setGroupname(grupo.getNome());
				listaRadgroupreply.add(radgroupreply);
			}
		}
		radgroupreplyService.incluirLista(listaRadgroupreply);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void bloquearContrato(Contrato contrato) {
		Radcheck radcheck = new Radcheck();
		radcheck.setAttribute("Auth-Type");
		radcheck.setOp(":=");
		radcheck.setValue("Reject");
		radcheck.setUsername(contrato.getAutenticacoes().get(0).getUsername());
		radcheckService.incluir(radcheck);
	}
	
	public void desbloquearContrato(Contrato contrato){
		
		radcheckService.excluirPorUsernameAttributeValue(contrato.getAutenticacoes().get(0).getUsername(),"Auth-Type","Reject");
		
		
	}
	
	
	
}
