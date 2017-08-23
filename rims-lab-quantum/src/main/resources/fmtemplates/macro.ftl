<#macro page>
Welcome to ${name}
<#-- This process the enclosed content: -->
<#nested>
</#macro>

<#macro macro1 p1>
<p>The input parameter is ${p1}</p>
</#macro>