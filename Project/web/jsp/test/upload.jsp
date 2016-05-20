<%--
  Created by IntelliJ IDEA.
  User: lutingming
  Date: 14-1-11
  Time: 下午12:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <form name="form1" action="uploadFile" method="post" enctype="multipart/form-data">
        <input name="text" value="text123">
        <br>
        <input name="fileName" value="testFileName.jpg"/>
        <br>
        <input name="file" type="file"/>
        <br>
        <input type="submit" value="submit">
    </form>
</body>
</html>

<script language="javascript">
function mySubmit() {
    document.form1.submit();
}
</script>