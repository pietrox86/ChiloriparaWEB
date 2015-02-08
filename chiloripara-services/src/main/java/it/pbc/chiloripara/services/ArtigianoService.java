package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.ArtigianoDAO;
import it.pbc.chiloripara.services.dao.PostDAO;
import it.pbc.chiloripara.services.dao.VotoDAO;
import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.IBachecaService;
import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;
import it.pbc.chiloripara.services.interfaces.IUtilityService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Post;
import it.pbc.chiloripara.web.model.entities.Voto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtigianoService implements IArtigianoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUtilityService utilityService;
	@Autowired
	private IBachecaService bachecaService;
	@Autowired
	private IRegistrazioneService regService;
	
	@Autowired
	private ArtigianoDAO artDAO;

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private VotoDAO votoDAO;
	@Autowired
	private IGoogleService googleService;

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#aggiungiMessaggio(it.pbc.chiloripara.web.model.entities.Artigiano, it.pbc.chiloripara.web.model.entities.Post)
	 */
	@Override
	@Transactional
	public void aggiungiMessaggio(Artigiano art, Post post) {
		Artigiano existing = artDAO.get(art.getId());
		Post p = new Post();
		existing.getBacheca().add(p);
		artDAO.update(existing);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#aggiornaDatiPersonali(it.pbc.chiloripara.web.model.entities.Artigiano)
	 */
	@Override
	@Transactional
	public Artigiano aggiornaDatiPersonali(Artigiano artigiano) throws RollbackException {
		Artigiano artAttached = artDAO.get(artigiano.getId());
		String[] ignored = new String[]{"comune","bacheca","role","artCat","voti"};
		BeanUtils.copyProperties(artigiano, artAttached,ignored);
	
		artAttached.setComune(utilityService.getComune(artigiano.getComune().getId()));
		try {
			HashMap<String, Float> coord = googleService.getCoordinate(artAttached.getIndirizzo() + " " + artAttached.getComune().getName());
			if (coord.get("STATUS").floatValue() == 1) {
				artAttached.setLat(coord.get("LAT"));
				artAttached.setLng(coord.get("LNG"));
			} else {
				throw new RollbackException("Attenzione, l'indirizzo non è stato riconosciuto");
			}
		} catch (Exception e) {
			throw new RollbackException("Attenzione, l'indirizzo non è stato riconosciuto");
		}
		checkDetached(artAttached); 
		return artDAO.update(artAttached);
	}
	
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#checkDetached(it.pbc.chiloripara.web.model.entities.Artigiano)
	 */
	@Override
	public void checkDetached(Artigiano art) {

		artDAO.checkDetached(art);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#deletePost(it.pbc.chiloripara.web.model.entities.Post)
	 */
	@Override
	@Transactional
	public void deletePost(Post post) {
		Post p = postDAO.getPost(post.getId());
		postDAO.delete(p);
		;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#vota(int, java.lang.Long)
	 */
	@Override
	@Transactional
	public void vota(int voto, Long id) {

		Voto v = new Voto();
		v.setData(new Date(System.currentTimeMillis()));
		v.setVoto(voto);
		v.setArtigiano(artDAO.get(id));
		votoDAO.add(v);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#getArtigianoByUsername(java.lang.String)
	 */
	@Override
	public Artigiano getArtigianoByUsername(String username) {
		Artigiano art = artDAO.getArtigianoByUsername(username);
		return art;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#get(java.lang.Long)
	 */
	@Override
	public Artigiano get(Long id) {
		Artigiano art = artDAO.get(id);
		return art;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#save(it.pbc.chiloripara.web.model.entities.Artigiano)
	 */
	@Override
	@Transactional
	public void save(Artigiano art) {
		artDAO.save(art);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#list()
	 */
	@Override
	public List<Artigiano> list() {
		ArrayList<Artigiano> dtoList = new ArrayList<Artigiano>();
		List<Artigiano> art = artDAO.list();
		return art;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IArtigianoService#getVoti(java.lang.Long)
	 */
	@Override
	public List<Voto> getVoti(Long artId) {
		ArrayList<Voto> dtoList = new ArrayList<Voto>();
		return dtoList;

	}

}
