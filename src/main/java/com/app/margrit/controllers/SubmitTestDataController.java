package com.app.margrit.controllers;


import com.app.margrit.dto.ClassDto;
import com.app.margrit.dto.DTO;
import com.app.margrit.dto.MethodDto;
import com.app.margrit.dto.ParameterDto;
import com.app.margrit.entities.Class;
import com.app.margrit.entities.Method;
import com.app.margrit.entities.Parameter;
import com.app.margrit.repositories.ClassRepository;
import com.app.margrit.repositories.MethodRepository;
import com.app.margrit.repositories.ParameterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SubmitTestDataController {

    @Autowired
    protected ClassRepository classRepository;

    @Autowired
    protected MethodRepository methodRepository;

    @Autowired
    protected ParameterRepository parameterRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/submitClassesTestData")
    public ResponseEntity<?> submitClassesTestData(@RequestBody List<ClassDto> classesDto){
        for (ClassDto classDto : classesDto){
            updateMethods(classDto);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    protected void updateMethods(ClassDto classDto) {
        List<Method> methods = new ArrayList<>();

        for (MethodDto methodDto : classDto.getMethods()){

            updateParameters(methodDto);

            Method method = methodRepository.findOne(methodDto.getId());
            modelMapper.map(methodDto, method);
            methods.add(method);
        }

        methodRepository.save(methods);
    }

    protected void updateParameters(MethodDto methodDto) {
        List<Parameter> parameters = new ArrayList<>();

        for (ParameterDto parameterDto : methodDto.getParameters()){
            Parameter parameter = parameterRepository.findOne(parameterDto.getId());
            modelMapper.map(parameterDto, parameter);
            parameters.add(parameter);
        }

        parameterRepository.save(parameters);
    }
}
