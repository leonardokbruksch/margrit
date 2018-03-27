package com.app.margrit.dto;

import com.app.margrit.entities.Method;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ClassDto {

    @Id
    @NotNull
    private String className;

    @OneToMany
    private List<MethodDto> methods;

    private boolean isValid;

    private String packageName;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<MethodDto> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDto> methods) {
        this.methods = methods;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
