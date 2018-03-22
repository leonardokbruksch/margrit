package com.app.margrit.controllers;

import com.app.margrit.entities.Class;
import com.app.margrit.repositories.ClassRepository;
import com.app.margrit.services.JunitTestGenerationService;
import com.app.margrit.services.RandomizeParametersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zeroturnaround.zip.ZipUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class GenerateTestCasesController {

    @Autowired
    protected ClassRepository classRepository;

    @Autowired
    private JunitTestGenerationService testGenerationService;

    private static String UPLOADED_FOLDER = "C://margrit//abstractStructures//";

    private static String TESTCASES_FOLDER = "C://margrit//testCases//";
    private static String TESTCASES_JUNIT_FOLDER = "C://margrit//testCases//junit";
    private static String TESTCASES_ZIP = "C://margrit//testCases//junit.zip";

    @RequestMapping("/generateAbstractStructure")
    public void generateAbstractStructure(HttpServletResponse response) throws IOException {
        List<Class> allClasses = classRepository.findByIsValidTrue();

        ObjectMapper objectMapper = new ObjectMapper();
        String classesAsJson = objectMapper.writeValueAsString(allClasses);

        String fileName = "abstractStructure.txt";
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.getOutputStream().write(classesAsJson.getBytes());
    }

    @RequestMapping(value="/generateTestCases", produces="application/zip")
    public void generateTestCases(HttpServletResponse response) throws IOException, JClassAlreadyExistsException {
        List<Class> classes = classRepository.findByIsValidTrue();

        testGenerationService.createTestCases(classes);

        generateZipFile(response);
    }

    private void generateZipFile(HttpServletResponse response) throws IOException {

        File zipFile = new File(TESTCASES_ZIP);
        ZipUtil.pack(new File(TESTCASES_JUNIT_FOLDER), zipFile);

        addZipToResponse(response, zipFile);

        FileUtils.cleanDirectory(new File(TESTCASES_FOLDER));
    }

    private void addZipToResponse(HttpServletResponse response, File zipFile) throws IOException {
        String fileName = "junit.zip";
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        FileInputStream inputStream = new FileInputStream(zipFile);
        IOUtils.copy(inputStream, response.getOutputStream());

        inputStream.close();
        response.getOutputStream().close();
    }
}
