package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Registro;
import it.pbc.chiloripara.web.model.idClass.RegistroId;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class RegistroDAO extends GeneralDAO {

	

	
	public void add(Registro artCat) {
		entityManager.persist(artCat);

	}

	public Registro getById(RegistroId id) {
		return entityManager.find(Registro.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Registro> getByAuthCode(String authCode) {
		Query query = entityManager.createQuery("Select a From Registro a where a.authCode = :cod");
		query.setParameter("cod", authCode);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Registro> getByArtigiano(Long artId) {
		Query query = entityManager.createQuery("Select a From Registro a where a.artigiano.id = :artId");
		query.setParameter("artId", artId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Registro> getArtigianiByCategoria(Long catId) {
		logger.debug("inizio la ricerca degli artigiani della categoria " + catId);
		Query query = entityManager.createQuery("Select a From Registro a where a.categoria.id = :cat and a.dtExpire> current_date()");
		query.setParameter("cat", catId);
		return query.getResultList();
	}
	
	public void updateAuthCode(String authCode) {
		Query query = entityManager.createQuery("update Registro a set a.enabled= :en where  a.authCode = :cod");
		query.setParameter("en", true);
		query.setParameter("cod", authCode);
		query.executeUpdate();
	}

}
