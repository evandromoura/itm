package br.com.trixti.itm.service.freeradius;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.GrupoParametro;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Radcheck;
import br.com.trixti.itm.entity.Radgroupreply;
import br.com.trixti.itm.entity.Radreply;
import br.com.trixti.itm.entity.Radusergroup;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.radcheck.RadcheckService;
import br.com.trixti.itm.service.radgroupreply.RadgroupreplyService;
import br.com.trixti.itm.service.radreply.RadreplyService;
import br.com.trixti.itm.service.radusergroup.RadusergroupService;

@Stateless
public class FreeRadiusService {

	private @Inject RadreplyService radreplyService;
	private @Inject RadcheckService radcheckService;
	private @Inject RadusergroupService radusergroupService;
	private @Inject RadgroupreplyService radgroupreplyService;
	private @Inject ParametroService parametroService;
	
	public void sincronizarContrato(Contrato contrato){
		if(contrato.getAutenticacoes() != null 
					&& !contrato.getAutenticacoes().isEmpty()){	
			sincronizarRadCheck(contrato);
			sincronizarUserGroup(contrato);
		}	
		
	}

	private void sincronizarUserGroup(Contrato contrato) {
		
		
		if(contrato.getContratoGrupos() != null && !contrato.getContratoGrupos().isEmpty()){
			for(ContratoAutenticacao contratoAutenticacao:contrato.getAutenticacoes()){
				radusergroupService.excluirPorUsername(contratoAutenticacao.getUsername());
				Radusergroup radusergroup  = new Radusergroup();
				radusergroup.setGroupname(contratoAutenticacao.getGrupo());
				radusergroup.setUsername(contratoAutenticacao.getUsername());
				radusergroup.setPriority(1);
				radusergroupService.incluir(radusergroup);
			}	
		}	
	}


	private void sincronizarRadCheck(Contrato contrato) {
		for(ContratoAutenticacao contratoAutenticacao:contrato.getAutenticacoes()){
			radcheckService.excluirPorUsername(contratoAutenticacao.getUsername());
			radreplyService.excluirPorUsername(contratoAutenticacao.getUsername());
			List<Radcheck> listaRadCheck = new ArrayList<Radcheck>();
			Radcheck radcheckLogin = new Radcheck();
			radcheckLogin.setAttribute("Cleartext-Password");
			radcheckLogin.setOp(":=");
			radcheckLogin.setValue(contratoAutenticacao.getSenha());
			radcheckLogin.setUsername(contratoAutenticacao.getUsername());
			listaRadCheck.add(radcheckLogin);
			Radcheck radcheckLoginSimultaneo = new Radcheck();
			radcheckLoginSimultaneo.setAttribute("Simultaneous-Use");
			radcheckLoginSimultaneo.setOp(":=");
			radcheckLoginSimultaneo.setValue("1");
			radcheckLoginSimultaneo.setUsername(contratoAutenticacao.getUsername());
			listaRadCheck.add(radcheckLoginSimultaneo);
			if(contratoAutenticacao.getIp() != null && !contratoAutenticacao.getIp().equals("")){
				Radreply radreply = new Radreply();
				radreply.setAttribute("Framed-IP-Address");
				radreply.setOp("=");
				radreply.setValue(contratoAutenticacao.getIp());
				radreply.setUsername(contratoAutenticacao.getUsername());
				radreplyService.incluir(radreply);
			}
			radcheckService.incluirLista(listaRadCheck);
		}	
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
		Parametro parametro = parametroService.recuperarParametro();
		if(contrato.getAutenticacoes() != null && !contrato.getAutenticacoes().isEmpty()){
			for(ContratoAutenticacao contratoAutenticacao:contrato.getAutenticacoes()){

//				Radcheck radcheck = new Radcheck();
//				radcheck.setAttribute("Auth-Type");
//				radcheck.setOp(":=");
//				radcheck.setValue("Reject");
//				radcheck.setUsername(contratoAutenticacao.getUsername());

				
				Radcheck radcheck = new Radcheck();
				radcheck.setAttribute("Pool-Name");
				radcheck.setOp(":=");
				radcheck.setValue(parametro.getPoolNameBloqueio());
				radcheck.setUsername(contratoAutenticacao.getUsername());
				radcheckService.incluir(radcheck);
				

				Radreply radreplyAddress = new Radreply();
				radreplyAddress.setUsername(contratoAutenticacao.getUsername());
				radreplyAddress.setAttribute("Mikrotik-Address-List");
				radreplyAddress.setOp("=");
				radreplyAddress.setValue(parametro.getPoolNameBloqueio());
				radreplyService.incluir(radreplyAddress);
				
				
				Radreply radreplyIpv6Address = new Radreply();
				radreplyIpv6Address.setUsername(contratoAutenticacao.getUsername());
				radreplyIpv6Address.setAttribute("Mikrotik-Delegated-IPv6-Pool");
				radreplyIpv6Address.setOp("=");
				radreplyIpv6Address.setValue(parametro.getPoolNameIpv6Bloqueio());
				radreplyService.incluir(radreplyIpv6Address);

				Radreply radreplyFramedIpv6Address = new Radreply();
				radreplyFramedIpv6Address.setUsername(contratoAutenticacao.getUsername());
				radreplyFramedIpv6Address.setAttribute("Framed-IPv6-Pool");
				radreplyFramedIpv6Address.setOp("=");
				radreplyFramedIpv6Address.setValue(parametro.getPoolNameIpv6Bloqueio());
				radreplyService.incluir(radreplyFramedIpv6Address);
				
				Radreply radreply = new Radreply();
				radreply.setAttribute("Mikrotik-Rate-Limit");
				radreply.setOp(":=");
				radreply.setValue(parametro.getUploadSuspensao().toString()+"k/"+parametro.getDownloadSuspensao().toString()+"k");
				radreply.setUsername(contratoAutenticacao.getUsername());
				radreplyService.incluir(radreply);
			}	
		}	
	}
	
	public void desbloquearContrato(Contrato contrato){
		if(contrato.getAutenticacoes() != null && !contrato.getAutenticacoes().isEmpty()){
			Parametro parametro = parametroService.recuperarParametro();
			for(ContratoAutenticacao contratoAutenticacao:contrato.getAutenticacoes()){
				radcheckService.excluirPorUsernameAttributeValue(contratoAutenticacao.getUsername(),"Auth-Type","Reject");
				radcheckService.excluirPorUsernameAttributeValue(contratoAutenticacao.getUsername(),"Pool-Name",parametro.getPoolNameBloqueio());
				radreplyService.excluirPorUsernameAttributeValue(contratoAutenticacao.getUsername(), "Mikrotik-Address-List", parametro.getPoolNameBloqueio());
				radreplyService.excluirPorUsernameAttributeValue(contratoAutenticacao.getUsername(), "Mikrotik-Delegated-IPv6-Pool", parametro.getPoolNameIpv6Bloqueio());
				radreplyService.excluirPorUsernameAttributeValue(contratoAutenticacao.getUsername(), "Framed-IPv6-Pool", parametro.getPoolNameIpv6Bloqueio());
				radreplyService.excluirPorUsernameAttribute(contratoAutenticacao.getUsername(), "Mikrotik-Rate-Limit");
			}	
		}	
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void suspenderContrato(Contrato contrato) {
		Parametro parametro = parametroService.recuperarParametro();
		if(contrato.getAutenticacoes() != null && !contrato.getAutenticacoes().isEmpty()){
			for(ContratoAutenticacao contratoAutenticacao:contrato.getAutenticacoes()){
				Radreply radreply = new Radreply();
				radreply.setAttribute("Mikrotik-Rate-Limit");
				radreply.setOp(":=");
				radreply.setValue(parametro.getUploadSuspensao().toString()+"k/"+parametro.getDownloadSuspensao().toString()+"k");
				radreply.setUsername(contratoAutenticacao.getUsername());
				radreplyService.incluir(radreply);
			}	
		}	
	}
	
	//TODO REMOVER A SUSPENSAO 
	public void removerSuspensaoContrato(Contrato contrato){
		if(contrato.getAutenticacoes() != null && !contrato.getAutenticacoes().isEmpty()){
			for(ContratoAutenticacao contratoAutenticacao:contrato.getAutenticacoes()){
				radreplyService.excluirPorUsernameAttribute(contratoAutenticacao.getUsername(),"Mikrotik-Rate-Limit");
			}	
		}	
	}
	
	public void excluirPorUsername(String username){
		radcheckService.excluirPorUsername(username);
		radusergroupService.excluirPorUsername(username);
	}
	
}
