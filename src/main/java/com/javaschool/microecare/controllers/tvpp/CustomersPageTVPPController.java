package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private boolean successfulAction = false;
    private String successActionName;
    private long successId;

    final CommonEntityService commonEntityService;
    final CustomersService customersService;

    public CustomersPageTVPPController(CommonEntityService commonEntityService, CustomersService customersService) {
        this.commonEntityService = commonEntityService;
        this.customersService = customersService;
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
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
}
