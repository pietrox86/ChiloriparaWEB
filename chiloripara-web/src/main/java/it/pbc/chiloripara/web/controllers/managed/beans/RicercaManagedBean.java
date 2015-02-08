package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IRicercaService;
import it.pbc.chiloripara.web.model.notPersisted.ArtigianoDistanza;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ricercaMB")
@Scope("session")
public class RicercaManagedBean {

    private static final Logger logger = LoggerFactory.getLogger(RicercaManagedBean.class);
    @Autowired
    private IRicercaService ricercaService;
    @Autowired
    private IGoogleService googleService;

    private List<ArtigianoDistanza> result;

    private Long catId;
    private String address;

    public IRicercaService getRicercaService() {
	return ricercaService;
    }

    public void setRicercaService(IRicercaService ricercaService) {
	this.ricercaService = ricercaService;
    }

    public List<ArtigianoDistanza> getResult() {
    	logger.info("ritorno i risultati: "+result.size());
	return result;
    }

    public void setResult(List<ArtigianoDistanza> result) {
    	logger.info("salvo i risultati: "+result.size());
	this.result = result;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public Long getCatId() {
	return catId;
    }

    public void setCatId(Long catId) {
	this.catId = catId;
    }

    private float lat;
    private float lng;

    public float getLat() {
	return lat;
    }

    public void setLat(float lat) {
	this.lat = lat;
    }

    public float getLng() {
	return lng;
    }

    public void setLng(float lng) {
	this.lng = lng;
    }

    public String find(){
	logger.debug("inizio la ricerca degli artigiani");
	
	logger.debug("categoria "+ getCatId());
	logger.debug("indirizzo "+ getAddress());
	
	HashMap<String, Float> coordinate;
	try {
	    coordinate = googleService.getCoordinate(getAddress());
	   this.result= ricercaService.getArtigianiByCatAndCoord(getCatId(), coordinate.get("LAT"), coordinate.get("LNG"));
	   logger.debug("risultati: "+result.size());
	} catch (IOException e) {
		e.printStackTrace();
	    // TODO Auto-generated catch block
	    return "home";
	}
	logger.debug("cooordinate : "+coordinate.get("LAT")+" - "+coordinate.get("LNG"));
	return "risultati";
    }
}
