<jsp:include page="../checkLogin.jsp"></jsp:include>
<%
    /**
     * 程序：李扬
     * 时间：2004-10-28
     * 说明：保存业务数据配置
     *
     */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>


<%
    String strActions = (String)request.getParameter("Actions");
//获得工作流编号
    String strWorkflowID = (String)request.getParameter("WorkflowID");

    if (strWorkflowID == null || strWorkflowID.equals("")) {
        throw new Exception("执行SaveBizData.jsp页面发生异常，无法获得工作流编号！");
    }
//获得工作流名称
    String strWorkflowName = ProcessInfo.getWorkflwoName(Integer.parseInt(strWorkflowID));
    BizData bd = new BizData();

//根据新表名，更改XML中业务数据配置
    if (strActions != null && strActions.equals("ChangeTable")) {
        strWorkflowID = request.getParameter("WorkflowID");
        String strTableName = request.getParameter("TableName");
        bd = new BizData();
        bd.setWorkflowID(Integer.parseInt(strWorkflowID));
        bd.setDataTable(strTableName);
        bd.initBizField();
    }
    else {
        //查找XML中的业务数据配置
        bd = new BizData();
        //设置工作流编号
        bd.setWorkflowID(Integer.parseInt(strWorkflowID));
        //根据工作流编号，查找出对应的Element对象
        List listElem = bd.search();

        if (listElem != null && listElem.size() > 0) {
            Element elem = (Element)listElem.get(0);
            bd = bd.buildObject(elem);
        }
    }
%>
<html>
<head>
    <script language="javascript">
        function  submitForm(form, target) {
            if (target == "ChangeTable") {
                if (checkForm(form)) {
                    form.acton = "SaveBizData.jsp";
                    form.Actions.value = "ChangeTable";
                    form.submit();
                }
                else {
                    window.alert("请输入正确的数据库表名");
                }
            }
            if (target == "SaveBizData") {
                if (checkForm(form)) {
                    form.action = "SaveBizDataOK.jsp";
                    form.Actions.value = "SaveBizData";
                    form.submit();
                }
                else {
                    window.alert("请输入正确的数据库表名");
                }
            }
        }
        function checkForm(form) {
            if (form.WorkflowID.value == "" || form.TableName.value == "") {
                return false;
            }
            else {
                return true;
            }
        }
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>保存数据业务配置</title>
    <link href="../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<form name="form" method="post" action="">
    <center>
        <font style="font-size:12pt "><strong>保存业务数据配置</strong></font><BR><BR>
        <table width="90%" class="TableINBgStyle"  border=0 align="center" cellpadding=0 cellspacing=1>
            <tr class="TableBgStyle">
                <td ><div align="right">工作流名称：</div></td>
                <td class="TableTdBgStyle"><input name="WorkflowName" type="text" class="TextReadOnlyStyle" id="WorkflowName" value="<%=strWorkflowName%>" readonly="true">
                    <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=strWorkflowID%>"></td>
            </tr>
            <tr class="TableBgStyle">
                <td ><div align="right">表名:&nbsp; </div></td>
                <td class="TableTdBgStyle"><input name="TableName" type="text" class="TextStyle" id="TableName" value="<%=bd.getDataTable()%>">
                    <input name="Button" type="button" class="ButtonStyle" onClick="submitForm(this.form, 'ChangeTable')" value="更改数据表"></td>
            </tr>
            <tr>
                <td valign="top" class="TableBgStyle"><div align="right"><br>
                    字段：</div></td>
                <td><table width="100%"  border=0 align="center" cellpadding=0 cellspacing=1>
                    <tr class="DateListTableHeadStyle">
                        <td height="20" align="center">编号</td>
                        <td align="center">字段名</td>
                        <td align="center">显示名</td>
                        <td align="center">类型</td>
                    </tr>
                    <%
                        List listBF = bd.getBizField();  //获得字段信息
                        Iterator itBF = listBF.iterator();
                        while (itBF.hasNext()) {
                            BizField bf = (BizField)itBF.next();
                            String strIndex = String.valueOf(bf.getIndex());
                            String strName = bf.getName();  //获得字段名
                            String strTitle = bf.getTitle();  //获得字段显示名
                            String strType = bf.getType();  //获得字段类型
                    %>
                    <tr class="TableBgStyle">
                        <td><div align="center"><%=strIndex%></div></td>
                        <td><div align="center"><%=strName%></div></td>
                        <td><div align="center">
                            <input name="Title" type="text" class="TextStyle" id="Title" value="<%=strTitle%>">
                        </div></td>
                        <td><div align="center"><%=strType%></div></td>
                    </tr>
                    <%
                        }
                    %>
                </table></td>
            </tr>
        </table></td>
        </tr>
        </table>
        <div align="center"><br>
            <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="submitForm(this.form, 'SaveBizData')" value="保存">
            <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt "  value="重写">
            <input name="Actions" type="hidden" id="Actions" value="">
        </div>
        </center>
</form>
</body>
</html>
