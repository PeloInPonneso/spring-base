package it.cabsb.persistence.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.cabsb.model.Role;
import it.cabsb.persistence.AbstractHibernateDao;
import it.cabsb.persistence.dao.IRoleDao;

@Repository
public class RoleDao extends AbstractHibernateDao<Role> implements IRoleDao {
	
	public Role findByAuthority(String authority) {
		return findByCriteria(Restrictions.eq("authority", authority)).get(0);
	}
	
}
