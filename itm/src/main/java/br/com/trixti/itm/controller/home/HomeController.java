package br.com.trixti.itm.controller.home;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import br.com.trixti.itm.to.HomeTO;

@Model
public class HomeController {

	private HomeTO homeTO;
	
	@PostConstruct
	private void init(){
		
	}


	public HomeTO getHomeTO() {
		if (homeTO == null) {
			homeTO = new HomeTO();
		}
		return homeTO;
	}


	public void setHomeTO(HomeTO homeTO) {
		this.homeTO = homeTO;
	}

	
}
