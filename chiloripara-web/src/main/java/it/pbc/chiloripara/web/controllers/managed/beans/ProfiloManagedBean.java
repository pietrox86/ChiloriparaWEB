package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.IBachecaService;
import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;
import it.pbc.chiloripara.services.interfaces.IUtilityService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Comune;
import it.pbc.chiloripara.web.model.entities.Post;
import it.pbc.chiloripara.web.model.entities.Voto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("profiloMB")
@Scope("session")
public class ProfiloManagedBean extends GeneralBean {
	@Autowired
	private IArtigianoService artServ;
	@Autowired
	private SecurityManagedBean security;
	@Autowired
	private IArtigianoService artService;
	@Autowired
	private IUtilityService utilityService;
	@Autowired
	private IBachecaService bachecaService;
	@Autowired
	private IGoogleService googleService;
	@Autowired
	private IRegistrazioneService regService;

	private MapModel model = new DefaultMapModel();

	private Artigiano artigiano;
	private String newTopic = "";
	private Long postId;

	private UploadedFile file;
	private int mediaVoto;
	private int voto;
	private boolean fromRisultati = false;

	private Map<Integer, Integer> caps = new HashMap<Integer, Integer>();
	private Map<String, String> comuni = new HashMap<String, String>();

	private List<Comune> comuniCombo;

	private String cap;

	public Map<Integer, Integer> getCaps() {
		return caps;
	}

	public void setCaps(Map<Integer, Integer> caps) {
		this.caps = caps;
	}

	public Map<String, String> getComuni() {
		return comuni;
	}

	public void setComuni(Map<String, String> comuni) {
		this.comuni = comuni;
	}

	public List<Comune> getComuniCombo() {
		return comuniCombo;
	}

	public void setComuniCombo(List<Comune> comuniCombo) {
		this.comuniCombo = comuniCombo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public IUtilityService getUtilityService() {
		return utilityService;
	}

	public void setUtilityService(IUtilityService utilityService) {
		this.utilityService = utilityService;
	}

	public IBachecaService getBachecaService() {
		return bachecaService;
	}

	public void setBachecaService(IBachecaService bachecaService) {
		this.bachecaService = bachecaService;
	}

	public String goToRisultati() {
		this.fromRisultati = false;
		return "risultati";
	}

	public boolean isFromRisultati() {
		return fromRisultati;
	}

	public void setFromRisultati(boolean fromRisultati) {
		this.fromRisultati = fromRisultati;
	}

	public Artigiano getArtigiano() {
		return artigiano;
	}

	public void setArtigiano(Artigiano artigiano) {
		this.artigiano = artigiano;
	}

	public Long getPostId() {
		return postId;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String loadLoggedProfile() {
		logger.info("inizio la lettura");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.artigiano = (Artigiano) authentication.getPrincipal();
		logger.info("artigiano caricato");
		setMarker();
		this.calcolaMediaVoti();
		this.fromRisultati = false;
		return "profilo";
	}

	public String editLoggedProfile() {
		logger.info("inizio la lettura");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.artigiano = null;
		this.artigiano = artService.get(((Artigiano) authentication.getPrincipal()).getId());
		this.loadComuni();
		logger.info("artigiano caricato");
		setMarker();
		this.fromRisultati = false;
		return "modprofilo";
	}

	public void loadComuni() {
		logger.debug("load comuni start");
		artService.checkDetached(artigiano);
		Integer cap = this.artigiano.getComune().getCap().getCap();
		artService.checkDetached(artigiano);
		this.comuni.clear();
		comuniCombo = utilityService.getComuni(cap);

		for (int i = 0; i < comuniCombo.size(); i++)
			this.comuni.put(comuniCombo.get(i).getName(), comuniCombo.get(i).getId() + "");

	}

	public String aggiornaDati() {
		logger.debug("ARTIGIANO TOSTRING " + this.artigiano.getId());

		try {
			this.artigiano = artServ.aggiornaDatiPersonali(artigiano);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return editLoggedProfile();
	}

	public void aggiornaComune() {
		Comune comune = utilityService.getComune(artigiano.getComune().getId());
		artigiano.setComune(comune);
	}

	public String showProfile() {
		logger.debug("inizio lo showProfile");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		logger.debug("inizio lo showProfile id = " + params.get("idArt"));
		this.artigiano = null;
		this.artigiano = artService.get(new Long(params.get("idArt")));
		setMarker();
		this.calcolaMediaVoti();
		this.fromRisultati = true;
		return "profilo";

	}

	public String showProfile(String idArt) {
		this.artigiano = artService.get(new Long(idArt));
		setMarker();
		this.calcolaMediaVoti();
		this.fromRisultati = true;
		return "profilo";

	}

	private void calcolaMediaVoti() {
		List<Voto> voti = this.artigiano.getVoti();
		if (voti == null || voti.size() == 0) {
			this.mediaVoto = 0;
			return;
		} else {
			int somma = 0;
			int i = 0;
			for (; i < voti.size(); i++)
				somma += voti.get(i).getVoto();

			this.mediaVoto = somma / i;
		}

	}

	public void vota() {
		logger.debug("inizio il voto");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String cookieName = "CLRVT" + this.artigiano.getId() + sdf.format(new Date(cal.getTimeInMillis()));
		logger.debug("il cookie da cercare è " + cookieName);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		logger.debug("Recupero la mappa dei cookies");
		Map<String, Object> cookies = fc.getExternalContext().getRequestCookieMap();

		if (cookies.get(cookieName) != null) {
			logger.debug("Ho trovato il cookie");
			faceMsg.addErrorMessage(labels.getLabel("profilo_voto_doppio"), "");
			return;
		} else {
			logger.debug("cookie non trovato, lo creo");
			Cookie cookie = new Cookie(cookieName, "gia votato!");
			cookie.setMaxAge(86400);
			response.addCookie(cookie);
		}
		faceMsg.addInfoMessage(labels.getLabel("profilo_voto"), "");
		artServ.vota(this.voto, this.artigiano.getId());

	}

	private void setMarker() {
		Marker marker = new Marker(new LatLng(artigiano.getLat(), artigiano.getLng()), artigiano.getRagioneSociale());
		this.model.addOverlay(marker);

	}

	public MapModel getModel() {
		return this.model;
	}

	public void setModel(MapModel model) {
		this.model = model;
	}

	public void addTopic() {
		logger.error("inizio a caricare il post");
		Post post = new Post();
		post.setDtInsert(new Timestamp(System.currentTimeMillis()));
		post.setTesto(this.getNewTopic());
		if (post.getTesto() == null)
			post.setTesto("");
		post.setArtigiano(this.artigiano);
		post.setHasImage(!file.getFileName().equalsIgnoreCase(""));

		try {
			InputStream is = file.getInputstream();
			long length = file.getSize();

			byte[] bytes = new byte[(int) length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file ");
			}
			is.close();
			post.setImg(bytes);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.artigiano.getBacheca().add(post);
		bachecaService.salvaPost(post);
		// TODO completare il sort
		// Collections.sort(this.artigiano.getBacheca());
		// this.artigiano = artServ.aggiorna(this.artigiano);
		logger.info("merge concluso");

	}

	public void deleteTopic(ActionEvent event) {
		postId = (Long) event.getComponent().getAttributes().get("postId");
		try {
			for (int i = 0; i < this.artigiano.getBacheca().size(); i++) {
				if (this.artigiano.getBacheca().get(i).getId().compareTo(postId) == 0) {
					Post deletable = this.artigiano.getBacheca().get(i);
					this.artigiano.getBacheca().remove(deletable);
					deletable.setArtigiano(null);
					logger.info("chiamo la delete");
					bachecaService.cancellaPost(postId);

					break;
				}

			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossibile eliminare il messaggio","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Topic eliminato.","");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public String getNewTopic() {
		return newTopic;
	}

	public void setNewTopic(String newTopic) {
		this.newTopic = newTopic;
	}

	public SecurityManagedBean getSecurity() {
		return security;
	}

	public void setSecurity(SecurityManagedBean security) {
		this.security = security;
	}

	public int getMediaVoto() {
		return mediaVoto;
	}

	public void setMediaVoto(int mediaVoto) {
		this.mediaVoto = mediaVoto;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public IArtigianoService getArtServ() {
		return artServ;
	}

	public void setArtServ(IArtigianoService artServ) {
		this.artServ = artServ;
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
		this.file = event.getFile();

		if (!file.getFileName().equalsIgnoreCase(""))
		{
			try {
				InputStream is = file.getInputstream();
				long length = file.getSize();

				byte[] bytes = new byte[(int) length];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
					offset += numRead;
				}
				if (offset < bytes.length) {
					throw new IOException("Could not completely read file ");
				}
				is.close();
				artigiano.setIcon(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		} 

	public void deleteIcon(){
		this.file=null;
		this.artigiano.setIcon(null);
		
	}
	
	public String aggiornaPassword(){
		try {
			this.artigiano.setPassword(regService.encodePassword(this.artigiano.getPassword()));
			this.artigiano = artServ.aggiornaDatiPersonali(artigiano);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return editLoggedProfile();
	}
}
