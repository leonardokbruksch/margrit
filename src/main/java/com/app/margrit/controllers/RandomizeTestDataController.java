package com.app.margrit.controllers;

import com.app.margrit.dto.ClassDto;
import com.app.margrit.dto.MethodDto;
import com.app.margrit.dto.ParameterDto;
import com.app.margrit.entities.Class;
import com.app.margrit.entities.Parameter;
import com.app.margrit.repositories.ClassRepository;
import com.app.margrit.repositories.MethodRepository;
import com.app.margrit.repositories.ParameterRepository;
import com.app.margrit.services.RandomizeParametersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/random")
public class RandomizeTestDataController extends SubmitTestDataController{

    @Autowired
    private RandomizeParametersService randomizeParametersService;

    @PostMapping("/randomizeTestData")
    public ResponseEntity<?> randodmizeTestData(@RequestBody List<ClassDto> classesDto, HttpServletRequest request) throws JsonProcessingException {

        List<Class> classes = updateClasses(classesDto);

        String classesAsJson = new ObjectMapper().writeValueAsString(classes);
        request.getSession().setAttribute("classesAsJson", classesAsJson);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    protected void updateParameters(MethodDto methodDto) {
        randomizeParametersService.randomizeParameters(methodDto.getParameters());
    }

}
