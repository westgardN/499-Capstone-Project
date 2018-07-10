package edu.metrostate.ics499.prim.validator;

import edu.metrostate.ics499.prim.datatransfer.UserDataTransfer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Used to validate that the password and confirm password fields match.
 * It is invoked by the @PasswordMatches annotation.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * Required by the interface. Simply initializes this validator with annotation used to invoke it.
     * @param constraintAnnotation the annotation used to invoke the validator
     */
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    /**
     * Returns true if the passwords of the specified UserDataTransfer objects match
     *
     * @param obj the UserDataTransfer object to validate the passwords
     * @param context the context we are working in.
     *
     * @return true if the passwords match; otherwise false.
     */
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDataTransfer user = (UserDataTransfer) obj;
        return user.passwordsMatch();
    }

}