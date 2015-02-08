package it.pbc.chiloripara.web.model.idClass;

import java.io.Serializable;
import java.sql.Timestamp;

public class RegistroId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long artigiano;
    private Long categoria;
    private Timestamp dtInsert;

    public RegistroId() {

    }

    public RegistroId(Long artId, Long catId, Timestamp t) {
	this.artigiano = artId;
	this.categoria = catId;
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

    public Long getCategoria() {
	return categoria;
    }

    public void setCategoria(Long categoria) {
	this.categoria = categoria;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    @Override
    public boolean equals(Object o) {
	if (o == null)
	    return false;

	if (!(o instanceof RegistroId))
	    return false;

	RegistroId other = (RegistroId) o;

	if (!(getArtigiano() == other.getArtigiano()))
	    return false;
	if (!(getCategoria() == other.getCategoria()))
	    return false;

	return true;
    }

    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result= (PRIME+getArtigiano().intValue())*(getCategoria().intValue()+dtInsert.hashCode());
	return (result + "").hashCode();
    }
}
