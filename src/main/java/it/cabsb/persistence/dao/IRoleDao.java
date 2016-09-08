package it.cabsb.persistence.dao;

import it.cabsb.model.Role;
import it.cabsb.persistence.IOperations;

public interface IRoleDao extends IOperations<Role> {

	public Role findByAuthority(String authority);
	
}
