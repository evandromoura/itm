package br.com.trixti.itm.infra.combo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jrimum.bopepo.BancosSuportados;

import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.enums.PerfilEnum;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.service.contacorrente.ContaCorrenteService;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.service.grupo.GrupoService;
import br.com.trixti.itm.service.produto.ProdutoService;

@Named
public class CombosBean {

	private @Inject ProdutoService produtoService;
	private @Inject EquipamentoService equipamentoService;
	private @Inject GrupoService grupoService;
	private @Inject ContaCorrenteService contaCorrenteService;
	
	public TipoPessoaEnum[] getTipoPessoa(){
		return TipoPessoaEnum.values();
	}

	public PerfilEnum[] getPerfis(){
		return PerfilEnum.values();
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
   
   public List<ContaCorrente> getListaContaCorrente(){
	   return contaCorrenteService.listar();
   }
   
   public TipoLancamentoEnum[] getListaTipoLancamento(){
	   return TipoLancamentoEnum.values();
   }
   
   public BancosSuportados[] getListaBancosSuportados(){
	   return BancosSuportados.values();
   }
	
}
 