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
 * <p>Classe Java de Indicador complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Indicador">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Conteudo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Municipio" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Pessoa" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Sigla">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="IPL4SM"/>
 *             &lt;enumeration value="IPL5SM"/>
 *             &lt;enumeration value="IAU1"/>
 *             &lt;enumeration value="IPL1"/>
 *             &lt;enumeration value="IPL2"/>
 *             &lt;enumeration value="IPL3"/>
 *             &lt;enumeration value="IPL6IM"/>
 *             &lt;enumeration value="IEM1"/>
 *             &lt;enumeration value="IEM2"/>
 *             &lt;enumeration value="IEM3"/>
 *             &lt;enumeration value="IEM4"/>
 *             &lt;enumeration value="IEM5"/>
 *             &lt;enumeration value="IEM6"/>
 *             &lt;enumeration value="IEM7"/>
 *             &lt;enumeration value="IEM8"/>
 *             &lt;enumeration value="IEM9"/>
 *             &lt;enumeration value="IEM10"/>
 *             &lt;enumeration value="QAIPL4SM"/>
 *             &lt;enumeration value="QAIPL4SM"/>
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
@XmlType(name = "Indicador", propOrder = {
    "conteudo",
    "municipio",
    "pessoa"
})
public class Indicador {

    @XmlElement(name = "Conteudo")
    protected List<Conteudo> conteudo;
    @XmlElement(name = "Municipio")
    protected List<Municipio> municipio;
    @XmlElement(name = "Pessoa")
    protected List<Pessoa> pessoa;
    @XmlAttribute(name = "Sigla")
    protected String sigla;

    /**
     * Gets the value of the conteudo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conteudo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConteudo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Conteudo }
     * 
     * 
     */
    public List<Conteudo> getConteudo() {
        if (conteudo == null) {
            conteudo = new ArrayList<Conteudo>();
        }
        return this.conteudo;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the municipio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMunicipio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Municipio }
     * 
     * 
     */
    public List<Municipio> getMunicipio() {
        if (municipio == null) {
            municipio = new ArrayList<Municipio>();
        }
        return this.municipio;
    }

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
     * Obtém o valor da propriedade sigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Define o valor da propriedade sigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigla(String value) {
        this.sigla = value;
    }

}
