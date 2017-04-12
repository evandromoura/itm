package br.com.trixti.itm.service.boleto;

import java.io.File;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.parametro.ParametroService;


@Stateless
public class GeradorBoletoService {
	
	private @Inject ParametroService parametroService;
	
	public File gerarBoleto(br.com.trixti.itm.entity.Boleto boleto){
		
		Parametro parametro = parametroService.recuperarParametro();
		
		Cedente cedente = new Cedente(parametro.getNomeEmpresa(), parametro.getCnpj());
		 
         /*
          * INFORMANDO DADOS SOBRE O SACADO.
          */
         Sacado sacado = new Sacado(boleto.getContrato().getCliente().getNome(), 
        		 boleto.getContrato().getCliente().getCpfCnpj());

         // Informando o endereço do sacado. / Contrato
         Endereco enderecoSac = new Endereco();
         enderecoSac.setUF(UnidadeFederativa.valueOfSigla(boleto.getContrato().getCliente().getUf()));
         enderecoSac.setLocalidade(boleto.getContrato().getCliente().getEndereco());
         enderecoSac.setCep(new CEP(boleto.getContrato().getCliente().getCep()));
         enderecoSac.setBairro(boleto.getContrato().getCliente().getCep());
         enderecoSac.setLogradouro("");
         enderecoSac.setNumero(boleto.getContrato().getCliente().getNumeroEndereco());
         sacado.addEndereco(enderecoSac);
         
         ContaBancaria contaBancaria = new ContaBancaria(
        		 getBanco(boleto.getContrato().getContaCorrente().getBanco()).create());
         
         
         contaBancaria.setNumeroDaConta(new NumeroDaConta(
        		 Integer.valueOf(boleto.getContrato().getContaCorrente().getNumeroContaCorrente()),
        		 boleto.getContrato().getContaCorrente().getDigitoContaCorrente()));
         
         contaBancaria.setCarteira(new Carteira(Integer.valueOf(boleto.getContrato().getContaCorrente().getNumeroCarteira())));
         
         contaBancaria.setAgencia(new Agencia(Integer.valueOf(boleto.getContrato().getContaCorrente().getNumeroAgencia())));
//         
         Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
         //TODO Criar um sequencial 
         titulo.setNumeroDoDocumento("4208");
         titulo.setNossoNumero(boleto.getContrato().getContaCorrente().getNossoNumero());
         titulo.setDigitoDoNossoNumero(boleto.getContrato().getContaCorrente().getDigitoNossoNumero());
         titulo.setValor(boleto.getValor());
         titulo.setDataDoDocumento(boleto.getDataCriacao());
         titulo.setDataDoVencimento(boleto.getDataVencimento());
         titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
         titulo.setAceite(Aceite.A);
//         titulo.setDesconto(new BigDecimal(0.05));
         titulo.setDeducao(BigDecimal.ZERO);
         titulo.setMora(BigDecimal.ZERO);
         titulo.setAcrecimo(BigDecimal.ZERO);
         titulo.setValorCobrado(BigDecimal.ZERO);
//
//         /*
//          * INFORMANDO OS DADOS SOBRE O BOLETO.
//          */
         Boleto boletoGerado = new Boleto(titulo);
         
         boletoGerado.setLocalPagamento("Pagável preferencialmente na Rede X ou em " +
                         "qualquer Banco até o Vencimento.");
         boletoGerado.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
                         "cobrado não é o esperado, aproveite o DESCONTÃO!");
         boletoGerado.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
         boletoGerado.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
         boletoGerado.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
         boletoGerado.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
         boletoGerado.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
         boletoGerado.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
         boletoGerado.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
         boletoGerado.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
//
//
         BoletoViewer boletoViewer = new BoletoViewer(boletoGerado);
         StringBuilder sb = new StringBuilder();
         for(int i=0;i<boletoViewer.getBoleto().getCodigoDeBarras().getFieldsLength();i++){
        	 System.out.println(i+" = "+ boletoViewer.getBoleto().getCodigoDeBarras().get(i).getValue());
        	 sb.append(boletoViewer.getBoleto().getCodigoDeBarras().get(i).getValue());
        	 System.out.println(sb.toString());
         }
         
         System.out.println(sb.toString());
         String nomeArquivo = boleto.getContrato().getCliente().getNome() + boleto.getDataVencimento().toString()+".pdf";
         File arquivoPdf = boletoViewer.getPdfAsFile(nomeArquivo);
         return arquivoPdf;
		
		
	}
	
	private BancosSuportados getBanco(String banco){
		for(BancosSuportados bancoSuportado:BancosSuportados.values()){
			if(bancoSuportado.name().equals(banco)){
				return bancoSuportado;
			}
			
		}
		return BancosSuportados.BANCO_DO_BRASIL;
	}
	
	

}
