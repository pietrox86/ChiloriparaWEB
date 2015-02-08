package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.Registro;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("linkMB")
@Scope("request")
public class LinkManagedBean {

    private static Logger logger = LoggerFactory.getLogger(LinkManagedBean.class);
    private Map<String, String> categorie = new HashMap<String, String>();
    private Map<String, String> artigiani = new HashMap<String, String>();

    private Long idArt;
    private Long idCat;
    private Date dtExp;
    @Autowired
    private IRegistrazioneService regService;
//    @Autowired
//    private CategoriaDAO catDAO;
//    @Autowired
//    private ArtigianoDAO artDAO;
//    @Autowired
//    private RegistroDAO registroDAO;
    
    @Autowired
    private ICategoriaService catService;
    @Autowired
    private IArtigianoService artService;
  
    
    public void link() {
	Registro artCat = new Registro();
	Artigiano arti = artService.get(idArt);
	//Cat.setArtigiano(arti);
	artCat.setEnabled(true);
	artCat.setCategoria(catService.get(idCat));
	artCat.setDtExpire(new java.sql.Date(dtExp.getTime()));
	artCat.setDtInsert(new java.sql.Timestamp(System.currentTimeMillis()));

	regService.add(artCat);

    }

    @PostConstruct
    private void init() {
	Calendar c = Calendar.getInstance();
	c.setTimeInMillis(System.currentTimeMillis());
	c.add(Calendar.YEAR, 1);
	dtExp = new Date(c.getTimeInMillis());

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

	logger.debug("recuper la lista degli artigiani");
	List<Artigiano> listArt = artService.list();
	logger.debug("lista artigiani recuperata");
	if (listArt != null) {
	    logger.debug("list artigiani contiene " + listArt.size() + " elementi");
	    for (int i = 0; i < listArt.size(); i++) {
		artigiani.put(listArt.get(i).getRagioneSociale(), listArt.get(i).getId() + "");
	    }
	}
    }

    public Date getDtExp() {
	return dtExp;
    }

    public void setDtExp(Date dtExp) {
	this.dtExp = dtExp;
    }

    public static Logger getLogger() {
	return logger;
    }

    public static void setLogger(Logger logger) {
	LinkManagedBean.logger = logger;
    }

    public Map<String, String> getCategorie() {
	return categorie;
    }

    public void setCategorie(Map<String, String> categorie) {
	this.categorie = categorie;
    }

    public Map<String, String> getArtigiani() {
	return artigiani;
    }

    public void setArtigiani(Map<String, String> artigiani) {
	this.artigiani = artigiani;
    }

    public Long getIdArt() {
	return idArt;
    }

    public void setIdArt(Long idArt) {
	this.idArt = idArt;
    }

    public Long getIdCat() {
	return idCat;
    }

    public void setIdCat(Long idCat) {
	this.idCat = idCat;
    }

    

   

}
