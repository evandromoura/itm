package br.com.trixti.itm.service.radreply;

import javax.inject.Inject;

import org.apache.commons.net.util.SubnetUtils;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.radreply.RadreplyDAO;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Radreply;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.parametro.ParametroService;

public class RadreplyService extends AbstractService<Radreply> {

	
	private @Inject RadreplyDAO radcheckDAO;
	private @Inject ParametroService parametroService;
	
	@Override
	public AbstractDAO<Radreply> getDAO() {
		return radcheckDAO;
	}
	
	public void excluirPorUsername(String username){
		radcheckDAO.excluirPorUsername(username);
	}

	public void excluirPorUsernameAttributeValue(String username, String attribute, String value) {
		radcheckDAO.excluirPorUsernameAttributeValue(username,attribute,value);
	}
	
	public void excluirPorUsernameAttribute(String username, String attribute) {
		radcheckDAO.excluirPorUsernameAttribute(username,attribute);
	}
	
	public String recuperarIpDisponivel(){
		Parametro parametro = parametroService.recuperarParametro();
		SubnetUtils utils = new SubnetUtils(parametro.getFramedIpStart());
		String[] allIps = utils.getInfo().getAllAddresses();
		for(String ip:allIps){
			if(radcheckDAO.recuperarPorValue(ip) == null){
				return ip;
			}
		}
		return null;
	}
}
