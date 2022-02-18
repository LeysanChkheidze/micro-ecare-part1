package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.catalogmanagement.service.CommonEntityService;
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

    public TariffsPageTVPPController(TariffsService tariffsService, CommonEntityService commonEntityService) {
        this.tariffsService = tariffsService;
        this.commonEntityService = commonEntityService;
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
    }

    private void setAllTariffsModel(Model model) {
        model.addAttribute("tariffs", tariffsService.getAllTariffViews());
        if (successfulAction) {
           // String successEntityName = "Tariff";
            model.addAllAttributes(Map.of("successfulAction", true,
                    "successEntityName", "Tariff",
                    "successAction", successActionName,
                    "successId", successId));
        }
    }

    @GetMapping
    public String getTariffsPage(Model model) {
        setAllTariffsModel(model);
        successfulAction = false;
        return templateFolder + "tariffs";
    }

    private void setModelForTariffsPage(Model model) {
        //todo: will be used to populate options to model

    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewTariffPage(TariffDTO tariffDTO, Model model) {
        setModelForTariffsPage(model);
        return templateFolder + "new_tariff";
    }

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


    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid TariffDTO tariffDTO,
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

    @DeleteMapping("/{id}")
    public String deleteTariff(@PathVariable("id") int id, Model model) {
        try {
            tariffsService.deleteTariffById(id);
            successfulAction = true;
            successActionName = "deleted";
            successId = id;
        } catch (RuntimeException e) {
            //todo: add error popup
        }
        return "redirect:" + controllerPath;
    }


}
