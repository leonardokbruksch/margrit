package com.app.margrit.repositories;

import com.app.margrit.entities.Class;
import com.app.margrit.entities.RandomOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RandomOptionsRepository extends JpaRepository<RandomOptions, String> {
}
