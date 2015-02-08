package it.pbc.chiloripara.rest.model;

import java.io.Serializable;
import java.sql.Date;

public class VotoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private int voto;
	private Date data;

	public VotoRest() {
		super();
	}

	public VotoRest(Long id, int voto, Date data) {
		super();
		this.id = id;
		this.voto = voto;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
