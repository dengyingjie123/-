<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/6/5
  Time: 16:17
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
                <td align="right">名称</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetApplication.productName" id="search_productName<%=token%>" style="width:150px;"/>
                </td>
                <td align="right">资产类型</td>
                <td>
                    <input type="text" class="easyui-combotree" name="assetApplication.assetTypeId" editable="false" id="search_AssetTypeId<%=token%>" style="width:150px;"/>
                </td>
                <td align="right">申请部门</td>
                <td>
                    <input type="text" class="easyui-combotree" name="assetApplication.applicationDepartmentId" editable="false" id="search_applicationDepartmentId<%=token%>" style="width:150px;"/>
                </td>
              </tr>
            <tr>
                <td align="right">开始日期</td>
                <td><input type='text' class='easyui-datetimebox' name='search_time_start' id='search_time_start<%=token%>' style='width:150px;' editable='false'/></td>
                <td align="right">至</td>
                <td><input type='text' class='easyui-datetimebox' name='search_time_end' id='search_time_end<%=token%>' style='width:150px;' editable='false'/></td>
                <td align="right">验收开始时间</td>
                <td><input type='text' class='easyui-datetimebox' name='search_reimburseTime_start' id='search_reimburseTime_start<%=token%>' style='width:150px;' editable='false'/></td>
                <td align="right">至</td>
                <td><input type='text' class='easyui-datetimebox' name='search_reimburseTime_end' id='search_reimburseTime_end<%=token%>' style='width:150px;' editable='false'/>
             </tr>

            <tr>
            <tr>
                <td align="right">请购人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetApplication.applicantId" id="search_ApplicantId<%=token%>" style="width:150px;"/>
                </td>
                <td align="right" >申请原因</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="assetApplication.purpose" id="search_Purpose<%=token%>" style="width:150px;"/>
                </td>
            </tr>
                <td>
                    <a id='btnAssetApplicationSearchSubmit<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-search'>查询</a>
                </td>
                <td>
                    <a id='btnAssetApplicationSearchReset<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-cut'>重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <div style="height:400px;">
    <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
        <div title='我的申请' style='padding:5px'>
            <table id='assetApplicationTable<%=token%>'></table>
        </div>
        <div title='等待我审批' style='padding:5px'>
            <table id="WaitassetApplicationTable<%=token%>"></table>
        </div>
        <div title='已完成' style='padding:5px'>
            <table id="articipantassetApplicationTable<%=token%>"></table>
        </div>
    </div></div>
</div>
</body>
</html>