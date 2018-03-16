package br.com.trixti.itm.infra.financeiro;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusRetorno;
import br.com.trixti.itm.enums.StatusRemessaEnum;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.movimentacaofinanceira.MovimentacaoFinanceiraService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.service.retorno.RetornoService;
import br.com.trixti.itm.util.Base64Utils;
import br.com.trixti.itm.util.FileUtil;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilString;

@Named
public class IntegracaoFinanceiraItau {

	private Parametro parametro;
	private @Inject ParametroService parametroService;
	private @Inject RemessaService remessaService;
	private @Inject RetornoService retornoService;
	private @Inject BoletoService boletoService;
	private @Inject MovimentacaoFinanceiraService movimentacaoFinanceiraService;

	@PostConstruct
	private void init() {
		parametro = parametroService.recuperarParametro();
	}

	public void gerarRemessa(List<Boleto> boletos) {
		Remessa remessa = new Remessa();
		File layout=null;
		File arquivoFinal=null;
		UtilArquivo utilArquivo = new UtilArquivo();
		UtilData utilData = new UtilData();
		UtilString utilString = new UtilString();
		try {
			if (boletos != null && !boletos.isEmpty()) {
				arquivoFinal = new File("arquivo-final.txt");
				BigDecimal valorTotal = BigDecimal.ZERO;
				layout = utilArquivo.getFileFromBytes(UtilArquivo.converteInputStreamEmBytes(IntegracaoFinanceiraItau.class.getClassLoader().getResourceAsStream("layout-cnab400-itau-envio.xml")), "layout-cnab400-itau-envio.xml");
				FlatFile<Record> ff = Texgit.createFlatFile(layout);
				Boleto boletoHeader = boletos.get(0);
				ff.addRecord(createHeader(ff, boletoHeader));
				int index = 2;
				for (Boleto boleto : boletos) {
					ff.addRecord(createTransacaoTitulos(ff, boleto, index++));
					boleto.setRemessa(remessa);
					valorTotal = valorTotal.add(boleto.getValor());
				}
				ff.addRecord(createTrailer(ff, index));
				FileUtils.writeLines(arquivoFinal, ff.write(), "\r\n");
				remessa.setDataCriacao(new Date());
				remessa.setArquivo(Base64Utils.base64Encode(utilArquivo.getBytesFromFile(arquivoFinal)));
				remessa.setBanco(boletoHeader.getContrato().getContaCorrente().getBanco());
				remessa.setStatus(StatusRemessaEnum.GERADO);
				remessa.setValor(valorTotal);
				remessa.setQtdBoletoAberto(boletos.size());
				remessa.setQtdBoletoFechado(0);
				remessa.setValorRecebido(BigDecimal.ZERO);
				remessa.setCodigo(utilString.completaComZerosAEsquerda(String.valueOf(utilData.getMes(boletos.get(0).getDataVencimento())),2) +"/"+utilData.getAno(boletos.get(0).getDataVencimento()));
				remessaService.incluir(remessa);
				boletoService.alterarLista(boletos);
			}
		} catch (Exception e) {
			System.out.println("ERRO AO GERAR O ARQUIVO");
			e.printStackTrace();
		}finally {
			if(layout != null && layout.exists()){
				layout.delete();
			}
			if(arquivoFinal != null && arquivoFinal.exists()){
				arquivoFinal.delete();
			}
		}

	}

	public void processarRetorno(List<Retorno> listaRetorno) {
		File layout = null;
		
			UtilArquivo utilArquivo = new UtilArquivo();
			for (Retorno retorno : listaRetorno) {
				try {
					byte[] bytes = Base64Utils.base64Decode(retorno.getArquivo());
					File arquivoRetorno = utilArquivo.getFileFromBytes(bytes, retorno.getNomeArquivo());
					layout = utilArquivo.getFileFromBytes(UtilArquivo.converteInputStreamEmBytes(IntegracaoFinanceiraItau.class.getClassLoader().getResourceAsStream("layout-cnab400-itau-retorno.xml")), "layout-cnab400-itau-retorno.xml");
					FlatFile<Record> ff = Texgit.createFlatFile(layout);
					ff.read(FileUtil.read(arquivoRetorno.getAbsolutePath()));
					Record header = ff.getRecord("Header");
					if(header == null){
						throw new Exception("Arquivo invalido!");
					}
					Collection<Record> titulosEmCobranca = ff.getRecords("TransacaoTitulo");
					for (Record titulo : titulosEmCobranca) {
						System.out.println(((Integer)titulo.getValue("NossoNumero")).toString()+" = "+(BigDecimal)titulo.getValue("ValorPago"));
						Boleto boleto = boletoService.recuperarPorNossoNumero(((Integer)titulo.getValue("NossoNumero")).toString(),StatusBoletoEnum.ABERTO);
						if(boleto != null){
							BigDecimal valorPago = (BigDecimal)titulo.getValue("ValorPago");
							if(valorPago != null && valorPago.compareTo(BigDecimal.ZERO) == 1){
								boleto.setDataPagamento(new Date());
								boleto.setStatus(StatusBoletoEnum.PAGO);
								boleto.getRemessa().setQtdBoletoAberto(boleto.getRemessa().getQtdBoletoAberto() - 1);
								boleto.getRemessa().setQtdBoletoFechado(boleto.getRemessa().getQtdBoletoFechado() + 1);
								boleto.getRemessa().setValorRecebido(boleto.getRemessa().getValorRecebido().add(valorPago));
								if(boleto.getRemessa().getQtdBoletoAberto().equals(0)){
									boleto.getRemessa().setStatus(StatusRemessaEnum.FECHADO);
								}
								boleto.setRetorno(retorno);
								boleto.setValorPago(valorPago);
								boletoService.alterar(boleto);
								remessaService.alterar(boleto.getRemessa());
								movimentacaoFinanceiraService.incluir(boleto);
							}
						}	
					}
					retorno.setDataProcessamento(new Date());
					retorno.setStatus(StatusRetorno.PROCESSADO);
					retornoService.alterar(retorno);
					
				} catch (Exception e) {
					retorno.setDataProcessamento(new Date());
					retorno.setStatus(StatusRetorno.ERRO_PROCESSAMENTO);
					retorno.setMensagem(e.getMessage());
					retornoService.alterar(retorno);
				}finally {
					if(layout != null && layout.exists()){
						layout.delete();
					}
				}
			}

	}
	
	public List<Record> comporRetorno(Retorno retorno) {
		File layout = null;
		File arquivoRetorno = null;
		Collection<Record> titulosEmCobranca=null;
		try {
			UtilArquivo utilArquivo = new UtilArquivo();
				byte[] bytes = Base64Utils.base64Decode(retorno.getArquivo());
				arquivoRetorno = utilArquivo.getFileFromBytes(bytes, retorno.getNomeArquivo());
				layout = utilArquivo.getFileFromBytes(UtilArquivo.converteInputStreamEmBytes(IntegracaoFinanceiraItau.class.getClassLoader().getResourceAsStream("layout-cnab400-itau-retorno.xml")), "layout-cnab400-itau-retorno.xml");
				FlatFile<Record> ff = Texgit.createFlatFile(layout);
				ff.read(FileUtil.read(arquivoRetorno.getAbsolutePath()));
				titulosEmCobranca = ff.getRecords("TransacaoTitulo");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(layout != null && layout.exists()){
				layout.delete();
			}
			if(arquivoRetorno != null && arquivoRetorno.exists()){
				arquivoRetorno.delete();
			}
		}	
		return titulosEmCobranca != null ? new ArrayList<Record>(titulosEmCobranca):new ArrayList<Record>();

	}

	private Record createHeader(FlatFile<Record> ff, Boleto boleto) {
		Record header = ff.createRecord("Header");
		header.setValue("LiteralRemessa", formatarValorPorTamanho("REMESSA", 7));
		header.setValue("CodigoDeServico", formatarValorPorTamanho("01", 2));
		header.setValue("LiteralServico", formatarValorPorTamanho("COBRANCA", 15));
		header.setValue("Agencia", boleto.getContrato().getContaCorrente().getNumeroAgencia());
		header.setValue("Zeros", "00");
		header.setValue("Conta",
				formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNumeroContaCorrente(), 5));
		header.setValue("DacConta",
				formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getDigitoContaCorrente(), 1));
		header.setValue("Brancos1", "        ");
		header.setValue("NomeEmpresa", formatarValorPorTamanho(this.parametro.getNomeEmpresa(), 30));
		header.setValue("CodigoCompensacao",
				formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getCodigoCompensacao(), 3));
		header.setValue("NomeBanco",
				formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNomeBanco(), 15));
		header.setValue("DataGeracao", boleto.getDataCriacao());
		header.setValue("Brancos2",
				"                                                                                                                                                                                                                                                                                                     ");
		header.setValue("NumeroSequencialRegistro", "000001");
		return header;
	}

	private Record createTransacaoTitulos(FlatFile<Record> ff, Boleto boleto, Integer index) {
		Record transacaoTitulos = ff.createRecord("TransacaoTitulo");
		UtilString utilString = new UtilString();
		try{
			transacaoTitulos.setValue("NumeroInscricao",formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(parametro.getCnpj()), 14));
			transacaoTitulos.setValue("Agencia",formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNumeroAgencia(), 4));
			transacaoTitulos.setValue("Conta",formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNumeroContaCorrente(), 5));
			transacaoTitulos.setValue("DacConta",formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getDigitoContaCorrente(), 1));
			transacaoTitulos.setValue("Brancos1", "    ");
			transacaoTitulos.setValue("InstrucaoAlegacao", "0000");
			transacaoTitulos.setValue("UsoDaEmpresa", formatarValorPorTamanho("N/A", 25));
			transacaoTitulos.setValue("NossoNumeroComDigito",formatarValorPorTamanho(boleto.getNossoNumero(), 8));
			transacaoTitulos.setValue("QtdMoeda", formatarValorPorTamanho("0", 13));
			transacaoTitulos.setValue("NrCarteira",formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNumeroCarteira(), 3));
			transacaoTitulos.setValue("UsoDoBanco",formatarValorPorTamanho("", 21));
			transacaoTitulos.setValue("CodigoCarteira",formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getCodigoCarteira(), 1));
			transacaoTitulos.setValue("CodigoDeOcorrencia",	formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getCodigoOcorrencia(), 2));
			transacaoTitulos.setValue("NumeroDoDocumento", formatarValorPorTamanho(boleto.getNumeroDocumento(), 10));
			transacaoTitulos.setValue("Vencimento", boleto.getDataVencimento());
			transacaoTitulos.setValue("Valor", boleto.getValor());
			transacaoTitulos.setValue("CodigoCompensacaoBancoRecebedor",boleto.getContrato().getContaCorrente().getCodigoCompensacao());
			transacaoTitulos.setValue("AgenciaCobradora", boleto.getContrato().getContaCorrente().getNumeroAgencia());
			transacaoTitulos.setValue("EspecieDeTitulo", "08");
			transacaoTitulos.setValue("Aceite", "N");
			transacaoTitulos.setValue("Emissao", boleto.getDataCriacao());
			transacaoTitulos.setValue("Instrucao1", "05");
			transacaoTitulos.setValue("Instrucao2", "39");
			transacaoTitulos.setValue("JurosDeMora", "0");
			transacaoTitulos.setValue("DataDesconto", boleto.getDataVencimento());
			transacaoTitulos.setValue("DescontoConcedido", "0");
			transacaoTitulos.setValue("IOF_Devido", "0");
			transacaoTitulos.setValue("AbatimentoConcedido", "0");
			transacaoTitulos.setValue("TipoInscricaoSacado", "02");
			transacaoTitulos.setValue("NumeroInscricaoSacado", utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getCpfCnpj()));
			transacaoTitulos.setValue("NomeSacado", formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getNome()), 30));
			transacaoTitulos.setValue("Brancos2", "          ");
			transacaoTitulos.setValue("LogradouroSacado", formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getEndereco()), 40));
			transacaoTitulos.setValue("BairroSacado", formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getBairro()), 12));
			transacaoTitulos.setValue("CepSacado", utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getCep()));
			transacaoTitulos.setValue("Cidade", formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getCidade().getNome()), 15));
			transacaoTitulos.setValue("Estado", formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getUf().getSigla()), 2));
			transacaoTitulos.setValue("SacadorAvalista", "");
			transacaoTitulos.setValue("Brancos3", "    ");
			transacaoTitulos.setValue("DataDeMora", "");
			transacaoTitulos.setValue("Prazo", "00");
			transacaoTitulos.setValue("Brancos4", " ");
			transacaoTitulos.setValue("NumeroSequencialRegistro", index);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}	
		return transacaoTitulos;
	}

	private Record createTrailer(FlatFile<Record> ff, Integer index) {
		Record trailer = ff.createRecord("Trailler");
		trailer.setValue("NumeroSequencialRegistro", index);
		return trailer;
	}

	public String formatarValorPorTamanho(String valorCampo, int tamanhoValor) {
		UtilString utilString = new UtilString();
		if ((valorCampo != null) && !utilString.vazio(valorCampo) && (tamanhoValor != 0)) {
			if (valorCampo.length() <= tamanhoValor) {
				return valorCampo;
			} else {
				return valorCampo.substring(0, tamanhoValor);
			}
		} else {
			return "";
		}
	}

}
