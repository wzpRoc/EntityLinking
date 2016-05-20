<%@ page language="java" import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>

<script language="javascript" src="./js/jquery.form.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script type="text/javascript">
        function uploadImage() {
            var options = {
                url: "<%=path%>/uploadFile.action",
                type: "POST",
                dataType: "text",
                success: function (msg) {
                    if (msg.indexOf("#") > 0) {
                        var data = msg.split("#");
                        $("#tipDiv").html(data[0]);
                        $("#showImage").html("<img src="+ data[1]+">");
                    } else {
                        $("#tipDiv").html(msg);
                    }
                }
            };
            $("#form2").ajaxSubmit(options);
            return false;
        }

        $(document).ready(function(){
            $("#showImage").html("<img src='upload/images/189.gif'>");
            $("#fileupload").change(function(){
               $("#showImage").html("<img src='./img/loading.gif'>");
               t=setTimeout("uploadImage()",200);
           });
        });
    </script>
</head>

<body>
    <form id="form2" method="post" enctype="multipart/form-data">
        <table width="400" border="1" cellspacing="1" cellpadding="10">
            <tr>
                <td height="25">
                    浏览图片：
                </td>
                <td>
                    <input id="fileupload" name="fileupload" type="file"/>

                    <div id="tipDiv"></div>
                </td>
            </tr>

        </table>
    </form>
    <br>
    图片预览
    <div id="showImage"></div>
</body>
</html>
