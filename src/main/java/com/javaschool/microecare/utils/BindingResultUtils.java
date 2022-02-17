package com.javaschool.microecare.utils;

import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BindingResultUtils {

    public static void setNiceValidationMessages(Model model, BindingResult bindingResult, List<String> fieldsToReplaceMessages,
                                                 String replacedMessage, String newMessage) {

        List<FieldError> allFieldErrors = bindingResult.getFieldErrors();
        BindingResult newResult = new BeanPropertyBindingResult(bindingResult.getTarget(), bindingResult.getObjectName());

        for (FieldError error : allFieldErrors) {
            if (fieldsToReplaceMessages.contains(error.getField())) {
                if (error.getDefaultMessage() != null && error.getDefaultMessage().contains(replacedMessage)) {
                    FieldError newError = new FieldError(bindingResult.getObjectName(), error.getField(), newMessage);
                    newResult.addError(newError);
                } else {
                    newResult.addError(error);
                }
            } else {
                newResult.addError(error);
            }
        }
        model.addAttribute("org.springframework.validation.BindingResult." + bindingResult.getObjectName(), newResult);

    }
}