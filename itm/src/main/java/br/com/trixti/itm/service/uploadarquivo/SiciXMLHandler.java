package br.com.trixti.itm.service.uploadarquivo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.com.trixti.itm.enums.SiglaTemplateSiciEnum;

public class SiciXMLHandler extends DefaultHandler {

   public boolean existe = false;
   public SiglaTemplateSiciEnum sigla;
   private String valorAnterior;
   private String siglaAnterior;

   @Override
   public void startElement(String uri,  String localName, String qName, Attributes attributes) throws SAXException {
//	   System.out.println("VALOR ANTERIOR = "+valorAnterior);
//	   System.out.println("SIGLA ANTERIOR = "+siglaAnterior);
	   
      if (qName.equalsIgnoreCase("Indicador")) {
         String rollNo = attributes.getValue("Sigla");
         if(rollNo.equals(sigla.name())){
        	 if(sigla.equals(SiglaTemplateSiciEnum.IEM4)){
        		 System.out.println("Entrou");
        	 }
        	 System.out.println("Sigla = "+rollNo); 
        	 existe = true;
         } 
      }
      valorAnterior = qName;
      siglaAnterior = attributes.getValue("Sigla");
   }
   
}