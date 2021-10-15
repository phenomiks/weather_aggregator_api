package ru.geekbrains.api.auth_api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.auth_api.exception.ErrorCode;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;

@Component
public class ValidateRequestUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateRequestUtils.class);

    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Value("${request-number-fields.register}")
    private int userRegisterParametersCount;

    @Value("${request-number-fields.user-keys}")
    private int userParametersCount;

    public Pair<ErrorResponse, UserRegParams> validateUserRegistrationParameters(ObjectNode parameters) {
        ErrorResponse status = checkJsonFieldsCount(parameters, userRegisterParametersCount);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = checkPresenceField(parameters, LOGIN);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = checkPresenceField(parameters, EMAIL);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = checkPresenceField(parameters, PASSWORD);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = isStringTypeField(parameters.path(LOGIN));
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = isStringTypeField(parameters.path(EMAIL));
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = isStringTypeField(parameters.path(PASSWORD));
        if (status != null) {
            return ImmutablePair.left(status);
        }

        String login = parameters.path(LOGIN).textValue();
        String email = parameters.path(EMAIL).textValue();
        char[] password = parameters.path(PASSWORD).textValue().toCharArray();

        status = isValidEmail(email);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        UserRegParams userRegParams = GenericBuilder.of(UserRegParams::new)
                .with(UserRegParams::setLogin, login)
                .with(UserRegParams::setPassword, password)
                .with(UserRegParams::setEmail, email)
                .build();

        return ImmutablePair.right(userRegParams);

//        return ImmutablePair.right(new UserRegParams(login, password, email));
    }

    public Pair<ErrorResponse, UserParams> validateUserParameters(ObjectNode parameters) {
        ErrorResponse status = checkJsonFieldsCount(parameters, userParametersCount);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = checkPresenceField(parameters, LOGIN);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = checkPresenceField(parameters, PASSWORD);
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = isStringTypeField(parameters.path(LOGIN));
        if (status != null) {
            return ImmutablePair.left(status);
        }

        status = isStringTypeField(parameters.path(PASSWORD));
        if (status != null) {
            return ImmutablePair.left(status);
        }

        String login = parameters.path(LOGIN).textValue();
        char[] password = parameters.path(PASSWORD).textValue().toCharArray();

        return ImmutablePair.right(new UserParams(login, password));
    }

    private ErrorResponse checkJsonFieldsCount(ObjectNode parameters, int count) {
        if (parameters.size() == count) {
            return null;
        }
        LOGGER.warn("{}. Validation input parameters size not equals {}",
                ErrorCode.JSON_VALIDATION_ERROR, count);

        return new ErrorResponse(ErrorCode.JSON_VALIDATION_ERROR,
                "Check the number of fields in the message");
    }

    private ErrorResponse checkPresenceField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return null;
        }
        LOGGER.warn("{}. Not found {} field", ErrorCode.JSON_VALIDATION_ERROR, fieldName);

        return new ErrorResponse(ErrorCode.JSON_VALIDATION_ERROR, "Not found " + fieldName + " field");
    }

    private ErrorResponse isStringTypeField(JsonNode field) {
        if (field.isTextual()) {
            return null;
        }
        LOGGER.warn("{}. {} value must be a string", ErrorCode.JSON_VALIDATION_ERROR, field);

        return new ErrorResponse(ErrorCode.JSON_VALIDATION_ERROR, field + " value must be a string");
    }

    private ErrorResponse isValidEmail(String email) {
        boolean isValid = EmailValidator.getInstance().isValid(email);
        if (isValid) {
            return null;
        }
        LOGGER.warn("{}. Email {} is not valid", ErrorCode.EMAIL_VALIDATION_ERROR, email);

        return new ErrorResponse(ErrorCode.EMAIL_VALIDATION_ERROR, email);
    }
}
