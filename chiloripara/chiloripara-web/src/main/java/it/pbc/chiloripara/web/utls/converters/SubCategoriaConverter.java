package it.pbc.chiloripara.web.utls.converters;

import it.pbc.chiloripara.services.dao.CategoriaDAO;
import it.pbc.chiloripara.services.dao.SubCategoriaDAO;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.SubCategoria;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SubCategoriaConverter implements Converter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired


	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		logger.debug("converto "+value);
		SubCategoria subCat = new SubCategoria();
		String[] splitted = value.split("##");
		try {
			subCat.setId(new Long(splitted[0]));
			
			subCat.setName(splitted[1]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("ritorno "+subCat);
		return subCat;
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		SubCategoria subCat =  ((SubCategoria) value);
		StringBuffer sb = new StringBuffer();
		sb.append(subCat.getId().toString());
		
		sb.append("##");
		sb.append(subCat.getName());
		return sb.toString();
	}
}
