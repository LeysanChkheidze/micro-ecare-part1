package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.dto.OptionListDTO;
import com.javaschool.microecare.catalogmanagement.service.OptionsService;
import com.javaschool.microecare.catalogmanagement.service.TariffsService;
import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.catalogmanagement.viewmodel.ShortOptionView;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.commonentitymanagement.dao.EntityCannotBeSavedException;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.contractmanagement.dao.Contract;
import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import com.javaschool.microecare.contractmanagement.dto.ContractCustomerDTO;
import com.javaschool.microecare.contractmanagement.dto.ContractDTO;
import com.javaschool.microecare.contractmanagement.dto.TariffAndNumberDTO;
import com.javaschool.microecare.contractmanagement.service.ContractsService;
import com.javaschool.microecare.contractmanagement.service.MobileNumbersService;
import com.javaschool.microecare.contractmanagement.viewmodel.ContractView;
import com.javaschool.microecare.contractmanagement.viewmodel.MobileNumberView;
import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dto.CustomerDTO;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import com.javaschool.microecare.utils.EntityActions;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("${endpoints.tvpp.contracts.controller_path}")
@PropertySource("messages.properties")
public class ContractsPageTVPPController {
    @Value("${directory.templates.tvpp.contracts}")
    private String templateFolder;
    @Value("${endpoints.tvpp.contracts.controller_path}")
    private String controllerPath;
    @Value("${endpoints.tvpp.contracts.path.customer}")
    private String contractCustomerPagePath;
    @Value("${endpoints.tvpp.contracts.path.tariff_number}")
    private String contractTariffPagePath;
    @Value("${endpoints.tvpp.contracts.path.options}")
    private String contractOptionsPagePath;
    @Value("${endpoints.tvpp.contracts.path.overview}")
    private String contractOverviewPagePath;

    @Resource(name = "sessionScopedContractDTO")
    ContractDTO sessionScopedContractDTO;

    @Resource(name = "sessionScopedContractView")
    ContractView sessionScopedContractView;

    private final String ENTITY_NAME = "Contract";

    private boolean successfulAction = false;
    private long successId;
    private EntityActions action;
    private boolean viewDetails = false;
    private String errorMessage;


    private final ContractsService contractsService;
    private final CommonEntityService commonEntityService;
    private final MobileNumbersService mobileNumbersService;
    private final TariffsService tariffsService;
    private final OptionsService optionsService;
    private final CustomersService customersService;


    public ContractsPageTVPPController(ContractsService contractsService, CommonEntityService commonEntityService, MobileNumbersService mobileNumbersService, TariffsService tariffsService, OptionsService optionsService, CustomersService customersService) {
        this.contractsService = contractsService;
        this.commonEntityService = commonEntityService;
        this.mobileNumbersService = mobileNumbersService;
        this.tariffsService = tariffsService;
        this.optionsService = optionsService;
        this.customersService = customersService;
    }


    @ModelAttribute
    public void setCommonAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
        model.addAttribute("contractCustomerPath", controllerPath + contractCustomerPagePath);
        model.addAttribute("tariffNumberPath", controllerPath + contractTariffPagePath);
        model.addAttribute("contractOptionsPath", controllerPath + contractOptionsPagePath);
        model.addAttribute("contractOverviewPath", controllerPath + contractOverviewPagePath);
        model.addAttribute("submitPath", controllerPath + contractOverviewPagePath);
        model.addAttribute("contractDTO", sessionScopedContractDTO);


    }

    private void setAllContractsModel(Model model) {
        model.addAttribute("contracts", contractsService.getAllContractViews());
        if (successfulAction) {
            commonEntityService.setSuccessfulActionModel(model, ENTITY_NAME, action, successId);
        }
        if (viewDetails) {
            model.addAttribute("viewContractDetails", true);
            model.addAttribute("displayedContract", sessionScopedContractView);
            viewDetails = false;
        }
        if (errorMessage != null) {
            commonEntityService.setErrorModel(model, ENTITY_NAME, errorMessage, action);
        }

    }

    @GetMapping
    public String getContractsPage(Model model, @RequestParam(required = false) Boolean cancel) {
        setAllContractsModel(model);
        successfulAction = false;
        errorMessage = null;
        action = null;
        viewDetails = false;
        if (cancel != null && cancel) {
            contractsService.resetContractDTO(sessionScopedContractDTO);
        }
        return templateFolder + "contracts";
    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String startNewContractFlow(TariffAndNumberDTO tariffAndNumberDTO, Model model) {
        return "redirect:" + controllerPath + contractCustomerPagePath;
    }

    @GetMapping("${endpoints.tvpp.contracts.path.customer}")
    public String showContractCustomerPage(ContractCustomerDTO contractCustomerDTO, Model model) {
        model.addAttribute("dataSubmitted", false);
        model.addAttribute("allCustomers", customersService.getAllCustomerViews());
        return templateFolder + "contract_customer";
    }

    @PostMapping("${endpoints.tvpp.contracts.path.customer}")
    public String postCustomerData(@Valid ContractCustomerDTO contractCustomerDTO, BindingResult result, Model model) {
        model.addAttribute("dataSubmitted", true);
        if (result.hasErrors()) {
            model.addAttribute("contractDTO", sessionScopedContractDTO);
            return templateFolder + "contract_customer";
        }
        sessionScopedContractDTO.setCustomerID(contractCustomerDTO.getCustomerID());
        return "redirect:" + controllerPath + contractTariffPagePath;
    }

    @GetMapping("${endpoints.tvpp.contracts.path.tariff_number}")
    public String showTariffAndNumberPage(TariffAndNumberDTO tariffAndNumberDTO, Model model) {
        model.addAttribute("dataSubmitted", false);
        int randomNumber = mobileNumbersService.getRandomNumber();
        MobileNumber mobileNumber = new MobileNumber(randomNumber);
        //todo: проверить что конструктор работает:
        model.addAttribute("mobileNumberView", new MobileNumberView(randomNumber));
        model.addAttribute("randomMobileNumber", randomNumber);
        model.addAttribute("allTariffs", tariffsService.getAllTariffViews());
        return templateFolder + "tariff_number";
    }


    @PostMapping("${endpoints.tvpp.contracts.path.tariff_number}")
    public String postTariffAndNumberData(@Valid TariffAndNumberDTO tariffAndNumberDTO, BindingResult result, Model model) {
        model.addAttribute("dataSubmitted", true);
        if (result.hasErrors()) {
            model.addAttribute("contractDTO", sessionScopedContractDTO);

            return templateFolder + "tariff_number";
        }
        sessionScopedContractDTO.setTariffID(tariffAndNumberDTO.getTariffID());
        sessionScopedContractDTO.setMobileNumber(tariffAndNumberDTO.getMobileNumber());
        return "redirect:" + controllerPath + contractOptionsPagePath;


    }

    @GetMapping("${endpoints.tvpp.contracts.path.options}")
    public String showContractOptionsPage(OptionListDTO optionListDTO, Model model) {
        model.addAttribute("allOptions", optionsService.getAllOptionViews());
        model.addAttribute("overviewPath", contractOverviewPagePath);

        return templateFolder + "contract_options";
    }

    @PostMapping("${endpoints.tvpp.contracts.path.options}")
    public String postContractOptions(@Valid OptionListDTO optionListDTO, BindingResult result, Model model) {
        model.addAttribute("dataSubmitted", true);
        if (result.hasErrors()) {
            model.addAttribute("contractDTO", sessionScopedContractDTO);
            return templateFolder + "contract_options";
        }
        sessionScopedContractDTO.setOptionIDs(optionListDTO.getOptionIDs());
        return "redirect:" + controllerPath + contractOverviewPagePath;


    }

    @GetMapping("${endpoints.tvpp.contracts.path.overview}")
    public String showContractOverviewPage(Model model) {
        ContractView contractView = contractsService.getContractViewFromDTO(sessionScopedContractDTO);

        model.addAttribute("customerFirstName", contractView.getCustomerView().getPersonalDataView().getFirstName());
        model.addAttribute("customerLastName", contractView.getCustomerView().getPersonalDataView().getLastName());
        model.addAttribute("customerPersonalData", contractView.getCustomerView().getPersonalDataView());
        model.addAttribute("tariffName", contractView.getTariffName());
        model.addAttribute("optionNames", contractView.getOptionNames());
        model.addAttribute("mobileNumberView", contractView.getNumberView());

        return templateFolder + "contract_overview";
    }

    @PostMapping("${endpoints.tvpp.contracts.path.overview}")
    public String saveNewCustomer(Model model) {
        action = EntityActions.CREATE;
        try {
            Contract contract = contractsService.saveNewContract(sessionScopedContractDTO);
            contractsService.resetContractDTO(sessionScopedContractDTO);
            successfulAction = true;
           // successActionName = "created";
            successId = contract.getId();
            return "redirect:" + controllerPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            return templateFolder + "contract_overview";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteContract(@PathVariable("id") int id, Model model) {
        action = EntityActions.DELETE;
        try {
            contractsService.deleteContract(id);
            successfulAction = true;
           // successActionName = "deleted";
            successId = id;
        } catch (RuntimeException e) {
            //todo: add error popup
        }
        return "redirect:" + controllerPath;
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") int id, Model model) {
        Contract contract = contractsService.getContract(id);
        ContractView contractView = new ContractView(contract);
        TariffView tariffView = new TariffView(contract.getTariff());
        Set<String> optionViews = optionsService.getOptionDescriptions(contract.getOptions());
        model.addAttribute("id", id);
        model.addAttribute("customerData", contractView.getCustomerView());
        model.addAttribute("mobileNumber", contractView.getNumberView());
        model.addAttribute("tariffView", tariffView.getShortTariffView());
        model.addAttribute("optionViews", optionViews);


        return templateFolder + "contract_details";
    }

}
