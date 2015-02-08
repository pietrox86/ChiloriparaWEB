package it.pbc.chiloripara.services.security;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.web.model.entities.Artigiano;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements UserDetailsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Autowired
	// private ArtigianoDAO artDAO;
	@Autowired
	private IArtigianoService artService;

	public Artigiano findByLogin(String username) {
		// return artDAO.getArtigianoByUsername(username);
		Artigiano dto = artService.getArtigianoByUsername(username);

		return dto;
	}

	public Artigiano getUserFromSession() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof Artigiano) {
			Artigiano userDetails = (Artigiano) principal;
			return userDetails;
		}
		return null;
	}

	@Override
	public Artigiano loadUserByUsername(String username) throws UsernameNotFoundException {
		// return artDAO.getArtigianoByUsername(username);

		Artigiano dto = artService.getArtigianoByUsername(username);

		return dto;

	}

}
