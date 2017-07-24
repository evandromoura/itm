package br.com.trixti.itm.service.remessa;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.remessa.RemessaDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.enums.StatusRemessaEnum;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;

@Stateless
public class RemessaService extends AbstractService<Remessa>{

	private @Inject RemessaDAO remessaDAO;
	private @Inject BoletoService boletoService;

	@Override
	public AbstractDAO<Remessa> getDAO() {
		return remessaDAO;
	}
	
	public List<Remessa> pesquisar(Remessa remessa){
		return remessaDAO.pesquisar(remessa);
	}

	public Remessa recuperarCompleto(Integer id) {
		return remessaDAO.recuperarCompleto(id);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void enviarRemessa(Remessa remessa){
		remessa = remessaDAO.recuperarCompleto(remessa.getId());
		remessa.setDataEnvio(new Date());
		remessa.setStatus(StatusRemessaEnum.ENVIADO);
		remessaDAO.alterar(remessa);
	}
	
	public void removerBoletoRemessa(Boleto boleto){
		Remessa remessa = recuperar(boleto.getRemessa().getId());
		remessa.setValor(remessa.getValor().subtract(boleto.getValor()));
		remessa.setQtdBoletoAberto(remessa.getQtdBoletoAberto() - 1);
		remessaDAO.alterar(remessa);
		boleto.setRemessa(null);
		boletoService.alterar(boleto);
	}
	
}
