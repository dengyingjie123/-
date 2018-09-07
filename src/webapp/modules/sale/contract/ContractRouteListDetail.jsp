<%@ page import="com.youngbook.entity.vo.Sale.contract.ContractRouteListVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.service.sale.contract.ContractService" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="java.sql.SQLException" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 销售合同流转列表
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
    String contractNo = request.getParameter("contractNo");
    int status = Integer.parseInt(request.getParameter("status"));

    String orgId = request.getParameter("orgId");
    List<KVObject> conditions = new ArrayList<KVObject>();
    ContractService service = new ContractService();
    Connection conn = Config.getConnection();

    ContractRouteListVO c1 = new ContractRouteListVO();
    List<ContractRouteListVO> contractRouteListVOList = new ArrayList<ContractRouteListVO>();

    try {
        contractRouteListVOList = service.listContractRouteListVOs(contractNo, conditions, conn);
        if (contractRouteListVOList.size() == 0) {
            return;
        }
        c1 = contractRouteListVOList.get(0);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
%>
<html>
<head>
    <title>销售合同流转详情</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <link rel="stylesheet" type="text/css"
          href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.css">
    <link rel="stylesheet" type="text/css"
          href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.print.css" media='print'>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript"
            src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div class="easyui-layout" style="padding:5px;">
    <table class="easyui-datagrid" title="合同概述" style="padding:10px;" data-options="fitColumns:true,singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'code',width:100">合同号</th>
            <th data-options="field:'name',width:100">产品名称</th>
            <th data-options="field:'price',width:100">合同状态</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><%=contractNo%>
            </td>
            <td><%=c1.getProductionName()%>
            </td>
            <td><% if (status == 0) {

                out.print("签约");
            } else if (status == 1) {

                out.print("未签约");
            } else if (status == 2) {
                out.print("作废");
            } else {
                out.print("");
            }%></td>
        </tr>
        </tbody>
    </table>
</div>
<div class="easyui-layout" style="padding:5px;">
    <table class="easyui-datagrid" title="流转状态" style="padding:10px;" data-options="fitColumns:false,singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'code1'">序号</th>
            <th data-options="field:'code2'">时间</th>
            <th data-options="field:'code4'">操作人</th>
            <th data-options="field:'code6'">备注</th>
        </tr>
        </thead>
        <tbody>
        <%
            int i = contractRouteListVOList.size();
            for (ContractRouteListVO c : contractRouteListVOList) {
        %>
        <tr>
            <td><%=i--%></td>
            <td><%=c.getActionTime()%></td>
            <td><%=c.getActionUserName()%></td>
            <td><%=c.getActionDescription()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
