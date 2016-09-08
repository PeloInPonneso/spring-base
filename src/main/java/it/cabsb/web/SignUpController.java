package it.cabsb.web;

import it.cabsb.i18n.Iso;
import it.cabsb.mail.MailService;
import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

@Controller
@SessionAttributes(types = {User.class})
public class SignUpController {
	
	private static Logger _log = LoggerFactory.getLogger(SignUpController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private Iso iso;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public ModelAndView showForm() {
		Locale currentLocale = LocaleContextHolder.getLocale();
		ModelAndView mav = new ModelAndView("sign_up/form");
		User user = new User();
		mav.addObject("user", user);
		mav.addObject("countries", iso.getCountries().get(currentLocale.getLanguage()));
		return mav;
	}
	
	@RequestMapping(value = "/sign-up/success", method = RequestMethod.GET)
	public String signUpSuccess(ModelMap model) {
		return "sign_up/success";
	}
	
	@RequestMapping(value = "/sign-up/confirmation", method = RequestMethod.GET)
	public String signUpConfirmation(ModelMap model) {
		return "sign_up/confirmation";
	}
	
	@RequestMapping(value = "/sign-up/submit", method = RequestMethod.POST)  
	public ModelAndView signUp(@ModelAttribute("user") @Valid User user, BindingResult result, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("sign_up/form");
			mav.addObject("user", user);
			mav.addObject("countries", iso.getCountries().get(currentLocale.getLanguage()));
			mav.addObject(result);
			return mav;
        } else {
        	user.setPassword(Hashing.sha256().hashString(user.getFormPasswordOne(), Charsets.UTF_8).toString());
        	userService.createUser(user);
        	mailService.sendRegistrationConfirmationEmail(user, currentLocale);
        	ModelAndView successMav = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/sign-up/success");
        	successMav.addObject("user", user);
        	_log.info("New User registration: username=" + user.getUsername());
			return successMav;
        }
	}
	
	@RequestMapping(value = "/sign-up/email-confirmation/{username}/{hashCode}", method = RequestMethod.GET)
	public ModelAndView emailConfirmation(@PathVariable String username, @PathVariable int hashCode) {
		User user = userService.loadUserByUsername(username);
		if(user!=null) {
			if(hashCode == user.hashCode()) {
				user.setEnabled(true);
				userService.updateUser(user);
			} else {
				user = null;
			}
		}
		ModelAndView confirmationMav = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/sign-up/confirmation");
		confirmationMav.addObject("user", user);
		return confirmationMav;
	}
}
