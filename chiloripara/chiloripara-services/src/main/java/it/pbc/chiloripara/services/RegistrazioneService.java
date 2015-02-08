package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.ArtigianoDAO;
import it.pbc.chiloripara.services.dao.CategoriaDAO;
import it.pbc.chiloripara.services.dao.RegistroDAO;
import it.pbc.chiloripara.services.dao.RoleDAO;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.Registro;
import it.pbc.chiloripara.web.model.entities.Role;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrazioneService implements IRegistrazioneService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private CategoriaDAO catDAO;

	@Autowired
	private RegistroDAO regDAO;

	final private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#getArtUserRole()
	 */
	@Override
	public Role getArtUserRole() {
		return roleDAO.getRoleByName("ROLE_ART");
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#encodePassword(java.lang.String)
	 */
	@Override
	public String encodePassword(String plainPassword) {
		return encoder.encode(plainPassword);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#listCategorieAttive()
	 */
	@Override
	public List<Categoria> listCategorieAttive() {
		List<Categoria> allCat = catDAO.list();
		List<Categoria> filteredCat = new ArrayList<Categoria>();
		for (int i = 0; i < allCat.size(); i++)
			if (allCat.get(i).isAbilitato())
				filteredCat.add(allCat.get(i));
		return filteredCat;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#listCategorieDisponibili(it.pbc.chiloripara.web.model.entities.Artigiano)
	 */
	@Override
	public List<Categoria> listCategorieDisponibili(Artigiano art) {
		List<Categoria> allCat = catDAO.list();
		List<Categoria> filteredCat = new ArrayList<Categoria>();
		for (int i = 0; i < allCat.size(); i++)
			if (allCat.get(i).isAbilitato()) {
				filteredCat.add(allCat.get(i));
			}
		ArrayList<Registro> artCat = new ArrayList<Registro>();
		artCat.addAll(art.getArtCat());
		for (int i = 0; i < filteredCat.size(); i++) {
			for (int k = 0; k < artCat.size(); k++) {
				if (artCat.get(k).isEnabled())
					if (filteredCat.get(i).getId().compareTo(artCat.get(k).getCategoria().getId()) == 0) {
						filteredCat.remove(i);
						i--;
						break;
					}

			}
		}

		return filteredCat;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#linkArtToCats(it.pbc.chiloripara.web.model.entities.Artigiano, java.util.List, java.sql.Date)
	 */
	@Override
	@Transactional
	public void linkArtToCats(Artigiano art, List<Categoria> cats, Date expDt) {
		for (int i = 0; i < cats.size(); i++) {
			Registro reg = new Registro();
			reg.setArtigiano(art);
			reg.setCategoria(catDAO.get(cats.get(i).getId()));
			reg.setDtInsert(new Timestamp(System.currentTimeMillis()));
			reg.setDtExpire(expDt);
			logger.info("authCode " + reg.getAuthCode());
			art.setArtCat(new ArrayList<Registro>());
			art.getArtCat().add(reg);
			regDAO.add(reg);
		}
	}

	@Autowired
	private ArtigianoDAO artDAO;

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#linkArtToCatsAuthCode(java.lang.Long, java.util.List, java.sql.Date, java.lang.String)
	 */
	@Override
	@Transactional
	public void linkArtToCatsAuthCode(Long idArt, List<Categoria> cats, Date expDt, String authCode) {
		for (int i = 0; i < cats.size(); i++) {
			Registro artCat = new Registro();
			Artigiano arti = artDAO.get(idArt);
			artCat.setAuthCode(authCode);
			artCat.setArtigiano(arti);
			artCat.setEnabled(false);
			artCat.setCategoria(catDAO.get(cats.get(i).getId()));
			artCat.setDtExpire(expDt);
			artCat.setDtInsert(new java.sql.Timestamp(System.currentTimeMillis()));
			regDAO.add(artCat);
		}
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#getRegistroAttivo(it.pbc.chiloripara.web.model.entities.Artigiano)
	 */
	@Override
	public List<Registro> getRegistroAttivo(Artigiano art) {
		logger.debug("inizio il recupero del registro attivo");
		ArrayList<Registro> reg = new ArrayList<Registro>();
		reg.addAll(art.getArtCat());
		for (int i = 0; i < reg.size(); i++) {
			logger.debug("nel Ciclo " + i);
			if (!reg.get(i).isEnabled()) {
				reg.remove(i);
				i--;
			}
		}
		logger.debug("fine del recupero del registro attivo");
		return reg;

	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#getRegistroArtigiano(java.lang.Long)
	 */
	@Override
	public List<Registro> getRegistroArtigiano(Long artId) {
		logger.debug("inizio il recupero del registro attivo");
		ArrayList<Registro> regDtos= new ArrayList<Registro>();
		
		List<Registro> reg = regDAO.getByArtigiano(artId);
		
		return reg;

	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#generateAuthCode(it.pbc.chiloripara.web.model.entities.Artigiano, java.sql.Date, java.lang.String)
	 */
	@Override
	public String generateAuthCode(Artigiano art, Date date,String modalita) {
		StringBuffer sb = new StringBuffer();
		sb.append(art.getRagioneSociale().hashCode());
		sb.append(date.hashCode());
		return modalita+sb.toString();
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#controllaPartitaIva(java.lang.String)
	 */
	@Override
	public boolean controllaPartitaIva(String piva) {
		return false;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#generatePayPalItems(java.util.List, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public Map<String, String> generatePayPalItems(List<Categoria> target, BigDecimal prezzoPrimaCategoria, BigDecimal prezzoSuccessiveCategorie) {
		HashMap<String, String> items = new HashMap<String, String>();

		return items;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#activateRegistro(java.lang.String)
	 */
	@Override
	@Transactional
	public void activateRegistro(String authCode) {
		regDAO.updateAuthCode(authCode);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#usernameExist(java.lang.String)
	 */
	@Override
	public boolean usernameExist(String username) {
		return artDAO.getArtigianoByUsername(username) != null;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#saveArtigiano(it.pbc.chiloripara.web.model.entities.Artigiano)
	 */
	@Override
	@Transactional
	public void saveArtigiano(Artigiano art) {
		Role role = roleDAO.find(new Long(1));
		art.setRole(role);
		artDAO.save(art);

	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IRegistrazioneService#add(it.pbc.chiloripara.web.model.entities.Registro)
	 */
	@Override
	@Transactional
	public void add(Registro artCat) {
	
		regDAO.add(artCat);
	}

}
