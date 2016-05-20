<#-- 页面标题-->
<div id="content_title">
	${pageTitle}
</div>

<#-- 先判断是什么方法-->
<#if methodType=="list">
	<#if page.list?exists && (page.list?size>0)>
		<#include "userMessageList.ftl">
		<@list feedbacks=page.list>
		</@list>
	</#if>
<#elseif methodType=="insert">

</#if>
