package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Log;

public class LogTO {

	private Log log;
	private List<Log> logs;
	private Log logPesquisa;
	
	
	public Log getLog() {
		if (log == null) {
			log = new Log();
		}
		return log;
	}
	
	public void setLog(Log log) {
		this.log = log;
	}
	
	public List<Log> getLogs() {
		if (logs == null) {
			logs = new ArrayList<Log>();
		}

		return logs;
	}
	
	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Log getLogPesquisa() {
		if (logPesquisa == null) {
			logPesquisa = new Log();
		}
		return logPesquisa;
	}

	public void setLogPesquisa(Log logPesquisa) {
		this.logPesquisa = logPesquisa;
	}

}