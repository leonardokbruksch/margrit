package com.app.margrit.services;

import com.app.margrit.entities.Class;
import com.app.margrit.entities.Method;
import com.sun.codemodel.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JunitTestGenerationService {

    private static String TESTCASES_FOLDER = "C://margrit//testCases//";

    final String STRING_TYPE = "String";
    final String CHAR_TYPE = "char";
    final String BOOLEAN_TYPE = "boolean";
    final String INT_TYPE = "int";
    final String DOUBLE_TYPE = "double";

    JCodeModel codeModel = new JCodeModel();

    private String currentTestObjectName;

    public void createTestCases(List<Class> classes) throws JClassAlreadyExistsException, IOException {
        for (Class aClass : classes){
            createTestCase(aClass);
        }
    }

    private void createTestCase(Class aClass) throws JClassAlreadyExistsException, IOException {

        JDefinedClass definedClass = createClass(aClass, codeModel);

        setJUnitTestDependencies(aClass, definedClass);

        //Set current Object name as Attribute for further use
        currentTestObjectName = aClass.getClassName();

        for(Method method : aClass.getMethods()){
            createTestMethod(method, definedClass);
        }

        codeModel.build(new File(TESTCASES_FOLDER + ""));
    }

    private JDefinedClass createClass(Class aClass, JCodeModel codeModel) throws JClassAlreadyExistsException {
        JPackage jPackage = codeModel._package("junit");
        JDefinedClass definedClass = jPackage._class(aClass.getClassName() + "Test");
        definedClass.javadoc().add("Generated Junit Test Case for: " + aClass.getClassName() + " class.");
        return definedClass;
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

    private void createTestMethod(Method method, JDefinedClass definedClass) {
        JMethod testMethod = definedClass.method(JMod.PUBLIC, codeModel.VOID, "test" + method.getMethodName());
        testMethod.annotate(Test.class);

        JBlock body = testMethod.body();

        body.directStatement(buildMethodCallStatement(method));

        if (method.getReturnType() != null) {
            JInvocation assertEqualsInvok = buildAssertStatement(method, definedClass);
            body.add(assertEqualsInvok);
        }
    }

    private String buildMethodCallStatement(Method method) {
        String methodReturn = "";

        if(method.getReturnType() != null){
            methodReturn = method.getReturnType() + " returnValue = ";
        }

        String methodCall = methodReturn + currentTestObjectName + "." + method.getMethodName() + "(" + method.getParametersAsString() + ");";

        return methodCall;
    }

    private JInvocation buildAssertStatement(Method method, JDefinedClass definedClass) {
        
        JExpression expression = getExpessionForMethodReturn(method);
        JInvocation assertEquals = codeModel.ref(Assert.class).staticInvoke("assertEquals").arg(expression).arg(JExpr.ref("returnValue"));

        return assertEquals;
    }

    private JExpression getExpessionForMethodReturn(Method method){

        JExpression expression = JExpr._this();

        String returnType = method.getReturnType();

        if (returnType.equalsIgnoreCase(STRING_TYPE)){
            expression = JExpr.lit(method.getExpectedReturnValue());
        }
        else if (returnType.equalsIgnoreCase(CHAR_TYPE)) {
            expression = JExpr.lit(method.getExpectedReturnValue().charAt(0));
        }
        else if (returnType.equalsIgnoreCase(BOOLEAN_TYPE)) {
            expression = JExpr.lit(Boolean.parseBoolean(method.getExpectedReturnValue()));
        }
        else if (returnType.equalsIgnoreCase(INT_TYPE)) {
            expression = JExpr.lit(Integer.parseInt(method.getExpectedReturnValue()));
        }
        else if (returnType.equalsIgnoreCase(DOUBLE_TYPE)) {
            expression = JExpr.lit(Double.parseDouble(method.getExpectedReturnValue()));
        }

        return expression;
    }

}
