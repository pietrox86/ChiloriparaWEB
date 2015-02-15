package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.ICategoriaService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.Preferenza;
import it.pbc.chiloripara.web.model.entities.Registro;
import it.pbc.chiloripara.web.model.entities.SubCategoria;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
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

	private HashMap<String, Long> comboCat;
	private List<SubCategoria> subCats;
	private Long catId;
	private boolean renderSubCats = false;
	private DualListModel<SubCategoria> subCategorie;
	private List<SubCategoria> listSubCat;
	private List<SubCategoria> SubCatSelezionate;
	private TreeNode root;

	public String editSubcatAdmin() {
		comboCat = new HashMap<String, Long>();
		for (Categoria cat : catService.list()) {
			comboCat.put(cat.getDescrizione(), cat.getId());
		}
		return "subcat";
	}

	public String conferma() {
		artService.savePreferenza(artigiano, subCategorie.getTarget());
		renderSubCats=false;
		generateTree();
		this.loadCat();
		catId=null;
		return "subcat";
	}

	public DualListModel<SubCategoria> getSubCategorie() {
		return subCategorie;
	}

	public void setSubCategorie(DualListModel<SubCategoria> subCategorie) {
		this.subCategorie = subCategorie;
	}

	public List<SubCategoria> getListSubCat() {
		return listSubCat;
	}

	public void setListSubCat(List<SubCategoria> listSubCat) {
		this.listSubCat = listSubCat;
	}

	public List<SubCategoria> getSubCatSelezionate() {
		return SubCatSelezionate;
	}

	public void setSubCatSelezionate(List<SubCategoria> subCatSelezionate) {
		SubCatSelezionate = subCatSelezionate;
		this.artigiano = artService.get(artigiano.getId());
		generateTree();
	}

	public String editSubcat() {
		logger.debug("inizio editSubcat");
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		this.artigiano = null;
		this.artigiano = artService.get(((Artigiano) authentication
				.getPrincipal()).getId());
		generateTree();
		subCats = new ArrayList<SubCategoria>();
		listSubCat = new ArrayList<SubCategoria>();
		SubCatSelezionate = new ArrayList<SubCategoria>();
		this.subCategorie = new DualListModel<SubCategoria>(listSubCat,
				SubCatSelezionate);
		renderSubCats = false;
		catId = null;
		this.loadCat();
		return "subcat";
	}

	private void loadCat() {
		for (Registro reg : artigiano.getArtCat()) {
			comboCat.put(reg.getCategoria().getDescrizione(), reg
					.getCategoria().getId());
		}
	}

	public void loadSubCat() {
		logger.debug("inizio il load");

		this.subCats.clear();
		Categoria c = catService.getNoLazy(getCatId());
		logger.debug("inizio editSubcat " + getCatId());
		renderSubCats = false;
		if (c != null && c.getSubCat() != null && c.getSubCat().size() > 0) {
			renderSubCats = true;
			logger.debug("prima ciclo ");
			for (int i = 0; i < c.getSubCat().size(); i++) {
				this.listSubCat.add(c.getSubCat().get(i));
				logger.debug(c.getSubCat().get(i).getName());
			}
		}
		logger.debug("renderizzo " + renderSubCats);
	}

	public List<SubCategoria> getSubCats() {
		return subCats;
	}

	public void setSubCats(List<SubCategoria> subCats) {
		this.subCats = subCats;
	}

	public boolean isRenderSubCats() {
		return renderSubCats;
	}

	public void setRenderSubCats(boolean renderSubCats) {
		this.renderSubCats = renderSubCats;
	}

	private void generateTree() {
		comboCat = new HashMap<String, Long>();
		root = new DefaultTreeNode("Root", null);
		HashMap<String, List<SubCategoria>> albero = new HashMap<String, List<SubCategoria>>();
		logger.debug("numero preferenze: "+artigiano.getPreferenze().size());
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

	public HashMap<String, Long> getComboCat() {
		return comboCat;
	}

	public void setComboCat(HashMap<String, Long> comboCat) {
		this.comboCat = comboCat;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

}
