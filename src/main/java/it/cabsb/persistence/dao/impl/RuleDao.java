package it.cabsb.persistence.dao.impl;

import it.cabsb.model.Rule;
import it.cabsb.persistence.AbstractHibernateDao;
import it.cabsb.persistence.dao.IRuleDao;

import org.springframework.stereotype.Repository;

@Repository
public class RuleDao extends AbstractHibernateDao<Rule> implements IRuleDao {}
