package com.app.margrit.controllers;

import com.app.margrit.entities.Class;
import com.app.margrit.parser.DefaultOperationsCsvParser;
import com.app.margrit.parser.OperationsCsvParser;
import com.app.margrit.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/leo")
    public String testedoleo(){
        return "leo";
    }
}
