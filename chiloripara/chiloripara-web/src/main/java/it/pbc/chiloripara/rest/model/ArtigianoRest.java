package it.pbc.chiloripara.rest.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ArtigianoRest {
	private Long id;
	private String ragioneSociale;
	private String indirizzo;
	private String telefono;
	private String email;
	private String descrizione;
	private Date dataIscrizione;
	private float lat;
	private float lng;
	private String piva;
	private String username;
	private String password;
	private String cap;
	private String comune;
	private List<PostRest> bacheca = new ArrayList<PostRest>();
	private List<VotoRest> voti = new ArrayList<VotoRest>();
	private byte[] icon;

	public ArtigianoRest() {
		super();
	}

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

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataIscrizione() {
		return dataIscrizione;
	}

	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<PostRest> getBacheca() {
		return bacheca;
	}

	public void setBacheca(List<PostRest> bacheca) {
		this.bacheca = bacheca;
	}

	public List<VotoRest> getVoti() {
		return voti;
	}

	public void setVoti(List<VotoRest> voti) {
		this.voti = voti;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}
