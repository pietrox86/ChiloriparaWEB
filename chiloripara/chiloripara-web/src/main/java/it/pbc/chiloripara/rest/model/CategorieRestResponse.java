package it.pbc.chiloripara.rest.model;

import java.io.Serializable;
import java.util.List;

public class CategorieRestResponse extends GeneralRestResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CategoriaRest> categorie;

	public List<CategoriaRest> getCategorie() {
		return categorie;
	}

	public void setCategorie(List<CategoriaRest> categorie) {
		this.categorie = categorie;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
