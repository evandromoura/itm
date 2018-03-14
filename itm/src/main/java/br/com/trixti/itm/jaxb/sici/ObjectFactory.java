//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.03.09 às 10:49:25 AM BRT 
//


package br.com.trixti.itm.jaxb.sici;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Municipio_QNAME = new QName("", "Municipio");
    private final static QName _Outorga_QNAME = new QName("", "Outorga");
    private final static QName _Tecnologia_QNAME = new QName("", "Tecnologia");
    private final static QName _Conteudo_QNAME = new QName("", "Conteudo");
    private final static QName _Root_QNAME = new QName("", "root");
    private final static QName _UploadSICI_QNAME = new QName("", "UploadSICI");
    private final static QName _Indicador_QNAME = new QName("", "Indicador");
    private final static QName _Pessoa_QNAME = new QName("", "Pessoa");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Municipio }
     * 
     */
    public Municipio createMunicipio() {
        return new Municipio();
    }

    /**
     * Create an instance of {@link Outorga }
     * 
     */
    public Outorga createOutorga() {
        return new Outorga();
    }

    /**
     * Create an instance of {@link Tecnologia }
     * 
     */
    public Tecnologia createTecnologia() {
        return new Tecnologia();
    }

    /**
     * Create an instance of {@link Conteudo }
     * 
     */
    public Conteudo createConteudo() {
        return new Conteudo();
    }

    /**
     * Create an instance of {@link UploadSICI }
     * 
     */
    public UploadSICI createUploadSICI() {
        return new UploadSICI();
    }

    /**
     * Create an instance of {@link Indicador }
     * 
     */
    public Indicador createIndicador() {
        return new Indicador();
    }

    /**
     * Create an instance of {@link Pessoa }
     * 
     */
    public Pessoa createPessoa() {
        return new Pessoa();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Municipio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Municipio")
    public JAXBElement<Municipio> createMunicipio(Municipio value) {
        return new JAXBElement<Municipio>(_Municipio_QNAME, Municipio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Outorga }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Outorga")
    public JAXBElement<Outorga> createOutorga(Outorga value) {
        return new JAXBElement<Outorga>(_Outorga_QNAME, Outorga.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Tecnologia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Tecnologia")
    public JAXBElement<Tecnologia> createTecnologia(Tecnologia value) {
        return new JAXBElement<Tecnologia>(_Tecnologia_QNAME, Tecnologia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Conteudo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Conteudo")
    public JAXBElement<Conteudo> createConteudo(Conteudo value) {
        return new JAXBElement<Conteudo>(_Conteudo_QNAME, Conteudo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "root")
    public JAXBElement<Object> createRoot(Object value) {
        return new JAXBElement<Object>(_Root_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadSICI }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "UploadSICI")
    public JAXBElement<UploadSICI> createUploadSICI(UploadSICI value) {
        return new JAXBElement<UploadSICI>(_UploadSICI_QNAME, UploadSICI.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Indicador }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Indicador")
    public JAXBElement<Indicador> createIndicador(Indicador value) {
        return new JAXBElement<Indicador>(_Indicador_QNAME, Indicador.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pessoa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Pessoa")
    public JAXBElement<Pessoa> createPessoa(Pessoa value) {
        return new JAXBElement<Pessoa>(_Pessoa_QNAME, Pessoa.class, null, value);
    }

}
