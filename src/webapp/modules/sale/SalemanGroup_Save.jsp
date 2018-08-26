<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
  String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
  <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
    <form id="formSalemanGroup<%=token %>" name="formSalemanGroup" action="" method="post">
      <table border="0" cellspacing="5" cellpadding="0">
        <tr>
          <td align="right">公司</td>
          <td><input class="easyui-combotree" type="text" id="departmentId<%=token %>" name="salemanGroup.departmentId"  required="true" missingmessage="必须填写"     style="width:550px"/></td>
          </tr>
          <tr>
          <td align="right">组名</td>
          <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="salemanGroup.name"  required="true" missingmessage="必须填写"     style="width:550px"/></td>
          </tr>
          <tr>
            <td align="right">区域</td>
            <td><input class="easyui-combotree"  editable="false" type="text" id="areaId<%=token %>" name="salemanGroup.areaId"  required="true" missingmessage="必须填写"  style="width:180px"/></td>
          </tr>
          <tr>
          <td align="right">描述</td>
          <td><textarea  type="text" id="description<%=token %>" name="salemanGroup.description"       style="width:550px;height:25px;resize:none;"></textarea></td>
        </tr>
      </table>
        <input  type="hidden" id="sid<%=token %>" name="salemanGroup.sid"       style="width:200px"/>
        <input  type="hidden" id="id<%=token %>" name="salemanGroup.id"       style="width:200px"/>
        <input  type="hidden" id="state<%=token %>" name="salemanGroup.state"       style="width:200px"/>
        <input  type="hidden" id="operatorId<%=token %>" name="salemanGroup.operatorId"       style="width:200px"/>
    </form>
    <div  style="height:280px">
        <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
            <div  title="成员列表"  style="padding:5px ;" >
                <table id="SaleManTable<%=token%>" ></table>
            </div>
        </div>
    </div>
  </div>
  <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
    <a id="btnSalemanGroupSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
       href="javascript:void(0)">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
       onClick="fwCloseWindow('SalemanGroupWindow<%=token%>')">取消</a>
  </div>
</div>
</body>
</html>
