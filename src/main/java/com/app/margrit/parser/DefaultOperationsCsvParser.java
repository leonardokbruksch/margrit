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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DefaultOperationsCsvParser implements OperationsCsvParser {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private MethodRepository methodRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    String[] HEADER = { "Operation", "Class", "Definition"};

    public List<Class> readCsvOperationsFile(MultipartFile file) throws IOException {
        removeOldEntries();

        Reader inputStreamReader = new InputStreamReader(file.getInputStream());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADER).withFirstRecordAsHeader().parse(inputStreamReader);

        for (CSVRecord record : records) {
            createEntities(record);
        }

       return classRepository.findAll();

    }

    private void removeOldEntries() {
        classRepository.deleteAll();
        methodRepository.deleteAll();
        parameterRepository.deleteAll();
    }

    private void createEntities(CSVRecord record){

        Class currentClass = getClass(record);

        Method currentMethod = createMethod(record);

        currentClass.addMethod(currentMethod);

        classRepository.save(currentClass);
    }

    private Class getClass(CSVRecord record) {
        String className = record.get("Class").replaceAll("java::", "");

        Class existantClass = classRepository.findByClassName(className);

        if(existantClass != null){
            return existantClass;
        } else {
            return new Class(className);
        }

    }

    private Method createMethod(CSVRecord record) {
        String operation = record.get("Operation");

        String methodName = operation.replaceAll("\\((.*)", "");
        methodName = methodName.replace("public", "").trim();
        Method currentMethod = new Method(methodName);

        currentMethod.setReturnType(getMethodReturnType(operation));

        currentMethod.setParameters(createParameters(record));

        methodRepository.save(currentMethod);

        return currentMethod;
    }

    private String getMethodReturnType(String operation){
        String returnType = operation.replaceAll("(.*)\\:", "").trim();
        if(!returnType.equalsIgnoreCase("void")){
            return returnType;
        } else {
            return null;
        }
    }

    private List<Parameter> createParameters(CSVRecord record) {
        List<String> parameters = Arrays.asList(getParameters(record));

        List<Parameter> parametersObjects = new ArrayList<>();

        for(String parameter : parameters){

            String name = parameter.replaceAll("\\:(.*)", "").trim();
            String type = parameter.replaceAll("(.*)\\:", "").trim();;
            Parameter parameterObject = new Parameter(name, type);

            parametersObjects.add(parameterObject);

        }
        parameterRepository.saveAll(parametersObjects);
        //parameterRepository.save(parametersObjects);

        return parametersObjects;
    }

    private String[] getParameters(CSVRecord record) {
        String operation = record.get("Operation");

        String allParametersAsString = operation.substring(operation.indexOf("(") + 1, operation.indexOf(")"));
        String[] parameters = allParametersAsString.split(",");

        return parameters;
    }
}
