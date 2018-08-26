<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;

    ButtonPO btnAdd = new ButtonPO("btnSalemanGroupAdd" + token, "添加", "icon-add","系统管理-销售组管理-新增");
    ButtonPO btnEdit = new ButtonPO("btnSalemanGroupEdit" + token, "修改", "icon-edit","系统管理-销售组管理-修改");
    ButtonPO btnDelete= new ButtonPO("btnSalemanGroupDelete" + token, "删除", "icon-cut","系统管理-销售组管理-删除");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDelete);
%>
<html>
<head>
  <title></title>
</head>
<body>
<div class="easyui-layout" fit="true" style="padding:5px;">

  <div class="easyui-panel" title="查询" iconCls="icon-search">
    <table border="0" cellpadding="3" cellspacing="0">
      <tr>
        <td>公司</td>
        <td>
          <input type="text" class="easyui-combotree" name="salemanGroup.departmentId" id="Search_departmentId<%=token%>" style="width:200px;"/>
        </td>
          <td>组名</td>
        <td>
          <input type="text" class="easyui-validatebox" name="salemanGroup.name" id="Search_Name<%=token%>" style="width:200px;"/>
        </td>
        <td>描述</td>
        <td>
          <input type="text" class="easyui-validatebox" name="salemanGroup.description" id="Search_Description<%=token%>" style="width:200px;"/>
        </td>
        <td>
          <a id="btnSalemanGroupSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-search">查询</a>
        </td>
        <td>
          <a id="btnSalemanGroupSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-cut">重置</a>
        </td>
      </tr>
    </table>
  </div>
  <br>
    <table id="SalemanGroupTable<%=token%>" data-options="toolbar:toolbar"></table>
    <div id="SalemanGroupSelectArea<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelect<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('SalemanGroupSelectWindow<%=token%>')">取消</a>
    </div>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>