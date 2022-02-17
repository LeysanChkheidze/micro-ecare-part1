package com.javaschool.microecare.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@PropertySource("application.properties")
public class PageModelUtils {

    @Value("${endpoints.tvpp.entity.path.new}")
    private static String standardNewEntityPath;
    @Value("${endpoints.tvpp.entity.path.edit}")
    private static String standardEditEntityPath;

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

    public static void setStandardPathsAttributes(Model model, String controllerPath) {
        model.addAttribute("pathNew", controllerPath + standardNewEntityPath);
        model.addAttribute("pathEdit", controllerPath + standardEditEntityPath);
        model.addAttribute("pathDeleteUpdate", controllerPath + "/{id}");
        model.addAttribute("controllerPath", controllerPath);
    }
}