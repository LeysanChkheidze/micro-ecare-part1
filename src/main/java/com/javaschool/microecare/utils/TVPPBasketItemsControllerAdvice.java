package com.javaschool.microecare.utils;

import com.javaschool.microecare.ordermanagement.TVPPBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class TVPPBasketItemsControllerAdvice {

    @Autowired
    TVPPBasket TVPPBasket;

    @ModelAttribute
    public void setBasketItemsToModel(Model model) {
        int items = TVPPBasket.getNumberOfOrders();
        model.addAttribute("basketItems", items);
    }
}
