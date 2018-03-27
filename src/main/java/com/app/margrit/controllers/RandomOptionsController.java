package com.app.margrit.controllers;

import com.app.margrit.entities.RandomOptions;
import com.app.margrit.repositories.RandomOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class RandomOptionsController {

    @Autowired
    private RandomOptionsRepository randomOptionsRepository;

    @PostMapping("/setRandomOptions")
    public ResponseEntity<?> setRandomOptions(@RequestBody RandomOptions randomOptions){
        randomOptions.setOptionName("customOption");

        randomOptionsRepository.save(randomOptions);

        return new ResponseEntity(HttpStatus.OK);
    }
}
