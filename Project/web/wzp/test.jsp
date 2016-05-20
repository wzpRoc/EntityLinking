<%@ page language="java" import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String att1 = request.getParameter("a2");
    Object att3 = request.getAttribute("a3");
    Object a4 = request.getAttribute("a4");
%>

<script language="javascript" src="./js/jquery.form.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
    <%=att1%><br>
<%=att3%><br>
<%=a4%>
</body>
</html>
