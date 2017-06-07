package br.com.trixti.itm.to;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SnmpTO {

	private String download;
	private String upload;
	
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
	
	
}
