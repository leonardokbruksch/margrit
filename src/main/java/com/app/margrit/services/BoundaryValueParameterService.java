package com.app.margrit.services;

import com.app.margrit.entities.Method;
import com.app.margrit.entities.Parameter;
import com.app.margrit.entities.RandomOptions;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoundaryValueParameterService {

    public String getBellowLimitParameters(RandomOptions randomOptions, Method method){

        int numberMinValue = randomOptions.getNumberMinLength();
        int stringMinLength = randomOptions.getStringMinLength();

        List<String> listOfParameterValues = new ArrayList<>();

        for (Parameter parameter : method.getParameters()){

            if (parameter.isString()){
                listOfParameterValues.add(getStringBellowLimit(stringMinLength, parameter.getParameterValue()));
            } else if (parameter.isNumber()){
                listOfParameterValues.add(String.valueOf(numberMinValue - 1));
            } else {
                listOfParameterValues.add(parameter.getParameterValue());
            }

        }

        return String.join(" , ", listOfParameterValues);
    }

    private String getStringBellowLimit(int stringMinLength, String parameterValue){

        if (stringMinLength >= 1){
            int bellowLimitLength = stringMinLength - 1;
            return '"' + parameterValue.substring(0, bellowLimitLength) + '"';
        } else {
            // String Min Length is zero -> Using empty String
            parameterValue = "";
            return '"' + parameterValue + '"';
        }

    }

    public String getAboveLimitParameters(RandomOptions randomOptions, Method method){

        int numberMaxValue = randomOptions.getNumberMaxLength();
        int stringMaxLength = randomOptions.getStringMaxLength();

        List<String> listOfParameterValues = new ArrayList<>();

        for (Parameter parameter : method.getParameters()){

            if (parameter.isString()){
                listOfParameterValues.add(getStringAboveLimit(stringMaxLength, parameter.getParameterValue()));
            } else if (parameter.isNumber()){
                listOfParameterValues.add(String.valueOf(numberMaxValue + 1));
            } else {
                listOfParameterValues.add(parameter.getParameterValue());
            }

        }

        return String.join(" , ", listOfParameterValues);
    }

    private String getStringAboveLimit(int stringMaxLength, String parameterValue) {
        if (stringMaxLength >= 0){
            int aboveLimitLength = stringMaxLength + 1;
            return '"' + RandomStringUtils.randomAscii(aboveLimitLength) + '"';
        } else {
            return '"' + parameterValue + '"';
        }
    }
}
