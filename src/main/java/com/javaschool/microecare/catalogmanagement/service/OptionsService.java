package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.catalogmanagement.repository.OptionsRepo;
import com.javaschool.microecare.catalogmanagement.repository.TariffsRepo;
import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.catalogmanagement.viewmodel.ShortOptionView;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.commonentitymanagement.dao.EntityCannotBeSavedException;
import com.javaschool.microecare.commonentitymanagement.dao.EntityNotFoundInDBException;
import com.javaschool.microecare.contractmanagement.dao.Contract;
import com.javaschool.microecare.contractmanagement.service.ContractsService;
import com.javaschool.microecare.contractmanagement.viewmodel.ContractView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service to manage Option entities
 */
@PropertySource("messages.properties")
@Service
public class OptionsService {
    final CommonEntityService commonEntityService;
    final OptionsRepo optionsRepo;
    final TariffsRepo tariffsRepo;

    /**
     * Message text for non-unique name, returned at attempt to save Option with the same name as existing one.
     */
    @Value("${option.name.not_unique.msg}")
    String nonUniqueNameMessage;

    /**
     * Instantiates a new Options service.
     *
     * @param commonEntityService is used to manage features common for all entities
     * @param optionsRepo         the options repo
     * @param tariffsRepo         tariffs repo
     */
    public OptionsService(CommonEntityService commonEntityService, OptionsRepo optionsRepo,
                          TariffsRepo tariffsRepo) {
        this.commonEntityService = commonEntityService;
        this.optionsRepo = optionsRepo;
        this.tariffsRepo = tariffsRepo;
    }


    /**
     * Gets all option views as list sorted.
     *
     * @return the all option views
     */
    public List<OptionView> getAllOptionViews() {
        return optionsRepo.findAll().stream()
                .map(OptionView::new)
                .sorted()
                .collect(Collectors.toList());
    }


    /**
     * Gets all option short views as list sorted.
     *
     * @return the all option short views
     */
    public List<ShortOptionView> getAllOptionsShortViews() {
        return optionsRepo.findAll().stream()
                .map(ShortOptionView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<ShortOptionView> getShortViews(Set<Option> options) {
        if (options == null || options.size() == 0) {
            return Collections.emptySet();
        }

        return options.stream()
                .map(ShortOptionView::new)
                .collect(Collectors.toSet());
    }

    public OptionView getView(Option option) {
        return new OptionView(option);
    }

    public Set<OptionView> getViews(Set<Option> options) {
        if (options == null || options.size() == 0) {
            return Collections.emptySet();
        }

        return options.stream()
                .map(OptionView::new)
                .collect(Collectors.toSet());
    }

    public Set<OptionView> getViewsByIDs(Set<Long> optionIDs) {
        if (optionIDs == null || optionIDs.size() == 0) {
            return Collections.emptySet();
        }
        return optionIDs.stream()
                .map(id -> getOption(id))
                .map(OptionView::new)
                .collect(Collectors.toSet());
    }

    public Set<String> getOptionDescriptions(Set<Option> options) {
        if (options == null || options.size() == 0) {
            return Collections.emptySet();
        }

        return options.stream()
                .map(this::getOptionDescription)
                .collect(Collectors.toSet());
    }

    private String getOptionDescription(Option option) {
        return option.getOptionName() + ",\none-time price: " + option.getOneTimePrice() + "EUR\n monthly price: "
                + option.getMonthlyPrice() + "EUR\n description:\n" +
                option.getOptionDescription();

    }


    /**
     * Gets option.
     *
     * @param id the id
     * @return the option
     * @throws EntityNotFoundInDBException if option isn't found by id
     */
    public Option getOption(long id) {
        return optionsRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Option"));
    }

    /**
     * Save new option using OptionDTO.
     *
     * @param optionDTO the option dto
     * @return the option
     * @throws EntityCannotBeSavedException if optionName provided in OptionDTO is not unique
     */
    public Option saveNewOption(OptionDTO optionDTO) {
        Option option = new Option(optionDTO);
        try {
            return commonEntityService.saveWithUpdateTime(option, optionsRepo);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Option", "Key (option_name)", nonUniqueNameMessage);
        }
    }

    /**
     * Update option using OptionDTO. Updates updateTime as well.
     *
     * @param id        the id
     * @param optionDTO the option dto
     * @return the option
     * @throws EntityCannotBeSavedException if optionName provided in OptionDTO is not unique
     */
    public Option updateOption(long id, OptionDTO optionDTO) {
        Option option = optionsRepo.getById(id);
        option.setOptionName(optionDTO.getOptionName());
        option.setOneTimePrice(optionDTO.getOneTimePrice());
        option.setMonthlyPrice(option.getMonthlyPrice());
        option.setOptionDescription(optionDTO.getOptionDescription());
        option.setUpdateTime(LocalDateTime.now());

        try {
            return commonEntityService.saveWithUpdateTime(option, optionsRepo);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Option", "Key (option_name)", nonUniqueNameMessage);
        }
    }

    /**
     * Removes the option from tariff-option relationship, saves tariffs and deletes option
     *
     * @param id the id of option to delete
     */
    @Transactional
    public void deleteOption(long id) {
        Option option = optionsRepo.getById(id);
        for (Tariff relatedTariff : option.getCompatibleTariffs()) {
            relatedTariff.getCompatibleOptions().remove(option);
            commonEntityService.saveWithUpdateTime(relatedTariff, tariffsRepo);
        }
        optionsRepo.deleteById(id);
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
                .map(id -> optionsRepo.getById(id))
                .collect(Collectors.toSet());

    }

    public Set<String> getOptionNames(Set<Long> optionIDs) {
        if (null == optionIDs || optionIDs.size() == 0) {
            return Collections.emptySet();
        }

        return optionIDs.stream()
                .map(id -> optionsRepo.getById(id))
                .map(Option::getOptionName)
                .collect(Collectors.toSet());
    }




}
