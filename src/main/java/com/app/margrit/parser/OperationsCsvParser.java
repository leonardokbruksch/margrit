package com.app.margrit.parser;

import com.app.margrit.entities.Class;
import com.app.margrit.entities.Method;
import com.app.margrit.entities.Parameter;
import com.app.margrit.repositories.ClassRepository;
import com.app.margrit.repositories.MethodRepository;
import com.app.margrit.repositories.ParameterRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class OperationsCsvParser {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private MethodRepository methodRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    String[] HEADER = { "Operation", "Class", "Definition"};

    public void readCsvOperationsFile() throws IOException {
        Reader in = new FileReader("C:/Users/Leonardo Bruksch/Desktop/meucsv.csv");

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADER).withFirstRecordAsHeader().parse(in);

        for (CSVRecord record : records) {
            createEntities(record);
        }
    }

    private void createEntities(CSVRecord record){

        Class currentClass = createClass(record);

        Method currentMethod = createMethod(record);

        currentClass.addMethod(currentMethod);

        //classRepository.save(currentClass);
    }

    private List<Parameter> createParameters(CSVRecord record) {
        List<String> parameters = getParameters(record);

        List<Parameter> parametersObjects = new ArrayList<>();

        for(String parameter : parameters){
            // username : String
            // name and type
            String name = parameter.replaceAll("\\:(.*)", "").trim();
            String type = parameter.replaceAll("(.*)\\:", "").trim();;
            Parameter parameterObject = new Parameter(name, type);

            parametersObjects.add(parameterObject);
        }

        return parametersObjects;
    }

    private List<String> getParameters(CSVRecord record) {
        String operation = record.get("Operation");

        String onlyOneParameter = operation.substring(operation.indexOf("(") + 1, operation.indexOf(")"));

        List<String> parameters = new ArrayList<>();
        parameters.add(onlyOneParameter);

        return parameters;
    }

    private Method createMethod(CSVRecord record) {
        String operation = record.get("Operation");

        String methodName = operation.replaceAll("\\((.*)", "");
        methodName = methodName.replace("public", "").trim();
        Method currentMethod = new Method(methodName);

        String returnType = operation.replaceAll("(.*)\\:", "").trim();
        currentMethod.setReturnType(returnType);

        currentMethod.setParameters(createParameters(record));

        //methodRepository.save(currentMethod);

        return currentMethod;
    }

    private Class createClass(CSVRecord record) {
        String className = record.get("Class").replaceAll("java::", "");

        Class currentClass = new Class(className);
        //classRepository.save(currentClass);

        return currentClass;
    }
}
