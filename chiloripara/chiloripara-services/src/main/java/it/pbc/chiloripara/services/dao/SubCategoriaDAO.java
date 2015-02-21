package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.SubCategoria;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class SubCategoriaDAO extends GeneralDAO {
	@SuppressWarnings("unchecked")
	public List<SubCategoria> getAll() {
		return entityManager.createQuery("select c from SubCategoria c")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SubCategoria> getSubCat(Long catId) {
		Query q = entityManager
				.createQuery("select c from SubCategoria c where c.categoria.id = :catId");
		q.setParameter("catId", catId);
		return q.getResultList();
	}

	public SubCategoria get(Long id) {
		return entityManager.find(SubCategoria.class, id);
	}

	public void delete(Long subCatId) {
		entityManager.remove(get(subCatId));

	}

	public void save(SubCategoria sub) {
		entityManager.persist(sub);

	}

}
