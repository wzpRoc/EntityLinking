<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
    <style type="text/css">
        a {
            text-decoration: none;
            color: #002280
        }

        a:hover {
            text-decoration: underline
        }

        body {
            font-size: 9pt;
        }

        table {
            font: 9pt Tahoma, Verdana;
            color: #000000
        }

        .tableborder {
            background: #CDE2F8;
            border: 1px solid #8CBDEF
        }

        .header {
            font: 9pt Tahoma, Verdana;
            color: #FFFFFF;
            font-weight: bold;
            background-color: #8CBDEF
        }

        .header a {
            color: #FFFFFF
        }
    </style>
    <script language='javascript'>
        <%--var secs=1;//3秒--%>
        <%--for(i=1;i<=secs;i++) {--%>
        <%--window.setTimeout("update(" + i + ")", i * 2000);--%>
        <%--}--%>

        <%--function update(num) {--%>
        <%--if(num == secs) {--%>
        <%--window.${target!'self'}.location.href='${redirectUrl}';--%>
        <%--} else {--%>
        <%--}--%>
        <%--}--%>
        function f(){
            var errorMessageDiv = document.getElementById('errorMessage');
            if(errorMessageDiv.style.display=='none') {
                errorMessageDiv.style.display='';
            } else {
                errorMessageDiv.style.display='none';
            }
        }
    </script>
</head>

<body>
<br>
<br>
<br>
<br>
<br>

<br>
<table width="500" border="0" align="center" cellpadding="3" cellspacing="1" class="tableborder">
    <tr class="header">
        <td height="25">
            <div align="center">信息提示</div>
        </td>
    </tr>
    <tr bgcolor="#FFFFFF">
        <td height="80">
            <div align="center">
                <br>
                <b>抱歉，服务器内部错误，请将此错误报告给管理员。</b>
                <div style="cursor: pointer; margin-top: 20px" onclick="f();">点击查看详细信息</div>

                <div id="errorMessage" style="display: none; text-align: left; margin-top: 20px">
                    <%
                        Exception e = (Exception) request.getAttribute("exception");
                        StringBuffer sb = new StringBuffer(e.toString());
                        StackTraceElement[] trace = e.getStackTrace();
                        for (int i = 0; i < trace.length; i++)
                            sb.append("\n\tat ").append(trace[i]);
                    %>
                    <%=sb.toString()%>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>