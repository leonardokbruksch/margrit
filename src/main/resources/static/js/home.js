$('.custom-file-input').on('change',function(){
    var fileName = $(this).val().split('\\').pop();
    $(this).next('.custom-file-label').addClass("selected").html(fileName);
})


$(document).ready(function () {

    hideNonUploadContainers();

    $("#submitUpload").click(function (event) {
        event.preventDefault();
        fireAjaxFileUpload();
    });

    $("#classesTestDataForm").submit(function( event ) {
        event.preventDefault();
        submitTestData();
    });

    $("#randomizeCheckbox").change(function() {
        if(this.checked) {
            disableTestDataInputs();
        } else {
            enableTestDataInputs();
        }
    });

    $("#setClassPackage").change(function() {
        if(this.checked) {
            showPackageInputs();
        } else {
            hidePackageInputs();
        }
    });

     $("#submitGenerateTestCases").click(function (event) {
         event.preventDefault();
         generateTestCases($("#generateTestCases"));
     });

    $("#submitClassSelection").click(function (event) {
        event.preventDefault();
        setSelectedClasses();
    });

    $('#customFile').change(function () {
        handleUploadSubmitButton();
    });

    $("#saveRandomOptions").click(function (event) {
        event.preventDefault();
        saveRandomOptions();
    });

});

function hideNonUploadContainers(){
    $('#classesSelectionContainer').hide();
    $('#classesContainer').hide();
    $('#generationOptionsContainer').hide();
}

function handleUploadSubmitButton(){
    if ( $('#customFile').val() != ''){
        $('#submitUpload').attr('disabled' , false);
    } else {
        $('#submitUpload').attr('disabled' , true);
    }
}

function showPackageInputs() {
    $('.packageName').attr('hidden', false);
}

function hidePackageInputs() {
    $('.packageName').attr('hidden', true);
}

function fireAjaxFileUpload() {

    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "upload",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        datatype: "json",
        success: function (data) {

            $('#fileUploadForm').hide();

            createBootstrapTable();

        }
    });

}

function createBootstrapTable(){

    $('#selectClassesTable').bootstrapTable({
        url: 'classesTable',
        columns: [{
            field: 'state',
            title: 'Checkbox',
            checkbox: 'true'

        }, {
            field: 'className',
            title: 'Class Name'
        }, ]
    });

    $('#classesSelectionContainer').show();

}

function setSelectedClasses() {

    activeClasses = getSelectedClasses();

    $.ajax( {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: '/setSelectedClasses',
        data: JSON.stringify(activeClasses),
        datatype: "json",
        success: function(data) {

            $('#classesSelectionContainer').hide();

            createClassesData(data);
            $('#classesContainer').show();
        }
    });

}

function getSelectedClasses(){
    selectedClasses = $('#selectClassesTable').bootstrapTable('getSelections');
    return selectedClasses;
}

function createClassesData(classes){
    $.each(classes, function(idx, obj) {

        var html = '';
        html += '<div class="classContainer card">';
        html += '<div class="className card-header"> <h1 class="h3 mb-3 font-weight-normal">' + obj.className + '</h1> </div>';

        html = createPackageInput(html);
        html = createIsValidInput(html, obj);

        html = createMethodsData(obj.methods, html);

        html += '</div>';

        $('#classesCards').append(html);

    });
}

function createPackageInput(html){

    html += '<div class="packageName form-inline" hidden="true">';

    html += '<div class="form-group mb-2">';
    html += '<input type="text" readonly="true" class="form-control-plaintext" value="Package Name"/>';
    html += '</div>';

    html += '<div class="form-group mx-sm-3 mb-2">';
    html += '<input type="text" hidden="true" name="packageName" class="form-control packageName packageValue" placeholder="Package"/>';
    html += '</div>';

    html += '</div>';

    return html;
}

function createIsValidInput(html, aClass){

    html += '<input hidden="true" class="isValid" name="id" value="' + aClass.valid + '"/>';

    return html;
}

function createMethodsData(methods, html) {
    $.each(methods, function(idx, method) {


        html += '<div class="card-body method">';
        html += '<h5 class="methodName card-title text-left">' + method.methodName + '</h5>';
        html += '<input hidden="true" class="methodId" name="id" value="' + method.id + '"/>';

        html = createParametersData(method.parameters, html);

        html = createReturnTypeInput(method, html);

        html += '</div>';

    });

    return html;

}

function createReturnTypeInput(method, html) {

    if(method.returnType != null) {
        // CREATE BOX FOR EXPECTED RETURN TYPE
        html += '<div class="returnType form-inline">';

        html += '<div class="form-group mb-2">';
        html += '<input type="text" readonly="true" class="form-control-plaintext" value="' + method.returnType + '"/>';
        html += '</div>';

        html += '<div class="form-group mx-sm-3 mb-2">';
        html += '<input type="text" name="expectedReturnValue" class="form-control expectedReturnValue" placeholder="Expected Return value"/>';
        html += '</div>';

        html += '</div>';
    }

    return html;
}

function createParametersData(parameters, html) {
    //CREATE BOX FOR PARAMETERS

    $.each(parameters, function(idx, parameter) {

        html += '<div class="parameter form-inline">';

            html += '<div class="form-group mb-2">';
                html += '<input type="text" readonly="true" class="form-control-plaintext" id="parameter" value="' + parameter.parameterType + ' : ' + parameter.parameterName + '"/>';
            html += '</div>';

            html += '<div class="form-group mx-sm-3 mb-2">';
                html += '<input type="text" name="parameterValue" class="form-control parameterValue" id="parameter#' + parameter.id + '" placeholder="Value"/>';
            html += '</div>';

            html += '<input hidden="true" class="parameterId" name="id" value="' + parameter.id + '"/>';

        html += '</div>';

    });

    return html;
}

function submitTestData() {

    if( $('#randomizeCheckbox').is( ":checked" ) ){
        fireAjaxSubmitClassesTestData("random/randomizeTestData");
    } else {
        fireAjaxSubmitClassesTestData("submitClassesTestData");
    }

}

function fireAjaxSubmitClassesTestData(url) {
    listOfClasses = getClassesDataToSubmit();

    $.ajax( {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: url,
        data: JSON.stringify(listOfClasses),
        success: function(data) {
            $('#classesContainer').hide();
            $('#generationOptionsContainer').show();
        }
    });
}

function getClassesDataToSubmit() {

    var listOfClasses = new Array();

    $(".classContainer").each(function (){

        var currentClass = $(this);

        var aClass = new Object();
        var className = currentClass.find($(".className")).text().trim();
        aClass.className = className;

        if ( $('#setClassPackage').is( ":checked" ) ) {
            aClass.packageName = currentClass.find($(".packageValue")).val();
        }

        aClass.valid = currentClass.find($(".isValid")).val();

        aClass.methods = getMethodsDataForClass(currentClass);

        listOfClasses.push(aClass);
    });

    return listOfClasses;
}

function getMethodsDataForClass(currentClass) {

    var listOfMethods = new Array();

    currentClass.children('.method').each( function (){
        var currentMethod = $(this);

        var method = new Object();
        method.id = currentMethod.find($('.methodId')).val();
        method.methodName = currentMethod.find($('.methodName')).text().trim();

        var expectedReturnValue = currentMethod.find($('.expectedReturnValue')).val();
        if(expectedReturnValue !== null && expectedReturnValue !== ''){
            method.expectedReturnValue = expectedReturnValue;
        }

        method.parameters = getParametersDataForMethod(currentMethod);

        listOfMethods.push(method);
    });
    return listOfMethods;
}

function getParametersDataForMethod(currentMethod) {
    var listOfParameters = new Array();

    currentMethod.children('.parameter').each( function(){
        var currentParameter = $(this);

        var parameter = new Object();
        parameter.id = currentParameter.find($('.parameterId')).val();
        parameter.parameterValue = currentParameter.find($('.parameterValue')).val();

        listOfParameters.push(parameter);
    });

    return listOfParameters;
}

function disableTestDataInputs(){
    $('.parameterValue').val('');
    $('.expectedReturnValue').val('');
    $('.parameterValue').prop('disabled', true);
    $('.expectedReturnValue').prop('disabled', true);

    $('#randomizingOptions').attr('hidden', false);
}

function enableTestDataInputs(){
    $('.parameterValue').prop('disabled', false);
    $('.expectedReturnValue').prop('disabled', false);

    $('#randomizingOptions').attr('hidden', true);
}

function generateTestCases(form) {

    if ( $('#generateAbstractStructure').is( ":checked" ) ){
        form.attr('action', "/generateAbstractStructure").submit();
    } else {
        form.attr('action', "/generateTestCases").submit();
    }

}

function saveRandomOptions() {

    randomOptions = getRandomOptions();

    $.ajax( {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: '/setRandomOptions',
        data: JSON.stringify(randomOptions),
        success: function(data) {
            $('#randomizeModal').modal('hide');
        }
    });

}

function getRandomOptions() {
    modal = $('#randomizeModal');

    var randomOptions = new Object();

    randomOptions.optionName = '';

    randomOptions.numberMinLength = modal.find($('#numberMinLength')).val();
    randomOptions.numberMaxLength = modal.find($('#numberMaxLength')).val();

    randomOptions.stringMinLength = modal.find($('#stringMinLength')).val();
    randomOptions.stringMaxLength = modal.find($('#stringMaxLength')).val();

    if ( $('#testValueLimits').is( ":checked" ) ) {
        randomOptions.testValueLimits = true;
    } else {
        randomOptions.testValueLimits = false;
    }

    return randomOptions;
}