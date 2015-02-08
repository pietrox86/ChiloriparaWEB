package it.pbc.chiloripara.rest.model;

import java.io.Serializable;
import java.util.List;

public class ArtigianoRestResponse extends GeneralRestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4143354189913967105L;
	
	private List<ArtigianoRest> artigiani;
	public List<ArtigianoRest> getArtigiani() {
		return artigiani;
	}
	public void setArtigiani(List<ArtigianoRest> artigiani) {
		this.artigiani = artigiani;
	}
	

}
