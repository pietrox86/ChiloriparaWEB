package it.pbc.chiloripara.services.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralDAO  {
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	protected EntityManager entityManager;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
