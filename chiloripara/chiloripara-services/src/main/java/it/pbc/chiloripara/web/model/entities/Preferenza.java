package it.pbc.chiloripara.web.model.entities;

import it.pbc.chiloripara.web.model.idClass.PreferenzaId;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(PreferenzaId.class)
public class Preferenza {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "subCategoria")
	@Id
	private SubCategoria subCategoria;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "artigiano")
	@Id
	private Artigiano artigiano;

	@Column(nullable = false)
	@Id
	private Timestamp dtInsert;

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Artigiano getArtigiano() {
		return artigiano;
	}

	public void setArtigiano(Artigiano artigiano) {
		this.artigiano = artigiano;
	}

	public Timestamp getDtInsert() {
		return dtInsert;
	}

	public void setDtInsert(Timestamp dtInsert) {
		this.dtInsert = dtInsert;
	}

	@Override
	public String toString() {
		return "Preferenza [subCategoria=" + subCategoria + ", artigiano="
				+ artigiano + ", dtInsert=" + dtInsert + "]";
	}
	
	

}
