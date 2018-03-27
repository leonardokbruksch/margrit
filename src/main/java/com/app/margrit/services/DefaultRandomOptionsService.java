package com.app.margrit.services;

import com.app.margrit.entities.RandomOptions;
import com.app.margrit.repositories.RandomOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRandomOptionsService {

    @Autowired
    private RandomOptionsRepository randomOptionsRepository;

    public void createDefaultOptions() {
        RandomOptions defaultOption = new RandomOptions("defaultOption");

        defaultOption.setNumberMinLength(1);
        defaultOption.setNumberMaxLength(30);

        defaultOption.setStringMinLength(1);
        defaultOption.setStringMaxLength(30);

        defaultOption.setTestValueLimits(false);

        randomOptionsRepository.save(defaultOption);
    }
}
