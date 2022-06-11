package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.OptionListDTO;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.catalogmanagement.service.OptionsService;
import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.catalogmanagement.viewmodel.ShortOptionView;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.catalogmanagement.service.TariffsService;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.commonentitymanagement.dao.EntityCannotBeSavedException;
import com.javaschool.microecare.contractmanagement.service.ContractsService;
import com.javaschool.microecare.utils.EntityActions;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller Tariffs page in TVPP.
 */
@Controller
@RequestMapping("${endpoints.tvpp.tariffs.controller_path}")
@PropertySource("messages.properties")
public class TariffsPageTVPPController {
    @Value("${directory.templates.tvpp.tariffs}")
    private String templateFolder;
    @Value("${endpoints.tvpp.tariffs.controller_path}")
    private String controllerPath;
    @Value("${endpoints.tvpp.tariffs.path.edit_options}")
    private String pathEditOptions;
    @Value("${general.price.nonnumber.msg}")
    private String priceDigitsMessage;
    @Value("${tariff.delete.in_contract.msg}")
    private String tariffDeleteInContractMessage;

    private final String ENTITY_NAME = "Tariff";

    private boolean successfulAction = false;
    private long successId;
    private String errorMessage;
    private EntityActions action;

    private boolean viewDetails = false;
    private TariffView displayedTariff;
    private int numberOfContractsWithTariff;

    final TariffsService tariffsService;
    final CommonEntityService commonEntityService;
    final OptionsService optionsService;
    final ContractsService contractsService;

    /**
     * Instantiates a new Tariffs page tvpp controller.
     *
     * @param commonEntityService the CommonEntityService service with methods relevant to any entity
     * @param tariffsService      the TariffsService
     */
    public TariffsPageTVPPController(TariffsService tariffsService, CommonEntityService commonEntityService,
                                     OptionsService optionsService, ContractsService contractsService) {
        this.tariffsService = tariffsService;
        this.commonEntityService = commonEntityService;
        this.optionsService = optionsService;
        this.contractsService = contractsService;
    }

    /**
     * Sets paths attributes for paths which are standard for CRUD operations for any entity.
     *
     * @param model the model of the page
     */
    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
        model.addAttribute("pathEditOptions", controllerPath + pathEditOptions);
    }

    /**
     * Sets list of all found tariff views and attributes to display confirmation modal window into model
     *
     * @param model the model of the page
     */
    private void setAllTariffsModel(Model model) {
        model.addAttribute("tariffs", tariffsService.getAllTariffViews());
        if (successfulAction) {
            model.addAllAttributes(Map.of("successfulAction", true,
                    "successEntityName", "Tariff",
                    "successAction", action.getText(),
                    "successId", successId));
        }
        if (errorMessage != null) {
            model.addAttribute("errorEntity", ENTITY_NAME);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorAction", action.getText());
        }

        if (viewDetails) {
            model.addAttribute("viewTariffDetails", true);
            model.addAttribute("displayedTariff", displayedTariff);
            model.addAttribute("numberOfContractsWithTariff", numberOfContractsWithTariff);
        }
    }

    /**
     * Returns all tariffs page template with required model attributes at get request.
     * Sets successfulAction to false after the actual value of the field was set into model in setAllTariffsModel method
     *
     * @param model the model
     * @return all tariffs page template
     */
    @GetMapping
    public String getTariffsPage(Model model) {
        setAllTariffsModel(model);
        successfulAction = false;
        errorMessage = null;
        action = null;
        viewDetails = false;
        return templateFolder + "tariffs";
    }


    private void setModelForTariffCreationPage(Model model) {
        List<ShortOptionView> allExistingOptions = optionsService.getAllOptionsShortViews();
        model.addAttribute("allOptions", allExistingOptions);
    }

    /**
     * Returns new tariff page at get request.
     *
     * @param tariffDTO the tariff dto which will be used to create a new tariff
     * @param model     the model of the page
     * @return new tariff page template
     */
    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewTariffPage(TariffDTO tariffDTO, Model model) {
        setModelForTariffCreationPage(model);
        return templateFolder + "new_tariff";
    }

    /**
     * Creates new tariff at post request using TariffDTO.
     * In case of validation errors in TariffDTO returns new tariff page with human-readable validation messages in model
     * In case if EntityCannotBeSavedException caught during saving new tariff returns new tariff page with
     * error field name and error message in model
     *
     * @param tariffDTO the tariff dto to create new tariff
     * @param result    binding result
     * @param model     page model
     * @return all tariffs or new tariff page template depending on result of saving of the new tariff
     */
    @PostMapping
    //todo: add description like "Java supports Regular Expressions, but they're kind of cumbersome if you actually want to use them to extract matches. I think the easiest way to get at the string you want in your example is to just use the Regular Expression support in the String class's replaceAll method:
    //This simply deletes everything up-to-and-including the first (, and the same for the ) and everything thereafter. This just leaves the stuff between the parenthesis.
    //
    //However, the result of this is still a String. If you want an integer result instead then you need to do another conversion:"
    // and it fails with Reason: Duplicate key value violates unique constraint

    public String createNewTariff(@Valid TariffDTO tariffDTO, BindingResult result, Model model) {
        action = EntityActions.CREATE;
        if (result.hasErrors()) {
            model.addAttribute("errorAction", action.getText());
            setModelForTariffCreationPage(model);
            commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage), "java.lang.NumberFormatException");
            return templateFolder + "new_tariff";
        }
        try {
            Tariff newTariff = tariffsService.saveNewTariff(tariffDTO);
            successfulAction = true;
            successId = newTariff.getId();
            return "redirect:" + controllerPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorAction", action.getText());
            setModelForTariffCreationPage(model);
            return templateFolder + "new_tariff";
        }
    }

    /**
     * Returns update tariff page at get request.
     *
     * @param id    the id of the tariff to update
     * @param model the page model
     * @return update tariff page template
     */
    @GetMapping("${endpoints.tvpp.entity.path.edit}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Tariff tariff = tariffsService.getTariff(id);
        TariffDTO tariffDTO = new TariffDTO(tariff);
        TariffView tariffView = new TariffView(tariff);
        setModelForTariffCreationPage(model);

        model.addAttribute("tariffDTO", tariffDTO);
        model.addAttribute("tariffView", tariffView);
        return templateFolder + "edit_tariff";
    }

    /**
     * Updates existing tariff at patch request using validated TariffDTO.
     * In case of validation errors in TariffDTO returns update tariff page with human-readable validation messages in model
     * In case if EntityCannotBeSavedException caught during saving updated tariff returns update tariff page with
     * error field name and error message in model
     *
     * @param id        the id of the tariff to update
     * @param tariffDTO the tariff dto to use to set new parameters of the tariff
     * @param result    the binding result
     * @param model     the page model
     * @return all tariffs or update tariff template depending on result of saving of the new tariff
     */
    @PatchMapping("/{id}")
    public String updateTariff(@PathVariable("id") int id, @Valid TariffDTO tariffDTO,
                               BindingResult result, Model model) {
        action = EntityActions.UPDATE;
        if (result.hasErrors()) {
            model.addAttribute("errorAction", action.getText());
            setModelForTariffCreationPage(model);
            commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage), "java.lang.NumberFormatException");
            TariffView tariffView = new TariffView(tariffsService.getTariff(id));
            model.addAttribute("tariffView", tariffView);
            return templateFolder + "edit_tariff";
        }

        try {
            Tariff updatedTariff = tariffsService.updateTariff(id, tariffDTO);
            successfulAction = true;
            successId = updatedTariff.getId();
            return "redirect:" + controllerPath;

        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorAction", action.getText());
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            setModelForTariffCreationPage(model);
            TariffView tariffView = new TariffView(tariffsService.getTariff(id));
            model.addAttribute("tariffView", tariffView);
            return templateFolder + "edit_tariff";
        }
    }

    private void setModelForUpdateTariffOptionsPage(Model model, Tariff tariff) {
        TariffView tariffView = new TariffView(tariff);
        List<ShortOptionView> allExistingOptions = optionsService.getAllOptionsShortViews();
        model.addAttribute("allOptions", allExistingOptions);
        Set<ShortOptionView> compatibleOptions = optionsService.getShortViews(tariff.getCompatibleOptions());
        model.addAttribute("compatibleOptions", compatibleOptions);
        model.addAttribute("tariffView", tariffView);
        model.addAttribute("tariffView", tariffView);
    }

    /**
     * Returns update tariff compatible options page at get request.
     *
     * @param id    the id of the tariff to update compatible options
     * @param model the page model
     * @return update tariff options page template
     */
    @GetMapping("${endpoints.tvpp.tariffs.path.edit_options}")
    public String showUpdateOptionsForm(@PathVariable("id") int id, Model model) {
        Tariff tariff = tariffsService.getTariff(id);
        setModelForUpdateTariffOptionsPage(model, tariff);
        OptionListDTO optionListDTO = new OptionListDTO(tariff);
        model.addAttribute("optionListDTO", optionListDTO);
        return templateFolder + "edit_tariff_options";
    }


    /**
     * Updates compatible options for existing tariff at patch request using validated OptionListDTO.
     * In case of validation errors in TariffDTO returns update tariff page with human-readable validation messages in model
     * In case if EntityCannotBeSavedException caught during saving updated tariff returns update tariff page with
     * error field name and error message in model
     *
     * @param id            the id of the tariff to update
     * @param optionListDTO the optionListDTO to use to set new parameters of the tariff
     * @param result        the binding result
     * @param model         the page model
     * @return all tariffs or update tariff template depending on result of saving of the new tariff
     */
    @PatchMapping("${endpoints.tvpp.tariffs.path.edit_options}")
    public String updateTariffOptions(@PathVariable("id") int id, @Valid OptionListDTO optionListDTO,
                                      BindingResult result, Model model) {
        action = EntityActions.UPDATE;
        if (result.hasErrors()) {
            model.addAttribute("errorAction", action.getText());
            Tariff tariff = tariffsService.getTariff(id);
            setModelForUpdateTariffOptionsPage(model, tariff);
            return templateFolder + "edit_tariff_options";
        }

        try {
            Tariff updatedTariff = tariffsService.updateCompatibleOptionsInTariff(id, optionListDTO);
            successfulAction = true;
            successId = updatedTariff.getId();
            return "redirect:" + controllerPath;

        } catch (EntityCannotBeSavedException | JpaObjectRetrievalFailureException e) {
            model.addAttribute("errorAction", action.getText());
            Tariff tariff = tariffsService.getTariff(id);
            setModelForUpdateTariffOptionsPage(model, tariff);
            if (e instanceof EntityCannotBeSavedException) {
                EntityCannotBeSavedException exception = (EntityCannotBeSavedException) e;
                model.addAttribute("errorEntity", exception.getEntityName());
                model.addAttribute("errorMessage", e.getMessage());
            } else {
                JpaObjectRetrievalFailureException exception = (JpaObjectRetrievalFailureException) e;
                model.addAttribute("errorEntity", ENTITY_NAME);
                model.addAttribute("errorMessage", commonEntityService.resolveJpaObjectRetrievalFailureExceptionMessage(exception));
            }
            return templateFolder + "edit_tariff_options";
        }
    }

    /**
     * Deletes existing tariff at delete request.
     *
     * @param id    the id of tariff to delete
     * @param model the model
     * @return all tariffs page template
     */
    @DeleteMapping("/{id}")
    public String deleteTariff(@PathVariable("id") int id, Model model) {
        action = EntityActions.DELETE;
        try {
            tariffsService.deleteTariff(id);
            successfulAction = true;
            successId = id;
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (ExceptionUtils.getRootCauseMessage(e).contains("table \"contracts\"")) {
                errorMessage = tariffDeleteInContractMessage;
            } else {
                errorMessage = e.getMessage();
            }
            return "redirect:" + controllerPath;
        }
        return "redirect:" + controllerPath;
    }

    @GetMapping("/{id}")
    public String getTariffDetails(@PathVariable("id") int id, Model model) {
        action = EntityActions.READ;
        try {
            Tariff tariff = tariffsService.getTariff(id);
            viewDetails = true;
            displayedTariff = new TariffView(tariff);
            numberOfContractsWithTariff = contractsService.getNumberOfContractsWithTariff(tariff);

        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            return "redirect:" + controllerPath;
        }
        //todo: do i need redirect here?
        // https://stackoverflow.com/questions/68949567/pass-data-from-spring-boot-controller-to-boostrap-modal
        return "redirect:" + controllerPath;
    }


}
