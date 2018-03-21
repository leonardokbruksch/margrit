package com.app.margrit.services;

import com.app.margrit.entities.Class;
import com.app.margrit.entities.Method;
import com.sun.codemodel.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JunitTestGenerationService {

    private static String TESTCASES_FOLDER = "C://margrit//testCases//";

    JCodeModel codeModel = new JCodeModel();

    public void createTestCases(List<Class> classes) throws JClassAlreadyExistsException, IOException {
        for (Class aClass : classes){
            createTestCase(aClass);
        }
    }

    private void createTestCase(Class aClass) throws JClassAlreadyExistsException, IOException {

        JDefinedClass definedClass = createClass(aClass, codeModel);

        setJUnitTestDependencies(aClass, definedClass);

        for(Method method : aClass.getMethods()){
            createTestMethod(method, definedClass);
        }

        codeModel.build(new File(TESTCASES_FOLDER + ""));
    }

    private void setJUnitTestDependencies(Class aClass, JDefinedClass definedClass) {

        //CREATE ATTRIBUTE
        // Attribute must be of the type of class and
        String fullClassName = "com.test." + aClass.getClassName();
        JClass objectReference = codeModel.ref(fullClassName);
        JFieldVar objectAttribute = definedClass.field(JMod.PRIVATE | JMod.FINAL | JMod.STATIC, objectReference, aClass.getClassName());

        //CREATE @Before METHOD
        JMethod setupMethod = definedClass.method(JMod.PUBLIC, codeModel.VOID, "setUp");
        setupMethod.annotate(Before.class);

        //Instantiate Obj
        JBlock body = setupMethod.body();
        body.assign(objectAttribute, JExpr._new(objectReference));

    }

    private JDefinedClass createClass(Class aClass, JCodeModel codeModel) throws JClassAlreadyExistsException {
        JPackage jPackage = codeModel._package("com.generated.junit");
        JDefinedClass definedClass = jPackage._class(aClass.getClassName() + "Test");
        definedClass.javadoc().add("Generated Junit Test Case for: " + aClass.getClassName() + " class.");
        return definedClass;
    }

    private void createTestMethod(Method method, JDefinedClass definedClass) {
        JMethod testMethod = definedClass.method(JMod.PUBLIC, codeModel.VOID, "test" + method.getMethodName());
        testMethod.annotate(Test.class);

        JBlock body = testMethod.body();

        String methodCall = "className" + "." + method.getMethodName() + "(" + "parameters" + ")";
        body.directStatement(methodCall);
    }
}
