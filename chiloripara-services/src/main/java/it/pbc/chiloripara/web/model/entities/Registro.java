package it.pbc.chiloripara.web.model.entities;

import it.pbc.chiloripara.web.model.idClass.RegistroId;

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
@IdClass(RegistroId.class)
public class Registro  {
       
   
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="categoria")
    @Id
    private Categoria categoria;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="artigiano")
    @Id
    private Artigiano artigiano;
    
    @Column(nullable=false)
    private Date dtExpire;
    
    @Id
    @Column(nullable=false)
    private Timestamp dtInsert;
    @Column
    private String authCode;
    @Column
    private boolean enabled =false;

    

    public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Artigiano getArtigiano() {
        return artigiano;
    }

    public void setArtigiano(Artigiano artigiano) {
        this.artigiano = artigiano;
    }

    public Date getDtExpire() {
        return dtExpire;
    }

    public void setDtExpire(Date dtExpire) {
        this.dtExpire = dtExpire;
    }

    public Timestamp getDtInsert() {
        return dtInsert;
    }

    public void setDtInsert(Timestamp dtInsert) {
        this.dtInsert = dtInsert;
    }


  
   
    
}
