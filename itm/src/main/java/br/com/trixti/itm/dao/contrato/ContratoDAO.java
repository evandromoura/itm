package br.com.trixti.itm.dao.contrato;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoGrupo;
import br.com.trixti.itm.entity.ContratoProduto;

@Stateless
public class ContratoDAO extends AbstractDAO<Contrato> {

	public Contrato recuperarCompleto(Integer id) {
		return inicializarContrato(recuperar(id));
	}
	
	
	private Contrato inicializarContrato(Contrato contrato){
		
		contrato.getCliente().getId();
		if(contrato.getContratoEquipamentos() != null){
			for(ContratoEquipamento contratoEquipamento:contrato.getContratoEquipamentos()){
				contratoEquipamento.getEquipamento().getId();
			}
		}
		if(contrato.getContratoProdutos() != null){
			for(ContratoProduto contratoProduto:contrato.getContratoProdutos()){
				contratoProduto.getProduto().getId();
				contratoProduto.getProduto().getNome();
			}
		}
		
		if(contrato.getContratoGrupos() != null){
			for(ContratoGrupo contratoGrupo:contrato.getContratoGrupos()){
				contratoGrupo.getGrupo().getId();
			}
		}
		
		if(contrato.getBoletos() != null){
			contrato.getBoletos().size();
			for(Boleto boleto:contrato.getBoletos()){
				if(boleto.getLancamentos() != null){
					for(BoletoLancamento boletoLancamento:boleto.getLancamentos()){
						boletoLancamento.getContratoLancamento().getDescricao();
					}
				}
			}
		}
		if(contrato.getAutenticacoes() != null){
			contrato.getAutenticacoes().size();
		}
		
		if(contrato.getContaCorrente() != null){
			contrato.getContaCorrente().getId();
		}
		
		return contrato;
	}
	
	public List<Contrato> listarAtivo() {
		CriteriaQuery<Contrato> criteria = getCriteriaBuilder().createQuery(Contrato.class);
		Root<Contrato> root = criteria.from(Contrato.class);
		return getManager().createQuery(
				criteria.select(root).where(getCriteriaBuilder().isNull(root.get("dataExclusao")))).getResultList();
	}

}
