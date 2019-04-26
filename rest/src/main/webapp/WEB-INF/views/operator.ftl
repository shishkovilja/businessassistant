<#import "templates/ftl/common.ftl" as c>
<#import "templates/ftl/login.ftl" as l>
<#import "templates/ftl/operatorinfo.ftl" as oi>

<@c.page>
    <h1>Cat's info</h1>

    <table class="table">
        <tr>
            <th>Request type</th>
            <th>URL</th>
            <th>Value</th>
        </tr>

        <tr>
            <td>Get cat by id <code><strong>GET</strong></code></td>
            <td>/operator/get/{id}</td>
            <td>
                id: <input id="getOperatorID" value="">
                <button type="button" onclick="RestGetOp($('#getOperatorID').val())">try</button>
            </td>
        </tr>

        <tr>
            <td>Get all cats<code><strong>GET</strong></code></td>
            <td>/operator/get/all</td>
            <td>
                <button type="button" onclick="RestGetAllOp()">try</button>
            </td>
        </tr>

        <tr>
            <td>Add cat<code><strong>POST</strong></code></td>
            <td>/operator/add</td>
            <td>
                <form class="form-inline">
                    e-mail: <input id="postEmail" value="default e-mail">
                    password: <input id="postPassword" value="default password">
                    login: <input id="postName" value="default name">
                    city: <input id="postDescription" value="default description">

                    <button type="button" onclick="RestPostOp($('#postEmail').val(), $('#postPassword').val(), $('#postName').val(), $('#postDescription').val())">try</button>
                </form>
            </td>
        </tr>

        <tr>
            <td>Update cat<code><strong>PUT</strong></code></td>
            <td>/operator/update</td>
            <td>
                <form class="form-inline">
                    id: <input id="inputId" value="">
                    name: <input id="putName" value="default name">
                    description: <input id="putDescription" value="default description">

                    <button type="button" onclick="RestPutOp($('#inputId').val(), $('#putName').val(), $('#putDescription').val())">try</button>
                </form>
            </td>
        </tr>

        <tr>
            <td>Delete cat by id <code><strong>GET</strong></code></td>
            <td>/operator/delete/{id}</td>
            <td>
                id: <input id="deleteId" value="">
                <button type="button" onclick="RestDeleteOp($('#deleteId').val())">try</button>
            </td>
        </tr>
    </table>

    <div class="panel panel-default">
        <div class="page-heading">
            <strong>RESPONSE</strong>
        </div>
        <div class="panel-body" id="response"></div>
    </div>
</@c.page>
