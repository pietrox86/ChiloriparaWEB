package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Cap;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CapDAO extends GeneralDAO  {

	 
	
	@SuppressWarnings("unchecked")
	public List<Cap> getAll(){
		return entityManager.createQuery("select c from Cap c").getResultList();
	}
}
