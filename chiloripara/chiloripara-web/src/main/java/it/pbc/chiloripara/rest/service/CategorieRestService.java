package it.pbc.chiloripara.rest.service;

import it.pbc.chiloripara.rest.model.CategoriaRest;
import it.pbc.chiloripara.rest.model.CategorieRestResponse;
import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Categoria;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/categorie")
public class CategorieRestService {
	

	@Autowired
	private ICategoriaService catService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public CategorieRestResponse getListaCategorie() {
		CategorieRestResponse response = new CategorieRestResponse();
		try {
			List<Categoria> list = catService.list();
			ArrayList<CategoriaRest> categorie = new ArrayList<CategoriaRest>();
			for (Categoria c : list) {
				CategoriaRest cat = new CategoriaRest(c.getId(), c.getDescrizione());
				categorie.add(cat);
			}
			response.setCategorie(categorie);
			response.setReturnCode("OK");
			response.setContentSize(categorie.size());
		} catch (Exception e) {
			response.setReturnCode("KO");
			response.setReturnMessage(e.getMessage());
			response.setContentSize(0);
		}
		return response;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public CategorieRestResponse categoryById(@PathParam("id") Long id) {
		CategorieRestResponse response = new CategorieRestResponse();
		Categoria cat = catService.get(id);
		if (cat == null) {
			response.setReturnCode("KO");
			response.setReturnMessage("Artigiano non trovato");
			response.setContentSize(0);
		} else {

			CategoriaRest result = new CategoriaRest(cat.getId(), cat.getDescrizione());

			ArrayList<CategoriaRest> res = new ArrayList<CategoriaRest>();
			res.add(result);
			response.setCategorie(res);
			response.setReturnCode("OK");
			response.setContentSize(res.size());
		}
		return response;
	}

}
