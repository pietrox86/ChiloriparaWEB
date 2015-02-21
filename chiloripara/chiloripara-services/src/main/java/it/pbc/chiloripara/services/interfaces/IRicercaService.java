package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.notPersisted.ArtigianoDistanza;

import java.util.List;

public interface IRicercaService {

	public abstract List<Artigiano> getArtigianiByCategoria(Long catId);

	public abstract List<ArtigianoDistanza> getArtigianiByCatAndCoord(Long catId, float lat, float lng);
	
	public abstract List<ArtigianoDistanza> getArtigianiByCatAndAddress(Long catId,String address) throws Exception;

	public abstract List<ArtigianoDistanza> getArtigianiByCatAndCoordSubCat(
			Long subCatId, Float lat, Float lng);

}