<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>welcome</title>
  </head>
  <body>
  <%
      for(int i=0; i<10; i++) {
          String s = "abcdefghijklmn".substring(0, i);
  %>
  welcome <%=i%> <%=s%><br>
  <%
      }
  %>
  </body>
</html>