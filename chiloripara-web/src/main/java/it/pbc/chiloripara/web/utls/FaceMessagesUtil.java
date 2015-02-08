package it.pbc.chiloripara.web.utls;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class FaceMessagesUtil {

	private FacesMessage msg;
	
	public FacesMessage getMsg() {
		return msg;
	}
	public void setMsg(FacesMessage msg) {
		this.msg = msg;
	}
	public void addErrorMessage(String msg1, String msg2) {
		 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void addWarningMessage(String msg1, String msg2) {
		 msg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, msg2);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void addInfoMessage(String msg1, String msg2) {
		 msg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, msg2);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void addFatalMessage(String msg1, String msg2) {
		msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg1, msg2);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void setErrorMessage(String msg1, String msg2) {
		 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
		
	}
	public void setWarningMessage(String msg1, String msg2) {
		 msg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, msg2);
		
	}
	public void setInfoMessage(String msg1, String msg2) {
		 msg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, msg2);
		
	}
	public void setFatalMessage(String msg1, String msg2) {
		msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg1, msg2);
		
	}
	
}
