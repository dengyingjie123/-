<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token") ;
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
                <td>组织编号</td>
                <td><input type="text" id="search_OrgId<%=token %>" name="financemeetingapplicationwfa.orgId" style="width:100px;" /></td>
                <td>名称</td>
                <td><input type="text" id="search_Name<%=token %>" name="financemeetingapplicationwfa.name" style="width:100px;" /></td>

                <td>地点</td>
                <td><input type="text" id="search_Address<%=token %>" name="financemeetingapplicationwfa.address" style="width:100px;" /></td>
                <td>参与人员</td>
                <td><input type="text" id="search_Participant<%=token %>" name="financemeetingapplicationwfa.participant" style="width:100px;" /></td>

                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>

            </tr>
            <tr>
                <td>开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td>结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_EndTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_EndTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="FinancemeetingTable<%=token%>"></table>

</div>
</body>
</html>
