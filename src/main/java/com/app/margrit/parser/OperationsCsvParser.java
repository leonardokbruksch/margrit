package com.app.margrit.parser;

import com.app.margrit.entities.Class;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OperationsCsvParser {

    List<Class> readCsvOperationsFile(MultipartFile file) throws IOException;
}
