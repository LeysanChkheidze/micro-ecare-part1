package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dto.*;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import com.javaschool.microecare.customermanagement.service.PassportType;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;
import com.javaschool.microecare.ordermanagement.TVPPBasket;
import com.javaschool.microecare.ordermanagement.NewCustomerOrder;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * Controller for Customers page in TVPP.
 */
@Controller
@RequestMapping("${endpoints.tvpp.customers.controller_path}")
@PropertySource("messages.properties")
public class CustomersPageTVPPController {
    @Value("${directory.templates.tvpp.customers}")
    private String templateFolder;
    @Value("${endpoints.tvpp.customers.controller_path}")
    private String controllerPath;
    @Value("${endpoints.tvpp.customers.path.personal_data}")
    private String personalDataPath;
    @Value("${endpoints.tvpp.customers.path.address}")
    private String addressPath;
    @Value("${endpoints.tvpp.customers.path.passport}")
    private String passportPath;
    @Value("${endpoints.tvpp.customers.path.login}")
    private String loginPath;
    @Value("${endpoints.tvpp.customers.path.overview}")
    private String overviewPath;
    @Value("${endpoints.tvpp.basket.controller_path}")
    private String basketControllerPath;
    @Value("${general.field.not_date.msg}")
    private String notDateErrorMessage;


    private boolean successfulAction = false;
    private String successActionName;
    private long successId;

    final CommonEntityService commonEntityService;
    final CustomersService customersService;

    public CustomersPageTVPPController(CommonEntityService commonEntityService, CustomersService customersService) {
        this.commonEntityService = commonEntityService;
        this.customersService = customersService;
    }

    @Lookup
    public TVPPBasket getBasket() {
        return null;
    }

    @Lookup
    public CustomerDTO getCustomerDTO() {
        return null;
    }

    @ModelAttribute
    public void setCommonAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
        model.addAttribute("personalDataPath", controllerPath + personalDataPath);
        model.addAttribute("addressPath", controllerPath + addressPath);
        model.addAttribute("passportPath", controllerPath + passportPath);
        model.addAttribute("loginPath", controllerPath + loginPath);
        model.addAttribute("submitPath", controllerPath + overviewPath);
        model.addAttribute("overviewPath", overviewPath);
        model.addAttribute("basketPath", basketControllerPath);
    }

    private void setAllCustomersModel(Model model) {
        model.addAttribute("customers", customersService.getAllCustomerViews());
        if (successfulAction) {
            model.addAllAttributes(Map.of("successfulAction", true,
                    "successEntityName", "Customer",
                    "successAction", successActionName,
                    "successId", successId));
        }
    }

    @GetMapping
    public String getCustomersPage(Model model, @RequestParam(required = false) Boolean cancel) {
        setAllCustomersModel(model);
        successfulAction = false;
        if (cancel != null && cancel) {
            customersService.resetCustomerDTO(getCustomerDTO());
        }
        return templateFolder + "customers";
    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String getCreateNewCustomer(PersonalDataDTO personalDataDTO, Model model) {
        return "redirect:" + controllerPath + personalDataPath;
    }

    @GetMapping("${endpoints.tvpp.customers.path.personal_data}")
    public String showNewCustomerPersonalDataPage(PersonalDataDTO personalDataDTO, Model model) {
        model.addAttribute("dataSubmitted", false);
        model.addAttribute("customerDTO", getCustomerDTO());
        return templateFolder + "new_personal_data";
    }

    @PostMapping("${endpoints.tvpp.customers.path.personal_data}")
    public String postPersonalData(@Valid PersonalDataDTO personalDataDTO, BindingResult result, Model model) {
        model.addAttribute("dataSubmitted", true);
        if (result.hasErrors()) {
            model.addAttribute("customerDTO", getCustomerDTO());
            return templateFolder + "new_personal_data";
        }

        if (!CommonEntityService.validateDate(personalDataDTO.getBirthday())) {
            result.addError(new FieldError("customerDTO", "birthday", notDateErrorMessage));
            return templateFolder + "new_personal_data";
        }
        getCustomerDTO().setPersonalDataDTO(personalDataDTO);
        return "redirect:" + controllerPath + passportPath;
    }


    private void setPassportPageModel(Model model) {
        PassportType[] passportTypes = PassportType.values();
        model.addAttribute("passportTypes", passportTypes);

    }

    @GetMapping("${endpoints.tvpp.customers.path.passport}")
    public String showPassportPage(PassportDTO passportDTO, Model model) {
        setPassportPageModel(model);
        model.addAttribute("dataSubmitted", false);
        model.addAttribute("customerDTO", getCustomerDTO());
        return templateFolder + "new_passport_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.passport}")
    public String postPassportData(@Valid PassportDTO passportDTO, BindingResult result, Model model) {
        model.addAttribute("dataSubmitted", true);
        boolean issueDateValid = CommonEntityService.validateDate(passportDTO.getIssueDate());
        if (result.hasErrors() || !issueDateValid) {
            setPassportPageModel(model);
            if (!issueDateValid) {
                result.addError(new FieldError("passportDTO", "issueDate", notDateErrorMessage));
            }
            return templateFolder + "new_passport_page";
        }
        getCustomerDTO().setPassportDTO(passportDTO);
        return "redirect:" + controllerPath + addressPath;
    }

    @GetMapping("${endpoints.tvpp.customers.path.address}")
    public String showAddressPage(AddressDTO addressDTO, Model model) {
        model.addAttribute("customerDTO", getCustomerDTO());
        model.addAttribute("dataSubmitted", false);
        return templateFolder + "new_address_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.address}")
    public String postAddressData(@Valid AddressDTO addressDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dataSubmitted", true);
            return templateFolder + "new_address_page";
        }
        getCustomerDTO().setAddressDTO(addressDTO);
        return "redirect:" + controllerPath + loginPath;
    }

    @GetMapping("${endpoints.tvpp.customers.path.login}")
    public String showLoginDataPage(LoginDataDTO loginDataDTO, Model model) {
        model.addAttribute("customerDTO", getCustomerDTO());
        model.addAttribute("dataSubmitted", false);
        return templateFolder + "new_login_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.login}")
    public String postLoginData(@Valid LoginDataDTO loginDataDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dataSubmitted", true);
            return templateFolder + "new_login_page";
        }
        getCustomerDTO().setLoginDataDTO(loginDataDTO);
        return "redirect:" + controllerPath + overviewPath;
    }

    @GetMapping("${endpoints.tvpp.customers.path.overview}")
    public String showOverviewPage(Model model) {
        model.addAttribute("customerView", new CustomerView(getCustomerDTO()));
        return templateFolder + "overview_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.overview}")
    public String postNewCustomerOrder(Model model) {
        NewCustomerOrder newCustomerOrder = new NewCustomerOrder(getCustomerDTO());
        getBasket().add(newCustomerOrder);
        customersService.resetCustomerDTO(getCustomerDTO());
        return "redirect:" + basketControllerPath;
    }


    @DeleteMapping("/{id}")
    public String deleteTariff(@PathVariable("id") int id, Model model) {
        try {
            customersService.deleteCustomer(id);
            successfulAction = true;
            successActionName = "deleted";
            successId = id;
        } catch (RuntimeException e) {
            //todo: add error popup
        }
        return "redirect:" + controllerPath;
    }
}
