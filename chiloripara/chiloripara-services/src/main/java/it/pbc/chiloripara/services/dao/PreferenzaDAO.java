package it.pbc.chiloripara.services.dao;

import java.util.List;

import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Preferenza;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class PreferenzaDAO extends GeneralDAO {

	public void save(Preferenza preferenza) {
		entityManager.persist(preferenza);
	}

	public void deletePreferenza(Long artId, Long subCatId) {
		Query query = entityManager
				.createQuery("DELETE FROM Preferenza p where p.artigiano.id = :idArt and p.subCategoria.id = (:subId) ");
		query.setParameter("idArt", artId);
		query.setParameter("subId", subCatId);
		query.executeUpdate();

	}

	public void deleteAllPref(Long subCatId) {
		Query query = entityManager
				.createQuery("DELETE FROM Preferenza p where p.subCategoria.id = (:subId) ");
		query.setParameter("subId", subCatId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Artigiano> getArtigianiBySubCategoria(Long subCatId) {
		Query query = entityManager
				.createQuery("Select p.artigiano from Preferenza p where p.subCategoria.id = :subId ");
		query.setParameter("subId", subCatId);
		return query.getResultList();

	}

}
