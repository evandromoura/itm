package br.com.trixti.itm.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.Normalizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import br.com.trixti.itm.jaxb.sici.UploadSICI;

public class UtilJaxb {

	public String marshal(UploadSICI uploadSICI) {
        final StringWriter out = new StringWriter();
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(uploadSICI.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            marshaller.marshal(uploadSICI, new StreamResult(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retirarAcentosDaString(out.toString());
    }
	
	public String retirarAcentosDaString(String string) {
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		return string.replaceAll("[^\\p{ASCII}]", "");
	}
	
	public UploadSICI unmarshal(String xml) {
		UploadSICI m = null;
        JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(UploadSICI.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
			reader.setContentHandler(unmarshaller.getUnmarshallerHandler());
			SAXSource source = new SAXSource(xmlFilter, new InputSource(new StringReader(xml))); 
			JAXBElement<UploadSICI> rootElement = unmarshaller.unmarshal(source,UploadSICI.class);
			m = rootElement.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return m;
    }

	
	class XMLNamespaceFilter extends XMLFilterImpl {
	    public XMLNamespaceFilter(XMLReader arg0) {
	       super(arg0);
	    }
	    @Override
	    public void startElement(String uri, String localName,String qName, Attributes attributes)throws SAXException {
	       super.startElement("ansTISS", localName, qName, attributes);
	    }
	}
}