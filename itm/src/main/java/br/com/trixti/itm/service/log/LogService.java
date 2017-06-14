package br.com.trixti.itm.service.log;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.log.LogDAO;
import br.com.trixti.itm.entity.Log;
import br.com.trixti.itm.entity.TipoLog;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class LogService extends AbstractService<Log> {

	private @Inject LogDAO logDAO;
	
	
	@Override
	public AbstractDAO<Log> getDAO() {
		return logDAO;
	}
	
	public List<Log> pesquisar(Log log){
		return logDAO.listar();
	}
	
	
	public void log(String login,TipoLog tipo){
		Log log = new Log();
		log.setDataLog(new Date());
		log.setLogin(login);
		log.setTipo(tipo);
		logDAO.incluir(log);
	}
	

}






