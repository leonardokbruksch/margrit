package com.app.margrit.services;

import com.app.margrit.dto.ParameterDto;
import com.app.margrit.entities.Parameter;
import com.app.margrit.entities.RandomOptions;
import com.app.margrit.repositories.ParameterRepository;
import com.app.margrit.repositories.RandomOptionsRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RandomizeParametersService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private RandomOptionsRepository randomOptionsRepository;

    private RandomOptions randomOptions;

    private Random random;

    final String AVAILABLE_CHARACTERS = "!#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    final String STRING_TYPE = "String";
    final String CHAR_TYPE = "char";
    final String BOOLEAN_TYPE = "boolean";
    final String INT_TYPE = "int";
    final String DOUBLE_TYPE = "double";

    public RandomizeParametersService(){
        random = new Random();
    }

    public void randomizeParameters(List<ParameterDto> parametersDto){
        setRandomOptionsObject();

        List<Parameter> parameters = new ArrayList<>();

        for (ParameterDto parameterDto : parametersDto){
            Parameter parameter = randomizeParameter(parameterRepository.findById(parameterDto.getId()).get());
            parameters.add(parameter);
        }

        parameterRepository.saveAll(parameters);
    }

    private void setRandomOptionsObject() {
        Optional<RandomOptions> random = randomOptionsRepository.findById("customOption");

        if (!random.isPresent()){
            randomOptions = randomOptionsRepository.findById("defaultOption").get();
        } else {
            randomOptions = random.get();
        }
    }

    private Parameter randomizeParameter(Parameter parameter) {

        String parameterType = parameter.getParameterType();

        if (parameterType.equalsIgnoreCase(STRING_TYPE)){
            parameter.setParameterValue(getRandomString());
        }
        else if (parameterType.equalsIgnoreCase(CHAR_TYPE)) {
            parameter.setParameterValue(getRandomChar());
        }
        else if (parameterType.equalsIgnoreCase(BOOLEAN_TYPE)) {
            parameter.setParameterValue(getRandomBoolean());
        }
        else if (parameterType.equalsIgnoreCase(INT_TYPE)) {
            parameter.setParameterValue(getRandomInteger());
        }
        else if (parameterType.equalsIgnoreCase(DOUBLE_TYPE)) {
            parameter.setParameterValue(getRandomDouble());
        }

        return parameter;
    }

    public String getRandomDouble(){
        double rangeMin = randomOptions.getNumberMinLength();
        double rangeMax = randomOptions.getNumberMaxLength();

        double randomDouble = rangeMin + (rangeMax - rangeMin) * random.nextDouble();

        return String.valueOf(randomDouble);
    }

    public String getRandomInteger() {
        int rangeMin = randomOptions.getNumberMinLength();
        int rangeMax = randomOptions.getNumberMaxLength();

        int randomInteger = random.nextInt((rangeMax - rangeMin) + 1) + rangeMin;

        return String.valueOf(randomInteger);
    }

    public String getRandomBoolean() {
        boolean randomBoolean = random.nextBoolean();

        return String.valueOf(randomBoolean);
    }

    public String getRandomChar() {
        char randomChar = AVAILABLE_CHARACTERS.charAt(random.nextInt(AVAILABLE_CHARACTERS.length()));

        return String.valueOf(randomChar);
    }

    public String getRandomString() {
        int minLength = randomOptions.getStringMinLength();
        int maxLength = randomOptions.getStringMaxLength();

        String randomString = RandomStringUtils.randomAscii(minLength, maxLength);
        
        return randomString;
    }
}
