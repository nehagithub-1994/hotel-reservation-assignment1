package com.panoramichotel.api.util;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public interface ValidationErrorProcessor {

    default <Type> ResponseEntity<Type> processValidationErrors(final BindingResult result) {

        final List<FieldError> fieldErrors = result.getFieldErrors();
        final List<ObjectError> globalError = result.getGlobalErrors();
        final List<String> messages = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            messages.add(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        for (ObjectError objectError : globalError) {
            messages.add(String.format("%s: %s", objectError.getObjectName(), objectError.getDefaultMessage()));
        }

        if (!messages.isEmpty()) {
            LoggerFactory.getLogger(ValidationErrorProcessor.class).error(messages.toString());
        }

        final ResponseEntity responseEntity = ResponseEntity.badRequest().body(messages);

        return responseEntity;
    }
}
