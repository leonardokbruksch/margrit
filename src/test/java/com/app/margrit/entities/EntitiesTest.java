package com.app.margrit.entities;

import com.app.margrit.repositories.ClassRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class EntitiesTest {

    @Autowired
    private ClassRepository classRepository;

    @Test
    public void testRepository() {
        classRepository.save(new Class("LeoClass"));

        Class leoClass = classRepository.findOne("LeoClass");

        leoClass.toString();
    }
}
