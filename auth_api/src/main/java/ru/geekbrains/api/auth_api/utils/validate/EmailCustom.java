package ru.geekbrains.api.auth_api.utils.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailCustomValidator.class)
@Documented
public @interface EmailCustom {
    String message() default "{Email.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
