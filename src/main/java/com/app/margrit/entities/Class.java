package com.app.margrit.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Class {

    @Id
    private String className;

    @OneToMany
    private List<Method> methods;

    public Class(String className){
        this.className = className;
    }

    @Override
    public String toString() {
        return "Class{" +
                "className='" + className + '\'' +
                ", methods=" + methods +
                '}';
    }
}
