package it.pbc.chiloripara.web.model.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User_ implements UserDetails{

    @Id
    @Column(name="id")
    private String username;
    @Column
    private String password;
   
    @OneToOne(cascade=CascadeType.ALL)  
    @JoinTable(name="user_roles",  
    joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})  
    private Role role;
    
  
    
    
    
   

  
	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	if(role==null)
	    return Collections.EMPTY_LIST;
	
	return Arrays.<GrantedAuthority>asList(getRole());
    }
    @Override
    public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
    }
    @Override
    public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
    }
    @Override
    public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
    }
    

}
