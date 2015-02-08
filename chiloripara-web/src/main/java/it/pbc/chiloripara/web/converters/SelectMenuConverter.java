package it.pbc.chiloripara.web.converters;

import it.pbc.chiloripara.services.interfaces.IUtilityService;
import it.pbc.chiloripara.web.model.entities.Comune;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;

public class SelectMenuConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.isEmpty()) {
			return null;
		}
		try {
			return findComune(arg2); // here's where should be retreived the
										// desired selected instance
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(("This is not a valid comune id")), e);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	private IUtilityService utility;

	public Comune findComune(String id) {
		return utility.getComune(new Long(id));
	}
}
