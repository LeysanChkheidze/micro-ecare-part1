package com.javaschool.microecare.catalogmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.catalogmanagement.repository.OptionsRepo;
import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
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
public class OptionsService {
    final CommonEntityService commonEntityService;
    final OptionsRepo optionsRepo;

    @Value("${option.name.not_unique.msg}")
    String nonUniqueNameMessage;

    public OptionsService(CommonEntityService commonEntityService, OptionsRepo optionsRepo) {
        this.commonEntityService = commonEntityService;
        this.optionsRepo = optionsRepo;
    }

    public List<OptionView> getAllOptionViews() {
        return optionsRepo.findAll().stream()
                .map(OptionView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public Option getOption(long id) {
        return optionsRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Option"));
    }

    public Option saveNewOption(OptionDTO optionDTO) {
        Option option = new Option(optionDTO);
        try {
            return optionsRepo.save(option);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Option", "Key (option_name)", nonUniqueNameMessage);
        }
    }

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

    public void deleteOption(long id) {
        optionsRepo.deleteById(id);
    }


}
