package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Comune;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class ComuneDAO extends GeneralDAO {

	 
	@SuppressWarnings("unchecked")
	public List<Comune> getAll(){
		return entityManager.createQuery("select c from Comune c").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Comune> getComuneByCap(Integer cap){
		Query query= entityManager.createQuery("select c from Comune c where c.cap.cap = :capId");
		query.setParameter("capId", cap);
		return query.getResultList();
	}

	public Comune getComune(Long id) {
		return entityManager.find(Comune.class, id);
	}

	protected void save(Comune comune) {
		entityManager.persist(comune);
		
	}
}
