package com.app.margrit.repositories;

import com.app.margrit.entities.Method;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Method, Long> {

}
