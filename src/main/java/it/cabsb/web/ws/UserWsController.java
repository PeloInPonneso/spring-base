package it.cabsb.web.ws;

import it.cabsb.model.User;
import it.cabsb.model.jaxb.wrapper.CollectionWrapper;
import it.cabsb.persistence.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/ws")
public class UserWsController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/get-users", method=RequestMethod.GET)
	public @ResponseBody CollectionWrapper<User> getUsers() {
		return new CollectionWrapper<User>(userService.getAllUsersWithRoles());
	}
	
	@RequestMapping(value="/get-user/username/{username}", method=RequestMethod.GET)
	public @ResponseBody User getUserByUsername(@PathVariable String username) {
		return userService.loadUserByUsername(username);
	}
	
	@RequestMapping(value="/get-user/role/{role}", method=RequestMethod.GET)
	public @ResponseBody CollectionWrapper<User> getUsersByRole(@PathVariable String role) {
		return new CollectionWrapper<User>(userService.loadRoleByAuthority(role).getUsers());
	}
}
