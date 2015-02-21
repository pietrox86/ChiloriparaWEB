package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.dao.ArtigianoDAO;
import it.pbc.chiloripara.services.dao.PreferenzaDAO;
import it.pbc.chiloripara.services.dao.RegistroDAO;
import it.pbc.chiloripara.services.interfaces.IDistanceCalculator;
import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IRicercaService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Registro;
import it.pbc.chiloripara.web.model.notPersisted.ArtigianoDistanza;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RicercaService implements IRicercaService {
	private final Logger logger = LoggerFactory.getLogger(RicercaService.class);

	@Autowired
	private RegistroDAO regDAO;

	@Autowired
	private ArtigianoDAO artDao;

	@Autowired
	private IDistanceCalculator distCalc;

	@Autowired
	private IGoogleService googleService;
	@Autowired
	private PreferenzaDAO prefDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.pbc.chiloripara.services.IRicercaService#getArtigianiByCategoria(java
	 * .lang.Long)
	 */
	@Override
	public List<Artigiano> getArtigianiByCategoria(Long catId) {

		List<Artigiano> result = new ArrayList<Artigiano>();

		List<Registro> registro = regDAO.getArtigianiByCategoria(catId);

		List<Long> artIdList = new ArrayList<Long>();

		if (registro != null) {

			for (int i = 0; i < registro.size(); i++) {
				if (registro.get(i).isEnabled()) {

					result.add(registro.get(i).getArtigiano());
				}
			}

		}

		return result;

	}

	public List<Artigiano> getArtigianiBySubCat(Long subCatId) {

		List risultati = prefDAO.getArtigianiBySubCategoria(subCatId);
		logger.debug("risultati ottenuti:" + risultati.size());
		return risultati;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.pbc.chiloripara.services.IRicercaService#getArtigianiByCatAndCoord
	 * (java.lang.Long, float, float)
	 */
	@Override
	public List<ArtigianoDistanza> getArtigianiByCatAndCoord(Long catId,
			float lat, float lng) {
		List<Artigiano> artigiani = this.getArtigianiByCategoria(catId);
		List<ArtigianoDistanza> result = new ArrayList<ArtigianoDistanza>();
		if (artigiani != null && artigiani.size() > 0) {

			for (int i = 0; i < artigiani.size(); i++) {
				ArtigianoDistanza artDist = new ArtigianoDistanza();
				artDist.setArt(artigiani.get(i));
				artDist.setDistanza(distCalc.calculateDistance(lat, lng,
						artDist.getArt().getLat(), artDist.getArt().getLng()));
				result.add(artDist);
			}
			Collections.sort(result);
		}
		return result;
	}

	@Override
	public List<ArtigianoDistanza> getArtigianiByCatAndAddress(Long catId,
			String address) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Float> coordinate;
		List<ArtigianoDistanza> result;
		try {
			coordinate = googleService.getCoordinate(address);
			if (coordinate.get("LAT") == null
					|| coordinate.get("LAT").equals(""))
				throw new Exception("Errore recupero coordinate");
			result = this.getArtigianiByCatAndCoord(catId,
					coordinate.get("LAT"), coordinate.get("LNG"));
			logger.debug("risultati: " + result.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore");
		}
		return result;
	}

	@Override
	public List<ArtigianoDistanza> getArtigianiByCatAndCoordSubCat(Long subCatId,
			Float lat, Float lng) {
		List<Artigiano> artigiani = this.getArtigianiBySubCat(subCatId);
		List<ArtigianoDistanza> result = new ArrayList<ArtigianoDistanza>();
		if (artigiani != null && artigiani.size() > 0) {

			for (int i = 0; i < artigiani.size(); i++) {
				ArtigianoDistanza artDist = new ArtigianoDistanza();
				artDist.setArt(artigiani.get(i));
				artDist.setDistanza(distCalc.calculateDistance(lat, lng,
						artDist.getArt().getLat(), artDist.getArt().getLng()));
				result.add(artDist);
			}
			Collections.sort(result);
		}
		return result;
	}

}
