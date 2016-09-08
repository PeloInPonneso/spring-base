package it.cabsb.web.group;

import it.cabsb.model.Role;
import it.cabsb.model.User;
import it.cabsb.model.editor.RoleEditor;
import it.cabsb.persistence.service.IUserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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
@SessionAttributes(types = {User.class, Role.class})
public class UserController {

	@Autowired
	private IUserService userService;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Role.class, new RoleEditor(userService));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	@RequestMapping(value = "/control_panel/users", method = RequestMethod.GET)
	public ModelAndView getUsers() {
		ModelAndView mav = new ModelAndView("control_panel/users/index");
		List<User> users = userService.getAllUsers();
		mav.addObject("users", users);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/roles", method = RequestMethod.GET)
	public ModelAndView getRoles() {
		ModelAndView mav = new ModelAndView("control_panel/roles/index");
		List<Role> roles = userService.getAllRolesWithUsers();
		mav.addObject("roles", roles);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/users/add", method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView mav = new ModelAndView("control_panel/users/addEditUser");
		User user = new User();
		List<Role> roles = userService.getAllRoles();
		mav.addObject("user", user);
		mav.addObject("roles", roles);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/roles/add", method = RequestMethod.GET)
	public ModelAndView addRole() {
		ModelAndView mav = new ModelAndView("control_panel/roles/addEditRole");
		Role role = new Role();
		mav.addObject("role", role);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/users/save", method = RequestMethod.POST)  
	public ModelAndView saveUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("control_panel/users/addEditUser");
			List<Role> roles = userService.getAllRoles();
			mav.addObject("user", user);
			mav.addObject("roles", roles);
			mav.addObject(result);
			return mav;
        } else {
			user.setPassword(Hashing.sha256().hashString(user.getFormPasswordOne(), Charsets.UTF_8).toString());
	    	if(user.getId()==null) {
				userService.createUser(user);
			} else {
				userService.updateUser(user);
			}
    		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/control_panel/users");
        }
	} 
	
	@RequestMapping(value = "/control_panel/roles/save", method = RequestMethod.POST)  
	public ModelAndView saveRole(@ModelAttribute("role") @Valid Role role, BindingResult result) {
		if(result.hasErrors()) {
			ModelAndView mav = new ModelAndView("control_panel/role/addEditRole");
			mav.addObject("role", role);
			mav.addObject(result);
			return mav;
        } else {
        	if(role.getId()==null) {
    			userService.createRole(role);
    		} else {
    			userService.updateRole(role);
    		}
    		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/control_panel/roles");  
        }
	} 
	
	@RequestMapping(value = "/control_panel/users/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("control_panel/users/addEditUser");
		User user = userService.findUserById(id);
		user.setPassword("");
		List<Role> roles = userService.getAllRoles();
		mav.addObject("user", user);
		mav.addObject("roles", roles);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/roles/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editRole(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("control_panel/roles/addEditRole");
		Role role = userService.findRoleById(id);
		mav.addObject("role", role);
		return mav;
	}
	
	@RequestMapping(value = "/control_panel/users/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/control_panel/users");
	}
	
	@RequestMapping(value = "/control_panel/roles/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteRole(@PathVariable Long id) {
		userService.deleteRole(id);
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/control_panel/roles");
	}
	
}
