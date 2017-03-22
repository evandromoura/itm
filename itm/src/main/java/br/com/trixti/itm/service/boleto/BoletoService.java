package br.com.trixti.itm.service.boleto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.boleto.BoletoDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteProduto;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.cliente.ClienteService;

@Stateless
public class BoletoService extends AbstractService<Boleto>{

	private @Inject BoletoDAO boletoDAO;
	private @Inject ClienteService clienteService;
	
	public void criarBoleto()throws Exception{
		Date dataAtual = new Date();
		List<Cliente> listaCliente = clienteService.listarAtivo();
		for(Cliente cliente:listaCliente){
			List<Boleto> listaBoleto = pesquisarBoletoCliente(cliente, dataAtual);
			if(listaBoleto == null || listaBoleto.isEmpty()){
				BigDecimal totalBoleto = new BigDecimal(0);
				for(ClienteProduto clienteProduto:cliente.getClienteProdutos()){
					if(clienteProduto.isAtivo() && 
							(clienteProduto.getDataInicio().before(dataAtual) && 
								clienteProduto.getDataFim().after(dataAtual))){
						totalBoleto = totalBoleto.add(clienteProduto.getValor());
					}
				}
				System.out.println("Valor do Boleto = "+totalBoleto);
				if(totalBoleto.intValue() > 0){
					Boleto boleto = new Boleto();
					boleto.setCliente(cliente);
					boleto.setDataCriacao(dataAtual);
					boleto.setValor(totalBoleto);
					//TODO: Calcular com o dia/mes vendimento do cliente
					boleto.setDataVencimento(dataAtual);
					super.incluir(boleto);
				}	
			}
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
