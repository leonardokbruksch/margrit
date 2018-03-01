package com.app.margrit.controllers;

import com.app.margrit.entities.Class;
import com.app.margrit.parser.DefaultOperationsCsvParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    @Autowired
    private DefaultOperationsCsvParser csvParser;

    private static String UPLOADED_FOLDER = "C://margrit//uploadedFiles//";

    @PostMapping("/upload")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file,
                                              Model model, HttpSession session) throws IOException {

        if (file.isEmpty()) {
            //Set error message
        }

        //saveFileOnFolder(file, redirectAttributes);

        List<Class> classes = csvParser.readCsvOperationsFile(file);

        //session.setAttribute("classes", classes);

        model.addAttribute("classes", classes);

        ObjectMapper objectMapper = new ObjectMapper();

        String classesAsJson = objectMapper.writeValueAsString(classes);

        //return "inputParametersTable";

        return new ResponseEntity(classesAsJson, HttpStatus.OK);

    }

    private void saveFileOnFolder(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/operations")
    public String parsedOperations(Model model, HttpSession session){
        model.addAttribute("classes", session.getAttribute("classes"));
        return "inputParametersTable";
    }

}
