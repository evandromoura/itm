package br.com.trixti.itm.service.boleto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.boleto.BoletoDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.enums.StatusRemessaEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boletolancamento.BoletoLancamentoService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.movimentacaofinanceira.MovimentacaoFinanceiraService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class BoletoService extends AbstractService<Boleto>{

	private @Inject BoletoDAO boletoDAO;
	private @Inject ContratoService contratoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject BoletoLancamentoService boletoLancamentoService;
	private @Inject ParametroService parametroService;
	private @Inject RemessaService remessaService;
	private @Inject MovimentacaoFinanceiraService movimentacaoFinanceiraService;
	private @Inject MailService mailService;
	private @Inject SMSService smsService;
	
	public void criarBoleto()throws Exception{
		Date dataAtual = new Date();
		List<Contrato> listaContrato = contratoService.listarAtivo();
		for(Contrato contrato:listaContrato){
			List<ContratoLancamento> listaLancamentoAberto  = contratoLancamentoService.pesquisarLancamentoAberto(contrato);
			Boleto boleto = new Boleto();
			List<BoletoLancamento> listaBoletoLancamento = new ArrayList<BoletoLancamento>();
			boleto.setDataCriacao(dataAtual);
			boleto.setContrato(contrato);
			boleto.setDataVencimento(dataAtual);
			BigDecimal totalBoleto = new BigDecimal(0);
			for(ContratoLancamento contratoLancamento:listaLancamentoAberto){
				BoletoLancamento lancamento = new BoletoLancamento();
				lancamento.setBoleto(boleto);
				lancamento.setContratoLancamento(contratoLancamento);
				totalBoleto = contratoLancamento.getTipoLancamento().equals(TipoLancamentoEnum.DEBITO)?
							totalBoleto.add(contratoLancamento.getValor()):totalBoleto.subtract(contratoLancamento.getValor());
			}
			boleto.setValor(totalBoleto);
			boleto.setLancamentos(listaBoletoLancamento);
			BigInteger nossoNumero = recuperarNossoNumero();
			boleto.setNumeroDocumento(nossoNumero.toString());
			boleto.setNossoNumero(nossoNumero.toString());
			boleto.setDigitoNossoNumero(String.valueOf(new CalculaBase10().getMod10(nossoNumero.toString())));
			boleto.setNossoNumeroCompleto(boleto.getNossoNumero()+boleto.getDigitoNossoNumero());
			super.incluir(boleto);
		}
	}
	
	public List<Boleto> pesquisarBoletoCliente(Cliente cliente,Date data){
		return boletoDAO.pesquisarBoletoCliente(cliente, data);
	}
	
	public List<Boleto> pesquisarBoletoRemessa(Remessa remessa){
		return boletoDAO.pesquisarBoletoRemessa(remessa);
	}
	
	public Boleto recuperarBoletoContrato(Contrato contrato,Date dataVencimento){
		try{
			return boletoDAO.recuperarBoletoContrato(contrato,dataVencimento);
		}catch(Exception e){
			return null;
		}	
	}
	
	
	public Boleto recuperarBoletoContratoMes(Contrato contrato, Date data){
		try{
			return boletoDAO.recuperarBoletoContratoMes(contrato,data);
		}catch(Exception e){
			return null;
		}	
	}

	@Override
	public AbstractDAO<Boleto> getDAO() {
		return boletoDAO;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Boleto> pesquisarBoletoEmAbertoContrato(Contrato contrato) {
		return boletoDAO.pesquisarBoletoEmAbertoContrato(contrato);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Boleto> pesquisarBoletoEmAberto() {
		return boletoDAO.pesquisarBoletoEmAberto();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Boleto> pesquisarBoletoEmAberto(Remessa remessa) {
		return boletoDAO.pesquisarBoletoEmAberto(remessa);
	}
	
	public List<Boleto> pesquisarBoletoEmAbertoContratoComAviso(Contrato contrato) {
		return boletoDAO.pesquisarBoletoEmAbertoContratoComAviso(contrato);
	}
	public List<Boleto> pesquisarBoletoEmAtraso(Contrato contrato) {
		return boletoDAO.pesquisarBoletoEmAtraso(contrato);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void excluirPorContrato(Contrato contrato){
		boletoLancamentoService.excluirPorContrato(contrato);
		boletoDAO.excluirPorContrato(contrato);
	}
	
	
	public List<Boleto> pesquisarUltimosBoletosCliente(Cliente cliente){
		return boletoDAO.pesquisarUltimosBoletosCliente(cliente);
		
	}

	public List<Boleto> pesquisarBoletoSemRemessa() {
		return boletoDAO.pesquisarBoletoSemRemessa();
	}
	
	public void alterarLista(List<Boleto> boletos){
		for(Boleto boleto:boletos){
			alterar(boleto);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BigInteger recuperarNossoNumero(){
		BigInteger nossoNumero = boletoDAO.recuperarNossoNumero();
		System.out.println("Gerou o NOSSO NUMEROO: "+nossoNumero);
		return nossoNumero;
	}

	public Boleto recuperarPorNossoNumero(String nossoNumero,StatusBoletoEnum... status) {
		try{
			return boletoDAO.recuperarPorNossoNumero(nossoNumero,status);
		}catch(Exception e){
			return null;
		}	
		
	}

	public List<Boleto> pesquisarBoletoNaoNotificado() {
		return boletoDAO.pesquisarBoletoNaoNotificado();
	}

	public List<Boleto> listarBoletoEmAtraso() {
		return boletoDAO.listarBoletoEmAtraso();
	}
	
	public BigDecimal somarBoletoEmAtraso(){
		return boletoDAO.somarBoletoEmAtraso();
	}

	public void pagar(Boleto boleto,String chaveMestra) throws Exception{
		Parametro parametro = parametroService.recuperarParametro();
		if(DigestUtils.md5Hex(chaveMestra).equals(parametro.getChaveMestra())){
			boleto.setDataPagamento(new Date());
			boleto.setStatus(StatusBoletoEnum.PAGO);
			boleto.getRemessa().setQtdBoletoAberto(boleto.getRemessa().getQtdBoletoAberto() - 1);
			boleto.getRemessa().setQtdBoletoFechado(boleto.getRemessa().getQtdBoletoFechado() + 1);
			boleto.getRemessa().setValorRecebido(boleto.getRemessa().getValorRecebido().add(boleto.getValorPago()));
			if(boleto.getRemessa().getQtdBoletoAberto().equals(0)){
				boleto.getRemessa().setStatus(StatusRemessaEnum.FECHADO);
			}
			alterar(boleto);
			remessaService.alterar(boleto.getRemessa());
			movimentacaoFinanceiraService.incluir(boleto);
			
		}else{
			throw new Exception("CHAVE MESTRA INVALIDA");
		}	
	}
	
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("pagamentoitrix@!"));
	}
	
	
	public void enviarEmail(Boleto boleto){
		mailService.enviarEmail(boleto,null,"Sua fatura está disponível");
	}

	public void enviarSMS(Boleto boleto){
		smsService.enviarSMS(boleto);
	}
	
	public void atualizarNotificacao(Boleto boleto, Date dataNotificacao){
		Boleto boletoRec = recuperar(boleto.getId());
		boletoRec.setDataNotificacao(dataNotificacao);
		boletoDAO.alterar(boletoRec);
	}
	
	public void enviarSegundaVia(Boleto boleto){
		UtilData utilData = new UtilData();
		boleto.setDataVencimento(utilData.adicionaDias(new Date(), 1));
		enviarEmail(boleto);
		enviarSMS(boleto);
	}
	
}
