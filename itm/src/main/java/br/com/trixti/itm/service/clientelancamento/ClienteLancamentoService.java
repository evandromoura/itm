package br.com.trixti.itm.service.clientelancamento;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.clientelancamento.ClienteLancamentoDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteLancamento;
import br.com.trixti.itm.entity.ClienteProduto;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.cliente.ClienteService;

@Stateless
public class ClienteLancamentoService extends AbstractService<ClienteLancamento>{

	private @Inject ClienteLancamentoDAO clienteLancamentoDAO;
	private @Inject ClienteService clienteService;

	@Override
	public AbstractDAO<ClienteLancamento> getDAO() {
		return clienteLancamentoDAO;
	}

	public void criarLancamentos(){
		Date dataAtual = new Date();
		List<Cliente> listaCliente = clienteService.listarAtivo();
		for(Cliente cliente:listaCliente){
//TODO VOLTAR AQUI			List<ClienteLancamento> listaClienteLancamento = pesquisarLancamentoCliente(cliente, dataAtual);
			List<ClienteLancamento> listaClienteLancamento = null;
			if(listaClienteLancamento == null || listaClienteLancamento.isEmpty()){
				BigDecimal totalClienteLancamento = new BigDecimal(0);
				for(ClienteProduto clienteProduto:cliente.getClienteProdutos()){
					if(clienteProduto.isAtivo() && 
							(clienteProduto.getDataInicio().before(dataAtual) && 
								clienteProduto.getDataFim().after(dataAtual))){
						ClienteLancamento lancamento = new ClienteLancamento();
						lancamento.setCliente(cliente);
						lancamento.setDataLancamento(dataAtual);
						lancamento.setValor(clienteProduto.getValor());
						lancamento.setTipoLancamento(TipoLancamentoEnum.DEBITO);
						lancamento.setStatus(StatusLancamentoEnum.PENDENTE);
						lancamento.setDescricao(clienteProduto.getProduto().getNome());
						super.incluir(lancamento);
					}
				}
				System.out.println("Valor do ClienteLancamento = "+totalClienteLancamento);
				if(totalClienteLancamento.intValue() > 0){
				}	
			}
		}
	}
	
	public List<ClienteLancamento> pesquisarLancamentoCliente(Cliente cliente,Date data){
		return clienteLancamentoDAO.pesquisarLancamentoCliente(cliente, data);
	}

	public List<ClienteLancamento> pesquisarLancamentoAberto(Cliente cliente) {
		return clienteLancamentoDAO.pesquisarLancamentoAberto(cliente); 
	}
	
}
