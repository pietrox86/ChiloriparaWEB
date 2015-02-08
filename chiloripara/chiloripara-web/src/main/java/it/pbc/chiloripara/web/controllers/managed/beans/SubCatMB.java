package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Preferenza;
import it.pbc.chiloripara.web.model.entities.Registro;
import it.pbc.chiloripara.web.model.entities.SubCategoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("subCatMB")
@Scope("session")
public class SubCatMB extends GeneralBean {

	private Artigiano artigiano;
	@Autowired
	private IArtigianoService artService;
	@Autowired
	private ICategoriaService catService;

	private TreeNode root;

	public String editSubcat() {
		logger.debug("inizio editSubcat");
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		this.artigiano = null;
		this.artigiano = artService.get(((Artigiano) authentication
				.getPrincipal()).getId());
		generateTree();

		
		return "subcat";
	}

	private void generateTree() {
		root = new DefaultTreeNode("Root", null);
		HashMap<String, List<SubCategoria>> albero = new HashMap<String, List<SubCategoria>>();
		for (Preferenza pref : artigiano.getPreferenze()) {
			if (albero.get(pref.getSubCategoria().getCategoria()
					.getDescrizione()) != null) {
				albero.get(
						pref.getSubCategoria().getCategoria().getDescrizione())
						.add(pref.getSubCategoria());
			} else {
				List<SubCategoria> elements = new ArrayList();
				elements.add(pref.getSubCategoria());
				albero.put(pref.getSubCategoria().getCategoria()
						.getDescrizione(), elements);
			}

		}
		Iterator<Entry<String, List<SubCategoria>>> iterator = albero
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<SubCategoria>> entry = iterator.next();
			TreeNode node = new DefaultTreeNode(entry.getKey(), root);
			for (SubCategoria subCat : entry.getValue()) {
				TreeNode childNode = new DefaultTreeNode(subCat.getName(), node);
			}
		}
	}

	public Artigiano getArtigiano() {
		return artigiano;
	}

	public void setArtigiano(Artigiano artigiano) {
		this.artigiano = artigiano;
	}

	public IArtigianoService getArtService() {
		return artService;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public void setArtService(IArtigianoService artService) {
		this.artService = artService;
	}

	public ICategoriaService getCatService() {
		return catService;
	}

	public void setCatService(ICategoriaService catService) {
		this.catService = catService;
	}

}
