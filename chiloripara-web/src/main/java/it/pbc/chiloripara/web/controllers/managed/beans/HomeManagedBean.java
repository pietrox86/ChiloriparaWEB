package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Categoria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("homeMB")
@Scope("request")
public class HomeManagedBean {
    private static final Logger logger = LoggerFactory.getLogger(HomeManagedBean.class);

    private Map<String, String> categorie = new HashMap<String, String>();
//    @Autowired
//    private CategoriaDAO catDAO;
    @Autowired
    private ICategoriaService catService;

    private Categoria categoria = new Categoria();
    private String indirizzo = "";

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
	List<Categoria> list = catService.list();
	logger.debug("lista categorie recuperata");
	if (list != null) {
	    logger.debug("list categorie contiene " + list.size() + " elementi");
	    for (int i = 0; i < list.size(); i++) {
		if (list.get(i).isAbilitato())
		    categorie.put(list.get(i).getDescrizione(), list.get(i).getId() + "");
	    }
	}

    }

}
