package br.com.trixti.itm.service.uploadarquivo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import br.com.trixti.itm.enums.SiglaTemplateSiciEnum;
import br.com.trixti.itm.infra.financeiro.IntegracaoFinanceiraItau;
import br.com.trixti.itm.util.UtilArquivo;

@Named
public class ValidadorTemplateSici {
	
	private Map<String,String> mapaTemplate = new HashMap<String,String>();
	
	@PostConstruct
	private void init(){
		mapaTemplate.put("01", "templete_coleta_janeiro_anual.xml");
		mapaTemplate.put("02", "templete_coleta_fevereiro_mensal.xml");
		mapaTemplate.put("03", "templete_coleta_marco_mensal.xml");
		mapaTemplate.put("04", "templete_coleta_abril_mensal.xml");
		mapaTemplate.put("05", "templete_coleta_maio_mensal.xml");
		mapaTemplate.put("06", "templete_coleta_junho_mensal.xml");
		mapaTemplate.put("07", "templete_coleta_julho_semestral.xml");
		mapaTemplate.put("08", "templete_coleta_agosto_mensal.xml");
		mapaTemplate.put("09", "templete_coleta_setembro_mensal.xml");
		mapaTemplate.put("10", "templete_coleta_outubro_mensal.xml");
		mapaTemplate.put("11", "templete_coleta_novembro_mensal.xml");
		mapaTemplate.put("12", "templete_coleta_dezembro_mensal.xml");
	}

	public boolean validar(String mes,SiglaTemplateSiciEnum sigla) {
		try{
			UtilArquivo utilArquivo = new UtilArquivo();
			 ValidadorTemplateSici.class.getResourceAsStream("/templates/sici/"+mapaTemplate.get(mes));
			 File inputFile = utilArquivo.getFileFromBytes(UtilArquivo.converteInputStreamEmBytes(ValidadorTemplateSici.class.getClassLoader().getResourceAsStream("/templates/sici/"+mapaTemplate.get(mes))), mapaTemplate.get(mes));
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         SAXParser saxParser = factory.newSAXParser();
	         SiciXMLHandler userhandler = new SiciXMLHandler();
	         userhandler.sigla = sigla;
	         saxParser.parse(inputFile, userhandler);
	         return userhandler.existe;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}    
	}
	
	public boolean isAnual(String mes){
		return mapaTemplate.get(mes).contains("anual");
	}

}
