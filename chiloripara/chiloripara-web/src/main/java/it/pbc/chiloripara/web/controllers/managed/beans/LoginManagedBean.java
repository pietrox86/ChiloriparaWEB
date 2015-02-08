package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.security.UserRepository;
import it.pbc.chiloripara.web.model.entities.Artigiano;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import antlr.Utils;

@Component("loginMB")
@Scope("request")
public class LoginManagedBean implements Serializable {

	private String username;
	private String password;
	private Artigiano loggedUser;

	@Autowired
	private UserRepository user;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public String logout() {
		logger.info("inizio il logout");
		SecurityContextHolder.clearContext();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout", "Logout effettuato correttamente");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "logout";
	}

	public String doLogin() throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		ResourceBundle labels = ResourceBundle.getBundle("Messages", httpRequest.getLocale());
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(this.getUsername(), this.getPassword());
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			loggedUser = getLoggedInUser();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, labels.getString("login_welcome"), labels.getString("login_logged") +" "+ loggedUser.getUsername());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (BadCredentialsException e1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, labels.getString("general_error"), labels.getString("login_error_credentials"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, labels.getString("general_error"), labels.getString("login_error"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "login";
		}

		return "home";
	}

	public Artigiano getLoggedInUser() {
		return user.getUserFromSession();
	}

	public Artigiano getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Artigiano loggedUser) {
		this.loggedUser = loggedUser;
	}

}
