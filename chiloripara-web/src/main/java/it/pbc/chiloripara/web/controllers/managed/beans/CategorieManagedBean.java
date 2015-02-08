package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Categoria;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("catMB")
@Scope("request")
public class CategorieManagedBean {
    private final Logger logger = LoggerFactory.getLogger(CategorieManagedBean.class);

//    @Autowired
//    private CategoriaDAO catDAO;
    
    @Autowired
    private ICategoriaService catService; 

    private Categoria cat;
    private List<Categoria> categorie;
    private Long catIdAction;

    public Long getCatIdAction() {
	return catIdAction;
    }

    public void setCatIdAction(Long catIdAction) {
	this.catIdAction = catIdAction;
    }

    public Logger getLogger() {
	return logger;
    }

    public void save() {

	try {
		catService.save(cat);
	} catch (Exception e) {
	    logger.debug("Creo il messaggio d'errore");
	    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Errore nell'inserimento della categoria");
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	    return;

	}
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Completato", "Categoria '"+cat.getDescrizione()+"' inserita correttamente.");
	FacesContext.getCurrentInstance().addMessage(null, msg);
	init();
    }

  

    public Categoria getCat() {
	return cat;
    }

    public void setCat(Categoria cat) {
	this.cat = cat;
    }

    public List<Categoria> getCategorie() {
	return categorie;
    }

    public void setCategorie(List<Categoria> categorie) {
	this.categorie = categorie;
    }

    @PostConstruct
    private void init() {
	logger.debug("Inizializzo Managed bean");
	this.categorie = catService.list();
	this.cat = new Categoria();

    }

    public void enable(ActionEvent event) {
	catIdAction = (Long) event.getComponent().getAttributes().get("catIdAction");
	catService.enableCat(catIdAction);
	init();
    }

    public void disable(ActionEvent event) {
	catIdAction = (Long) event.getComponent().getAttributes().get("catIdAction");
	catService.disableCat(catIdAction);
	
	init();
    }

    public void delete(ActionEvent event) {
	catIdAction = (Long) event.getComponent().getAttributes().get("catIdAction");
	try{
		catService.delete(catIdAction);
	}catch(Exception e)
	{
	    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore", "Impossibile eliminare la categoria");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return;
	}
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Completato", "Categoria eliminata.");
	FacesContext.getCurrentInstance().addMessage(null, msg);
	init();

    }

}
