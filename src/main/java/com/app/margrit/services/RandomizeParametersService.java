package com.app.margrit.services;

import com.app.margrit.dto.ParameterDto;
import com.app.margrit.entities.Parameter;
import com.app.margrit.repositories.ParameterRepository;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RandomizeParametersService {

    @Autowired
    private ParameterRepository parameterRepository;

    public void randomizeParameters(List<ParameterDto> parametersDto){
        List<Parameter> parameters = new ArrayList<>();

        for (ParameterDto parameterDto : parametersDto){
            Parameter parameter = randomizeParameter(parameterRepository.findOne(parameterDto.getId()));
            parameters.add(parameter);
        }

        parameterRepository.save(parameters);
    }

    private Parameter randomizeParameter(Parameter parameter) {
        parameter.setParameterValue(null);

        DataFactory dataFactory = new DataFactory();


        return parameter;
    }
}
