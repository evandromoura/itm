package br.com.trixti.itm.service.contratolancamento;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratolancamento.ContratoLancamentoDAO;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.contrato.ContratoService;

@Stateless
public class ContratoLancamentoService extends AbstractService<ContratoLancamento>{

	private @Inject ContratoLancamentoDAO contratoLancamentoDAO;
	private @Inject ContratoService contratoService;

	@Override
	public AbstractDAO<ContratoLancamento> getDAO() {
		return contratoLancamentoDAO;
	}

	public void criarLancamentos(){
		Date dataAtual = new Date();
		List<Contrato> listaContrato = contratoService.listarAtivo();
		for(Contrato contrato:listaContrato){
//TODO VOLTAR AQUI			List<ContratoLancamento> listaContratoLancamento = pesquisarLancamentoContrato(contrato, dataAtual);
			List<ContratoLancamento> listaContratoLancamento = null;
			if(listaContratoLancamento == null || listaContratoLancamento.isEmpty()){
				BigDecimal totalContratoLancamento = new BigDecimal(0);
				for(ContratoProduto contratoProduto:contrato.getContratoProdutos()){
					if(contratoProduto.isAtivo() && 
							(contratoProduto.getDataInicio().before(dataAtual) && 
								contratoProduto.getDataFim().after(dataAtual))){
						ContratoLancamento lancamento = new ContratoLancamento();
						lancamento.setContrato(contrato);
						lancamento.setDataLancamento(dataAtual);
						lancamento.setValor(contratoProduto.getValor());
						lancamento.setTipoLancamento(TipoLancamentoEnum.DEBITO);
						lancamento.setStatus(StatusLancamentoEnum.PENDENTE);
						lancamento.setDescricao(contratoProduto.getProduto().getNome());
						super.incluir(lancamento);
					}
				}
				if(totalContratoLancamento.intValue() > 0){
				}	
			}
		}
	}
	
	public List<ContratoLancamento> pesquisarLancamentoContrato(Contrato contrato,Date data){
		return contratoLancamentoDAO.pesquisarLancamentoContrato(contrato, data);
	}

	public List<ContratoLancamento> pesquisarLancamentoAberto(Contrato contrato) {
		return contratoLancamentoDAO.pesquisarLancamentoAberto(contrato); 
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void excluirPorContrato(Contrato contrato){
		contratoLancamentoDAO.excluirPorContrato(contrato);
	}

}
