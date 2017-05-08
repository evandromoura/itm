package br.com.trixti.itm.service.produto;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.produto.ProdutoDAO;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class ProdutoService extends AbstractService<Produto> {

	private @Inject ProdutoDAO produtoDAO;
	
	
	@Override
	public AbstractDAO<Produto> getDAO() {
		return produtoDAO;
	}


	public List<Produto> pesquisar(Produto produto) {
		return produtoDAO.pesquisar(produto);
	}
	

}






