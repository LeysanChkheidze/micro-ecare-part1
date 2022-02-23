package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.OptionListDTO;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.catalogmanagement.repository.OptionsRepo;
import com.javaschool.microecare.catalogmanagement.repository.TariffsRepo;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.commonentitymanagement.dao.EntityCannotBeSavedException;
import com.javaschool.microecare.commonentitymanagement.dao.EntityNotFoundInDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service to manage Tariff entities
 */
@PropertySource("messages.properties")
@Service
public class TariffsService {

    final TariffsRepo tariffRepo;
    final OptionsRepo optionsRepo;
    final CommonEntityService commonEntityService;
    final OptionsService optionsService;

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
    public TariffsService(TariffsRepo tariffRepo, CommonEntityService commonEntityService, OptionsService optionsService,
                          OptionsRepo optionsRepo) {
        this.tariffRepo = tariffRepo;
        this.commonEntityService = commonEntityService;
        this.optionsService = optionsService;
        this.optionsRepo = optionsRepo;
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
     * @throws EntityCannotBeSavedException if tariffName provided in tariffDTO is not unique
     */
    public Tariff saveNewTariff(TariffDTO tariffDTO) {
        Tariff tariff = new Tariff(tariffDTO);
        try {
            return commonEntityService.saveWithUpdateTime(tariff, tariffRepo);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Tariff", "Key (tariff_name)", nonUniqueNameMessage);
        }
    }

    /**
     * Update existing tariff using TariffDTO.
     *
     * @param tariffDTO the tariff dto
     * @return the tariff
     * @throws EntityCannotBeSavedException if tariffName provided in tariffDTO is not unique
     */
    public Tariff updateTariff(long id, TariffDTO tariffDTO) {
        Tariff tariff = tariffRepo.getById(id);
        tariff.setTariffName(tariffDTO.getTariffName().trim());
        tariff.setMonthlyPrice(tariffDTO.getMonthlyPrice());
        tariff.setTariffDescription(tariffDTO.getTariffDescription().trim());
        tariff.setUpdateTime(LocalDateTime.now());

        try {
            return commonEntityService.saveWithUpdateTime(tariff, tariffRepo);
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


    /**
     * Updates the list of compatible options for tariff
     *
     * @param id            tariff id which compatible options should be updated
     * @param optionListDTO optionListDTO to which compatible options should be set
     * @return updated tariff
     */
    public Tariff updateCompatibleOptionsInTariff(long id, OptionListDTO optionListDTO) {
        Tariff tariff = tariffRepo.getById(id);
        Set<Option> newOptions = getOptionsSetByIDs(optionListDTO.getOptionIDs());


        tariff.setCompatibleOptions(newOptions);
        try {
            return commonEntityService.saveWithUpdateTime(tariff, tariffRepo);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Tariff", "Key (tariff_name)", nonUniqueNameMessage);
        }
    }

    /**
     * Returns set of options by provided set of option id's
     *
     * @param optionIDs option id's to find options
     * @return set of found options
     */
    public Set<Option> getOptionsSetByIDs(Set<Long> optionIDs) {
        if (null == optionIDs || optionIDs.size() == 0) {
            return Collections.emptySet();
        }
        return optionIDs.stream()
                //todo: кешировать опции, чтобы не лазить в базу за ними все время
                .map(optionsRepo::getById)
                .collect(Collectors.toSet());
    }


}
