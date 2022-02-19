package com.javaschool.microecare.commonentitymanagement.service;

import com.javaschool.microecare.utils.EntityCannotBeSavedException;
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

/**
 * The service to manage features which are common for any entity
 */
@PropertySource("messages.properties")
@Service
public class CommonEntityService {
    @Value("${endpoints.tvpp.entity.path.new}")
    private String newPath;
    @Value("${endpoints.tvpp.entity.path.edit}")
    private String editPath;
    /**
     * The Constraint violation message, returned when an entity cannot be saved due to DB contraint violation of unknown source.
     */
    @Value("${general.unknown_field.constraint_violation.msg}")
    String constraintViolationMessage;

    /**
     * Sets standard paths for actions against any entity into attributes of model
     *
     * @param model          the model
     * @param controllerPath the controller path
     */
    public void setPathsAttributes(Model model, String controllerPath) {
        model.addAttribute("pathNew", controllerPath + newPath);
        model.addAttribute("pathEdit", controllerPath + editPath);
        model.addAttribute("pathDeleteUpdate", controllerPath + "/{id}");
        model.addAttribute("controllerPath", controllerPath);
    }

    /**
     * Create saving entity exception entity cannot be saved exception.
     *
     * @param e               the DataIntegrityViolationException exception
     * @param entityName      the entity name in human-readable manner to be used in controller response
     * @param searchSubstring the search substring to identify violated field
     * @param niceMessage     the nice error message to be used in controller response
     * @return the entity cannot be saved exception EntityCannotBeSavedException
     */
    public EntityCannotBeSavedException createSavingEntityException(DataIntegrityViolationException e, String entityName, String searchSubstring, String niceMessage) {
        //TODO: change String searchSubstring to List<String> for entities which have multiple unique fields
        String errorMessage = resolveIntegrityViolationMessage(e, searchSubstring, niceMessage);
        return new EntityCannotBeSavedException(entityName, errorMessage);
    }

    /**
     * Returns a human-readable error message for DataIntegrityViolationException based on most specific message in exception.
     * It can be a message about concrete field (param niceMessage) or general message about constraint violation (field constraintViolationMessage)
     * If most specific message is null - exception class name is returned
     *
     * @param e               the DataIntegrityViolationException exception
     * @param searchSubstring the search substring to identify violated field
     * @param niceMessage     the nice error message to be used in controller response
     * @return the string
     */
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


    /**
     * Creates a new BindingResult with desired validation messages and sets them to model.
     * Replaces default validation messages with human-readable ones and sets them to model.
     * Used for situations when Spring Validation provides exception message instead of nice one
     *
     * @param model           the model
     * @param bindingResult   the binding result
     * @param fieldMessageMap the map containing fields for which messages should be replaced as keys, and desired messages as values
     * @param replacedMessage the default message which has to be replaced
     */
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
