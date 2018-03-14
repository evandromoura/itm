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
 * <p>Classe Java de Outorga complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Outorga">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Indicador" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fistel">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="11"/>
 *             &lt;maxLength value="11"/>
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
@XmlType(name = "Outorga", propOrder = {
    "indicador"
})
public class Outorga {

    @XmlElement(name = "Indicador")
    protected List<Indicador> indicador;
    @XmlAttribute(name = "fistel")
    protected String fistel;

    /**
     * Gets the value of the indicador property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicador property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicador().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Indicador }
     * 
     * 
     */
    public List<Indicador> getIndicador() {
        if (indicador == null) {
            indicador = new ArrayList<Indicador>();
        }
        return this.indicador;
    }

    /**
     * Obtém o valor da propriedade fistel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFistel() {
        return fistel;
    }

    /**
     * Define o valor da propriedade fistel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFistel(String value) {
        this.fistel = value;
    }

}
