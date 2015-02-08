package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Artigiano;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class ArtigianoDAO extends GeneralDAO {

	public void save(Artigiano art) {
		entityManager.persist(art);
	}

	public Artigiano update(Artigiano art) {
		return entityManager.merge(art);
	}

	@SuppressWarnings("unchecked")
	public List<Artigiano> list() {
		return entityManager.createQuery("SELECT a FROM Artigiano a").getResultList();
	}

	public Artigiano get(Long id) {
		Artigiano art = entityManager.find(Artigiano.class, id);
		if (art != null) {
			art.getVoti().size();
			art.getAuthorities().size();
			art.getBacheca().size();
			art.getRole();
		}
		return art;
	}

	public void checkDetached(Artigiano art) {
		logger.debug("CONTAINS? :" + entityManager.contains(art));
		for (int i = 0; i < art.getArtCat().size(); i++)
			logger.debug("CONTAINS reg ? :" + entityManager.contains(art.getArtCat().get(i)));
		logger.debug("CONTAINS role? :" + entityManager.contains(art.getRole()));
		for (int i = 0; i < art.getBacheca().size(); i++)
			logger.debug("CONTAINS bacheca? :" + entityManager.contains(art.getBacheca().get(i)));
		logger.debug("CONTAINS comune? :" + entityManager.contains(art.getComune()));
		for (int i = 0; i < art.getVoti().size(); i++)
			logger.debug("CONTAINS voto? :" + entityManager.contains(art.getVoti().get(i)));
	}

	@SuppressWarnings("unchecked")
	public List<Artigiano> getByListId(List<Long> idList) {
		Query query = entityManager.createQuery("Select a from Artigiano a where a.id IN ( :list ) ");
		query.setParameter("list", idList);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Artigiano getArtigianoByUsername(String username) {
		Query sql = entityManager.createQuery("select a from Artigiano a where a.username= :username");
		sql.setParameter("username", username);
		List<Artigiano> result = sql.getResultList();
		if (result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

}
