package it.cabsb.drools.constraint.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Errors {
	
	private final List<Error> errors = Collections.synchronizedList(new ArrayList<Error>());
	 
	public Collection<Error> getErrors() {
		return Collections.unmodifiableCollection(errors);
	}
	 
	public void addError(Object target, String field, String message) {
		this.errors.add(new Error(target, field, message));
	}
	 
	public boolean hasErrors() {
		if (this.errors.size() > 0) {
			return true;
	    }
	    return false;
	}
}
	 
class Error {
	
	private final Object target;
	private final String field;
	private final String message;
	 
	public Error(Object target, String field, String message) {
		super();
		this.target = target;
		this.field = field;
		this.message = message;
	}
	 
	public Object getTarget() {
		return target;
	}
	 
	public String getField() {
		return field;
	}
	 
	public String getMessage() {
		return message;
	}
}
