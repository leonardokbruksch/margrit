package com.app.margrit.repositories;

import com.app.margrit.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClassRepository extends JpaRepository<Class, String> {

    Class findByClassName(String className);
}
