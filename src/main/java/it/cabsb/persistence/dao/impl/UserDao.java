package it.cabsb.persistence.dao.impl;

import java.util.Calendar;
import java.util.List;

import it.cabsb.model.User;
import it.cabsb.persistence.AbstractHibernateDao;
import it.cabsb.persistence.dao.IUserDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractHibernateDao<User> implements IUserDao {
	
	public User findByUsername(String username) {
		return findOneByCriteria(Restrictions.eq("username", username));
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUnconfirmedUsers() {
		Calendar sevenDaysAgo = Calendar.getInstance();
		sevenDaysAgo.add(Calendar.DAY_OF_MONTH, -7);
		
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("enabled", false));
		criteria.add(Restrictions.lt("creationDate", sevenDaysAgo.getTime()));
		return criteria.list();
	}
}
