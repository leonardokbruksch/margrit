package com.app.margrit.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class RandomOptions {

    @Id
    @NotNull
    private String optionName;

    private int numberMinLength;

    private int numberMaxLength;

    private int stringMinLength;

    private int stringMaxLength;

    private boolean testValueLimits;

    protected RandomOptions(){}

    public RandomOptions(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getNumberMinLength() {
        return numberMinLength;
    }

    public void setNumberMinLength(int numberMinLength) {
        this.numberMinLength = numberMinLength;
    }

    public int getNumberMaxLength() {
        return numberMaxLength;
    }

    public void setNumberMaxLength(int numberMaxLength) {
        this.numberMaxLength = numberMaxLength;
    }

    public int getStringMinLength() {
        return stringMinLength;
    }

    public void setStringMinLength(int stringMinLength) {
        this.stringMinLength = stringMinLength;
    }

    public int getStringMaxLength() {
        return stringMaxLength;
    }

    public void setStringMaxLength(int stringMaxLength) {
        this.stringMaxLength = stringMaxLength;
    }

    public boolean isTestValueLimits() {
        return testValueLimits;
    }

    public void setTestValueLimits(boolean testValueLimits) {
        this.testValueLimits = testValueLimits;
    }

    @Override
    public String toString() {
        return "RandomOptions{" +
                "optionName='" + optionName + '\'' +
                ", numberMinLength=" + numberMinLength +
                ", numberMaxLength=" + numberMaxLength +
                ", stringMinLength=" + stringMinLength +
                ", stringMaxLength=" + stringMaxLength +
                ", testValueLimits=" + testValueLimits +
                '}';
    }
}
