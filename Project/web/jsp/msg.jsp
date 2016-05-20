<%
    String msg = (String) request.getAttribute("msg");
    if(msg!=null && !"".equals(msg)) {
        msg = msg.replace("\"", "\\\"");
%>
<script language="javascript">
    alert("<%=msg%>");
</script>
<%
    }
%>
