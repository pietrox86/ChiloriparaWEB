package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.interfaces.IControllaPartitaIva;
import it.pbc.chiloripara.services.subsystem.CheckVat;
import it.pbc.chiloripara.services.subsystem.CheckVatResponse;
import it.pbc.chiloripara.services.subsystem.ObjectFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class ControllaPartitaIva implements IControllaPartitaIva {
	
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IControllaPartitaIva#getWebserviceTemplate()
	 */
	@Override
	public WebServiceTemplate getWebserviceTemplate() {
		return webserviceTemplate;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IControllaPartitaIva#setWebserviceTemplate(org.springframework.ws.client.core.WebServiceTemplate)
	 */
	@Override
	public void setWebserviceTemplate(WebServiceTemplate webserviceTemplate) {
		this.webserviceTemplate = webserviceTemplate;
	}

	@Autowired
	private WebServiceTemplate webserviceTemplate;
	
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IControllaPartitaIva#checkPartitaIva(java.lang.String)
	 */
	@Override
	public boolean checkPartitaIva(String partitaIva){
		CheckVat checkVat = new ObjectFactory().createCheckVat();
		checkVat.setCountryCode("IT");
		checkVat.setVatNumber(partitaIva);
		
		webserviceTemplate.setDefaultUri("http://ec.europa.eu/taxation_customs/vies/services/checkVatService");
		CheckVatResponse response = (CheckVatResponse) webserviceTemplate.marshalSendAndReceive(checkVat);
		return response.isValid();
	}

}
