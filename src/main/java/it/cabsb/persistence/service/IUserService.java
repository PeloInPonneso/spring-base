package it.cabsb.persistence.service;

import it.cabsb.model.Role;
import it.cabsb.model.User;

import java.util.List;

public interface IUserService {
	
	public User loadUserByUsername(String username);
	
	public Role loadRoleByAuthority(String authority);
	
	public User findUserById(Long id);
	
	public Role findRoleById(Long id);
	
	public List<User> getAllUsers();
	
	public List<User> getAllUsersWithRoles();
	
	public List<Role> getAllRoles();
	
	public List<Role> getAllRolesWithUsers();
	
	public void createUser(User user);
	
	public void createRole(Role role);
	
	public void updateUser(User user);
	
	public void updateRole(Role role);
	
	public void deleteUser(Long id);
	
	public void deleteRole(Long id);
	
	public void purgeUnconfirmedUsers();
	
	public int countUsers();
	
	public List<User> load(int first, int pageSize);
}
