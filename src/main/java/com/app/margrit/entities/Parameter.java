package com.app.margrit.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parameter {

    @Id
    private Long id;

    private String parameterType;

    private String parameterName;

    public Parameter(String parameterType, String parameterName){
        this.parameterType = parameterType;
        this.parameterName = parameterName;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", parameterType='" + parameterType + '\'' +
                ", parameterName='" + parameterName + '\'' +
                '}';
    }
}
