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
        <form id="formAllinpayCircleWithdrawByBankNormal<%=token %>" name="formAllinpayCircleWithdrawByBankNormal" action="" method="post">
            <table cellspacing="5">
                <tr>
                    <td>取现金额</td>
                    <td><label>
                        <input type="text" name="money" id="money<%=token%>">
                    </label></td>
                    <td>
                        <a id="btnAllinpayCircleWithdrawByBankNormalSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">取现</a>
                    </td>
                    <td>
                        <a id="btnAllinpayCircleWithdrawByBankNormalReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                    </td>
                </tr>
            </table>
            <input type="text" name="accountId" id="accountId<%=token%>">
        </form>
    </div>
</div>

</body>
</html>
