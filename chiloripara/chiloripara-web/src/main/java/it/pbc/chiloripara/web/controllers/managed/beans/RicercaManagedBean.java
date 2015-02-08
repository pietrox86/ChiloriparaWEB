package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IRicercaService;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.notPersisted.ArtigianoDistanza;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
    private Long subCatId;
    private Long marcaId;
    private String address;
    
    // HOMEMB
    private Map<String, String> categorie = new HashMap<String, String>();
	private Map<String, String> subCats = new HashMap<String, String>();
	@Autowired
	private ICategoriaService catService;
	private Categoria categoria = new Categoria();
	private String indirizzo = "";
	private boolean renderSubCats = false;
	private List<Categoria> list;
    
    
	
	
	  public Map<String, String> getCategorie() {
			return categorie;
		}

		public void setCategorie(Map<String, String> categorie) {
			this.categorie = categorie;
		}

		public Map<String, String> getSubCats() {
			return subCats;
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

		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}

		public String getIndirizzo() {
			return indirizzo;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}

		public boolean isRenderSubCats() {
			return renderSubCats;
		}

		public void setRenderSubCats(boolean renderSubCats) {
			this.renderSubCats = renderSubCats;
		}

		public List<Categoria> getList() {
			return list;
		}

		public void setList(List<Categoria> list) {
			this.list = list;
		}
		
		public void saveCat() {
			catService.save(categoria);
			categoria = new Categoria();
			invalidateCat();
		}

		private void invalidateCat() {
			categoria = null;
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
			this.subCats.clear();
			Categoria c = catService.getNoLazy(getCatId());
			renderSubCats = false;
			if (c != null && c.getSubCat() != null && c.getSubCat().size() > 0) {
				renderSubCats = true;
				for (int i = 0; i < c.getSubCat().size(); i++)
					this.subCats.put(c.getSubCat().get(i).getName(), c.getSubCat()
							.get(i).getId()
							+ "");
			}
		}
	
	/*FINE*/
	
	
	
    
   
  

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

	public IGoogleService getGoogleService() {
		return googleService;
	}

	public void setGoogleService(IGoogleService googleService) {
		this.googleService = googleService;
	}

	public Long getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(Long subCatId) {
		this.subCatId = subCatId;
	}

	public Long getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}

	public static Logger getLogger() {
		return logger;
	}
    
}
