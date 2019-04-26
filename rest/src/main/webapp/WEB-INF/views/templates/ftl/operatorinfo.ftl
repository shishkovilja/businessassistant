<#macro ajaxhead>
    <#--<head>-->
        <#--<title>Title</title>-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <#--</head>-->
</#macro>
<#macro ajaxscript>
    <script>
        var service = 'http://localhost:8080/operator';

        var RestGetOp = function (id) {
            $.ajax({
                type: 'GET',
                url: service + '/get/' + id,
                dataType: 'json',
                accept: 'json',
                contentType: 'application/json;utf-8',
                async: false,
                success: function (result) {
                    $('#response').html(JSON.stringify(result))
                },
                error: function (jqXHR, testStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        };

        var RestGetAllOp = function () {
            $.ajax({
                type: 'GET',
                url: service + '/get/all',
                dataType: 'json',
                accept: 'json',
                contentType: 'application/json;utf-8',
                async: false,
                success: function (result) {
                    $('#response').html(JSON.stringify(result))
                },
                error: function (jqXHR, testStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        };

        var RestDeleteOp = function (id) {
            $.ajax({
                type: 'DELETE',
                url: service + '/delete/' + id,
                dataType: 'json',
                accept: 'json',
                contentType: 'application/json;utf-8',
                async: false,
                success: function (result) {
                    $('#response').html(JSON.stringify(result))
                },
                error: function (jqXHR, testStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        };

        var RestPostOp = function (e-mail, password, login, city) {
            var JSONObject = {
                'e-mail': e-mail,
                'password': password,
                'login': login,
                'city': city
            };

            $.ajax({
                type: 'POST',
                url: service + '/add',
                contentType: 'application/json;utf-8',
                dataType: 'json',
                accept: 'json',
                data: JSON.stringify(JSONObject),
                async: false,
                success: function (result) {
                    $('#response').html(JSON.stringify(result))
                },
                error: function (jqXHR, testStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        };

        var RestPutOp = function (id, login, city) {
            var JSONObject = {
                'id': id,
                'login': login,
                'city': city
            };

            $.ajax({
                type: 'PUT',
                url: service + '/update',
                contentType: 'application/json;utf-8',
                dataType: 'json',
                accept: 'json',
                data: JSON.stringify(JSONObject),
                async: false,
                success: function (result) {
                    $('#response').html(JSON.stringify(result))
                },
                error: function (jqXHR, testStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        }

    </script>
</#macro>