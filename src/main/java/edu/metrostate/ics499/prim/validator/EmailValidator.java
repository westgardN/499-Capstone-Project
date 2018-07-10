package edu.metrostate.ics499.prim.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * An email validator that ensures the email address is in the proper format.
 * It is invoked using the @ValidEmail annotation.
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Required by the interface. Simply initializes this validator with annotation used to invoke it.
     * @param constraintAnnotation the annotation used to invoke the validator
     */
    @Override
    public void initialize(final ValidEmail constraintAnnotation) {
    }

    /**
     * Returns true if the specified e-mail is valid.
     *
     * @param email the email to validate
     * @param context the context we are working in.
     *
     * @return true if the email is valid; otherwise false.
     */
    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return (validateEmail(email));
    }

    private boolean validateEmail(final String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}