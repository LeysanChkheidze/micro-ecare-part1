package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dao.Address;
import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.PersonalData;
import com.javaschool.microecare.customermanagement.dto.CustomerDTO;
import com.javaschool.microecare.customermanagement.dto.PassportDTO;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private boolean successfulAction = false;
    private String successActionName;
    private long successId;

    final CommonEntityService commonEntityService;
    final CustomersService customersService;

    @Autowired
    CustomerDTO customerDTO;

    public CustomersPageTVPPController(CommonEntityService commonEntityService, CustomersService customersService) {
        this.commonEntityService = commonEntityService;
        this.customersService = customersService;
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
        model.addAttribute("personalDataPath", controllerPath + personalDataPath);
        model.addAttribute("addressPath", controllerPath + addressPath);
        model.addAttribute("passportPath", controllerPath + passportPath);
        model.addAttribute("loginPath", controllerPath + loginPath);
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
    public String getCustomersPage(Model model) {
        setAllCustomersModel(model);
        successfulAction = false;
        return templateFolder + "customers";
    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewCustomerPersonalDataPage(PersonalDataDTO personalDataDTO, Model model) {
        return templateFolder + "new_personal_data";
    }

    @PostMapping("${endpoints.tvpp.customers.path.personal_data}")
    public String PostPersonalData(@Valid PersonalDataDTO personalDataDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // commonEntityService.setNiceValidationMessages(model, result, Map.of("monthlyPrice", priceDigitsMessage, "oneTimePrice", priceDigitsMessage), "java.lang.NumberFormatException");
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

    @GetMapping("${endpoints.tvpp.customers.path.passport}")
    public String showPassportPage(PassportDTO passportDTO, Model model) {
        return templateFolder + "new_passport_page";
    }
}
