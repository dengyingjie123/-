<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/7/3
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style='padding:5px;'>
    <div class='easyui-panel' title='查询' iconCls='icon-search'>
        <table border='0' cellpadding='3' cellspacing='0'>
            <tr>
                <td align="right">主题</td>
                <td><input   type="text" id="search_subject<%=token %>"     style="width:200px"/></td>
                <td>内容</td>
                <td> <input  type="text" id="search_content<%=token %>"  style="width:200px;"/></td>
                <td>类型</td>
                <td> <input  type="text"  class="easyui-combobox" id="search_type<%=token %>"  style="width:200px;"/></td>
                <td>
                    <a id='btnSearchSubmit<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-search'>查询</a>
                </td>
                <td>
                    <a id='btnSearchReset<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-cut'>重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <div  style="height:480px;">
        <div id="smsTable<%=token%>" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
            <div  title="收件箱" id="ReceiveTab"  style="padding:5px ;" >
                <table id="SmsReceiverTable<%=token%>" ></table>
            </div>
            <div  title="发件箱"  id="sendTab" style="padding:5px ;" >
                <table id="SmsSenderTable<%=token%>" ></table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
