package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.security.UserRepository;
import it.pbc.chiloripara.web.model.entities.Artigiano;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import antlr.Utils;

@Component("securityMB")
@Scope("session")
public class SecurityManagedBean implements Serializable {

	@Autowired
	private UserRepository userRep;

	private boolean isUserAdmin = false;
	private boolean isUserArt = false;

	private boolean userLogged = false;

	private Artigiano art;

	public Artigiano getArt() {
		return art;
	}

	public void setArt(Artigiano art) {
		this.art = art;
	}

	public boolean isUserLogged() {
		return userRep.getUserFromSession() != null;
	}

	public void setUserLogged(boolean userLogged) {
		this.userLogged = userLogged;
	}

	public boolean isUserAdmin() {
		isUserAdmin = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			return false;
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if ("ROLE_ADMIN".equals(grantedAuthority.getAuthority())) {
				isUserAdmin = true;

				break;
			}
		}
		return isUserAdmin;
	}

	public void setUserAdmin(boolean isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	public UserRepository getUserRep() {
		return userRep;
	}

	public void setUserRep(UserRepository userRep) {
		this.userRep = userRep;
	}

	public boolean isUserArt() {
		isUserArt = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if ("ROLE_ART".equals(grantedAuthority.getAuthority())) {
				isUserArt = true;
				this.art = (Artigiano) authentication.getPrincipal();
			

				break;
			}
		}
		return isUserArt;
	}

	public void setUserArt(boolean isUserArt) {
		this.isUserArt = isUserArt;
	}
	// public Artigiano getArt() {
	// return art;
	// }
	// public void setArt(Artigiano art) {
	// this.art = art;
	// }

}
