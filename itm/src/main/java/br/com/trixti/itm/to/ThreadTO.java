package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Timer;

public class ThreadTO {
	
	private List<Timer> timers;

	public List<Timer> getTimers() {
		if (timers == null) {
			timers = new ArrayList<Timer>();
		}
		return timers;
	}

	public void setTimers(List<Timer> timers) {
		this.timers = timers;
	}

}
