package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Cap;
import it.pbc.chiloripara.web.model.entities.Comune;

import java.util.List;

public interface IUtilityService {

	public abstract List<Cap> getAll();

	public abstract List<Comune> getComuni(Integer cap);

	public abstract Comune getComune(Long id);

}