package it.cabsb.test.persistence.service;

import it.cabsb.model.Role;
import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/context.xml")
public class UserServiceTest {

	@Autowired
	private IUserService userService;
	
	@Test
	public void adminUserExistTest() {
		User user = userService.loadUserByUsername("admin");
		Assert.notNull(user);
	}
	
	@Test
	public void rolesSizeTest() {
		List<Role> roleList = userService.getAllRoles();
		Assert.notEmpty(roleList);
		Assert.isTrue(roleList.size() == 2);
	}
}
