package it.cabsb.drools.constraint;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import it.cabsb.drools.constraint.validator.BusinessRulesConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy=BusinessRulesConstraintValidator.class)
public @interface BusinessRulesConstraint {
	
	String message() default "{it.cabsb.drools.constraint.BusinessRulesConstraint.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
