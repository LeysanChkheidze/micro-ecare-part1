package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.ordermanagement.AbstractOrder;
import com.javaschool.microecare.ordermanagement.TVPPBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${endpoints.tvpp.basket.controller_path}")
@PropertySource("messages.properties")
public class BasketPageTVPPController {

    @Value("${directory.templates.tvpp.tvpp_basket}")
    private String templateFolder;
    @Value("${endpoints.tvpp.basket.controller_path}")
    private String controllerPath;

    //todo: basket has @SessionScope, but is
    @Autowired
    TVPPBasket TVPPBasket;
    @Autowired
    final CommonEntityService commonEntityService;

    public BasketPageTVPPController(CommonEntityService commonEntityService) {
        this.commonEntityService = commonEntityService;
    }


    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
    }

    @GetMapping
    public String getBasketPage(Model model) {
        List<AbstractOrder> savedOrders = TVPPBasket.getOrdersInBasket();
        model.addAttribute("orders", savedOrders);
        return templateFolder + "basket";
    }

   /* private void setAllCustomersModel(Model model) {
        model.addAttribute("customers", customersService.getAllCustomerViews());
        if (successfulAction) {
            model.addAllAttributes(Map.of("successfulAction", true,
                    "successEntityName", "Customer",
                    "successAction", successActionName,
                    "successId", successId));
        }
    }*/


}
