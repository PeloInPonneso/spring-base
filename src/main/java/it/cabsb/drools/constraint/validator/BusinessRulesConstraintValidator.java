package it.cabsb.drools.constraint.validator;

import it.cabsb.drools.constraint.BusinessRulesConstraint;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessRulesConstraintValidator implements ConstraintValidator<BusinessRulesConstraint, Object> {
	
	private static Logger _log = LoggerFactory.getLogger(BusinessRulesConstraintValidator.class);
	
	@Autowired
	private StatelessKnowledgeSession statelessKnowledgeSession;
	
	@Override
	public void initialize(BusinessRulesConstraint constraintAnnotation) {}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		final Errors errors = new Errors();
		_log.info("BusinessRulesConstraintValidator " + errors);
		try {
			statelessKnowledgeSession.execute(Arrays.asList(value, errors));
			if (errors.hasErrors()) {
				context.disableDefaultConstraintViolation();
				for (Error error : errors.getErrors()) {
					context.buildConstraintViolationWithTemplate(error.getMessage()).addNode(error.getField()).addConstraintViolation();
				}
	            return false;
            }
		} catch (Exception e) {
			_log.error(e.getMessage());
			return false;
		}
		return true;
	}
}
