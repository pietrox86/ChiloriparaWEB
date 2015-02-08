package it.pbc.chiloripara.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoriaRest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6396506906501255349L;
	
	
	private Long id;
	private String descrizione;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public CategoriaRest(Long id, String descrizione) {
		super();
		this.id = id;
		this.descrizione = descrizione;
	}
	public CategoriaRest(){
		super();
	}
	
	

}
