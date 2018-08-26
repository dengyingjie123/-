<%--
  Created by IntelliJ IDEA.
  User: 小周
  Date: 2015/6/12
  Time: 15:41
  选择回退节点界面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.ClientApplications" %>
<%@ page import="com.youngbook.common.wf.processdefine.Node" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%
    String token = request.getParameter("token");
    int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));  //获得工作流编号
    int intCurrNodeID = Integer.parseInt(request.getParameter("CurrNodeID"));  //获得当前节点编号
%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true">
        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td align="right" valign="top">选择回退的地点:</td>
                <td colspan="4">
                    <select name="NextNode" size="3" multiple id="NextNode<%=token %>"
                            style="width:350px;height:100px;">
                        <%
                            List listNode = ClientApplications.getNextNode(intWorkflowID, intCurrNodeID,Config.getSystemVariable("AutomForward.Transition.Attribute.Up"));
                            Iterator itNode = listNode.iterator();
                            while (itNode.hasNext()) {
                                Node node = (Node) itNode.next();
                                String strNodeName = node.getName();
                                int intNextNodeID = node.getID();
                        %>
                        <option value="<%=intNextNodeID%>"><%=strNodeName %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
        </table>

        <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">

            <a id="btnok<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
               href="javascript:void(0)">确定</a>

            <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
               onClick="fwCloseWindow('RouteRejectWindow<%=token%>')">取消</a>
        </div>
</div>
</body>
</html>
