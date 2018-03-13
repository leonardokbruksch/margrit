package com.app.margrit.dto;

import com.app.margrit.entities.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MethodDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String expectedReturnValue;

    @OneToMany
    private List<ParameterDto> parameters;

    public List<ParameterDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDto> parameters) {
        this.parameters = parameters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpectedReturnValue() {
        return expectedReturnValue;
    }

    public void setExpectedReturnValue(String expectedReturnValue) {
        this.expectedReturnValue = expectedReturnValue;
    }
}
