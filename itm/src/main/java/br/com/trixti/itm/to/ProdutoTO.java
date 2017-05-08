package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Produto;

public class ProdutoTO {

	private Produto produto;
	private List<Produto> produtos;
	private Produto produtoPesquisa;
	
	
	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public List<Produto> getProdutos() {
		if (produtos == null) {
			produtos = new ArrayList<Produto>();
		}

		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProdutoPesquisa() {
		if (produtoPesquisa == null) {
			produtoPesquisa = new Produto();
		}
		return produtoPesquisa;
	}

	public void setProdutoPesquisa(Produto produtoPesquisa) {
		this.produtoPesquisa = produtoPesquisa;
	}

}