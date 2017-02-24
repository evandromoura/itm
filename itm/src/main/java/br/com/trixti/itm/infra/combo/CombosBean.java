package br.com.trixti.itm.infra.combo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.service.grupo.GrupoService;
import br.com.trixti.itm.service.produto.ProdutoService;

@Named
public class CombosBean {

	private @Inject ProdutoService produtoService;
	private @Inject EquipamentoService equipamentoService;
	private @Inject GrupoService grupoService;
	
	public TipoPessoaEnum[] getTipoPessoa(){
		return TipoPessoaEnum.values();
	}
	
   public List<Produto> getListaProduto(){
	   return produtoService.listar();
   } 	
   
   public List<Equipamento> getListaEquipamento(){
	   return equipamentoService.listar();
   }
   
   public List<Grupo> getListaGrupo(){
	   return grupoService.listar();
   }
	
}
 