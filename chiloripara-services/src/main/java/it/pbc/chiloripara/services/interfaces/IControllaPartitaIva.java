package it.pbc.chiloripara.services.interfaces;

import org.springframework.ws.client.core.WebServiceTemplate;

public interface IControllaPartitaIva {

	public abstract WebServiceTemplate getWebserviceTemplate();

	public abstract void setWebserviceTemplate(WebServiceTemplate webserviceTemplate);

	public abstract boolean checkPartitaIva(String partitaIva);

}