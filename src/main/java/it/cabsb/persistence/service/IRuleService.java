package it.cabsb.persistence.service;

import it.cabsb.model.Rule;

import java.util.List;

public interface IRuleService {

	public Rule findRuleById(Long id);
	
	public List<Rule> getAllRules();
	
	public void createRule(Rule rule);
	
	public void updateRule(Rule rule);
	
	public void deleteRule(Long id);
}
