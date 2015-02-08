package it.pbc.chiloripara.rest.model;

import java.io.Serializable;

public class ArtigianoHeaderRest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2401926271013092105L;
	private Long id;
	private String ragioneSociale;
	private String cap;
	private String comune;
	private String indirizzo;
	private float distanza;
	private byte[] icon;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public float getDistanza() {
		return distanza;
	}
	public void setDistanza(float distanza) {
		this.distanza = distanza;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ArtigianoHeaderRest(){
		super();
	}
}
