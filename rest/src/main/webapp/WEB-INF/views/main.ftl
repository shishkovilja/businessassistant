<#import "templates/ftl/common.ftl" as c>
<#import "templates/ftl/login.ftl" as l>

<@c.page>
    <@l.logout/>

    <div>Hello, user</div>
    <a href="/main">MAIN PAGE</a>
</@c.page>