
package it.pbc.chiloripara.web.utls.converters;

import it.pbc.chiloripara.services.dao.CategoriaDAO;
import it.pbc.chiloripara.web.model.entities.Categoria;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class CategoriaConverter implements Converter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CategoriaDAO catDAO;
    
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		logger.info(catDAO+" inizio la conversione "+value);
		Categoria cat = new Categoria();
		String[] splitted = value.split("##");
		cat.setId(new Long(splitted[0]));
		cat.setDescrizione(splitted[1]);
		cat.setAbilitato(splitted[2].equalsIgnoreCase("1"));
		logger.info("return "+cat);
		return cat;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    Categoria cat = ((Categoria) value);
    StringBuffer sb = new StringBuffer();
    sb.append(cat.getId().toString());
    sb.append("##");
    sb.append(cat.getDescrizione());
    sb.append("##");
    sb.append(cat.isAbilitato()?"1":"0");
    return sb.toString();
    }
}
