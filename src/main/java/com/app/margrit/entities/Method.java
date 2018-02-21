package com.app.margrit.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Arrays;
import java.util.List;

@Entity
public class Method {

    @Id
    private Long id;

    private String methodName;

    @OneToMany
    private List<Parameter> parameters;

    private String returnType;

    public Method(String methodName){
        this.methodName = methodName;
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
