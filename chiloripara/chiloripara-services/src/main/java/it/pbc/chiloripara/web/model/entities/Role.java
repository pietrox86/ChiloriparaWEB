package it.pbc.chiloripara.web.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2063038771860377215L;

	@Column
	@Id
	private Long id;

	@Column
	private String roleName;

	@OneToMany(cascade={CascadeType.MERGE})
	@JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private List<Artigiano> userList;

	public List<Artigiano> getUserList() {
		return userList;
	}

	public void setUserList(List<Artigiano> userList) {
		this.userList = userList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return roleName;
	}

	

	public void removeUser(Long id) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId().compareTo(id) == 0) {
				this.userList.remove(i);
				break;
			}
		}
	}

}
