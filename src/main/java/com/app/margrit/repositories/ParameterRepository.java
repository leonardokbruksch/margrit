package com.app.margrit.repositories;

import com.app.margrit.entities.Method;
import com.app.margrit.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
