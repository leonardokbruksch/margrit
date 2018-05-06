package com.app.margrit.services;

import com.app.margrit.entities.Class;
import com.app.margrit.entities.Method;
import com.app.margrit.entities.RandomOptions;
import com.app.margrit.repositories.RandomOptionsRepository;
import com.sun.codemodel.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RandomOptionsRepository randomOptionsRepository;

    @Autowired
    private BoundaryValueParameterService boundaryValueParameterService;

    JCodeModel codeModel = new JCodeModel();

    private String currentTestObjectName;

    private boolean isObjectParam;

    private RandomOptions randomOptions;

    public void createTestCases(List<Class> classes) throws JClassAlreadyExistsException, IOException {

        randomOptions = randomOptionsRepository.findOne("customOption");

        for (Class aClass : classes){
            currentTestObjectName = aClass.getClassName();
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

    private JDefinedClass createClass(Class aClass, JCodeModel codeModel) throws JClassAlreadyExistsException {
        JPackage jPackage = codeModel._package("junit");
        JDefinedClass definedClass = jPackage._class(aClass.getClassName() + "Test");
        definedClass.javadoc().add("Generated Junit Test Case for: " + aClass.getClassName() + " class.");
        return definedClass;
    }

    private void setJUnitTestDependencies(Class aClass, JDefinedClass definedClass) {

        String fullClassName = getFullClassName(aClass);
        JClass objectReference = codeModel.ref(fullClassName);
        JFieldVar objectAttribute = definedClass.field(JMod.PRIVATE | JMod.FINAL | JMod.STATIC, objectReference, aClass.getClassName());

        JMethod setupMethod = definedClass.method(JMod.PUBLIC, codeModel.VOID, "setUp");
        setupMethod.annotate(Before.class);

        JBlock body = setupMethod.body();
        body.assign(objectAttribute, JExpr._new(objectReference));
    }

    private String getFullClassName(Class aClass){
        if ( aClass.getPackageName() == null || aClass.getPackageName().isEmpty()){
            return aClass.getClassName();
        } else {
            return aClass.getPackageName() + "." + aClass.getClassName();
        }
    }

    private void createTestMethod(Method method, JDefinedClass definedClass) {
        JMethod testMethod = definedClass.method(JMod.PUBLIC, codeModel.VOID, "test" + method.getMethodName());
        testMethod.annotate(Test.class);

        JBlock body = testMethod.body();

        buildMethodCall(method, body);

        if (method.getReturnType() != null && method.getExpectedReturnValue() != null) {
            JInvocation assertEqualsInvok = buildAssertStatement(method, definedClass);

            if (isObjectParam = true){
                body.directStatement("// for the assert bellow you must manually add Object");
            }

            body.add(assertEqualsInvok);
        }
    }

    private void buildMethodCall(Method method, JBlock body) {

        if ( randomOptions != null && randomOptions.isTestValueLimits() == true){
            buildMethodMultipleCallStatement(method, body);
        } else {
            body.directStatement(buildMethodSingleCallStatement(method));
        }

    }

    private void buildMethodMultipleCallStatement(Method method, JBlock body) {

        //METHOD CALL WITH VALUE BELLOW RANGE
        String bellowLimitParameters = boundaryValueParameterService.getBellowLimitParameters(randomOptions, method);
        body.directStatement(buildMethodCallStatementWithCustomParam(method, bellowLimitParameters));

        // METHOD CALL WITH RANDOM VALUE
        body.directStatement(buildMethodSingleCallStatement(method));

        // METHOD CALL WITH VALUE ABOVE RANGE
        String aboveLimitParameters = boundaryValueParameterService.getAboveLimitParameters(randomOptions, method);
        body.directStatement(buildMethodCallStatementWithCustomParam(method, aboveLimitParameters));

    }

    private String buildMethodCallStatementWithCustomParam(Method method, String parameters){
        String methodReturn = "";

        if(method.getReturnType() != null){
            methodReturn = method.getReturnType() + " returnValue = ";
        }

        String methodCall = methodReturn + currentTestObjectName + "." + method.getMethodName() + "(" + parameters + ");";

        return methodCall;
    }

    private String buildMethodSingleCallStatement(Method method) {
        String methodReturn = "";

        if(method.getReturnType() != null){
            methodReturn = method.getReturnType() + " returnValue = ";
        }

        String methodCall = methodReturn + currentTestObjectName + "." + method.getMethodName() + "(" + method.getParametersAsString() + ");";

        return methodCall;
    }

    private JInvocation buildAssertStatement(Method method, JDefinedClass definedClass) {

        JExpression expression = getExpressionForMethodReturn(method);
        JInvocation assertEquals = codeModel.ref(Assert.class).staticInvoke("assertEquals").arg(expression).arg(JExpr.ref("returnValue"));

        return assertEquals;
    }

    private JExpression getExpressionForMethodReturn(Method method){

        isObjectParam = false;

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
        } else {
            isObjectParam = true;
        }

        return expression;
    }

}
