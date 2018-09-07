<jsp:include page="../checkLogin.jsp"></jsp:include>
<%
    /**
     * 程序：李扬
     * 时间：2004-10-28
     * 说明：保存业务数据配置成功
     *
     */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>


<html>
<head>
    <title>保存数据业务配置成功</title>
    <link href="../style/common.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        <!--
        .style2 {
            color: #FFFFFF;
            font-weight: bold;
        }
        -->
    </style>
</head>
<%
    BizData bd = new BizData();
    String strActions = request.getParameter("Actions");
//获得工作流编号
    String strWorkflowID = request.getParameter("WorkflowID");
    if (strActions != null && strActions.equals("SaveBizData")) {
        //重新根据新表名，构造新的 BizData
        String strTableName = request.getParameter("TableName");
        bd = new BizData();
        bd.setWorkflowID(Integer.parseInt(strWorkflowID));

        bd.setDataTable(strTableName);
        bd.initBizField();
        //更新业务数据配置的 Title
        String[] strTitle = request.getParameterValues("Title");
        for (int i = 0; i < strTitle.length; i++) {
            List listBizField = bd.getBizField();
            BizField bf = (BizField)listBizField.get(i);
            bf.setTitle(strTitle[i]);
        }
        //跟新业务数据配置
        bd.update();
    }
%>
<body>
<div align="center">  <table width="300"  border="0" align="center" cellpadding="0" cellspacing="0" class="TableBorderStyle" id="bg">
    <tr>
        <td height="23" class="TableTdStyle">系统提示:</td>
    </tr>
    <tr>
        <td class="TableBgStyle"><div align="center">
            保存数据业务配置成功</span>!<br>
            <br>
            <a href="AutoMaker/default.jsp?WorkflowID=<%=strWorkflowID%>">自动生成相关代码</a><br>
            <br>
            [ <a href="BizData.jsp">返回</a> ]            <br>
            <br>
        </div></td>
    </tr>
</table>
</div>
</body>
</html>
