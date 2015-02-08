package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;
import it.pbc.chiloripara.web.model.entities.Registro;
import it.pbc.chiloripara.web.model.entities.Role;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

public interface IRegistrazioneService {

	public abstract Role getArtUserRole();

	public abstract String encodePassword(String plainPassword);

	public abstract List<Categoria> listCategorieAttive();

	public abstract List<Categoria> listCategorieDisponibili(Artigiano art);

	public abstract void linkArtToCats(Artigiano art, List<Categoria> cats, Date expDt);

	public abstract void linkArtToCatsAuthCode(Long idArt, List<Categoria> cats, Date expDt, String authCode);

	public abstract List<Registro> getRegistroAttivo(Artigiano art);

	public abstract List<Registro> getRegistroArtigiano(Long artId);

	public abstract String generateAuthCode(Artigiano art, Date date, String modalita);

	public abstract boolean controllaPartitaIva(String piva);

	public abstract Map<String, String> generatePayPalItems(List<Categoria> target, BigDecimal prezzoPrimaCategoria, BigDecimal prezzoSuccessiveCategorie);

	public abstract void activateRegistro(String authCode);

	public abstract boolean usernameExist(String username);

	public abstract void saveArtigiano(Artigiano art);

	public abstract void add(Registro artCat);

}