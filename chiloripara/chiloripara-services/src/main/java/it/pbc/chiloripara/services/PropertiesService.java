package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.PropertiesDAO;
import it.pbc.chiloripara.services.interfaces.IPropertiesService;
import it.pbc.chiloripara.web.model.entities.Properties;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("prop")
@Scope("application")
public class PropertiesService implements IPropertiesService {

	private HashMap<String, String> properties = null;
	@Autowired
	PropertiesDAO propDAO;

	@PostConstruct
	private void init() {
		List<Properties> props = null;
		if (properties == null) {
			properties = new HashMap<String, String>();
			props = propDAO.getAll();
			for (int i = 0; i < props.size(); i++) {
					properties.put(props.get(i).getCodice(), props.get(i).getValore());
			}

		}

	}
	
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPropertiesService#getValore(java.lang.String)
	 */
	@Override
	public String getValore(String codice){
		return properties.get(codice);
	}
	
	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPropertiesService#getDispayDetails()
	 */
	@Override
	public String getDispayDetails(){
		return getValore("displayDetails");
		
	}
	
}
