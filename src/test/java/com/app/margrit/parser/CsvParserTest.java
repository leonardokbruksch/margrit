package com.app.margrit.parser;

import java.io.IOException;

public class CsvParserTest {

    public static void main(String[] args) throws IOException {
        OperationsCsvParser parser = new OperationsCsvParser();

        parser.readCsvOperationsFile();
    }
}
