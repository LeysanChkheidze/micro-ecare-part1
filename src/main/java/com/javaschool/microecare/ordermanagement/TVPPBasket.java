package com.javaschool.microecare.ordermanagement;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
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
