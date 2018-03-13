$('.custom-file-input').on('change',function(){
    var fileName = $(this).val().split('\\').pop();
    $(this).next('.custom-file-label').addClass("selected").html(fileName);
})


$(document).ready(function () {

    hideClassesContent();

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
            disableParameterInputs();
        } else {
            enableParameterInputs();
        }
    });

});

function hideClassesContent(){
    $('#classesContainer').hide();
}

function fireAjaxFileUpload() {

    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "upload",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        datatype: "json",
        success: function (data) {

            createClassesData(data);

            $('#fileUploadForm').hide();

            $('#classesContainer').show();

            console.log("SUCCESS : ", data);

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);

        }
    });

}

function createClassesData(data){
    $.each(JSON.parse(data), function(idx, obj) {

        var html = '';
        html += '<div class="classContainer card">';
        html += '<div class="className card-header"> <h1 class="h3 mb-3 font-weight-normal">' + obj.className + '</h1> </div>';

        html = createMethodsData(obj.methods, html);

        html += '</div>';

        $('#classesCards').append(html);

    });
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
        html += '<input type="text" name="expectedReturnValue" class="form-control expectedReturnValue" placeholder="Return value"/>';
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
            debugger;
        }
    });
}

function fireAjaxRandomizeTestData() {
    listOfClasses = getClassesDataToSubmit();

    $.ajax( {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: 'randomizeTestData',
        data: JSON.stringify(listOfClasses),
        success: function(data) {
            debugger;
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
        method.expectedReturnValue = currentMethod.find($('.expectedReturnValue')).val();

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

function disableParameterInputs(){
    $('.parameterValue').prop('disabled', true);
}

function enableParameterInputs(){
    $('.parameterValue').prop('disabled', false);
}