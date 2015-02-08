package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Post;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class PostDAO extends GeneralDAO  {
	

	
	public void save(Post post) {
		entityManager.persist(post);
	}

	
	public void delete(Post post) {
		logger.info("inizio la remove");
		entityManager.remove(post);
		entityManager.flush();
	}

	
	public void delete(Long id) {
		entityManager.createQuery("DELETE FROM Post c where c.id =" + id).executeUpdate();
	}
	
	public Post getPost(Long id){
		return entityManager.find(Post.class, id);
	}


	@SuppressWarnings("unchecked")
	public List<Post> getArtPosts(Long artId) {
		Query query = entityManager.createQuery("Select a from Post a where a.artigiano.id = :artId  ");
		query.setParameter("artId", artId);
		return query.getResultList();
		
	}

}
