package it.cabsb.persistence.dao;

import java.util.List;

import it.cabsb.model.User;
import it.cabsb.persistence.IOperations;

public interface IUserDao extends IOperations<User> {
	
	public User findByUsername(String username);
	
	public List<User> findUnconfirmedUsers();
}
