package it.cabsb.persistence.service.impl;

import it.cabsb.model.Rule;
import it.cabsb.persistence.dao.IRuleDao;
import it.cabsb.persistence.service.IRuleService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  
@Transactional(rollbackFor=Exception.class)
public class RuleService implements IRuleService {
	
	@Autowired
	private IRuleDao ruleDao;
	
	public Rule findRuleById(Long id) {
		return ruleDao.findOne(id);
	}
	
	public List<Rule> getAllRules() {
		return ruleDao.findAll();
	}
	
	public void createRule(Rule rule) {
		rule.setCreationDate(new Date());
		rule.setLastModifyDate(new Date());
		ruleDao.create(rule);
	}
	
	public void updateRule(Rule rule) {
		rule.setLastModifyDate(new Date());
		ruleDao.update(rule);
	}
	
	public void deleteRule(Long id) {
		ruleDao.deleteById(id);
	}
}
