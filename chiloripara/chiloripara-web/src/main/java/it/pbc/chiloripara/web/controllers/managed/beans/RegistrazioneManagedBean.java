package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IControllaPartitaIva;
import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;
import it.pbc.chiloripara.services.interfaces.IUtilityService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Comune;
import it.pbc.chiloripara.web.model.entities.Post;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("registrazioneMB")
@Scope("session")
public class RegistrazioneManagedBean extends GeneralBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(RegistrazioneManagedBean.class);

	
	@Autowired
	private IGoogleService googleService;
	@Autowired
	private IUtilityService utilityService;
	

	@Autowired
	private IRegistrazioneService regService;

	private UploadedFile file;
	private Artigiano art;
	private String idComune;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public IGoogleService getGoogleService() {
		return googleService;
	}

	public void setGoogleService(IGoogleService googleService) {
		this.googleService = googleService;
	}

	public IUtilityService getUtilityService() {
		return utilityService;
	}

	public void setUtilityService(IUtilityService utilityService) {
		this.utilityService = utilityService;
	}

	public IRegistrazioneService getRegService() {
		return regService;
	}

	public void setRegService(IRegistrazioneService regService) {
		this.regService = regService;
	}

	public String getIdComune() {
		return idComune;
	}

	public void setIdComune(String idComune) {
		this.idComune = idComune;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Logger getLogger() {
		return logger;
	}

	@PostConstruct
	private void init() {
		this.art = new Artigiano();
		this.art.setComune(new Comune());
	}

	private void invalidate() {
		this.art = new Artigiano();

	}

	public void aggiornaComune() {
		Comune comune = utilityService.getComune(new Long(getIdComune()));
		art.setComune(comune);
	}

	@Autowired
	private IControllaPartitaIva pIvaService;

	public IControllaPartitaIva getpIvaService() {
		return pIvaService;
	}

	public void setpIvaService(IControllaPartitaIva pIvaService) {
		this.pIvaService = pIvaService;
	}

	public String save() {
		art.setDataIscrizione(new Date(System.currentTimeMillis()));
		Comune comune = utilityService.getComune(new Long(getIdComune()));
		art.setComune(comune);
		try {
			HashMap<String, Float> coord = googleService.getCoordinate(art.getIndirizzo() + " " + art.getComune().getName());
			if (coord.get("STATUS").floatValue() == 1) {
				art.setLat(coord.get("LAT"));
				art.setLng(coord.get("LNG"));
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Attenzione, l'indirizzo non è stato riconosciuto");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "newArtigiano";
			}

			logger.debug("icona: "+art.getIcon());
			art.setPassword(regService.encodePassword(art.getPassword()));
			art.setBacheca(new ArrayList<Post>());
			regService.saveArtigiano(art);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Errore nell'inserimento");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "home";
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Completato", "Artigiano inserito correttamente :" + art.getRagioneSociale());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		invalidate();
		return "home";
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
				art.setIcon(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.debug("icona caricata: "+art.getIcon());
		}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	

	public Artigiano getArt() {
		return art;
	}

	public void setArt(Artigiano art) {
		this.art = art;
	}

	public void isUsernameValid(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		String newUsername = value.toString();
		if (regService.usernameExist(newUsername)) {
			faceMsg.setErrorMessage(labels.getLabel("general_error"), labels.getLabel("registrazione_username_invalid"));
			throw new ValidatorException(faceMsg.getMsg());
		}
		if (newUsername.contains(" ")) {
			faceMsg.setErrorMessage(labels.getLabel("general_error"), labels.getLabel("registrazione_username_incorrect"));
			throw new ValidatorException(faceMsg.getMsg());
		}
	}
}
