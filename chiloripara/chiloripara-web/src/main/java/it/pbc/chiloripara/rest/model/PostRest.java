package it.pbc.chiloripara.rest.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostRest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5458886036372536709L;
	private Long id;
	private Timestamp dtInsert;
	private String testo;
	private byte[] img;
	private boolean hasImage;

	public PostRest() {
		super();
	}

	public PostRest(Long id, Timestamp dtInsert, String testo, byte[] img, boolean hasImage) {
		super();
		this.id = id;
		this.dtInsert = dtInsert;
		this.testo = testo;
		this.img = img;
		this.hasImage = hasImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDtInsert() {
		return dtInsert;
	}

	public void setDtInsert(Timestamp dtInsert) {
		this.dtInsert = dtInsert;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
