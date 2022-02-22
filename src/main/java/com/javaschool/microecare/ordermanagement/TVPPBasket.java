package com.javaschool.microecare.ordermanagement;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Configuration
@SessionScope
public class TVPPBasket {

    List<AbstractOrder> ordersInBasket = new ArrayList<>();

    public TVPPBasket() {
    }



    public void add(AbstractOrder order) {
        ordersInBasket.add(order);
    }

    public int getNumberOfOrders(){
        return ordersInBasket.size();
    }

    public List<AbstractOrder> getOrdersInBasket() {
        return ordersInBasket;
    }

    public void setOrdersInBasket(List<AbstractOrder> ordersInBasket) {
        this.ordersInBasket = ordersInBasket;
    }
}
