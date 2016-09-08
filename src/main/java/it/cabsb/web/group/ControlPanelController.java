package it.cabsb.web.group;

import it.cabsb.persistence.service.ISearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControlPanelController {
	
	@Autowired
	private ISearchService searchService;
	
	@RequestMapping(value = "/control_panel", method = RequestMethod.GET)
	public ModelAndView getControlPanel() {
		ModelAndView mav = new ModelAndView("control_panel/index");
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/reindex", method = RequestMethod.GET)
	public ModelAndView getReindexAllSearchIndexes() {
		ModelAndView mav = new ModelAndView("control_panel/index");
		searchService.reindex();
		return mav;
	}
}
