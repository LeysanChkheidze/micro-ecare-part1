package com.javaschool.microecare.contractmanagement.service;

import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.commonentitymanagement.dao.EntityNotFoundInDBException;
import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import com.javaschool.microecare.contractmanagement.repository.MobileNumbersRepo;
import com.javaschool.microecare.contractmanagement.viewmodel.MobileNumberView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MobileNumbersService {

    private final MobileNumbersRepo mobileNumbersRepo;

    public MobileNumbersService(MobileNumbersRepo mobileNumbersRepo) {
        this.mobileNumbersRepo = mobileNumbersRepo;
    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(10000000);
    }

    public List<MobileNumberView> getAllNumbers() {
        return mobileNumbersRepo.findAll().stream()
                .map(MobileNumberView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public MobileNumber getMobileNumber(long id) {
        return mobileNumbersRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Mobile number"));
    }


}
