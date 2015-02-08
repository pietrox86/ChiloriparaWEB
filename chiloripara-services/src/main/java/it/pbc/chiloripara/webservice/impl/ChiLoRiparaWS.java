package it.pbc.chiloripara.webservice.impl;

import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.webservice.interfaces.IChiLoRiparaWS;
import it.pbc.chiloripara.webservice.model.CategoriaWS;
import it.pbc.chiloripara.webservice.model.RecuperaCategorieResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ChiLoRiparaWS implements IChiLoRiparaWS{
	@Autowired
	private ICategoriaService catService;

	@Override
	public RecuperaCategorieResponse recuperaCategorie() {
		List<Categoria> cats= catService.list();
		RecuperaCategorieResponse response = new RecuperaCategorieResponse();
		CategoriaWS[] results = new CategoriaWS[cats.size()];
		int i=0;
		for(Categoria c:cats){
			CategoriaWS cat = new CategoriaWS();
			cat.setDesc(c.getDescrizione());
			cat.setId(c.getId().longValue());
			results[i]=cat;
			i++;
		}
		response.setReturnCode("0");
		response.setCategorie(results);
		return response;
	}

}
