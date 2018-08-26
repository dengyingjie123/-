<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/1
  Time: 17:32
  To change this template use File | Settings | File Templates.
  用章类型保存页面
--%>
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
        <form id="formSealUsageItem<%=token %>" name="formSealUsageItem" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">印章名称</td>
                    <td><input type="text" class="easyui-combotree" id="sealId<%=token %>" name="sealUsageItem.sealId" style="width:250px"/>
                    </td>
               </tr><tr>
                    <td align="right">印章份数</td>
                    <td><input type="text" class="easyui-validatebox" data-options="required:true,validType:'number'" id="topies<%=token %>" name="sealUsageItem.topies" style="width:250px"/>
                    </td>
                </tr>

            </table>
                <%--<td align="right">印章编号</td>--%>
            <input type="hidden" id="sealName<%=token %>" name="sealUsageItem.sealName" style="width:200px"/>
             <%--id--%>
            <input type="hidden" id="id<%=token %>" name="sealUsageItem.id" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="sealUsageItem.sid" style="width:200px"/>
          <%--operatorId--%>
            <input type="hidden" id="operatorId<%=token %>" name="sealUsageItem.operatorId" style="width:200px"/>
            <%--operateTime--%>
            <input type="hidden" id="operateTime<%=token %>" name="sealUsageItem.operateTime" style="width:200px"/>
            <%--申请编号--%>
            <input type="hidden" id="applicationId<%=token %>" name="sealUsageItem.applicationId" style="width:200px"/>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSealUsageItemSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('SealUsageItemWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>

