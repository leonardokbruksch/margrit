package com.app.margrit.controllers;

import com.app.margrit.entities.Class;
import com.app.margrit.repositories.ClassRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class GenerateTestCasesController {

    @Autowired
    protected ClassRepository classRepository;

    private static String UPLOADED_FOLDER = "C://margrit//abstractStructures//";

    @RequestMapping("/generateAbstractStructure")
    public void generateAbstractStructure(HttpServletResponse response) throws IOException {
        List<Class> allClasses = classRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String classesAsJson = objectMapper.writeValueAsString(allClasses);

        String fileName = "abstractStructure.txt";

        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.getOutputStream().write(classesAsJson.getBytes());
    }

    @RequestMapping("/generateTestCases")
    public void generateTestCases(){
        return;
    }
}
