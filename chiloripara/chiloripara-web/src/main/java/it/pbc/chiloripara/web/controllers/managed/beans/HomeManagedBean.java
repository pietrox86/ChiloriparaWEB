package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.SubCategoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.swing.event.ChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("homeMB")
@Scope("request")
public class HomeManagedBean {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeManagedBean.class);

	private Map<String, String> categorie = new HashMap<String, String>();
	private Map<String, String> subCats = new HashMap<String, String>();
	@Autowired
	private ICategoriaService catService;
	private Long catId;

	private Categoria categoria = new Categoria();
	private String indirizzo = "";
	private boolean renderSubCats = false;
	private List<Categoria> list;

	public Map<String, String> getSubCats() {
		return subCats;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public void setSubCats(Map<String, String> subCats) {
		this.subCats = subCats;
	}

	public ICategoriaService getCatService() {
		return catService;
	}

	public void setCatService(ICategoriaService catService) {
		this.catService = catService;
	}

	public boolean isRenderSubCats() {
		return renderSubCats;
	}

	public void setRenderSubCats(boolean renderSubCats) {
		this.renderSubCats = renderSubCats;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void saveCat() {
		catService.save(categoria);
		categoria = new Categoria();
		invalidateCat();
	}

	private void invalidateCat() {
		categoria = null;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public static Logger getLogger() {
		return logger;
	}

	public Map<String, String> getCategorie() {
		return categorie;
	}

	public void setCategorie(Map<String, String> categorie) {
		this.categorie = categorie;
	}

	@PostConstruct
	private void init() {
		logger.debug("recuper la lista delle categorie");
		list = catService.list();
		logger.debug("lista categorie recuperata");
		if (list != null) {
			logger.debug("list categorie contiene " + list.size() + " elementi");
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isAbilitato())
					categorie.put(list.get(i).getDescrizione(), list.get(i)
							.getId() + "");
			}
		}

	}

	public void loadSubCat() {
		logger.debug("inizio il load + "+getCatId());
		this.subCats.clear();
		Categoria c = null;
		;
		for (int i = 0; i < list.size(); i++) {
			logger.debug("recupero la categoria con id ="+i);
			c = list.get(i);logger.debug(""+c.getId() +"   "+getCatId());
			if (c.getId().compareTo(getCatId()) == 0)
				break;
		}
		logger.debug(c+ " "+ c.getSubCat().size());
		if (c != null && c.getSubCat() != null && c.getSubCat().size() > 0) {
			renderSubCats = true;
			for (int i = 0; i < c.getSubCat().size(); i++)
				this.subCats.put(c.getSubCat().get(i).getName(), c.getSubCat()
						.get(i).getId()
						+ "");
		}
		logger.debug(subCats.toString());
	}
	
	public void loadSubCat(ValueChangeEvent event) {
		logger.debug("echecazzo");
		this.subCats.clear();
		Categoria c = null;
		;
		for (int i = 0; i < list.size(); i++) {
			logger.debug("recupero la categoria con id ="+i);
			c = list.get(i);
			Long id = (Long) event.getNewValue();
			if (c.getId().compareTo(id) == 0)
				break;
		}
		if (c != null && c.getSubCat() != null && c.getSubCat().size() > 0) {
			renderSubCats = true;
			for (int i = 0; i < c.getSubCat().size(); i++)
				this.subCats.put(c.getSubCat().get(i).getName(), c.getSubCat()
						.get(i).getId()
						+ "");
		}
	}

}
