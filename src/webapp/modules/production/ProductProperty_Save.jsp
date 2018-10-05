<!-- // ProductProperty_Save.jsp /////////////////////////////////////////// -->
<!-- 弹出窗口使用方法，参考JS注释 -->
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
    <form id="formProductProperty<%=token %>" name="formProductProperty" action="" method="post">
      <table border="0" cellspacing="5" cellpadding="0">
        <tr>
          <td align="right">产品名称</td>
          <td>
            <input  type="text" id="productName<%=token %>" name="productProperty.productName" readonly="readonly" style="width:200px"/>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td align="right">类型</td>
          <td><input class="easyui-combotree"  editable="false" type="text" id="typeId<%=token %>" name="productProperty.typeId"  style="width:200px"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td align="right">
          	值
          </td>
          <td><input  type="text" id="value<%=token %>" name="productProperty.value"       style="width:200px"/></td>
          <td><a id="btnProductPropertyCKEditor<%=token %>" class="easyui-linkbutton" iconCls="icon-edit" href="javascript:void(0)">富文本</a></td>
        </tr>
      </table>
      <input  type="hidden" id="sid<%=token %>" name="productProperty.sid" style="width:200px"/>
      <input  type="hidden" id="id<%=token %>" name="productProperty.id" style="width:200px"/>
      <input  type="hidden" id="operatorId<%=token %>" name="productProperty.operatorId"       style="width:200px"/>
      <input  type="hidden" id="state<%=token %>" name="productProperty.state"       style="width:200px"/>
      <input  type="hidden" id="operateTime<%=token %>" name="productProperty.operateTime"       style="width:200px"/>
      <input  type="hidden" id="productId<%=token %>" name="productProperty.productId"       style="width:200px"/>
      <input  type="hidden" id="valueType<%=token %>" name="productProperty.valueType"       style="width:200px"/>
    </form>
  </div>
  <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
    <a id="btnProductPropertySubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductPropertyWindow<%=token%>')">取消</a>
  </div>
</div>
</body>
</html>