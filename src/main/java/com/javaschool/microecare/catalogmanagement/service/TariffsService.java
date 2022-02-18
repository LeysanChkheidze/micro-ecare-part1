package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.catalogmanagement.repository.TariffRepo;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import com.javaschool.microecare.utils.EntityNotFoundInDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("messages.properties")
@Service
public class TariffsService {
    final TariffRepo tariffRepo;
    final CommonEntityService commonEntityService;

    @Value("${general.unknown_field.constraint_violation.msg}")
    String constraintViolationMessage;
    @Value("${tariff.name.not_unique.msg}")
    String nonUniqueNameMessage;

    public TariffsService(TariffRepo tariffRepo, CommonEntityService commonEntityService) {
        this.tariffRepo = tariffRepo;
        this.commonEntityService = commonEntityService;
    }

    public List<TariffView> getAllTariffViews() {
        return tariffRepo.findAll().stream()
                .map(TariffView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public Tariff getTariff(long id) {
        return tariffRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Tariff"));
    }


    public Tariff saveNewTariff(TariffDTO tariffDTO) {
        Tariff tariff = new Tariff(tariffDTO);
        try {
            return tariffRepo.save(tariff);
        } catch (DataIntegrityViolationException e) {
            throw createSavingEntityException(e);
        }
    }

    public Tariff updateTariff(long id, TariffDTO tariffDTO) {
        Tariff tariff = tariffRepo.getById(id);
        tariff.setTariffName(tariffDTO.getTariffName().trim());
        tariff.setMonthlyPrice(tariffDTO.getMonthlyPrice());
        tariff.setTariffDescription(tariffDTO.getTariffDescription().trim());
        tariff.setUpdateTime(LocalDateTime.now());

        try {
            return tariffRepo.save(tariff);
        } catch (DataIntegrityViolationException e) {
            throw createSavingEntityException(e);
        }
    }

    public void deleteTariffById(long id) {
        tariffRepo.deleteById(id);
    }


    private EntityCannotBeSavedException createSavingEntityException(DataIntegrityViolationException e) {
        String errorMessage = commonEntityService.resolveIntegrityViolationMessage(e, "Key (tariff_name)", nonUniqueNameMessage);
        return new EntityCannotBeSavedException("Tariff", errorMessage);
    }

}
