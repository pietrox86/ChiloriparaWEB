package it.pbc.chiloripara.webservice.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RecuperaCategorieResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String returnCode;
	
	private CategoriaWS[] categorie;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public CategoriaWS[] getCategorie() {
		return categorie;
	}
	public void setCategorie(CategoriaWS[] categorie) {
		this.categorie = categorie;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
