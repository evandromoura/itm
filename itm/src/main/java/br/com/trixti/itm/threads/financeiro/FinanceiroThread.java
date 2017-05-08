package br.com.trixti.itm.threads.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.util.UtilData;

@Named
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FinanceiroThread {

	private @Inject ClienteService clienteService;
	private @Inject ContratoProdutoService contratoProdutoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject ContratoService contratoService;
	private @Inject BoletoService boletoService;
	private @Inject FreeRadiusService freeRadiusService;
	private @Inject ParametroService parametroService;
	private Parametro parametro;

	@Schedule(minute = "*", hour = "*", persistent = false)
	public void executar() throws Exception {
		parametro = parametroService.recuperarParametro();
		List<Cliente> clientes = clienteService.listarAtivo();
		for (Cliente cliente : clientes) {
			BigDecimal valor = BigDecimal.ZERO;
			List<BoletoLancamento> lancamentosBoleto = new ArrayList<BoletoLancamento>();
			for (Contrato contrato : cliente.getContratos()) {
				gerarBoleto(valor, lancamentosBoleto, contrato);
				verificarContrato(contrato);
			}
		}
	}
	
	
	private void verificarContrato(Contrato contrato){

		/*
		 * Bloqueio
		 */
		UtilData utilData = new UtilData();
		List<Boleto> boletos = boletoService.pesquisarBoletoEmAbertoContrato(contrato);
		boolean desbloquear = true;
		for(Boleto boleto:boletos){
			if(utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasBloqueio()  && 
					(contrato.getDataParaBloqueio() == null || 
						utilData.data1MaiorIgualData2(new Date(),contrato.getDataParaBloqueio()))){
				if(contrato.getStatus().equals(StatusContrato.ATIVO)){
					contrato.setStatus(StatusContrato.BLOQUEADO);
					contratoService.alterar(contrato);
					freeRadiusService.bloquearContrato(contrato);
				}	
				desbloquear = false;
			}
		}
		if(desbloquear && contrato.getStatus().equals(StatusContrato.BLOQUEADO)){
			contrato.setStatus(StatusContrato.ATIVO);
			contrato.setDataParaBloqueio(null);
			contratoService.alterar(contrato);
			freeRadiusService.desbloquearContrato(contrato);
		}
		
	}

	private void gerarBoleto(BigDecimal valor, List<BoletoLancamento> lancamentosBoleto, Contrato contrato) {
		if (contrato.isGeraBoleto()){
				List<ContratoLancamento> lancamentosEmAberto = contratoLancamentoService.pesquisarLancamentoAberto(contrato);
				List<ContratoProduto> produtos = contratoProdutoService.pesquisarVigentePorContrato(contrato);
				Boleto boleto = new Boleto();
				UtilData utilData = new UtilData();
				Date dataVencimento = utilData.ajustaData(utilData.adicionarMeses(new Date(), 1),contrato.getDiaMesVencimento(), 23, 59, 59);
				Boleto boletoJaCriado = boletoService.recuperarBoletoContrato(contrato, dataVencimento);
				if (boletoJaCriado == null) {
					for (ContratoLancamento lancamentoAberto : lancamentosEmAberto) {
						valor = lancamentoAberto.getTipoLancamento().equals(TipoLancamentoEnum.DEBITO)
								? valor.add(lancamentoAberto.getValor()) : valor.subtract(lancamentoAberto.getValor());
						lancamentosBoleto.add(new BoletoLancamento(boleto, lancamentoAberto));
					}

					for (ContratoProduto produto : produtos) {
						ContratoLancamento contratoLancamento = new ContratoLancamento();
						contratoLancamento.setContrato(contrato);
						contratoLancamento.setDataLancamento(new Date());
						contratoLancamento.setDescricao(produto.getProduto().getNome());
						contratoLancamento.setStatus(StatusLancamentoEnum.PENDENTE);
						contratoLancamento.setTipoLancamento(TipoLancamentoEnum.DEBITO);
						contratoLancamento.setValor(produto.getValor());
						contratoLancamentoService.incluir(contratoLancamento);
						
						BoletoLancamento boletoLancamento = new BoletoLancamento();
						boletoLancamento.setBoleto(boleto);
						boletoLancamento.setContratoLancamento(contratoLancamento);
						
						lancamentosBoleto.add(boletoLancamento);
						valor = valor.add(produto.getValor());
					}
					if(valor.intValue() > 0){
						boleto.setContrato(contrato);
						boleto.setLancamentos(lancamentosBoleto);
						boleto.setDataCriacao(new Date());
						boleto.setStatus(StatusBoletoEnum.ABERTO);
						boleto.setValor(valor);
						boleto.setDataVencimento(dataVencimento);
						boletoService.incluir(boleto);
					}else{
						boleto.setContrato(contrato);
						boleto.setLancamentos(lancamentosBoleto);
						boleto.setDataCriacao(new Date());
						boleto.setStatus(StatusBoletoEnum.PAGO);
						boleto.setValor(BigDecimal.ZERO);
						boleto.setDataVencimento(dataVencimento);
						if(boleto.getLancamentos() != null && !boleto.getLancamentos().isEmpty()){
							boletoService.incluir(boleto);
						}	
						//TODO: getResources
						if(valor.abs().intValue() > 0){
							contratoLancamentoService.incluir(
									new ContratoLancamento("Credito em Conta",contrato,valor.abs(),TipoLancamentoEnum.CREDITO, 
											new Date(), StatusLancamentoEnum.PENDENTE));
						}	
						
				}	
			}
		}
	}
	
	public static void main(String[] args) {
		BigDecimal total = new BigDecimal(-10.3);
		System.out.println(total);
	}


	public Parametro getParametro() {
		return parametro;
	}


	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
}