<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/15/14
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
	String index = request.getParameter("index");
    String root = Config.getWebRoot();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
    <script type="javascript">
        $("input[type='radio']:checked").val();

    </script>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formProductionDes<%=token %>" name="formProduction" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">标题</td>
                    <td><input type="text" id="title<%=token %>" name="productionInfo.title<%=index %>" class="easyui-validatebox" required="true"
                               missingmessage="必须填写"  style="width:620px"/>
                     </td>
                    </tr>
                    <tr>
                    <td align="right" valign="top">产品描述</td>
                    <td><textarea id="content<%=token %>" name="productionInfo.content<%=index %>" style="width:700px"></textarea>
                    </td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">是否显示</td>
                        <td><input type="text" class="easyui-combotree" name="productionInfo.isDisplay<%=index %>" id="isDisplay<%=token %>" style="width:100px;" editable="false" /></td>
                    </tr>
            </table>
            <input type="hidden" name="productionInfo.id" id="productionInfoId<%=token %>" />
            <input type="hidden" name="index" id="index<%=token %>" />

            <input type="hidden" name="productionInfo.productionId" id="productionId<%=token %>" />
        </form>
    </div>

    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionDesSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ProductionDesWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>