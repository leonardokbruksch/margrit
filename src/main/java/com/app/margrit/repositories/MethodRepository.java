package com.app.margrit.repositories;

import com.app.margrit.entities.Class;
import com.app.margrit.entities.Method;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MethodRepository extends JpaRepository<Method, Long> {

}
