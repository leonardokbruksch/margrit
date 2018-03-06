package com.app.margrit.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class Method {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String methodName;

    @OneToMany
    private List<Parameter> parameters;

    private String returnType;

    private String expectedReturnValue;

    public Method(String methodName){
        this.methodName = methodName;
    }

    protected Method(){}

    public String getExpectedReturnValue() {
        return expectedReturnValue;
    }

    public void setExpectedReturnValue(String expectedReturnValue) {
        this.expectedReturnValue = expectedReturnValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "Method{" +
                "id=" + id +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + parameters +
                ", returnType='" + returnType + '\'' +
                '}';
    }

}
