<#import "operatorinfo.ftl" as oi>
<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>BootBA</title>
    <@oi.ajaxhead />
</head>
<@oi.ajaxscript />
<body>
<#nested>
</body>
</html>
</#macro>