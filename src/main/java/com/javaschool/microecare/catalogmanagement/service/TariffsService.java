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

/**
 * Service to manage Tariff entities
 */
@PropertySource("messages.properties")
@Service
public class TariffsService {

    final TariffsRepo tariffRepo;
    final CommonEntityService commonEntityService;

    /**
     * Message text for non-unique name, returned at attempt to save Tariff with the same name as existing one.
     */
    @Value("${tariff.name.not_unique.msg}")
    String nonUniqueNameMessage;

    /**
     * Instantiates a new Tariffs service.
     *
     * @param tariffRepo          the tariff repo
     * @param commonEntityService is used to manage features common for all entities
     */
    public TariffsService(TariffsRepo tariffRepo, CommonEntityService commonEntityService) {
        this.tariffRepo = tariffRepo;
        this.commonEntityService = commonEntityService;
    }

    /**
     * Gets all tariff views sorted.
     *
     * @return the all tariff views
     */
    public List<TariffView> getAllTariffViews() {
        return tariffRepo.findAll().stream()
                .map(TariffView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Gets tariff.
     *
     * @param id the id
     * @return the tariff
     * @throws EntityNotFoundInDBException if tariff isn't found by id
     */
    public Tariff getTariff(long id) {
        return tariffRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Tariff"));
    }


    /**
     * Save new tariff using TariffDTO.
     *
     * @param tariffDTO the tariff dto
     * @return the tariff
     * @throws com.javaschool.microecare.utils.EntityCannotBeSavedException if tariffName provided in tariffDTO is not unique
     */
    public Tariff saveNewTariff(TariffDTO tariffDTO) {
        Tariff tariff = new Tariff(tariffDTO);
        try {
            return tariffRepo.save(tariff);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Tariff", "Key (tariff_name)", nonUniqueNameMessage);
        }
    }

    /**
     * Update existing tariff using TariffDTO.
     *
     * @param tariffDTO the tariff dto
     * @return the tariff
     * @throws com.javaschool.microecare.utils.EntityCannotBeSavedException if tariffName provided in tariffDTO is not unique
     */
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

    /**
     * Delete tariff.
     *
     * @param id the id
     */
    public void deleteTariff(long id) {
        tariffRepo.deleteById(id);
    }



}
