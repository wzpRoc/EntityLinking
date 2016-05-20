<%@ page language="java" import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String a = (String) request.getAttribute("n123");
    String b = (String) request.getParameter("n123");
%>

<script language="javascript" src="./js/jquery.form.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
    <%="a"+a%>
    <%="b"+b%>
</body>
</html>
