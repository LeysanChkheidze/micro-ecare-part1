package com.javaschool.microecare.catalogmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

@PropertySource("messages.properties")
@Service
public class CommonEntityService {
    @Value("${endpoints.tvpp.entity.path.new}")
    private String newPath;
    @Value("${endpoints.tvpp.entity.path.edit}")
    private String editPath;
    @Value("${general.unknown_field.constraint_violation.msg}")
    String constraintViolationMessage;

    public void setPathsAttributes(Model model, String controllerPath) {
        model.addAttribute("pathNew", controllerPath + newPath);
        model.addAttribute("pathEdit", controllerPath + editPath);
        model.addAttribute("pathDeleteUpdate", controllerPath + "/{id}");
        model.addAttribute("controllerPath", controllerPath);
    }

    public String resolveIntegrityViolationMessage(DataIntegrityViolationException e, String searchSubstring, String niceMessage) {
        String specificMessage = e.getMostSpecificCause().getMessage();
        if (specificMessage != null) {
            return getIntegrityViolationMessage(specificMessage, searchSubstring, niceMessage);
        } else {
            return e.getClass().getName();
        }
    }

    private String getIntegrityViolationMessage(String receivedMessage, String searchSubstring, String niceMessage) {
        if (receivedMessage.contains(searchSubstring)) {
            return niceMessage;
        }
        return constraintViolationMessage;
    }



    public void setNiceValidationMessages(Model model, BindingResult bindingResult, Map<String, String> fieldMessageMap,
                                          String replacedMessage) {

        List<FieldError> allFieldErrors = bindingResult.getFieldErrors();
        BindingResult newResult = new BeanPropertyBindingResult(bindingResult.getTarget(), bindingResult.getObjectName());

        for (FieldError error : allFieldErrors) {
            FieldError newError = resolveValidationError(error, bindingResult.getObjectName(), fieldMessageMap, replacedMessage);
            newResult.addError(newError);
        }
        model.addAttribute("org.springframework.validation.BindingResult." + bindingResult.getObjectName(), newResult);
    }

    private FieldError resolveValidationError(FieldError originalError, String objectName, Map<String, String> fieldsMessagesMap, String replacedMessage) {
        String fieldName = originalError.getField();
        if (fieldsMessagesMap.containsKey(fieldName)) {
            if (originalError.getDefaultMessage() != null && originalError.getDefaultMessage().contains(replacedMessage)) {
                return new FieldError(objectName, originalError.getField(), fieldsMessagesMap.get(fieldName));
            }
        }
        return originalError;
    }


}
