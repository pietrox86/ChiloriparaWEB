package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.SubCategoria;

import java.util.List;

public interface ICategoriaService {

	public abstract void save(Categoria cat);

	public abstract List<Categoria> list();

	public abstract Categoria get(Long catIdAction);

	public abstract void delete(Long catIdAction);

	public abstract void enableCat(Long catIdAction);

	public abstract void disableCat(Long catIdAction);

	public abstract Categoria getNoLazy(Long id);
	
	public abstract SubCategoria getSubCat(Long id);
	
	

}