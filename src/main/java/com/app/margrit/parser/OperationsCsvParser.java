package com.app.margrit.parser;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OperationsCsvParser {

    void readCsvOperationsFile(MultipartFile file) throws IOException;
}
