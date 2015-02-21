package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.CategoriaDAO;
import it.pbc.chiloripara.services.dao.PreferenzaDAO;
import it.pbc.chiloripara.services.dao.SubCategoriaDAO;
import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.SubCategoria;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements ICategoriaService {

	@Autowired
	private CategoriaDAO catDAO;
	@Autowired
	private SubCategoriaDAO subCatDAO;
	@Autowired
	private PreferenzaDAO prefDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.pbc.chiloripara.services.ICategoriaService#save(it.pbc.chiloripara
	 * .web.model.entities.Categoria)
	 */
	@Override
	@Transactional
	public void save(Categoria cat) {
		catDAO.save(cat);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.pbc.chiloripara.services.ICategoriaService#list()
	 */
	@Override
	public List<Categoria> list() {
		List<Categoria> cat = catDAO.list();

		return cat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.pbc.chiloripara.services.ICategoriaService#get(java.lang.Long)
	 */
	@Override
	public Categoria get(Long catIdAction) {

		return catDAO.get(catIdAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.pbc.chiloripara.services.ICategoriaService#delete(java.lang.Long)
	 */
	@Override
	@Transactional
	public void delete(Long catIdAction) {
		Categoria cat = catDAO.get(catIdAction);
		catDAO.delete(cat);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.pbc.chiloripara.services.ICategoriaService#enableCat(java.lang.Long)
	 */
	@Override
	@Transactional
	public void enableCat(Long catIdAction) {
		Categoria catRetrieved = catDAO.get(catIdAction);
		catRetrieved.setAbilitato(true);
		catDAO.update(catRetrieved);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.pbc.chiloripara.services.ICategoriaService#disableCat(java.lang.Long)
	 */
	@Override
	@Transactional
	public void disableCat(Long catIdAction) {
		Categoria catRetrieved = catDAO.get(catIdAction);
		catRetrieved.setAbilitato(false);
		catDAO.update(catRetrieved);

	}

	@Override
	public Categoria getNoLazy(Long id) {
		Categoria cat = this.get(id);
		cat.getSubCat();
		return cat;

	}

	@Override
	public SubCategoria getSubCat(Long id) {
		return subCatDAO.get(id);
	}

	@Override
	@Transactional
	public void deleteSubCat(Long subCatId) {
		prefDAO.deleteAllPref(subCatId);
		subCatDAO.delete(subCatId);

	}

	@Override
	@Transactional
	public void createSubCat(Long catId, String descrizione) {
		Categoria cat = getNoLazy(catId);
		SubCategoria sub = new SubCategoria();
		sub.setCategoria(cat);
		sub.setName(descrizione);
		subCatDAO.save(sub);

	}

}
