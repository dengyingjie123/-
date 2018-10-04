<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formAllinpayCircleQuery<%=token %>" name="formAllinpayCircleQuery" action="" method="post">
            <table cellspacing="5">
                <tr>
                    <td>原交易流水号</td>
                    <td><label>
                        <input type="text" name="bizId" id="bizId<%=token%>">
                    </label></td>
                    <td>
                        <a id="btnAllinpayCircleQuerySubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
                    </td>
                    <td>
                        <a id="btnAllinpayCircleQueryReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>
