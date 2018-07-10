package br.com.trixti.itm.infra.combo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jrimum.bopepo.BancosSuportados;

import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.EquipamentoMarca;
import br.com.trixti.itm.entity.EquipamentoMarcaModelo;
import br.com.trixti.itm.entity.EquipamentoTipo;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusEquipamentoEnum;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.entity.TecnologiaEnum;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.entity.TipoMovimentacaoFinanceira;
import br.com.trixti.itm.entity.TipoProduto;
import br.com.trixti.itm.entity.TipoServico;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.entity.UnidadeEquipamentoEnum;
import br.com.trixti.itm.enums.PerfilEnum;
import br.com.trixti.itm.enums.StatusRemessaEnum;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.service.centrocusto.CentroCustoService;
import br.com.trixti.itm.service.cidade.CidadeService;
import br.com.trixti.itm.service.contacorrente.ContaCorrenteService;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.service.equipamentomarca.EquipamentoMarcaService;
import br.com.trixti.itm.service.equipamentomarcamodelo.EquipamentoMarcaModeloService;
import br.com.trixti.itm.service.equipamentotipo.EquipamentoTipoService;
import br.com.trixti.itm.service.grupo.GrupoService;
import br.com.trixti.itm.service.produto.ProdutoService;
import br.com.trixti.itm.service.servico.ServicoService;
import br.com.trixti.itm.service.tag.TagService;
import br.com.trixti.itm.service.uf.UfService;

@Named
public class CombosBean {

	private @Inject ProdutoService produtoService;
	private @Inject EquipamentoService equipamentoService;
	private @Inject GrupoService grupoService;
	private @Inject ContaCorrenteService contaCorrenteService;
	private @Inject TagService tagService;
	private @Inject CentroCustoService centroCustoService;
	private @Inject UfService ufService;
	private @Inject CidadeService cidadeService;
	private @Inject ServicoService servicoService;
	private @Inject EquipamentoMarcaService equipamentoMarcaService;
	private @Inject EquipamentoMarcaModeloService equipamentoMarcaModeloService;
	private @Inject EquipamentoTipoService equipamentoTipoService;
	
	public TipoPessoaEnum[] getTipoPessoa(){
		return TipoPessoaEnum.values();
	}

	public PerfilEnum[] getPerfis(){
		return PerfilEnum.values();
	}
	
	public TipoServico[] getTipoServicos(){
		return TipoServico.values();
	}
	
	public TipoProduto[] getTipoProdutos(){
		return TipoProduto.values();
	}
	
	public TecnologiaEnum[] getTecnologias(){
		return TecnologiaEnum.values();
	}
	
	public TipoCentroCusto[] getTipoCentroCustos(){
		return TipoCentroCusto.values();
	}
	
	public StatusEquipamentoEnum[] getStatusEquipamentos(){
		return StatusEquipamentoEnum.values();
	}
	
	public UnidadeEquipamentoEnum[] getUnidadeEquipamentos(){
		return UnidadeEquipamentoEnum.values();
	}
	
	public PerfilEnum[] getPerfis(PerfilEnum perfil){
		List<PerfilEnum> listaPerfilPermitido = new ArrayList<PerfilEnum>();
		boolean encontrou = false;
		for(PerfilEnum perfilEnum:PerfilEnum.values()){
			if(perfilEnum.equals(perfil)){
				encontrou = true;
			}
			if(encontrou){
				listaPerfilPermitido.add(perfilEnum);
			}
		}
		return (PerfilEnum[]) listaPerfilPermitido.toArray(new PerfilEnum[listaPerfilPermitido.size()]);
	}
	
   public List<Produto> getListaProduto(){
	   return produtoService.listar();
   } 
   
   
   public List<CentroCusto> getListaCentroCusto(){
	   return centroCustoService.listar();
   } 
   
   public List<Equipamento> getListaEquipamento(){
	   return equipamentoService.listar();
   }
   
   public List<Grupo> getListaGrupo(){
	   return grupoService.listar();
   }
   
   public List<Servico> getListaServico(){
	   return servicoService.listar();
   }
   
   public List<Uf> getListaUf(){
	   return ufService.listar();
   }
   
   public List<Cidade> getListaCidadePorUf(Uf uf){
	   try{
		   return cidadeService.pesquisarPorUf(uf);
	   }catch(Exception e){
		   return null;
	   }	   
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
   
   public StatusContrato[] getStatusContrato(){
		return StatusContrato.values();
	}
   
   public StatusRemessaEnum[] getStatusRemessaEnum(){
	   return StatusRemessaEnum.values();
   }
   
   public List<Tag> getListaTag(){
	   return tagService.listar();
   }
   
   public TipoMovimentacaoFinanceira[] getListaTipoMovimentacaoFinanceira(){
	   return TipoMovimentacaoFinanceira.values();
   }
   
//   public List<SelectItem> getListYears(){
//		UtilData utilData = new UtilData();
//		int yearFirst = utilData.getAno(queueLog.getCallDate());
//		int year = utilData.getAno(new Date());
//		int diff = year - yearFirst;
//		for(int i = 0;i<=diff;i++){
//			listYear.add(new SelectItem(String.valueOf(yearFirst + i),String.valueOf(yearFirst + i)));
//		}
//		return listYear;
//	}
   
   public List<EquipamentoMarca> getListaEquipamentoMarca(EquipamentoTipo tipo){
	   return  equipamentoMarcaService.pesquisarPorTipo(tipo);
   }
   
   public List<EquipamentoMarcaModelo> getListaEquipamentoMarcaModelo(EquipamentoMarca marca){
	   return  equipamentoMarcaModeloService.pesquisarPorMarca(marca);
   }
   
   public List<EquipamentoTipo> getListaEquipamentoTipo(){
	   return equipamentoTipoService.listar();
   }
	
}
 