//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.03.09 às 10:49:25 AM BRT 
//


package br.com.trixti.itm.jaxb.sici;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "root", propOrder = {
    "uploadSICI"
})
public class Root {

	@XmlElement(name = "UploadSICI")
    protected UploadSICI uploadSICI;

	public UploadSICI getUploadSICI() {
		if (uploadSICI == null) {
			uploadSICI = new UploadSICI();
		}
		return uploadSICI;
	}

	public void setUploadSICI(UploadSICI uploadSICI) {
		this.uploadSICI = uploadSICI;
	}
	
	
}
