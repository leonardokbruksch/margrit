package com.app.margrit.controllers;

import com.app.margrit.entities.Class;
import com.app.margrit.parser.DefaultOperationsCsvParser;
import com.app.margrit.parser.OperationsCsvParser;
import com.app.margrit.repositories.ClassRepository;
import com.app.margrit.services.DefaultRandomOptionsService;
import com.app.margrit.services.JunitTestGenerationService;
import com.sun.codemodel.JClassAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DefaultRandomOptionsService defaultRandomOptionsService;

    @RequestMapping("/")
    public String home(){
        //On Start
        defaultRandomOptionsService.createDefaultOptions();

        return "home";
    }

    @RequestMapping("/leo")
    public String testedoleo(){
        return "leo";
    }

    @RequestMapping("/carousel")
    public String testedocarousel(){
        return "carousel";
    }

    @RequestMapping("/options")
    public String testedosubmit() { return "optionsTest"; }

}
