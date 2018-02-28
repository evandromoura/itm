package br.com.trixti.itm.service.contratoproduto;

import java.util.List;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoproduto.ContratoProdutoDAO;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.service.AbstractService;

public class ContratoProdutoService extends AbstractService<ContratoProduto> {

	
	private @Inject ContratoProdutoDAO contratoProdutoDAO;
	
	@Override
	public AbstractDAO<ContratoProduto> getDAO() {
		return contratoProdutoDAO;
	}
	
	public List<ContratoProduto> pesquisarVigentePorContrato(Contrato contrato){
		return contratoProdutoDAO.pesquisarVigentePorContrato(contrato);
		
	}
	
	public List<ContratoProduto> listarAtivos(){
		return contratoProdutoDAO.listarAtivos();
	}
	
	public List<ContratoProduto> pesquisarPorTag(Tag tag){
		return contratoProdutoDAO.pesquisarPorTag(tag);
	}
	
	
	public List<ContratoProduto> listarSemTag(){
		return contratoProdutoDAO.listarSemTag();
	}

}
