package com.app.margrit.controllers;


import com.app.margrit.entities.Class;
import com.app.margrit.entities.Parameter;
import com.app.margrit.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ClassesTestDataSubmitController {

    @Autowired
    private ClassRepository classRepository;

    @PostMapping("/submitClassesTestData")
    public String submitClassesTestData(@RequestBody List<Class> classes){
        classRepository.save(classes);

        List<Class> all = classRepository.findAll();
        return null;
    }
}
