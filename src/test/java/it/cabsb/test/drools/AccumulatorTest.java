package it.cabsb.test.drools;

import it.cabsb.drools.Report;
import it.cabsb.model.Role;
import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.base.RuleNameMatchesAgendaFilter;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.AgendaFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/context.xml")
public class AccumulatorTest {

	@Autowired
	private IUserService userService;
	
	@Test
	public void userRandomSelectTest() {
		KnowledgeBuilderConfiguration knowledgeBuilderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		knowledgeBuilderConfiguration.setProperty("drools.accumulate.function.randomSelect", "it.cabsb.drools.function.RandomSelectAccumulateFunction");
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(knowledgeBuilderConfiguration);
		
		kbuilder.add(ResourceFactory.newClassPathResource("it/cabsb/drools/user-report.drl"), ResourceType.DRL);
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		kbase.addKnowledgePackages(kbase.getKnowledgePackages());
		StatefulKnowledgeSession statefulKnowledgeSession = kbase.newStatefulKnowledgeSession();
		
		AgendaFilter agendaFilter = null;
		
		Report report = new Report(2);
		statefulKnowledgeSession.insert(report);
		List<User> userList = userService.getAllUsers();
		for (User user : userList) {
			statefulKnowledgeSession.insert(user);
		}
		List<Role> roleList = userService.getAllRoles();
		for (Role role : roleList) {
			statefulKnowledgeSession.insert(role);
		}
		
		agendaFilter = new RuleNameMatchesAgendaFilter("^(ListEnabledUsers)$");
		statefulKnowledgeSession.fireAllRules(agendaFilter);
		
		statefulKnowledgeSession.dispose();
		
		Assert.notEmpty(roleList);
		Assert.isTrue(roleList.size() == 2);
		
		Assert.notEmpty(userList);
		Assert.isTrue(userList.size() > 2);
		
		Assert.notEmpty(report.getItems());
		Assert.isTrue(report.getItems().size() == 2);
	}
}
