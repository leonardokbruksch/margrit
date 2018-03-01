$('.custom-file-input').on('change',function(){
    var fileName = $(this).val().split('\\').pop();
    $(this).next('.custom-file-label').addClass("selected").html(fileName);
})


$(document).ready(function () {

    $("#submitUpload").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {
    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "upload",
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        datatype: "json",
        success: function (data) {

            createClassesData(data);

            $('#fileUploadForm').hide();

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
        html += '<div class="className">';
        html += '<h1 class="h3 mb-3 font-weight-normal">' + obj.className + '</h1>';
        html += '</div>';

        html = createMethodsData(obj.methods, html);

        $('#classesContainer').append(html);

    });
}

function createMethodsData(methods, html) {
    $.each(methods, function(idx, method) {


        html += '<div class="methodName">';
        html += '<h1 class="h3 mb-3 font-weight-normal">' + method.methodName + '</h1>';
        html += '</div>';

        html = createParametersData(method.parameters, html);

    });

    return html;

}

function createParametersData(parameters, html) {
    $.each(parameters, function(idx, parameter) {

        // PRINT Parameter Name and Type
        html += '<div class="paramater">';
        html += '<h1 class="h3 mb-3 font-weight-normal">' + parameter.parameterType + ' : ' +  parameter.parameterName + '</h1>';
        html += '</div>';

        // Create input field for Parameter Value

    });

    return html;
}