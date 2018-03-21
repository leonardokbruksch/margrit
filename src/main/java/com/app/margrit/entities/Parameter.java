package com.app.margrit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Parameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String parameterType;

    private String parameterName;

    private String parameterValue;

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Parameter(String parameterName, String parameterType){
        this.parameterType = parameterType;
        this.parameterName = parameterName;
    }

    protected Parameter(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", parameterType='" + parameterType + '\'' +
                ", parameterName='" + parameterName + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                '}';
    }

    public String getParameterValueForGeneration(){
        if (parameterType.equalsIgnoreCase("String")){
            return '"' + parameterValue + '"';
        } else {
            return parameterValue;
        }
    }

}
