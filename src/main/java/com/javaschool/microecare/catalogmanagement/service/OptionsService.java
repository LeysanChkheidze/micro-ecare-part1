package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.catalogmanagement.repository.OptionsRepo;
import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.catalogmanagement.viewmodel.ShortOptionView;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.utils.EntityNotFoundInDBException;
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
 * Service to manage Option entities
 */
@PropertySource("messages.properties")
@Service
public class OptionsService {
    final CommonEntityService commonEntityService;
    final OptionsRepo optionsRepo;

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
     */
    public OptionsService(CommonEntityService commonEntityService, OptionsRepo optionsRepo) {
        this.commonEntityService = commonEntityService;
        this.optionsRepo = optionsRepo;
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
     * @throws com.javaschool.microecare.utils.EntityCannotBeSavedException if optionName provided in OptionDTO is not unique
     */
    public Option saveNewOption(OptionDTO optionDTO) {
        Option option = new Option(optionDTO);
        try {
            return optionsRepo.save(option);
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
     * @throws com.javaschool.microecare.utils.EntityCannotBeSavedException if optionName provided in OptionDTO is not unique
     */
    public Option updateOption(long id, OptionDTO optionDTO) {
        Option option = optionsRepo.getById(id);
        option.setOptionName(optionDTO.getOptionName());
        option.setOneTimePrice(optionDTO.getOneTimePrice());
        option.setMonthlyPrice(option.getMonthlyPrice());
        option.setOptionDescription(optionDTO.getOptionDescription());
        option.setUpdateTime(LocalDateTime.now());

        try {
            return optionsRepo.save(option);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Option", "Key (option_name)", nonUniqueNameMessage);
        }
    }

    /**
     * Delete option.
     *
     * @param id the id of option to delete
     */
    public int deleteOption(long id) {
        //TODO: Боря-Боря, что тут происходит, почему меня выкидывает из метода сразу после optionsRepo.deleteById(id);
        // и я даже не захожу в вывод на печать???
        int deletedRows = optionsRepo.deleteById(id);
        System.out.println("deleted " + deletedRows);
        return deletedRows;
    }


    /**
     * Returns set of options by provided set of option id's
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


}
