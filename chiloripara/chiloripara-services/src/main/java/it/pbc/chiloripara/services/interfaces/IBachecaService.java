package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Post;

import java.util.List;

import javax.transaction.Transactional;

public interface IBachecaService {

	public abstract void cancellaPost(Post post);

	public abstract void cancellaPost(Long id);

	public abstract void salvaPost(Post post);

	public abstract List<Post> leggiPost(Long artId);

}