package ru.geekbrains.api.auth_api.utils.validate;

import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailCustomValidator implements ConstraintValidator<EmailCustom, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmailValidator.getInstance().isValid(value);
    }
}
