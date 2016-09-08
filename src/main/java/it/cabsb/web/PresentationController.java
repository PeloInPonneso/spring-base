package it.cabsb.web;

import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Controller
public class PresentationController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public ModelAndView getUserPresentation(@PathVariable String username) {
		ModelAndView mav = new ModelAndView();
		User user = userService.loadUserByUsername(username);
		if(user!=null) {
			mav.setViewName("presentation");
			mav.addObject("user", user);
		} else {
			mav.setViewName(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/");
		}		
		return mav;
		
	}

}
