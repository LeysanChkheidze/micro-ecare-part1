package com.javaschool.microecare.ordermanagement.service;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dto.CustomerDTO;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import com.javaschool.microecare.ordermanagement.AbstractOrder;
import com.javaschool.microecare.ordermanagement.NewCustomerOrder;
import com.javaschool.microecare.ordermanagement.TVPPBasket;
import com.javaschool.microecare.ordermanagement.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasketService {

    //tODO: загадка! в дебаге я вижу поле ordersInBasket у корзины - нулл. но каким-то образом ордера находятся
    @Autowired
    TVPPBasket tvppBasket;

    final CustomerRepo customerRepo;
    final CustomersService customersService;

    public BasketService(CustomerRepo customerRepo, CustomersService customersService) {
        this.customerRepo = customerRepo;
        this.customersService = customersService;
    }


    public boolean validateBasket() {
        //TODO: implement validation
        return true;
    }

    public Map<Long, String> saveAllOrders() {
        Map<Long, String> savedEntities = new HashMap<>();
        for (AbstractOrder abstractOrder : tvppBasket.getOrdersInBasket()) {
            if (abstractOrder instanceof NewCustomerOrder) {
                NewCustomerOrder orderToSave = (NewCustomerOrder) abstractOrder;
                CustomerDTO customerDTO = orderToSave.getCustomerDTO();
                Customer newCustomer = new Customer(customerDTO);
                customersService.setInitialPassword(newCustomer);
                try {
                    Customer savedCustomer = customerRepo.save(newCustomer);
                    savedEntities.put(savedCustomer.getId(), "New customer");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return savedEntities;
    }

    public void cleanBasket(TVPPBasket basket) {
        basket.setOrdersInBasket(new ArrayList<>());
    }

}
