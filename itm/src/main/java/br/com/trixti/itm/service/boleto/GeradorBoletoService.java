package br.com.trixti.itm.service.boleto;

import java.io.File;

import javax.ejb.Stateless;

import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;


@Stateless
public class GeradorBoletoService {
	
	public File gerarBoleto(br.com.trixti.itm.entity.Boleto boleto){
		
		 Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");

         /*
          * INFORMANDO DADOS SOBRE O SACADO.
          */
         Sacado sacado = new Sacado("JavaDeveloper Pronto Para Férias", "222.222.222-22");

         // Informando o endereço do sacado.
         Endereco enderecoSac = new Endereco();
         enderecoSac.setUF(UnidadeFederativa.RN);
         enderecoSac.setLocalidade("Natal");
         enderecoSac.setCep(new CEP("59064-120"));
         enderecoSac.setBairro("Grande Centro");
         enderecoSac.setLogradouro("Rua poeta dos programas");
         enderecoSac.setNumero("1");
         sacado.addEndereco(enderecoSac);

         /*
          * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
          */
         SacadorAvalista sacadorAvalista = new SacadorAvalista("JRimum Enterprise", "00.000.000/0001-91");

         // Informando o endereço do sacador avalista.
         Endereco enderecoSacAval = new Endereco();
         enderecoSacAval.setUF(UnidadeFederativa.DF);
         enderecoSacAval.setLocalidade("Brasília");
         enderecoSacAval.setCep(new CEP("59000-000"));
         enderecoSacAval.setBairro("Grande Centro");
         enderecoSacAval.setLogradouro("Rua Eternamente Principal");
         enderecoSacAval.setNumero("001");
         sacadorAvalista.addEndereco(enderecoSacAval);

         /*
          * INFORMANDO OS DADOS SOBRE O TÍTULO.
          */
         
//         // Informando dados sobre a conta bancária do título.
//         ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
//         contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
//         contaBancaria.setCarteira(new Carteira(30));
//         contaBancaria.setAgencia(new Agencia(1234, "1"));
//         
//         Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
//         titulo.setNumeroDoDocumento("123456");
//         titulo.setNossoNumero("99345678912");
//         titulo.setDigitoDoNossoNumero("5");
//         titulo.setValor(BigDecimal.valueOf(0.23));
//         titulo.setDataDoDocumento(new Date());
//         titulo.setDataDoVencimento(new Date());
//         titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
//         titulo.setAceite(Aceite.A);
//         titulo.setDesconto(new BigDecimal(0.05));
//         titulo.setDeducao(BigDecimal.ZERO);
//         titulo.setMora(BigDecimal.ZERO);
//         titulo.setAcrecimo(BigDecimal.ZERO);
//         titulo.setValorCobrado(BigDecimal.ZERO);
//
//         /*
//          * INFORMANDO OS DADOS SOBRE O BOLETO.
//          */
//         Boleto boletoGerado = new Boleto(titulo);
//         
//         boletoGerado.setLocalPagamento("Pagável preferencialmente na Rede X ou em " +
//                         "qualquer Banco até o Vencimento.");
//         boletoGerado.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
//                         "cobrado não é o esperado, aproveite o DESCONTÃO!");
//         boletoGerado.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
//         boletoGerado.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
//         boletoGerado.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
//         boletoGerado.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
//         boletoGerado.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
//         boletoGerado.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
//         boletoGerado.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
//         boletoGerado.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
//
//
//         BoletoViewer boletoViewer = new BoletoViewer(boletoGerado);
//         File arquivoPdf = boletoViewer.getPdfAsFile("MeuPrimeiroBoleto.pdf");
//         return arquivoPdf;
		
		return null;
		
	}

}
