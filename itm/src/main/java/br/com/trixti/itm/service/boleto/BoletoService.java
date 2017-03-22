package br.com.trixti.itm.service.boleto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.boleto.BoletoDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteLancamento;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.clientelancamento.ClienteLancamentoService;

@Stateless
public class BoletoService extends AbstractService<Boleto>{

	private @Inject BoletoDAO boletoDAO;
	private @Inject ClienteService clienteService;
	private @Inject ClienteLancamentoService clienteLancamentoService;
	
	public void criarBoleto()throws Exception{
		Date dataAtual = new Date();
		List<Cliente> listaCliente = clienteService.listarAtivo();
		for(Cliente cliente:listaCliente){
			List<ClienteLancamento> listaLancamentoAberto  = clienteLancamentoService.pesquisarLancamentoAberto(cliente);
			
			Boleto boleto = new Boleto();
			List<BoletoLancamento> listaBoletoLancamento = new ArrayList<BoletoLancamento>();
			boleto.setDataCriacao(dataAtual);
			boleto.setCliente(cliente);
			boleto.setDataVencimento(dataAtual);
			BigDecimal totalBoleto = new BigDecimal(0);
			
			for(ClienteLancamento clienteLancamento:listaLancamentoAberto){
				BoletoLancamento lancamento = new BoletoLancamento();
				lancamento.setBoleto(boleto);
				lancamento.setClienteLancamento(clienteLancamento);
				totalBoleto = clienteLancamento.getTipoLancamento().equals(TipoLancamentoEnum.DEBITO)?
							totalBoleto.add(clienteLancamento.getValor()):totalBoleto.subtract(clienteLancamento.getValor());
			}
			boleto.setValor(totalBoleto);
			boleto.setLancamentos(listaBoletoLancamento);
			super.incluir(boleto);
			
		}
	}
	
	public List<Boleto> pesquisarBoletoCliente(Cliente cliente,Date data){
		return boletoDAO.pesquisarBoletoCliente(cliente, data);
	}

	@Override
	public AbstractDAO<Boleto> getDAO() {
		return boletoDAO;
	}
}
