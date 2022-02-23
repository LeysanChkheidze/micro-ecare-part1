package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.ordermanagement.AbstractOrder;
import com.javaschool.microecare.ordermanagement.TVPPBasket;
import com.javaschool.microecare.ordermanagement.service.BasketService;
import com.javaschool.microecare.commonentitymanagement.dao.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${endpoints.tvpp.basket.controller_path}")
@PropertySource("messages.properties")
public class BasketPageTVPPController {

    @Value("${directory.templates.tvpp.order_management}")
    private String templateFolder;
    @Value("${endpoints.tvpp.basket.controller_path}")
    private String controllerPath;
    @Value("${endpoints.tvpp.basket.confirmation}")
    private String confirmationPath;

    private Map<Long, String> resultMap = new HashMap<>();


    @Autowired
    final CommonEntityService commonEntityService;
    final BasketService basketService;

    public BasketPageTVPPController(CommonEntityService commonEntityService, BasketService basketService) {
        this.commonEntityService = commonEntityService;
        this.basketService = basketService;
    }

    @Lookup
    public TVPPBasket getBasket() {
        return null;
    }


    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
    }

    @GetMapping
    public String getBasketPage(Model model) {
        List<AbstractOrder> savedOrders = getBasket().getOrdersInBasket();
        model.addAttribute("orders", savedOrders);
        return templateFolder + "basket";
    }

    @PostMapping
    public String saveOrders(Model model) {

        if (basketService.validateBasket()) {
            try {
                resultMap = basketService.saveAllOrders();
                basketService.cleanBasket(getBasket());
                return "redirect:" + controllerPath + confirmationPath;
            } catch (EntityCannotBeSavedException e) {
                model.addAttribute("errorEntity", e.getEntityName());
                model.addAttribute("errorMessage", e.getMessage());
                return templateFolder + "basket";

            }
        }
        return templateFolder + "basket";
    }

    @GetMapping("${endpoints.tvpp.basket.confirmation}")
    public String showConfirmationPage(Model model) {
        Map<Long, String> modelResult = new HashMap<>(resultMap);
        model.addAttribute("resultMap", modelResult);
        resultMap.clear();
        return templateFolder + "confirmation";
    }
}