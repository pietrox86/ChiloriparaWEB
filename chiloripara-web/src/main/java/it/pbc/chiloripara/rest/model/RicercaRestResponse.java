package it.pbc.chiloripara.rest.model;

import java.io.Serializable;
import java.util.List;

public class RicercaRestResponse extends GeneralRestResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3996800202525977247L;
	
	private List<ArtigianoHeaderRest> list ;
	public RicercaRestResponse() {
		super();
	}
	public List<ArtigianoHeaderRest> getList() {
		return list;
	}
	public void setList(List<ArtigianoHeaderRest> list) {
		this.list = list;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
