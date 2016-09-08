package it.cabsb.persistence.service.impl;

import it.cabsb.model.Role;
import it.cabsb.model.User;
import it.cabsb.persistence.dao.IRoleDao;
import it.cabsb.persistence.dao.IUserDao;
import it.cabsb.persistence.service.IUserService;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  
@Transactional(rollbackFor=Exception.class)
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	public User loadUserByUsername(String username) {
		User user = userDao.findByUsername(username); 
		if(user!=null) Hibernate.initialize(user.getAuthorities());
		return user;
	}
	
	public Role loadRoleByAuthority(String authority) {
		return roleDao.findByAuthority(authority);
	}
	
	@Cacheable(value="user", key="#id")
	public User findUserById(Long id) {
		User user = userDao.findOne(id);
		if(user!=null) Hibernate.initialize(user.getAuthorities());
		return user;
	}
	
	@Cacheable(value="role", key="#id")
	public Role findRoleById(Long id) {
		return roleDao.findOne(id);
	}
	
	@Cacheable(value="user", key="#root.method.name")
	public List<User> getAllUsers() {
		return userDao.findAll();
	}
	
	@Cacheable(value="user", key="#root.method.name")
	public List<User> getAllUsersWithRoles() {
		List<User> users = userDao.findAll();
		for (User user : users) {
			Hibernate.initialize(user.getAuthorities());
		}
		return users;
	}
	
	@Cacheable(value="role", key="#root.method.name")
	public List<Role> getAllRoles() {
		return roleDao.findAll();
	}
	
	@Cacheable(value="role", key="#root.method.name")
	public List<Role> getAllRolesWithUsers() {
		List<Role> roles = roleDao.findAll();
		for (Role role : roles) {
			Hibernate.initialize(role.getUsers());
		}
		return roles;
	}
	
	@CacheEvict(value="user", allEntries=true)
	public void createUser(User user) {
		user.setCreationDate(new Date());
		user.setLastModifyDate(new Date());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		userDao.create(user);
	}
	
	@CacheEvict(value="role", allEntries=true)
	public void createRole(Role role) {
		roleDao.create(role);
	}
	
	@Caching(evict={
			@CacheEvict(value="user", allEntries=true),
			@CacheEvict(value="role", allEntries=true)			
	})
	public void updateUser(User user) {
		user.setLastModifyDate(new Date());
		userDao.update(user);
	}
	
	@Caching(evict={
			@CacheEvict(value="user", allEntries=true),
			@CacheEvict(value="role", allEntries=true)			
	})
	public void updateRole(Role role) {
		roleDao.update(role);
	}
	
	@Caching(evict={
			@CacheEvict(value="user", allEntries=true),
			@CacheEvict(value="role", allEntries=true)			
	})
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}
	
	@Caching(evict={
			@CacheEvict(value="user", allEntries=true),
			@CacheEvict(value="role", allEntries=true)			
	})
	public void deleteRole(Long id) {
		Role role = roleDao.findOne(id);
		for (User user : role.getUsers()) {
			user.getAuthorities().remove(role);
		}
		roleDao.delete(role);
	}
	
	public void purgeUnconfirmedUsers() {
		List<User> unconfirmedUsers = userDao.findUnconfirmedUsers();
		for (User unconfirmedUser : unconfirmedUsers) {
			userDao.delete(unconfirmedUser);
		}
	}
	
	public int countUsers() {
		return userDao.countAll();
	}
	
	public List<User> load(int first, int pageSize) {
		return userDao.findByCriteria(first, pageSize);
	}
}
