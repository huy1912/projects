<#import "macro.ftl" as u>
<@u.page>
Blah blah
</@u.page>
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

<#assign param1="This is parameter name">
<@u.macro1 p1=param1>
</@u.macro1>
<#include "common.ftl">
<#-- use default -->
Empty parameter ${param2!'N/A'}

<#assign param2="Param2....">
<#-- Null check -->
<#if param2??>
	Param2 is ${param2}
</#if>

<#-- Escape -->
${msg?html}
