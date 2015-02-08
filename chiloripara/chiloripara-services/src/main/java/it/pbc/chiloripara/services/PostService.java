package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.PostDAO;
import it.pbc.chiloripara.services.interfaces.IPostService;
import it.pbc.chiloripara.web.model.entities.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
	
	@Autowired
	private PostDAO postDAO;

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPostService#getPost(java.lang.Long)
	 */
	@Override
	public Post getPost(Long id) {
		return postDAO.getPost(id);
	}

}
