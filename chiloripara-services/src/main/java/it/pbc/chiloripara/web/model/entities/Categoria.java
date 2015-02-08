package it.pbc.chiloripara.web.model.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Categoria {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@Column(unique=true)
	private String descrizione;
	@Column
	private boolean abilitato;
	
	@OneToMany(mappedBy="categoria",orphanRemoval=true, cascade={CascadeType.PERSIST,CascadeType.MERGE})
	protected Set<Registro> artCat;

	public Set<Registro> getArtCat() {
	    return artCat;
	}
	
	public void setArtCat(Set<Registro> artCat) {
	    this.artCat = artCat;
	}

	

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isAbilitato() {
	    return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
	    this.abilitato = abilitato;
	}

	
}
