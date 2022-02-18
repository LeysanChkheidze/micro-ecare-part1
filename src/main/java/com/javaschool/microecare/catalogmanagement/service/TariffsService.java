package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.catalogmanagement.repository.TariffsRepo;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
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
    final TariffsRepo tariffRepo;
    final CommonEntityService commonEntityService;

    @Value("${tariff.name.not_unique.msg}")
    String nonUniqueNameMessage;

    public TariffsService(TariffsRepo tariffRepo, CommonEntityService commonEntityService) {
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
            throw commonEntityService.createSavingEntityException(e, "Tariff", "Key (tariff_name)", nonUniqueNameMessage);
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
            throw commonEntityService.createSavingEntityException(e, "Tariff", "Key (tariff_name)", nonUniqueNameMessage);
        }
    }

    public void deleteTariff(long id) {
        tariffRepo.deleteById(id);
    }



}
