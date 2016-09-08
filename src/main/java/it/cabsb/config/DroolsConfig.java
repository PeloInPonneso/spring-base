package it.cabsb.config;

import it.cabsb.persistence.service.IUserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.drools.ClockType;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.conf.KeepReferenceOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.JpaTransactionManager;

@Order(3)
@Configuration
public class DroolsConfig {
	
	private static Logger _log = LoggerFactory.getLogger(DroolsConfig.class);
	private static Logger _rulelog = LoggerFactory.getLogger("RuleLog");
	
	@Autowired
	private IUserService userService;
	
	@PersistenceContext(unitName="jpa-persistence")
    private EntityManager entityManager;
	
	private JpaTransactionManager transactionManager;
	
	@Bean
	public Environment droolsEnv() {
		final Environment droolsEnv = KnowledgeBaseFactory.newEnvironment();
		droolsEnv.set(EnvironmentName.ENTITY_MANAGER_FACTORY, entityManager);
		droolsEnv.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);
		return droolsEnv;
	}
	
	@Bean
	public KnowledgeBuilderConfiguration knowledgeBuilderConfiguration() {
		KnowledgeBuilderConfiguration knowledgeBuilderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		knowledgeBuilderConfiguration.setProperty("drools.accumulate.function.randomSelect", "it.cabsb.drools.function.RandomSelectAccumulateFunction");
		return knowledgeBuilderConfiguration;
	}
	
	/**
	 * The KnowledgeBase is a repository of all the application's knowledge definitions.
	 * It will contain rules, processes, functions, type models. 
	 * The KnowledgeBase itself does not contain runtime data, 
	 * instead sessions are created from the KnowledgeBase in which data can be inserted and process instances started.
	 * Creating the KnowledgeBase can be heavy, where as session creation is very light, 
	 * so it is recommended that KnowledgeBases be cached where possible to allow for repeated session creation. 
	 * The KnowledgeAgent can be used for this purpose. 
	 * The KnowledgeBase is created from the KnowledgeBaseFactory, and a KnowledgeBaseConfiguration can be used.
	 * @return KnowledgeBase
	 */
	@Bean
	public KnowledgeBase knowledgeBase(KnowledgeBuilderConfiguration knowledgeBuilderConfiguration) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(knowledgeBuilderConfiguration);
		kbuilder.add(ResourceFactory.newClassPathResource("it/cabsb/drools/user-validation.drl"), ResourceType.DRL);
		if(kbuilder.hasErrors()) {
			for (KnowledgeBuilderError error : kbuilder.getErrors()) {
				_log.error(error.getMessage());
			}
			throw new IllegalStateException("Error building kbase!");
		}
		final KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		return kbase;
	}
	
	/**
	 * Class to store Session related configuration. It must be used at session instantiation time or not used at all.
	 * Options:
	 * 1. drools.keepReference = <true|false>
	 *    Option to configure if the knowledge base should retain a reference to the knowledge session or not
	 * 2. drools.clockType = <pseudo|realtime> 
	 *    Realtime uses the system clock to determine the current timestamp, Pseudo uses an out of the box impl of clock controlled by the application
	 * @return KnowledgeSessionConfiguration
	 */
	@Bean
	public KnowledgeSessionConfiguration knowledgeSessionConfiguration() {
		final KnowledgeSessionConfiguration knowledgeSessionConfiguration = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
		knowledgeSessionConfiguration.setOption(KeepReferenceOption.YES);
		knowledgeSessionConfiguration.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.toString()));
		return knowledgeSessionConfiguration;
	}
	
	/**
	 * StatefulKnowledgeSession is the most common way to interact with the engine.
	 * A StatefulKnowledgeSession allows the application to establish an iterative conversation with the engine, 
	 * where the state of the session is kept across invocations. 
	 * The reasoning process may be triggered multiple times for the same set of data.
	 * 
	 * After the application finishes using the session, though, it must call the dispose() method in order to free the resources and used memory,
	 * in fact it releases all the current session resources, setting up the session for garbage collection.
	 * 
	 * StatefulKnowledgeSessions support globals. 
	 * Globals are used to pass information into the engine (like data or service callbacks that can be used in your rules and processes),
	 * but they should not be used to reason over. If you need to reason over your data, make sure you insert it as a fact, not a global.
	 * Globals are shared among ALL your rules and processes, so be especially careful of (and avoid as much as possible) mutable globals. 
	 * Also, it is a good practice to set your globals before inserting your facts or starting your processes. 
	 * Rules engines evaluate rules at fact insertion time, and so, if you are using a global to constraint a fact pattern, 
	 * and the global is not set, you may receive a NullPointerException.
	 * Globals can be resolved in two ways, Internal Globals, Delegate Globals.
	 * The StatefulKnowledgeSession supports getGlobals() which returns the internal Globals, which itself can take a delegate.
	 * Calling of setGlobal(String, Object) will set the global on an internal Collection. 
	 * Identifiers in this internal Collection will have priority over the externally supplied Globals delegate. 
	 * If an identifier cannot be found in the internal Collection, it will then check the externally supplied Globals delegate, if one has been set.
	 * @param kbase
	 * @param knowledgeSessionConfiguration
	 * @param droolsEnv
	 * @return StatefulKnowledgeSession
	 */
	@Bean
	public StatefulKnowledgeSession statefulKnowledgeSession(KnowledgeBase kbase, KnowledgeSessionConfiguration knowledgeSessionConfiguration, Environment droolsEnv) {
		final StatefulKnowledgeSession statefulKnowledgeSession = kbase.newStatefulKnowledgeSession(knowledgeSessionConfiguration, droolsEnv);
		statefulKnowledgeSession.setGlobal("log", _rulelog);
		_log.info("Loaded new statefulKnowledgeSession with id " + statefulKnowledgeSession.getId());
	    return statefulKnowledgeSession;
	}
	
	/**
	 * StatelessKnowledgeSession provides a convenience API, wrapping StatefulKnowledgeSession. It avoids the need to call dispose().
	 * Stateless sessions do not support iterative invocations, the act of calling execute(...) is a single shot method 
	 * that will internally instantiate a StatefulKnowledgeSession, add all the user data and execute user commands, call fireAllRules, and then call dispose().
	 * While the main way to work with this class is via the BatchExecution Command as supported by the CommandExecutor interface, 
	 * two convenience methods are provided for when simple object insertion is all that's required.
	 * 
	 * StatelessKnowledgeSessions support globals, scoped in a number of ways. I'll cover the non-command way first,
	 * as commands are scoped to a specific execution call. Globals can be resolved in three ways. 
	 * The StatelessKnowledgeSession supports getGlobals(), which returns a Globals instance. 
	 * These globals are shared for ALL execution calls, so be especially careful of mutable globals in these cases,
	 * as often execution calls can be executing simultaneously in different threads. Globals also supports a delegate
	 * Calling of setGlobal(String, Object) will actually be set on an internal Collection, 
	 * identifiers in this internal Collection will have priority over supplied delegate, if one is added. 
	 * If an identifier cannot be found in the internal Collection, it will then check the delegate Globals, if one has been set.
	 * The third way is execution scoped globals using the CommandExecutor and SetGlobal Commands
	 * @param kbase
	 * @param knowledgeSessionConfiguration
	 * @return StatelessKnowledgeSession
	 */
	@Bean 
	public StatelessKnowledgeSession statelessKnowledgeSession(KnowledgeBase kbase, KnowledgeSessionConfiguration knowledgeSessionConfiguration) {
		final StatelessKnowledgeSession statelessKnowledgeSession = kbase.newStatelessKnowledgeSession(knowledgeSessionConfiguration);
		statelessKnowledgeSession.setGlobal("userService", userService);
		statelessKnowledgeSession.setGlobal("log", _rulelog);
		_log.info("Loaded new statelessKnowledgeSession");
		return statelessKnowledgeSession;
	}
}
