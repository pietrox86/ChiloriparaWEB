package it.pbc.chiloripara.web.model.entities;

import static javax.persistence.FetchType.EAGER;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Artigiano implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String ragioneSociale;
	@Column
	private String indirizzo;

	@Column
	private String telefono;
	@Column
	private String email;
	@Column(length = 3000)
	private String descrizione;
	@Column
	private Date dataIscrizione;
	@Column
	private float lat;
	@Column
	private float lng;
	@Column(nullable = false)
	private String piva;
	@Column
	private String username;
	@Column
	private String password;

	@OneToOne(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Role role = new Role();

	@ManyToOne(fetch = EAGER,cascade={CascadeType.MERGE})
	private Comune comune = new Comune();

	@OneToMany(cascade={CascadeType.ALL}, fetch = EAGER, mappedBy = "artigiano", orphanRemoval = true)
	private List<Post> bacheca = new ArrayList<Post>();

	@OneToMany(cascade = {CascadeType.ALL }, fetch = EAGER, mappedBy = "artigiano", orphanRemoval = true)
	private List<Voto> voti = new ArrayList<Voto>();

	@OneToMany(mappedBy = "artigiano", cascade = {CascadeType.ALL }, fetch = EAGER, orphanRemoval = true)
	protected List<Registro> artCat;

	@Lob
	@Column
	private byte[] icon;

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public List<Voto> getVoti() {
		if (voti != null) {
			HashSet<Voto> v = new HashSet<Voto>();
			v.addAll(voti);
			voti.clear();
			voti.addAll(v);
		}
		return voti;
	}

	public void setVoti(List<Voto> voti) {
		this.voti = voti;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataIscrizione() {
		return dataIscrizione;
	}

	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public List<Post> getBacheca() {
		if (bacheca != null) {
			HashSet<Post> v = new HashSet<Post>();
			v.addAll(bacheca);
			bacheca.clear();
			bacheca.addAll(v);
		}
		return bacheca;
	}

	public void setBacheca(List<Post> bacheca) {
		this.bacheca = bacheca;
	}

	public List<Registro> getArtCat() {
		if (artCat != null) {
			HashSet<Registro> v = new HashSet<Registro>();
			v.addAll(artCat);
			artCat.clear();
			artCat.addAll(v);
		}
		return artCat;
	}

	public void setArtCat(List<Registro> artCat) {
		this.artCat = artCat;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (role == null)
			return Collections.emptyList();

		return Arrays.<GrantedAuthority> asList(getRole());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		
		this.role = role;

	}

	

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Comune getComune() {
		return comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	@Override
	public int hashCode() {
		int hashcode =0;
		hashcode += Integer.parseInt((username.hashCode() + "19" + id.hashCode()).hashCode()+"");
		return hashcode;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Artigiano))
			return false;
		Artigiano a = (Artigiano) o;
		if (id.compareTo(a.getId()) != 0)
			return false;
		if (!username.equalsIgnoreCase(a.getUsername()))
			return false;
		return true;
	}

}
