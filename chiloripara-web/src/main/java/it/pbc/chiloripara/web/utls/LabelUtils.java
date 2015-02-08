package it.pbc.chiloripara.web.utls;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LabelUtils {
	
	private HttpServletRequest httpRequest ;
	private ResourceBundle labels ;
	
	@PostConstruct
	private void init(){
		httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		labels = ResourceBundle.getBundle("Messages", httpRequest.getLocale());
		
	}
	
	public String getLabel(String key){
		return labels.getString(key);
	}

}
