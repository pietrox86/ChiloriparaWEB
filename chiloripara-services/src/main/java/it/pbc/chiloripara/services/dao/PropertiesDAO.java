package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Properties;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PropertiesDAO extends GeneralDAO {
	
	 
	
	@SuppressWarnings("unchecked")
	public List<Properties> getAll(){
		return entityManager.createQuery("select c from Properties c").getResultList();
	}

}
