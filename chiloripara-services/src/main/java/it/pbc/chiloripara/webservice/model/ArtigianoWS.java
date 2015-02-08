package it.pbc.chiloripara.webservice.model;

import java.io.Serializable;

public class ArtigianoWS implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private long id;
private String ragioneSociale;
private String indirizzo;
private String cap;
private String comune;
private float distanza;
private byte[] icona;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getRagioneSociale() {
	return ragioneSociale;
}
public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}
public String getIndirizzo() {
	return indirizzo;
}
public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
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
public float getDistanza() {
	return distanza;
}
public void setDistanza(float distanza) {
	this.distanza = distanza;
}
public byte[] getIcona() {
	return icona;
}
public void setIcona(byte[] icona) {
	this.icona = icona;
}


}
