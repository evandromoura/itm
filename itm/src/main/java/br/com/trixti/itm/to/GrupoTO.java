package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.GrupoParametro;

public class GrupoTO {
	
	private Grupo grupo;
	private Grupo grupoPesquisa;
	private List<Grupo> grupos;
	
	private GrupoParametro grupoParametro;
	
	
	public Grupo getGrupo() {
		if (grupo == null) {
			grupo = new Grupo();
		}
		return grupo;
	}
	
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public List<Grupo> getGrupos() {
		if (grupos == null) {
			grupos = new ArrayList<Grupo>();
		}

		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}


	public Grupo getGrupoPesquisa() {
		if (grupoPesquisa == null) {
			grupoPesquisa = new Grupo();
		}
		return grupoPesquisa;
	}


	public void setGrupoPesquisa(Grupo grupoPesquisa) {
		this.grupoPesquisa = grupoPesquisa;
	}


	public GrupoParametro getGrupoParametro() {
		if (grupoParametro == null) {
			grupoParametro = new GrupoParametro();
		}
		return grupoParametro;
	}


	public void setGrupoParametro(GrupoParametro grupoParametro) {
		this.grupoParametro = grupoParametro;
	}
	
	
	

}
