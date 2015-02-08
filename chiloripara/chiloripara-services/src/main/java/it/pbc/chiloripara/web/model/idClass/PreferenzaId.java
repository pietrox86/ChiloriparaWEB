package it.pbc.chiloripara.web.model.idClass;

import java.io.Serializable;
import java.sql.Timestamp;

public class PreferenzaId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long artigiano;
    private Long subCategoria;
    private Timestamp dtInsert;

    public PreferenzaId() {

    }

    public PreferenzaId(Long artId, Long catId, Timestamp t) {
	this.artigiano = artId;
	this.subCategoria = catId;
	this.dtInsert = t;
    }

    public Timestamp getDtInsert() {
		return dtInsert;
	}

	public void setDtInsert(Timestamp dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Long getArtigiano() {
	return artigiano;
    }

    public void setArtigiano(Long artigiano) {
	this.artigiano = artigiano;
    }

    public Long getsubCategoria() {
	return subCategoria;
    }

    public void setsubCategoria(Long subCategoria) {
	this.subCategoria = subCategoria;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }
/*
    @Override
    public boolean equals(Object o) {
	if (o == null)
	    return false;

	if (!(o instanceof PreferenzaId))
	    return false;

	PreferenzaId other = (PreferenzaId) o;

	if (!(getArtigiano() == other.getArtigiano()))
	    return false;
	if (!(getsubCategoria() == other.getsubCategoria()))
	    return false;

	return true;
    }

    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result= (PRIME+getArtigiano().intValue())*(getsubCategoria().intValue()+dtInsert.hashCode());
	return (result + "").hashCode();
    }
    */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artigiano == null) ? 0 : artigiano.hashCode());
		result = prime * result
				+ ((subCategoria == null) ? 0 : subCategoria.hashCode());
		result = prime * result
				+ ((dtInsert == null) ? 0 : dtInsert.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreferenzaId other = (PreferenzaId) obj;
		if (artigiano == null) {
			if (other.artigiano != null)
				return false;
		} else if (!artigiano.equals(other.artigiano))
			return false;
		if (subCategoria == null) {
			if (other.subCategoria != null)
				return false;
		} else if (!subCategoria.equals(other.subCategoria))
			return false;
		if (dtInsert == null) {
			if (other.dtInsert != null)
				return false;
		} else if (!dtInsert.equals(other.dtInsert))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PreferenzaId [artigiano=" + artigiano + ", subCategoria="
				+ subCategoria + ", dtInsert=" + dtInsert + "]";
	}
    
    
}
