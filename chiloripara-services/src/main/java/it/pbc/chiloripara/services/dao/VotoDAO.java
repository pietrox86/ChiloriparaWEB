package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Voto;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class VotoDAO extends GeneralDAO {
		
	public void add(Voto voto){
			entityManager.persist(voto);
			
		}

	@SuppressWarnings("unchecked")
	public List<Voto> getAll(Long artId) {
		Query query = entityManager.createQuery("Select a from Voto a where a.artigiano.id = :artId  ");
		query.setParameter("artId", artId);
		return query.getResultList();
	}
}
