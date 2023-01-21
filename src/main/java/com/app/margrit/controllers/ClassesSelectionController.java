package com.app.margrit.controllers;

import com.app.margrit.dto.ClassDto;
import com.app.margrit.entities.Class;
import com.app.margrit.repositories.ClassRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClassesSelectionController {

    @Autowired
    private ClassRepository classRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    @RequestMapping("/classesTable")
    public ResponseEntity<?> getUploadClassesNamesJson() throws JsonProcessingException {
        List<Class> classes = classRepository.findAll();

        String classesAsJson = new ObjectMapper().writeValueAsString(classes);

        return new ResponseEntity(classesAsJson, HttpStatus.OK);
    }

    @RequestMapping("/setSelectedClasses")
    public ResponseEntity<?> setSelectedClasses(@RequestBody List<ClassDto> classesDto) throws JsonProcessingException {

        List<Class> activeClasses = updateToActiveState(classesDto);

        String classesAsJson = new ObjectMapper().writeValueAsString(activeClasses);

        return new ResponseEntity(classesAsJson, HttpStatus.OK);
    }

    private List<Class> updateToActiveState(List<ClassDto> classesDto) {

        List<Class> classes = new ArrayList<>();

        for (ClassDto classDto : classesDto) {

            Class aClass = classRepository.findById(classDto.getClassName()).get();

            modelMapper.map(classDto, aClass);

            aClass.setValid(Boolean.TRUE);

            classes.add(aClass);
        }

        classRepository.saveAll(classes);

        return classes;
    }
}
