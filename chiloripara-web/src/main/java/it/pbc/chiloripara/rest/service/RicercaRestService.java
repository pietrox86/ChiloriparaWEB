package it.pbc.chiloripara.rest.service;

import it.pbc.chiloripara.rest.model.ArtigianoHeaderRest;
import it.pbc.chiloripara.rest.model.ArtigianoRest;
import it.pbc.chiloripara.rest.model.ArtigianoRestResponse;
import it.pbc.chiloripara.rest.model.PostRest;
import it.pbc.chiloripara.rest.model.RicercaRestResponse;
import it.pbc.chiloripara.rest.model.VotoRest;
import it.pbc.chiloripara.rest.model.VotoRestResponse;
import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.IRicercaService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Post;
import it.pbc.chiloripara.web.model.entities.Voto;
import it.pbc.chiloripara.web.model.notPersisted.ArtigianoDistanza;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/funzioni")
public class RicercaRestService {

	@Autowired
	private IRicercaService ricercaService;

	@Autowired
	private IArtigianoService artService;

	@GET
	@Path("/getArtById/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArtigianoRestResponse categoryById(@PathParam("id") Long id) {
		ArtigianoRestResponse response = new ArtigianoRestResponse();
		try {
			Artigiano art = artService.get(id);
			if (art == null) {
				response.setReturnCode("OK");
				response.setReturnMessage("Artigiano non trovato");
				response.setContentSize(0);
			} else {
				ArtigianoRest result = new ArtigianoRest();
				String[] ignored = new String[] { "comune", "bacheca", "role", "artCat", "voti", "password", "username" };
				BeanUtils.copyProperties(art, result, ignored);
				result.setComune(art.getComune().getName());
				result.setCap(art.getComune().getCap().getCap().toString());
				ArrayList<VotoRest> voti = new ArrayList<VotoRest>();
				if (art.getVoti() != null)
					for (Voto v : art.getVoti()) {
						VotoRest voto = new VotoRest(v.getId(), v.getVoto(), v.getData());
						voti.add(voto);
					}
				ArrayList<PostRest> bacheca = new ArrayList<PostRest>();
				if (art.getBacheca() != null)
					for (Post p : art.getBacheca()) {
						PostRest post = new PostRest(p.getId(), p.getDtInsert(), p.getTesto(), p.getImg(), p.isHasImage());
						bacheca.add(post);
					}

				result.setVoti(voti);

				ArrayList<ArtigianoRest> res = new ArrayList<ArtigianoRest>();
				res.add(result);
				response.setArtigiani(res);
				response.setReturnCode("OK");
				response.setContentSize(res.size());
			}
		} catch (Exception e) {
			response.setReturnCode("KO");
			response.setReturnMessage(e.getMessage());
			response.setContentSize(0);
		}
		return response;
	}

	@GET
	@Path("/ricerca/{catId}/{indirizzo}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public RicercaRestResponse categoryById(@PathParam("catId") Long catId, @PathParam("indirizzo") String address) {
		List<ArtigianoDistanza> serviceResult = null;
		RicercaRestResponse response = new RicercaRestResponse();
		try {
			serviceResult = ricercaService.getArtigianiByCatAndAddress(catId, address);

			if (serviceResult == null || serviceResult.size() == 0) {
				response.setReturnCode("OK");
				response.setReturnMessage("Nessun Artigiano Trovato");
				response.setContentSize(0);
			} else {
				List<ArtigianoHeaderRest> resultList = new ArrayList<ArtigianoHeaderRest>();
				for (ArtigianoDistanza art : serviceResult) {
					ArtigianoHeaderRest result = new ArtigianoHeaderRest();
					String[] ignored = new String[] { "comune", "bacheca", "role", "artCat", "voti", "password", "username" };
					BeanUtils.copyProperties(art.getArt(), result, ignored);
					result.setComune(art.getArt().getComune().getName());
					result.setCap(art.getArt().getComune().getCap().getCap().toString());
					result.setDistanza(art.getDistanza());
					resultList.add(result);

				}
				response.setList(resultList);
				response.setReturnCode("OK");
				response.setContentSize(resultList.size());

			}
		} catch (Exception e) {
			response.setReturnCode("KO");
			response.setReturnMessage(e.getMessage());
			response.setContentSize(0);
		}
		return response;
	}

	@GET
	@Path("/vota/{artId}/{voto}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public VotoRestResponse vota(@PathParam("artId") Long artId, @PathParam("voto") int voto) {
	
		VotoRestResponse response = new VotoRestResponse();
		try {
			artService.vota(voto, artId);
			response.setReturnCode("OK");
			response.setContentSize(0);

		} catch (Exception e) {
			response.setReturnCode("KO");
			response.setReturnMessage(e.getMessage());
			response.setContentSize(0);
		}
		return response;
	}
}
