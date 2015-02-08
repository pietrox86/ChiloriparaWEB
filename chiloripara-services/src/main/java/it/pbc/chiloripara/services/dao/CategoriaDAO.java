package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Categoria;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CategoriaDAO extends GeneralDAO {

	 

	
	public void save(Categoria cat) {
		entityManager.persist(cat);
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> list() {
		return entityManager.createQuery("SELECT c FROM Categoria c").getResultList();
	}
	
	
	public void update(Categoria cat){
	    entityManager.merge(cat);
	}
	
	public Categoria get(Long id)
	{
	    return entityManager.find(Categoria.class, id);
	}
	
	
	public void delete(Long catIdAction) {
	    entityManager.createQuery("DELETE FROM Categoria c where c.id ="+catIdAction).executeUpdate();    
	}
	
	public void delete(Categoria cat){
		entityManager.remove(cat);
	}
}
