package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Preferenza;

import org.springframework.stereotype.Repository;

@Repository
public class PreferenzaDAO extends GeneralDAO {
	
	public void save(Preferenza preferenza) {
		entityManager.persist(preferenza);
	}
	
	

}
