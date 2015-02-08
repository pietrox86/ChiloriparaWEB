package it.pbc.chiloripara.web.model.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Voto {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private int voto;
	@Column
	private Date data;
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name = "artigiano_id", referencedColumnName = "id", nullable = false, updatable = false)
	private Artigiano artigiano;
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
	public Artigiano getArtigiano() {
		return artigiano;
	}
	public void setArtigiano(Artigiano artigiano) {
		this.artigiano = artigiano;
	}
	
	
}
