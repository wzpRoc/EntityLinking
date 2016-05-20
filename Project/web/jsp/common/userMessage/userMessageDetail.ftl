<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<#assign select = "org.ailab.irica.frontend.SelectDirective"?new()>

<#--<link href="${ctx}/css/feedback.css" rel="stylesheet" type="text/css" />-->

<#include "../title.ftl">

<form action="${ctx}/admin/feedbackDetail!save" method="post">
<@s.hidden name="dowhat"/>
<@s.hidden name="dto.id"/>
<@s.hidden name="dto.userId"/>

<div>
    <div style="float: right;">
        <span>当前状态：${dto.stateDesc}</span>
    <#if user.role == 3>
        <input type="submit" value="&nbsp;&nbsp;保&nbsp;存&nbsp;&nbsp;" style="margin-left:10px"/>
        <input type="button" value="&nbsp;&nbsp;返&nbsp;回&nbsp;&nbsp;" style="margin-left:10px" onclick="window.location='feedback';"/>
    </#if>
    </div>
</div>

<br>

<div style="margin-top: 10px">
    <table cellspacing="5" cellpadding="5" width="100%">
        <tr>
            <td class="detailLabel" width="100">标题</td>
            <td><@s.textfield name="dto.title" size="117"></@s.textfield></td>
        </tr>
        <tr>
            <td class="detailLabel">内容</td>
            <td>
                <div align="left">${dto.content!''}</div>
            </td>
        </tr>
        <tr>
            <td class="detailLabel">提交人</td>
            <td>${dto.userName}</td>
        </tr>
        <tr>
            <td class="detailLabel" width="100">提交时间</td>
            <td>${dto.commitTimeInShort}</td>
        </tr>
        <tr>
            <td class="detailLabel">链接</td>
            <td>
                <a target="blank" href="${dto.url}">${dto.url}</a>
            </td>
        </tr>
        <tr>
            <td class="detailLabel">客户端信息</td>
            <td>${dto.clientInfo!''}</td>
        </tr>
        <tr>
            <td class="detailLabel">处理人</td>
            <td>${dto.solvingUserName!''}</td>
        </tr>
        <tr>
            <td class="detailLabel">处理时间</td>
            <td>${dto.solvingTimeInShort}</td>
        </tr>
        <tr>
            <td class="detailLabel">处理情况</td>
            <td><@s.textarea name="dto.solvingDesc" rows="5" cols="100"></@s.textarea></td>
        </tr>
    </table>
</div>
</form>


