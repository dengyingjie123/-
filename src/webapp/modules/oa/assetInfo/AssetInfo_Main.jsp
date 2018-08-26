<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/2
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
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
                <td>名称</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetInfo.name" id="Search_Name<%=token%>" style="width:150px;"/>
                </td>
                <td>所属部门</td>
                <td>
                    <input type="text" class="easyui-combotree" name="assetInfo.departmentId" id="Search_DepartmentId<%=token%>" style="width:150px;"/>
                </td>
                <td>规格型号</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetInfo.specification" id="Search_Specification<%=token%>" style="width:150px;"/>
                </td>

                <td>单价</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="Search_UnitPrice_Start" id="Search_UnitPrice_Start<%=token%>" style="width:50px;"/>
                    至
                    <input type="text" class="easyui-validatebox" name="Search_UnitPrice_End" id="Search_UnitPrice_End<%=token%>" style="width:50px;"/>
                </td>
            </tr> <tr>
                <td>金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="Search_Money_Start" id="Search_Money_Start<%=token%>" style="width:50px;"/>
                    至
                    <input type="text" class="easyui-validatebox" name="Search_Money_End" id="Search_Money_End<%=token%>" style="width:50px;"/>
                </td>
                <td>
                    开始采购时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_BuyTime_Start" id="Search_BuyTime_Start<%=token%>" editable="false" style="width:150px;"/>

                </td>
                <td>
                    结束采购时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_BuyTime_End" id="Search_BuyTime_End<%=token%>" editable="false" style="width:150px;"/>
                </td>
        </tr> <tr>
                <td>存放地点</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetInfo.storagePlace" id="Search_StoragePlace<%=token%>" style="width:150px;"/>
                </td>
                <td>保管人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetInfo.keeperName" id="Search_KeeperId<%=token%>" style="width:150px;"/>
                </td>
                <td>
                    <a id="btnAssetInfoSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnAssetInfoSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="AssetInfoTable<%=token%>"></table>

</div>
</body>
</html>