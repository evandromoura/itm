package br.com.trixti.itm.threads.financeiro;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.jrimum.bopepo.BancosSuportados;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.infra.financeiro.IntegracaoFinanceiraItau;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.retorno.RetornoService;
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
	private @Inject IntegracaoFinanceiraItau integracaoFinanceiraItau;
	private @Inject RetornoService retornoService;
	private Parametro parametro;

	
	
	@Schedule(minute = "*", hour = "*", persistent = false)
	public void processarBoleto() {
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
	
	@Schedule(minute = "*", hour = "*", persistent = false)
	public void processarRemessa(){
		List<Boleto> listaBoleto = boletoService.pesquisarBoletoSemRemessa();
		Map<String,List<Boleto>> mapaBoletoBanco = new HashMap<String,List<Boleto>>();
		for(Boleto boleto:listaBoleto){
			if(boleto.getStatus().equals(StatusBoletoEnum.ABERTO)){
				if(mapaBoletoBanco.get(boleto.getContrato().getContaCorrente().getBanco()) == null){
					mapaBoletoBanco.put(boleto.getContrato().getContaCorrente().getBanco(), new ArrayList<Boleto>());
				}
				mapaBoletoBanco.get(boleto.getContrato().getContaCorrente().getBanco()).add(boleto);
			}	
		}
		for(String banco:mapaBoletoBanco.keySet()){
			if(banco.equals(BancosSuportados.BANCO_ITAU.name())){
				integracaoFinanceiraItau.gerarRemessa(mapaBoletoBanco.get(banco));
			}
		}
		System.out.println(mapaBoletoBanco);
	}
	
	@Schedule(minute = "*", hour = "*", persistent = false)
	public void processarRetorno(){
		List<Retorno> listaRetorno = retornoService.listarPendentes();
		Map<String,List<Retorno>> mapaRetorno = new HashMap<String,List<Retorno>>();
		for(Retorno retorno:listaRetorno){
			if(retorno.getBanco().equals(BancosSuportados.BANCO_ITAU.name())){
				if(mapaRetorno.get(retorno.getBanco()) == null){
					mapaRetorno.put(retorno.getBanco(), new ArrayList<Retorno>());
				}
				mapaRetorno.get(retorno.getBanco()).add(retorno);
			}
		}
		for(String banco:mapaRetorno.keySet()){
			if(BancosSuportados.BANCO_ITAU.name().equals(banco)){
				integracaoFinanceiraItau.processarRetorno(mapaRetorno.get(banco));
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
						comporNossoNumero(boleto);
						boletoService.incluir(boleto);
					}else{
						boleto.setContrato(contrato);
						boleto.setLancamentos(lancamentosBoleto);
						boleto.setDataCriacao(new Date());
						boleto.setStatus(StatusBoletoEnum.PAGO);
						boleto.setValor(BigDecimal.ZERO);
						boleto.setDataVencimento(dataVencimento);
						comporNossoNumero(boleto);
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
	
	private void comporNossoNumero(Boleto boleto){
		BigInteger nossoNumero = boletoService.recuperarNossoNumero();
		boleto.setNumeroDocumento(nossoNumero.toString());
		boleto.setNossoNumero(nossoNumero.toString());
		boleto.setDigitoNossoNumero(String.valueOf(new CalculaBase10().getMod10(nossoNumero.toString())));
		boleto.setNossoNumeroCompleto(boleto.getNossoNumero()+boleto.getDigitoNossoNumero());
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