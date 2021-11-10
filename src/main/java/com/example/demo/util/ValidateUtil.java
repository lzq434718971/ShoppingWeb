package com.example.demo.util;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidateUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public static void beanValidate(Object obj) throws RuntimeException {
        Map<String, String> validatedMsg = new HashMap<>();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        for (ConstraintViolation<Object> c : constraintViolations) {
            validatedMsg.put(c.getPropertyPath().toString(), c.getMessage());
        }
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            throw new RuntimeException(validatedMsg.toString());
        }
    }

}