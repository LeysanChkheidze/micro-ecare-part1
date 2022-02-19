package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.catalogmanagement.service.TariffsService;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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
    @Value("${general.price.nonnumber.msg}")
    private String priceDigitsMessage;

    private boolean successfulAction = false;
    private String successActionName;
    private long successId;

    final TariffsService tariffsService;
    final CommonEntityService commonEntityService;

    /**
     * Instantiates a new Tariffs page tvpp controller.
     *
     * @param commonEntityService the CommonEntityService service with methods relevant to any entity
     * @param tariffsService      the TariffsService
     */
    public TariffsPageTVPPController(TariffsService tariffsService, CommonEntityService commonEntityService) {
        this.tariffsService = tariffsService;
        this.commonEntityService = commonEntityService;
    }

    /**
     * Sets paths attributes for paths which are standard for CRUD operations for any entity.
     *
     * @param model the model of the page
     */
    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
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
                    "successAction", successActionName,
                    "successId", successId));
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
        return templateFolder + "tariffs";
    }

    private void setModelForTariffsPage(Model model) {
        //todo: will be used to populate options to model

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
        setModelForTariffsPage(model);
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
    public String createNewTariff(@Valid TariffDTO tariffDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            setModelForTariffsPage(model);
            commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage), "java.lang.NumberFormatException");
            return templateFolder + "new_tariff";
        }
        try {
            Tariff newTariff = tariffsService.saveNewTariff(tariffDTO);
            successfulAction = true;
            successActionName = "created";
            successId = newTariff.getId();
            return "redirect:" + controllerPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            setModelForTariffsPage(model);
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
        setModelForTariffsPage(model);

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
        if (result.hasErrors()) {
            setModelForTariffsPage(model);
            commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage), "java.lang.NumberFormatException");
            TariffView tariffView = new TariffView(tariffsService.getTariff(id));
            model.addAttribute("tariffView", tariffView);
            return templateFolder + "edit_tariff";
        }

        try {
            Tariff updatedTariff = tariffsService.updateTariff(id, tariffDTO);
            successfulAction = true;
            successActionName = "updated";
            successId = updatedTariff.getId();
            return "redirect:" + controllerPath;

        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            setModelForTariffsPage(model);
            TariffView tariffView = new TariffView(tariffsService.getTariff(id));
            model.addAttribute("tariffView", tariffView);
            return templateFolder + "edit_tariff";
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
        try {
            tariffsService.deleteTariff(id);
            successfulAction = true;
            successActionName = "deleted";
            successId = id;
        } catch (RuntimeException e) {
            //todo: add error popup
        }
        return "redirect:" + controllerPath;
    }


}
