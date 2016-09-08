package it.cabsb.constraint.validator;
import it.cabsb.constraint.FieldMatchConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatchConstraint, Object> {

	private static Logger _log = LoggerFactory.getLogger(FieldMatchConstraintValidator.class);
	
	private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatchConstraint constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    	boolean isValid = false;
    	try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            isValid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception e) {
        	_log.error(e.getMessage());
        }
    	if(!isValid) {
    		context.disableDefaultConstraintViolation();
        	String message = context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(message).addNode(secondFieldName).addConstraintViolation();
    	}
        return isValid;
    }
}
