package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dto.*;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import com.javaschool.microecare.customermanagement.service.PassportType;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;
import com.javaschool.microecare.ordermanagement.TVPPBasket;
import com.javaschool.microecare.ordermanagement.NewCustomerOrder;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    private boolean successfulAction = false;
    private String successActionName;
    private long successId;

    final CommonEntityService commonEntityService;
    final CustomersService customersService;
    final CustomerDTO customerDTO;
    @Autowired
    TVPPBasket basket;

    public CustomersPageTVPPController(CommonEntityService commonEntityService, CustomersService customersService) {
        this.commonEntityService = commonEntityService;
        this.customersService = customersService;
        this.customerDTO = new CustomerDTO();
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
        model.addAttribute("personalDataPath", controllerPath + personalDataPath);
        model.addAttribute("addressPath", controllerPath + addressPath);
        model.addAttribute("passportPath", controllerPath + passportPath);
        model.addAttribute("loginPath", controllerPath + loginPath);
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
            customersService.resetCustomerDTO(customerDTO);
        }
        return templateFolder + "customers";
    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewCustomerPersonalDataPage(PersonalDataDTO personalDataDTO, Model model) {
        return templateFolder + "new_personal_data";
    }

    @PostMapping("${endpoints.tvpp.customers.path.personal_data}")
    public String postPersonalData(@Valid PersonalDataDTO personalDataDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return templateFolder + "new_personal_data";
        }
        try {
            customerDTO.setPersonalDataDTO(personalDataDTO);
            return "redirect:" + controllerPath + passportPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            return templateFolder + "new_personal_data";
        }
    }

    private void setPassportPageModel(Model model) {
        PassportType[] passportTypes = PassportType.values();
        model.addAttribute("passportTypes", passportTypes);
    }

    @GetMapping("${endpoints.tvpp.customers.path.passport}")
    public String showPassportPage(PassportDTO passportDTO, Model model) {
        setPassportPageModel(model);
        return templateFolder + "new_passport_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.passport}")
    public String postPassportData(@Valid PassportDTO passportDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage, "oneTimePrice", priceDigitsMessage), "java.lang.NumberFormatException");
            return templateFolder + "new_passport_page";
        }
        try {
            customerDTO.setPassportDTO(passportDTO);
            return "redirect:" + controllerPath + addressPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            return templateFolder + "new_passport_page";
        }
    }

    @GetMapping("${endpoints.tvpp.customers.path.address}")
    public String showAddressPage(AddressDTO addressDTO, Model model) {
        return templateFolder + "new_address_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.address}")
    public String postAddressData(@Valid AddressDTO addressDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage, "oneTimePrice", priceDigitsMessage), "java.lang.NumberFormatException");
            return templateFolder + "new_address_page";
        }
        try {
            customerDTO.setAddressDTO(addressDTO);
            return "redirect:" + controllerPath + loginPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            return templateFolder + "new_address_page";
        }
    }

    @GetMapping("${endpoints.tvpp.customers.path.login}")
    public String showLoginDataPage(LoginDataDTO loginDataDTO, Model model) {
        return templateFolder + "new_login_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.login}")
    public String postLoginData(@Valid LoginDataDTO loginDataDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage, "oneTimePrice", priceDigitsMessage), "java.lang.NumberFormatException");
            return templateFolder + "new_login_page";
        }
        try {
            customerDTO.setLoginDataDTO(loginDataDTO);
            return "redirect:" + controllerPath + overviewPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            return templateFolder + "new_login_page";
        }
    }

    @GetMapping("${endpoints.tvpp.customers.path.overview}")
    public String showOverviewPage(Model model) {
        model.addAttribute("customerView", new CustomerView(customerDTO));
        model.addAttribute("submitPath", controllerPath + overviewPath);
        return templateFolder + "overview_page";
    }

    @PostMapping("${endpoints.tvpp.customers.path.overview}")
    public String postNewCustomerOrder(Model model) {
        NewCustomerOrder newCustomerOrder = new NewCustomerOrder(customerDTO);
        basket.add(newCustomerOrder);
        customersService.resetCustomerDTO(customerDTO);
        return "redirect:" + basketControllerPath;
    }
}
