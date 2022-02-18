package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.catalogmanagement.service.OptionsService;
import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
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
@RequestMapping("${endpoints.tvpp.options.controller_path}")
@PropertySource("messages.properties")
public class OptionsPageTVPPController {
    @Value("${directory.templates.tvpp.options}")
    private String templateFolder;
    @Value("${endpoints.tvpp.options.controller_path}")
    private String controllerPath;
    @Value("${general.price.nonnumber.msg}")
    private String priceDigitsMessage;

    private boolean successfulAction = false;
    private String successActionName;
    private long successId;

    final CommonEntityService commonEntityService;
    final OptionsService optionsService;

    public OptionsPageTVPPController(CommonEntityService commonEntityService, OptionsService optionsService) {
        this.commonEntityService = commonEntityService;
        this.optionsService = optionsService;
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
    }

    private void setAllOptionsModel(Model model) {
        model.addAttribute("options", optionsService.getAllOptionViews());
        if (successfulAction) {
            model.addAllAttributes(Map.of("successfulAction", true,
                    "successEntityName", "Option",
                    "successAction", successActionName,
                    "successId", successId));
        }
    }

    @GetMapping
    public String getOptionsPage(Model model) {
        setAllOptionsModel(model);
        successfulAction = false;
        return templateFolder + "options";
    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewOptionPage(OptionDTO optionDTO, Model model) {
        return templateFolder + "new_option";
    }

    @PostMapping
    public String createNewTariff(@Valid OptionDTO optionDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage, "oneTimePrice", priceDigitsMessage), "java.lang.NumberFormatException");
            return templateFolder + "new_option";
        }
        try {
            Option newOption = optionsService.saveNewOption(optionDTO);
            successfulAction = true;
            successActionName = "created";
            successId = newOption.getId();
            return "redirect:" + controllerPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            return templateFolder + "new_option";
        }
    }

    @GetMapping("${endpoints.tvpp.entity.path.edit}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Option option = optionsService.getOption(id);
        OptionDTO optionDTO= new OptionDTO(option);
        OptionView optionView = new OptionView(option);
        model.addAttribute("optionDTO", optionDTO);
        model.addAttribute("optionView", optionView);
        return templateFolder + "edit_option";
    }

    @PatchMapping("/{id}")
    public String updateTariff(@PathVariable("id") int id, @Valid OptionDTO optionDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage, "oneTimePrice", priceDigitsMessage), "java.lang.NumberFormatException");
            OptionView optionView = new OptionView(optionsService.getOption(id));
            model.addAttribute("optionView", optionView);
            return templateFolder + "edit_option";
        }

        try {
            Option updatedOption = optionsService.updateOption(id, optionDTO);
            successfulAction = true;
            successActionName = "updated";
            successId = updatedOption.getId();
            return "redirect:" + controllerPath;

        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            OptionView optionView = new OptionView(optionsService.getOption(id));
            model.addAttribute("optionView", optionView);
            return templateFolder + "edit_option";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteTariff(@PathVariable("id") int id, Model model) {
        try {
            optionsService.deleteOption(id);
            successfulAction = true;
            successActionName = "deleted";
            successId = id;
        } catch (RuntimeException e) {
            //todo: add error popup
        }
        return "redirect:" + controllerPath;
    }
}
