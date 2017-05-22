package br.com.trixti.itm.infra.financeiro;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.util.UtilString;

@Named
public class IntegracaoFinanceira {
	
	private Parametro parametro;
	private @Inject ParametroService parametroService;
	
	@PostConstruct
	private void init(){
		parametro = parametroService.recuperarParametro();
	}
	
	public File gerarRemessa(Boleto boleto) throws IOException {

		File arquivoFinal = new File("arquivo-final.txt");
		File layout = new File(this.getClass().getResource("layout-cnab400-itau-envio.xml").getFile());
		FlatFile<Record> ff = Texgit.createFlatFile(layout);  

		ff.addRecord(createHeader(ff, boleto));
		int index = 2;
		for(BoletoLancamento lancamento:boleto.getLancamentos()){
			ff.addRecord(createTransacaoTitulos(ff, lancamento,index++));
		}
		ff.addRecord(createTrailer(ff, boleto,index));
		FileUtils.writeLines(arquivoFinal, ff.write(), "\r\n");
		
		return arquivoFinal;
	}

	private Record createHeader(FlatFile<Record> ff, Boleto boleto) {
		Record header = ff.createRecord("Header");
		header.setValue("LiteralRemessa", formatarValorPorTamanho("REMESSA", 7));
		header.setValue("CodigoDeServico", formatarValorPorTamanho("01", 2));
		header.setValue("LiteralServico", formatarValorPorTamanho("COBRANCA", 15));
		header.setValue("Agencia", boleto.getContrato().getContaCorrente().getNumeroAgencia());
		header.setValue("Zeros", "00");
		header.setValue("Conta", formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNumeroContaCorrente(),5));
		header.setValue("DacConta", formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getDigitoContaCorrente(),1));
		header.setValue("Brancos1", "        ");
		header.setValue("NomeEmpresa", formatarValorPorTamanho(this.parametro.getNomeEmpresa(),30));
		header.setValue("CodigoCompensacao", formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getCodigoCompensacao(), 3));
		header.setValue("NomeBanco", formatarValorPorTamanho(boleto.getContrato().getContaCorrente().getNomeBanco(), 15));
		header.setValue("DataGeracao", boleto.getDataCriacao());
		header.setValue("Brancos2", "                                                                                                                                                                                                                                                                                                     ");
		header.setValue("NumeroSequencialRegistro","000001");
		return header;
	}

	private Record createTransacaoTitulos(FlatFile<Record> ff, BoletoLancamento lancamento,Integer index) {
		Record transacaoTitulos = ff.createRecord("TransacaoTitulo");
		UtilString utilString = new UtilString();
		transacaoTitulos.setValue("NumeroInscricao", formatarValorPorTamanho(utilString.retiraCaracteresEspeciais(parametro.getCnpj()), 14));
		transacaoTitulos.setValue("Agencia", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getNumeroAgencia(), 4));  
		transacaoTitulos.setValue("Conta", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getNumeroContaCorrente(), 5));
		transacaoTitulos.setValue("DacConta", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getDigitoContaCorrente(), 1));
		transacaoTitulos.setValue("Brancos1", "    ");
		transacaoTitulos.setValue("InstrucaoAlegacao", "0000");
		transacaoTitulos.setValue("UsoDaEmpresa", formatarValorPorTamanho("N/A", 25));
		transacaoTitulos.setValue("NossoNumeroComDigito", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getNossoNumero()+lancamento.getBoleto().getContrato().getContaCorrente().getDigitoNossoNumero(), 8));
		transacaoTitulos.setValue("QtdMoeda", formatarValorPorTamanho("0", 13));
		transacaoTitulos.setValue("NrCarteira", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getNumeroCarteira(), 3));
		transacaoTitulos.setValue("UsoDoBanco", formatarValorPorTamanho("", 21));
		transacaoTitulos.setValue("CodigoCarteira", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getCodigoCarteira(), 1));
		transacaoTitulos.setValue("CodigoDeOcorrencia", formatarValorPorTamanho(lancamento.getBoleto().getContrato().getContaCorrente().getCodigoOcorrencia(), 2));
		transacaoTitulos.setValue("NumeroDoDocumento", formatarValorPorTamanho("J5H1048147", 10));
		transacaoTitulos.setValue("Vencimento", lancamento.getBoleto().getDataVencimento());
		transacaoTitulos.setValue("Valor", lancamento.getBoleto().getValor());
		transacaoTitulos.setValue("CodigoCompensacaoBancoRecebedor", lancamento.getBoleto().getContrato().getContaCorrente().getCodigoCompensacao());
		transacaoTitulos.setValue("AgenciaCobradora", lancamento.getBoleto().getContrato().getContaCorrente().getNumeroAgencia());
		transacaoTitulos.setValue("EspecieDeTitulo", "08");
		transacaoTitulos.setValue("Aceite", "N");
		transacaoTitulos.setValue("Emissao", lancamento.getBoleto().getDataCriacao());
		transacaoTitulos.setValue("Instrucao1", "05");
		transacaoTitulos.setValue("Instrucao2", "39");
		transacaoTitulos.setValue("JurosDeMora", "0");
		transacaoTitulos.setValue("DataDesconto", lancamento.getBoleto().getDataVencimento());
		transacaoTitulos.setValue("DescontoConcedido", "0");
		transacaoTitulos.setValue("IOF_Devido", "0");
		transacaoTitulos.setValue("AbatimentoConcedido", "0");
		transacaoTitulos.setValue("TipoInscricaoSacado", "02");
		transacaoTitulos.setValue("NumeroInscricaoSacado", utilString.retiraCaracteresEspeciais(parametro.getCnpj()));
		transacaoTitulos.setValue("NomeSacado", formatarValorPorTamanho(parametro.getNomeEmpresa(), 30));
		transacaoTitulos.setValue("Brancos2", "          ");
		transacaoTitulos.setValue("LogradouroSacado", formatarValorPorTamanho(parametro.getLogradouro(), 40));
		transacaoTitulos.setValue("BairroSacado",formatarValorPorTamanho(parametro.getBairro(),12));
		transacaoTitulos.setValue("CepSacado",utilString.retiraCaracteresEspeciais(parametro.getCep()));
		transacaoTitulos.setValue("Cidade",formatarValorPorTamanho(parametro.getCidade(), 15));
		transacaoTitulos.setValue("Estado",formatarValorPorTamanho(parametro.getUf(), 2));
		transacaoTitulos.setValue("SacadorAvalista","");
		transacaoTitulos.setValue("Brancos3","    ");
		transacaoTitulos.setValue("DataDeMora","");
		transacaoTitulos.setValue("Prazo","00");
		transacaoTitulos.setValue("Brancos4"," ");
		transacaoTitulos.setValue("NumeroSequencialRegistro",index);

		return transacaoTitulos;
	}

	private Record createTrailer(FlatFile<Record> ff, Boleto boleto,Integer index) {
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
