package it.cabsb.model.editor;

import it.cabsb.model.Role;
import it.cabsb.persistence.service.IUserService;

import java.beans.PropertyEditorSupport;

public class RoleEditor extends PropertyEditorSupport {
	
	private IUserService userService;
	
	public RoleEditor(IUserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Role role = userService.findRoleById(Long.valueOf(text));
		setValue(role);
	}
}
