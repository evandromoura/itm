package br.com.trixti.itm.dao.servico;

import java.io.Serializable;

import javax.ejb.Stateless;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Servico;

@Stateless
public class ServicoDAO extends AbstractDAO<Servico> {

	@Override
	public Servico recuperar(Serializable id) {
		Servico servico = super.recuperar(id);
		if(servico.getLocais() != null){
			servico.getLocais().size();
		}
		return servico;
	}

}

