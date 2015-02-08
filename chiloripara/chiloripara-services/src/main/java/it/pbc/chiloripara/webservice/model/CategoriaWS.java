package it.pbc.chiloripara.webservice.model;

import java.io.Serializable;

public class CategoriaWS implements Serializable{
	
	private long id;
	private String desc;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
