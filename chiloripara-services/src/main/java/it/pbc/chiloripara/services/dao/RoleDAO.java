package it.pbc.chiloripara.services.dao;

import it.pbc.chiloripara.web.model.entities.Role;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO extends GeneralDAO {
	
	public Role find(Long id){
    	Role role= entityManager.find(Role.class, id);
	return role;
    }
    public Role getRoleByName(String userRole){
	Query query = entityManager.createQuery("SELECT r FROM Role r where r.roleName = :usrRole");
	query.setParameter("usrRole", userRole);
	return (Role) query.getResultList().get(0);
    }
}
