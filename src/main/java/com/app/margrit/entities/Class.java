package com.app.margrit.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Class {

    @Id
    @NotNull
    private String className;

    @OneToMany
    private List<Method> methods;

    protected Class(){}

    public Class(String className){
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public void addMethod(Method method){
        if(methods == null) {
            methods = new ArrayList<>();
        }

        methods.add(method);
    }

    @Override
    public String toString() {
        return "Class{" +
                "className='" + className + '\'' +
                ", methods=" + methods +
                '}';
    }
}
