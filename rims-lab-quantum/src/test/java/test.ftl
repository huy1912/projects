<b>The message is: ${msg}</b>
<#list cars as car>
${car.name}: ${car.price}
</#list>
<#assign value = 4>
<#if value < 0>
	The number is negative
<#elseif value == 0>
	The number is zero
<#else>
	The number is positive
</#if>
<#assign colors = ["red", "green", "blue"] >
<#list colors as color>
	The color is ${color}
</#list>
<#assign items = {"pens" : 3, "cups": 2, "tables": 1} >

<#list items?keys as k>
	Key ${k}
</#list>

<#list items?values as v>
	Value ${v}
</#list>

<#assign weather="\t\tweather\n\n">
<#compress>
${weather}
</#compress>