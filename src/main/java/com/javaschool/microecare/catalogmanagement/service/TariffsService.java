package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.repository.TariffRepo;
import org.springframework.stereotype.Service;

@Service
public class TariffsService {
    final TariffRepo tariffRepo;


    public TariffsService(TariffRepo tariffRepo) {
        this.tariffRepo = tariffRepo;
    }
}
