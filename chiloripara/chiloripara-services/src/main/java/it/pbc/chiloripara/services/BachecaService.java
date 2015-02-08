package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.PostDAO;
import it.pbc.chiloripara.services.interfaces.IBachecaService;
import it.pbc.chiloripara.web.model.entities.Post;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BachecaService implements IBachecaService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PostDAO postDAO;
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IBachecaService#cancellaPost(it.pbc.chiloripara.web.model.entities.Post)
	 */
	@Override
	@Transactional
	public void cancellaPost(Post post) {
		postDAO.delete(post.getId());
	}
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IBachecaService#cancellaPost(java.lang.Long)
	 */
	@Override
	@Transactional
	public void cancellaPost(Long id) {
		postDAO.delete(id);
	}
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IBachecaService#salvaPost(it.pbc.chiloripara.web.model.entities.Post)
	 */
	@Override
	@Transactional
	public void salvaPost(Post post){
		postDAO.save(post);
	}
	
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IBachecaService#leggiPost(java.lang.Long)
	 */
	@Override
	public List<Post> leggiPost(Long artId){
		List<Post> posts = 	postDAO.getArtPosts(artId);
		return posts;
	
	}
	
	

}
