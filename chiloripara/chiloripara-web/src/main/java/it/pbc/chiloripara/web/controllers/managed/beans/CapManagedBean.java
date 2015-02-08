package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IUtilityService;
import it.pbc.chiloripara.web.model.entities.Cap;
import it.pbc.chiloripara.web.model.entities.Comune;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("capMB")
@Scope("view")
public class CapManagedBean {
	@Autowired
	private IUtilityService service ;

	private Map<Integer, Integer> caps = new HashMap<Integer, Integer>();
	private Map<String,String> comuni = new HashMap<String,String>();
	
	private List<Comune> comuniCombo;
	
	private String cap;

	
	public Map<Integer, Integer> getCaps() {
		return caps;
	}

	public void setCaps(Map<Integer, Integer> caps) {
		this.caps = caps;
	}

	

	public IUtilityService getService() {
		return service;
	}

	public void setService(IUtilityService service) {
		this.service = service;
	}

	public Map<String, String> getComuni() {
		return comuni;
	}

	public void setComuni(Map<String, String> comuni) {
		this.comuni = comuni;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	private void init() {
		List<Cap> listCap= service.getAll();
		
		for(int i=0; i<listCap.size(); i++)
			caps.put(listCap.get(i).getCap(), listCap.get(i).getCap());
		
	}
	
	public void loadComuni(){
		Integer cap= Integer.decode(getCap());
		System.out.println("cerco i comuni "+service);
		this.comuni.clear();
		comuniCombo = service.getComuni(cap);
		
		for(int i=0; i<comuniCombo.size(); i++)
			this.comuni.put(comuniCombo.get(i).getName(),comuniCombo.get(i).getId()+"");
		System.out.println("HO caricato l'array "+comuni.size());
		
	}
	

	public List<Comune> getComuniCombo() {
		return comuniCombo;
	}

	public void setComuniCombo(List<Comune> comuniCombo) {
		this.comuniCombo = comuniCombo;
	}
	

}
