<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--投资参与者--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>
                    投资计划名称
                </td>
                <td>
                    <input type="text" id="search_investmentplanName<%=token %>" style="width:150px;"/>
                </td>
                <td>
                    客户名称
                </td>
                <td>
                    <input type="text" id="search_customerName<%=token %>" style="width:150px;"/>
                </td>
                <td>
                    参与状态
                </td>
                <td>
                    <input type="text" class="easyui-combotree" id="search_statusName<%=token %>" style="width:150px;"
                           editable="false"/>
                </td>
                <td>
                    参与类型
                </td>
                <td>
                    <input type="text" class="easyui-combotree" id="search_typeName<%=token %>" style="width:150px;"
                           editable="false"/>
                </td>
            </tr>
            <tr>
                <td>
                    开始参与时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" id="search_JoinTime_Start<%=token %>" style="width:150px;"
                           editable="false"/>
                </td>
                <td>
                    结束参与时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" id="search_JoinTime_End<%=token %>" style="width:150px;"
                           editable="false"/>
                </td>
                <td>
                    开始预约时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" id="search_AppointmentTime_Start<%=token %>"
                           style="width:150px;"
                           editable="false"/>
                </td>
                <td>
                    结束预约时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" id="search_AppointmentTime_End<%=token %>"
                           style="width:150px;"
                           editable="false"/>
                </td>
            </tr>
            <tr>
                <td>
                    开始退出时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" id="search_QuitTime_Start<%=token %>" style="width:150px;"
                           editable="false"/>
                </td>
                <td>
                    结束退出时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" id="search_QuitTime_End<%=token %>" style="width:150px;"
                           editable="false"/>
                </td>
                <td></td>
                <td>
                    <a id="btninvestmentParticipantSearchSubmit<%=token %>" href="javascript:void(0)"
                       class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btninvestmentParticipantSearchReset<%=token %>" href="javascript:void(0)"
                       class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="InvestmentParticipantTable<%=token%>"></table>

</div>
</body>
</html>
