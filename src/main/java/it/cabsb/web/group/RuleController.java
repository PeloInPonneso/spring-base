package it.cabsb.web.group;

import it.cabsb.model.Rule;
import it.cabsb.persistence.service.IRuleService;
import it.cabsb.persistence.service.IUserService;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.validation.Valid;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Controller
@SessionAttributes(types = {Rule.class})
public class RuleController {
	
	private static Logger _log = LoggerFactory.getLogger(RuleController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRuleService ruleService;
	
	@RequestMapping(value = "/control_panel/rules", method = RequestMethod.GET)
	public ModelAndView getRules() {
		ModelAndView mav = new ModelAndView("control_panel/rules/index");
		List<Rule> rules = ruleService.getAllRules();
		mav.addObject("rules", rules);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/rules/add", method = RequestMethod.GET)
	public ModelAndView addRule() {
		ModelAndView mav = new ModelAndView("control_panel/rules/addEditRule");
		Rule rule = new Rule();
		mav.addObject("rule", rule);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/rules/save", method = RequestMethod.POST)  
	public ModelAndView saveRule(@ModelAttribute("rule") @Valid Rule rule, BindingResult result) {
		StringBuffer drl = null;
		try {
    		SpreadsheetCompiler sc = new SpreadsheetCompiler();
    		drl = new StringBuffer(sc.compile(rule.getFile().getInputStream(), InputType.XLS));
    		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    		kbuilder.add(ResourceFactory.newReaderResource((Reader) new StringReader(drl.toString())), ResourceType.DRL);
    		if(kbuilder.hasErrors()) {
    			result.rejectValue("file", "rule.upload.ko");
    			for (KnowledgeBuilderError error : kbuilder.getErrors()) {
    				_log.error(error.getMessage());
    			}
    		}
		} catch (Exception e) {
			result.rejectValue("file", "rule.upload.ko");
			_log.error(e.getMessage());
		}
		
		if(result.hasErrors()) {
			ModelAndView mav = new ModelAndView("control_panel/rules/addEditRule");
			mav.addObject("rule", rule);
			mav.addObject(result);
			return mav;
        } else {
        	if(rule.getId()==null) {
        		rule.setContent(drl.toString());
        		ruleService.createRule(rule);
    		} else {
    			rule.setContent(drl.toString());
    			ruleService.updateRule(rule);
    		}
    		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/control_panel/rules");  
        }
	}
	
	@RequestMapping(value = "/control_panel/rules/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editRule(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("control_panel/rules/addEditRule");
		Rule rule = ruleService.findRuleById(id);
		mav.addObject("rule", rule);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/rules/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteRule(@PathVariable Long id) {
		ruleService.deleteRule(id);
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/control_panel/rules");
	}
	
	@RequestMapping(value = "/control_panel/rules/run/{id}", method = RequestMethod.GET)
	public ModelAndView testRule(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("control_panel/rules/index");
		List<Rule> rules = ruleService.getAllRules();
		mav.addObject("rules", rules);
		
		Rule drl = ruleService.findRuleById(id);
		try {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			
			Resource resource = ResourceFactory.newReaderResource((Reader) new StringReader(drl.getContent()));
			kbuilder.add(resource, ResourceType.DRL);
			KnowledgeBase kbase = kbuilder.newKnowledgeBase();
			kbase.addKnowledgePackages(kbase.getKnowledgePackages());
			StatefulKnowledgeSession statefulKnowledgeSession = kbase.newStatefulKnowledgeSession();
			statefulKnowledgeSession.dispose();
			mav.addObject("message", "rule.test.ok");
		} catch (Exception e) {
			mav.addObject("message", "rule.test.ko");
		}
		
		return mav;
	}
}
