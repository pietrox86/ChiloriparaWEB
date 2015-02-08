package it.pbc.chiloripara.web.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cap {
	
	@Id
	@Column
	private Integer cap;

	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}
	
	

}
