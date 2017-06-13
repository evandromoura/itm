package br.com.trixti.itm.to;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SnmpTO {

	private String id;
	private String name;
	private String download;
	private String upload;

	private Double taxaDownload;
	private Double taxaUpload;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}

	public Double getTaxaDownload() {
		return taxaDownload;
	}

	public void setTaxaDownload(Double taxaDownload) {
		this.taxaDownload = taxaDownload;
	}

	public Double getTaxaUpload() {
		return taxaUpload;
	}

	public void setTaxaUpload(Double taxaUpload) {
		this.taxaUpload = taxaUpload;
	}

	public SnmpTO() {
	}

	public SnmpTO(String id,String name, String download, String upload) {
		setId(id);
		setName(name);
		setDownload(download);
		setUpload(upload);
		setTaxaDownload(Double.valueOf(0));
		setTaxaUpload(Double.valueOf(0));
	}

}
