package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.IPaymentService;
import it.pbc.chiloripara.services.interfaces.IPropertiesService;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.Registro;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

@Component("abbonamentoMB")
@Scope("session")
public class AbbonamentoManagedBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Artigiano artigiano;
	private BigDecimal prezzo = new BigDecimal(0);
	private BigDecimal iva = new BigDecimal(0);
	private BigDecimal prezzoSemestrale = new BigDecimal(0);
	private BigDecimal ivaSemestrale = new BigDecimal(0);
	private boolean displayPayPal = false;
	private String authCode;
	private String authCodeSem;
	private List<Registro> registro;
	private String payPalAction;
	private String confirmURL;
	private String cancelURL;
	private String IPNURL;
	private String paypalEmail;

	private BigDecimal prezzoPrimaCategoria;
	private BigDecimal prezzoSuccessiveCategorie;
	@Autowired
	private IRegistrazioneService regService;

	@Autowired
	private IPaymentService paypalService;
	@Autowired
	private IArtigianoService artService;

	private DualListModel<Categoria> categorie;
	private List<Categoria> listcat;
	private List<Categoria> categorieSelezionate;
	private int catSelected = 0;
	private String listCategorieString;

	public String getAuthCodeSem() {
		return authCodeSem;
	}

	public void setAuthCodeSem(String authCodeSem) {
		this.authCodeSem = authCodeSem;
	}

	public BigDecimal getPrezzoSemestrale() {
		return prezzoSemestrale;
	}

	public void setPrezzoSemestrale(BigDecimal prezzoSemestrale) {
		this.prezzoSemestrale = prezzoSemestrale;
	}

	public BigDecimal getIvaSemestrale() {
		return ivaSemestrale;
	}

	public void setIvaSemestrale(BigDecimal ivaSemestrale) {
		this.ivaSemestrale = ivaSemestrale;
	}

	public String getPaypalEmail() {
		return paypalEmail;
	}

	public void setPaypalEmail(String paypalEmail) {
		this.paypalEmail = paypalEmail;
	}

	public String getConfirmURL() {
		return confirmURL;
	}

	public void setConfirmURL(String confirmURL) {
		this.confirmURL = confirmURL;
	}

	public String getCancelURL() {
		return cancelURL;
	}

	public void setCancelURL(String cancelURL) {
		this.cancelURL = cancelURL;
	}

	public String getIPNURL() {
		return IPNURL;
	}

	public void setIPNURL(String iPNURL) {
		IPNURL = iPNURL;
	}

	public String getPayPalAction() {
		return payPalAction;
	}

	public void setPayPalAction(String payPalAction) {
		this.payPalAction = payPalAction;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public IPaymentService getPaypalService() {
		return paypalService;
	}

	public void setPaypalService(IPaymentService paypalService) {
		this.paypalService = paypalService;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Artigiano getArtigiano() {
		return artigiano;
	}

	public void setArtigiano(Artigiano artigiano) {
		this.artigiano = artigiano;
	}

	public boolean isDisplayPayPal() {
		return displayPayPal;
	}

	public void setDisplayPayPal(boolean displayPayPal) {
		this.displayPayPal = displayPayPal;
	}

	public int getCatSelected() {
		return catSelected;
	}

	public void setCatSelected(int catSelected) {
		this.catSelected = catSelected;
	}

	public Logger getLogger() {
		return logger;
	}

	@Autowired
	private IPropertiesService props;

	public void init() {
		this.categorieSelezionate = new ArrayList<Categoria>();
		this.listcat = regService.listCategorieDisponibili(this.artigiano);
		this.categorie = new DualListModel<Categoria>(listcat, categorieSelezionate);
		this.prezzo = new BigDecimal(0);
		this.prezzoSemestrale = new BigDecimal(0);
		this.catSelected = 0;
		this.displayPayPal = false;
		this.payPalAction = props.getValore("paypal_action");
		this.paypalEmail = props.getValore("paypal_email");
		this.cancelURL = props.getValore("paypal_cancelURL");
		this.confirmURL = props.getValore("paypal_confirmURL");
		this.IPNURL = props.getValore("paypal_IPNURL");
		this.prezzoPrimaCategoria = new BigDecimal(props.getValore("prezzo_prima"));
		this.prezzoSuccessiveCategorie = new BigDecimal(props.getValore("prezzo_altre"));
	}

	public String load() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.artigiano = (Artigiano) authentication.getPrincipal();
		this.artigiano = artService.get(artigiano.getId());
		init();
		this.registro = regService.getRegistroAttivo(this.artigiano);
		return "abbonamento";
	}

	public void calcolaPrezzo(TransferEvent event) {
		logger.info("catSelected " + catSelected);
		this.prezzo.setScale(2, BigDecimal.ROUND_UP);
		this.prezzoSemestrale.setScale(2, BigDecimal.ROUND_UP);
		if (catSelected == 0) {
			if (event.getItems() == null || event.getItems().size() == 0) {
				this.prezzo = new BigDecimal(0);
			} else {
				this.prezzo = this.prezzoPrimaCategoria;
				for (int i = 1; i < event.getItems().size(); i++)
					this.prezzo = this.prezzo.add(prezzoSuccessiveCategorie);
			}
			catSelected = catSelected + event.getItems().size();
		} else {
			if (event.isAdd()) {
				this.prezzo = prezzo.add((prezzoSuccessiveCategorie).multiply(new BigDecimal(event.getItems().size())));
				catSelected = catSelected + event.getItems().size();
			} else {
				catSelected = catSelected - event.getItems().size();
				if (catSelected == 0)
					this.prezzo = new BigDecimal(0);
				else if (catSelected == 1)
					this.prezzo = prezzoPrimaCategoria;
				else
					this.prezzo = this.prezzo.subtract(this.prezzoSuccessiveCategorie.multiply(new BigDecimal(event.getItems().size())));
			}

		}
		this.prezzoSemestrale = this.prezzo.multiply(new BigDecimal("1.2")).multiply(new BigDecimal("0.5"));
		logger.info("prezzo calcolato " + this.prezzo);
	}

	public synchronized void conferma(ActionEvent actionEvent) {
		String cancelURL = "http://www.chiloripara.it/cancel/";
		String returnURL = "http://www.chiloripara.it/OrderConfirm/";

		Date date = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.YEAR, 1);
		Date expDt = new Date(c.getTimeInMillis());
		this.authCode = regService.generateAuthCode(this.artigiano, date,"S");
		//c.add(Calendar.MONTH, 1);
		Date expDtSem = new Date(c.getTimeInMillis());
		date = new Date(System.currentTimeMillis());
		this.authCodeSem = regService.generateAuthCode(this.artigiano, date,"R");

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.categorie.getTarget().size(); i++)
			sb.append(this.categorie.getTarget().get(i).getDescrizione() + ", ");
		sb.delete(sb.length() - 2, sb.length() - 1);
		this.listCategorieString = sb.toString();
		String itemName = ResourceBundle.getBundle("Messages_it").getString("abbonamento_vocePaypal") + ", " + ResourceBundle.getBundle("Messages_it").getString("abbonamento_vocePaypal_Categorie");
		itemName = itemName + " : ";
		itemName = itemName + listCategorieString;
		Map<String, String> item = regService.generatePayPalItems(this.categorie.getTarget(), prezzoPrimaCategoria, prezzoSuccessiveCategorie);

		item.put("name", itemName);
		item.put("amt", this.prezzo.toString());
		item.put("qty", "1");

		this.iva = this.prezzo.multiply(new BigDecimal("0.22")).setScale(2, BigDecimal.ROUND_UP);
		this.ivaSemestrale = this.prezzoSemestrale.multiply(new BigDecimal("0.22")).setScale(2, BigDecimal.ROUND_UP);

		/*
		 * //HashMap npv =
		 * paypalService.setExpressCheckout(this.prezzo.toString(), returnURL,
		 * cancelURL, item); HashMap npv =
		 * paypalService.checkoutCetegorie(this.artigiano,
		 * this.categorie.getTarget(), returnURL, cancelURL);
		 * this.authCode=(String) npv.get("TOKEN");
		 */
		if (this.categorie.getTarget().size() > 0) {
			this.displayPayPal = true;
			regService.linkArtToCatsAuthCode(this.artigiano.getId(), this.categorie.getTarget(), expDt, authCode);
			try {
				wait(1000);
			} catch (InterruptedException e) {

			}
			regService.linkArtToCatsAuthCode(this.artigiano.getId(), this.categorie.getTarget(), expDtSem, authCodeSem);
		}

	}

	public String getListCategorieString() {
		return listCategorieString;
	}

	public void setListCategorieString(String listCategorieString) {
		this.listCategorieString = listCategorieString;
	}

	public void modifica(ActionEvent actionEvent) {
		this.displayPayPal = false;
	}

	public IRegistrazioneService getRegService() {
		return regService;
	}

	public void setRegService(IRegistrazioneService regService) {
		this.regService = regService;
	}

	public DualListModel<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(DualListModel<Categoria> categorie) {
		this.categorie = categorie;
	}

	public List<Categoria> getListcat() {
		return listcat;
	}

	public void setListcat(List<Categoria> listcat) {
		this.listcat = listcat;
	}

	public List<Categoria> getCategorieSelezionate() {
		return categorieSelezionate;
	}

	public void setCategorieSelezionate(List<Categoria> categorieSelezionate) {
		this.categorieSelezionate = categorieSelezionate;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public BigDecimal getPrezzoPrimaCategoria() {
		return prezzoPrimaCategoria;
	}

	public void setPrezzoPrimaCategoria(BigDecimal prezzoPrimaCategoria) {
		this.prezzoPrimaCategoria = prezzoPrimaCategoria;
	}

	public BigDecimal getPrezzoSuccessiveCategorie() {
		return prezzoSuccessiveCategorie;
	}

	public void setPrezzoSuccessiveCategorie(BigDecimal prezzoSuccessiveCategorie) {
		this.prezzoSuccessiveCategorie = prezzoSuccessiveCategorie;
	}

	public List<Registro> getRegistro() {
		return registro;
	}

	public void setRegistro(List<Registro> registro) {
		this.registro = registro;
	}

}
