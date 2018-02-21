package com.app.margrit.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class OperationsCsvParser {

    String[] HEADER = { "Operation", "Class", "Definition"};

    public void readCsvOperationsFile() throws IOException {
        Reader in = new FileReader("C:/Users/Leonardo Bruksch/Desktop/meucsv.csv");

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADER).withFirstRecordAsHeader().parse(in);

        for (CSVRecord record : records) {

            String className = record.get("Class").replaceAll("java::", "");

            String operation = record.get("Operation");
        }
    }
}
