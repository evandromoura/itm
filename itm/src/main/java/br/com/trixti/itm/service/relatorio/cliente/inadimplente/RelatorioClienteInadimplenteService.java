package br.com.trixti.itm.service.relatorio.cliente.inadimplente;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.to.RelatorioClienteInadimplenteTO;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class RelatorioClienteInadimplenteService {

	
	private @Inject BoletoService boletoService;
	private @Inject ParametroService parametroService;
	
	public RelatorioClienteInadimplenteTO gerar(){
		Parametro parametro = parametroService.recuperarParametro();
		UtilData utilData = new UtilData();
		RelatorioClienteInadimplenteTO retorno = new RelatorioClienteInadimplenteTO();
		List<Boleto> boletos = boletoService.listarBoletoEmAtraso();
		retorno.setParametro(parametro);
		for(Boleto boleto:boletos){
			long diferenca = utilData.getDiferencaDias(new Date(), boleto.getDataVencimento());
			boleto.setDiferencaDias(diferenca);
			if(diferenca <= parametro.getQtdDiasAviso()){
				retorno.getBoletos15Dias().add(boleto);
			}
			
			if(diferenca > parametro.getQtdDiasAviso() &&  diferenca <= parametro.getQtdDiasBloqueio()){
				retorno.getBoletos30Dias().add(boleto);
			}
			else if(diferenca > parametro.getQtdDiasBloqueio() && diferenca <= parametro.getQtdDiasInativacao()){
				retorno.getBoletos60Dias().add(boleto);
			}
			
			else if(diferenca > parametro.getQtdDiasInativacao() && diferenca <= parametro.getQtdDiasNegativacao()){
				retorno.getBoletos90Dias().add(boleto);
			}
			
			else if(diferenca >parametro.getQtdDiasNegativacao()){
				retorno.getBoletos90Dias().add(boleto);
			}
			
		}
		return retorno;
	}
}
