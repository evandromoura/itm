package br.com.trixti.itm.service.uploadarquivo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.com.trixti.itm.enums.SiglaTemplateSiciEnum;

public class SiciXMLHandler extends DefaultHandler {

   public boolean existe = false;
   public SiglaTemplateSiciEnum sigla;
   private String siglaAnterior;

   @Override
   public void startElement(String uri,  String localName, String qName, Attributes attributes) throws SAXException {
      if (siglaAnterior != null && siglaAnterior.equals(sigla.name()) && !qName.equalsIgnoreCase("Indicador")) {
          existe = true;
      }
      siglaAnterior = attributes.getValue("Sigla");
   }
   
}