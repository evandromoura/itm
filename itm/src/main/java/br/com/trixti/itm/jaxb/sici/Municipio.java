//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.03.09 às 10:49:25 AM BRT 
//


package br.com.trixti.itm.jaxb.sici;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Municipio complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Municipio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Pessoa" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Tecnologia" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="codmunicipio">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;length value="7"/>
 *             &lt;whiteSpace value="collapse"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Municipio", propOrder = {
    "pessoa",
    "tecnologia"
})
public class Municipio {

    @XmlElement(name = "Pessoa")
    protected List<Pessoa> pessoa;
    @XmlElement(name = "Tecnologia")
    protected List<Tecnologia> tecnologia;
    @XmlAttribute(name = "codmunicipio")
    protected String codmunicipio;

    /**
     * Gets the value of the pessoa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pessoa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPessoa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pessoa }
     * 
     * 
     */
    public List<Pessoa> getPessoa() {
        if (pessoa == null) {
            pessoa = new ArrayList<Pessoa>();
        }
        return this.pessoa;
    }

    /**
     * Gets the value of the tecnologia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tecnologia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTecnologia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tecnologia }
     * 
     * 
     */
    public List<Tecnologia> getTecnologia() {
        if (tecnologia == null) {
            tecnologia = new ArrayList<Tecnologia>();
        }
        return this.tecnologia;
    }

    /**
     * Obtém o valor da propriedade codmunicipio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodmunicipio() {
        return codmunicipio;
    }

    /**
     * Define o valor da propriedade codmunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodmunicipio(String value) {
        this.codmunicipio = value;
    }

}
