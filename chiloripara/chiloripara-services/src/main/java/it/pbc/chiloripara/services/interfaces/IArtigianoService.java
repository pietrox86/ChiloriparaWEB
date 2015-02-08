package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Post;
import it.pbc.chiloripara.web.model.entities.Voto;

import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;

public interface IArtigianoService {

	public abstract void aggiungiMessaggio(Artigiano art, Post post);

	public abstract Artigiano aggiornaDatiPersonali(Artigiano artigiano) throws RollbackException;

	public abstract void checkDetached(Artigiano art);

	public abstract void deletePost(Post post);

	public abstract void vota(int voto, Long id);

	public abstract Artigiano getArtigianoByUsername(String username);

	public abstract Artigiano get(Long id);

	public abstract void save(Artigiano art);

	public abstract List<Artigiano> list();

	public abstract List<Voto> getVoti(Long artId);

}