package com.javaschool.microecare.commonentitymanagement.service;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.commonentitymanagement.dao.EntityCannotBeSavedException;
import com.javaschool.microecare.ordermanagement.TVPPBasket;
import com.javaschool.microecare.utils.EntityActions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.cms.SCVPReqRes;
import org.hibernate.action.internal.EntityAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    @Value("${endpoints.tvpp.entity.path.view}")
    private String viewPath;
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * The Constraint violation message, returned when an entity cannot be saved due to DB contraint violation of unknown source.
     */
    @Value("${general.unknown_field.constraint_violation.msg}")
    String constraintViolationMessage;
    @Value("${general.unknown_field.retrieval_failure.msg}")
    String retrievalFailureMessage;

    @Autowired
    TVPPBasket TVPPBasket;

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
        model.addAttribute("pathView", controllerPath + viewPath);
        model.addAttribute("controllerPath", controllerPath);
    }

    /**
     * Sets number of items in basket to attributes of a model
     *
     * @param model the model
     */
    public void setBasketItems(Model model) {
        int items = TVPPBasket.getNumberOfOrders();
        model.addAttribute("basketItems", items);
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
                // = new FieldError(objectName, originalError.getField(), fieldsMessagesMap.get(fieldName));
                FieldError newError = new FieldError(objectName, originalError.getField(), originalError.getRejectedValue(), false, null, null, fieldsMessagesMap.get(fieldName));
                return newError;
                // return new FieldError(objectName, originalError.getField(), fieldsMessagesMap.get(fieldName));
            }
        }
        return originalError;
    }

    /**
     * Sets update time field in an entity and saves the entity in a provided repository
     *
     * @param baseEntity    an entity to set update time and save
     * @param jpaRepository repository relevant for the entity
     * @param <T>           specific type of the entity, e.g. Tariff, Option, etc.
     * @return saved entity
     */
    public <T extends BaseEntity> T saveWithUpdateTime(T baseEntity, JpaRepository<T, Long> jpaRepository) {
        baseEntity.setUpdateTime(LocalDateTime.now());
        return jpaRepository.save(baseEntity);

    }

    /**
     * Validates if provided string can be parsed as LocalDate with format dd.mm.YYYY
     *
     * @param dateString
     * @return
     */
    public static boolean validateDate(String dateString) {

        try {
            LocalDate.parse(dateString, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns if business process was started in a correct way (from the start,
     * not by direct link to random process page) based on model attribute processProgress
     *
     * @param model view model
     * @return boolean value of legal or illegal start
     */
    public Boolean isLegalProcessEntry(Model model) {
        if (model.containsAttribute("processProgress")) {
            if (model.getAttribute("processProgress") != null) {
                try {
                    return (Boolean) model.getAttribute("processProgress");
                } catch (NullPointerException | ClassCastException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public String resolveJpaObjectRetrievalFailureExceptionMessage(JpaObjectRetrievalFailureException e) {
        String specificMessage = e.getMostSpecificCause().getMessage();
        //Unable to find com.javaschool.microecare.catalogmanagement.dao.Option with id 283
        String entity = StringUtils.substringBetween(specificMessage, "dao.", " ");
        String id = specificMessage.substring(specificMessage.indexOf("id ") + 3);
        return String.format(retrievalFailureMessage, entity, id);
    }

    public void setSuccessfulActionModel(Model model, String ENTITY_NAME, EntityActions action, long successId) {
        model.addAllAttributes(Map.of("successfulAction", true,
                "successEntityName", ENTITY_NAME,
                "successAction", action.getText(),
                "successId", successId));
    }

    public void setErrorModel(Model model, String ENTITY_NAME, String errorMessage, EntityActions action) {
        model.addAttribute("errorEntity", ENTITY_NAME);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorAction", action.getText());
    }

    public void setEntityCannotBeSavedModel(Model model, EntityCannotBeSavedException e, EntityActions action) {
        model.addAttribute("errorEntity", e.getEntityName());
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorAction", action.getText());
    }

}
