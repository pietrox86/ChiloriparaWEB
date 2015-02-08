package it.pbc.chiloripara.web.model.entities;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Post implements Comparable<Post> {

	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Timestamp dtInsert;

	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name = "artigiano_id", referencedColumnName = "id", nullable = false, updatable = false)
	private Artigiano artigiano;

	@Column(nullable = false)
	private String testo;

	@Lob
	@Column
	private byte[] img;

	@Column
	private boolean hasImage;	
	

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
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

	public Artigiano getArtigiano() {
		return artigiano;
	}

	public void setArtigiano(Artigiano artigiano) {
		this.artigiano = artigiano;
	}

	@Override
	public int compareTo(Post o) {
		return this.getDtInsert().compareTo(o.getDtInsert())*-1;

	}

	@Override
	public boolean equals(Object o) {

		if (o == null)
			return false;

		if (!(o instanceof Post))
			return false;

		Post p = (Post) o;
		return this.id.compareTo(p.getId()) == 0;
	}

}
