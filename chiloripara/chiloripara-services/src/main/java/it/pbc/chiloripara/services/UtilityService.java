package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.CapDAO;
import it.pbc.chiloripara.services.dao.ComuneDAO;
import it.pbc.chiloripara.services.interfaces.IUtilityService;
import it.pbc.chiloripara.web.model.entities.Cap;
import it.pbc.chiloripara.web.model.entities.Comune;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilityService implements IUtilityService {

	@Autowired
	private CapDAO capDAO;

	@Autowired
	private ComuneDAO comuneDAO;

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IUtilityService#getAll()
	 */
	@Override
	public List<Cap> getAll() {
		List<Cap> caps = capDAO.getAll();
		return caps;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IUtilityService#getComuni(java.lang.Integer)
	 */
	@Override
	public List<Comune> getComuni(Integer cap) {
		List<Comune> comuni = comuneDAO.getComuneByCap(cap);
		
		return comuni;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IUtilityService#getComune(java.lang.Long)
	 */
	@Override
	public Comune getComune(Long id) {
		Comune comune = comuneDAO.getComune(id);
		return comune;
	}

}
