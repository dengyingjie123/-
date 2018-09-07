<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：列出所有节点，用于修改删除
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<% //Session Bean 用于保存新增的节点信息 %>
<jsp:useBean id="newNode" class="com.youngbook.common.wf.processdefine.Node" scope="session">
</jsp:useBean>
<% //Session Bean 用于保存修改的节点信息 %>
<jsp:useBean id="modNode" class="com.youngbook.common.wf.processdefine.Node" scope="session">
</jsp:useBean>

<%
//销毁原先的Session Bean，防止新建Session Bean出错
newNode.destroy();
modNode.destroy();
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      <script src="../../script/CheckBox.js" type=""></script>
      <script language="JavaScript" type="">
        function submitForm(form, target) {
          if (target == "Add") {
            form.action = "Node_Add.jsp";
            form.submit();
          }
          if (target == "ModNode") {
            if (checkForm(form)) {
              form.action = "Node_Mod.jsp";
              form.submit();
            }
          }
          if (target == "Del") {
            if (checkForm(form)) {
              if (confirm("是否确认删除该节点？")) {
                form.action = "Node_Del_Done.jsp";
                form.submit();
              }
            }
          }
          if (target == "QX") {
            if (checkForm(form)) {
              form.action = "Node_QX.jsp";
              form.submit();
            }
          }
        }
        function checkForm(form) {
          if (!isCheckedUnique(form, "ID"))
          return false;
          else
          return true;
        }

function doSelect(intCounter) {
		document.all("ID" + intCounter).checked = true;
}
        </script>
        <title>节点列表</title>
        <link href="../../style/common.css" rel="stylesheet" type="text/css">
         </head>
        <%
        //从request（SelectWorkflow页面）中获取工作流编号
        String strWorkflowID = (String)request.getParameter("WorkflowID");
        //实例化一个临时节点对象，循环列出供用户操作
        Node node = new Node();
        List listNode = new ArrayList();
        //判断工作流编号是否为空
        if (strWorkflowID != null && !strWorkflowID.equals("")) {
          int intWorkflowID = Integer.parseInt(strWorkflowID);
          //设置节点的工作流编号
          node.setWorkflowID(intWorkflowID);
        }
        %>
<body>
 <form name="form" method="post" action="">
                  <div align="center">
		<font style="font-size:12pt "><strong>选择节点</strong></font><br><br><table width="90%"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
                        <tr class="DateListTableHeadStyle">
                          <td width="14%" height="20"><div align="center">选择</div></td>
                          <td width="14%"><div align="center">节点编号</div></td>
                          <td width="30%"><div align="center">节点名称</div></td>
                          <td width="27%"><div align="center">节点状态</div></td>
                          <td width="15%"><div align="center">节点类型</div></td>
                        </tr>
                        <%
                        //根据获得的工作流编号，查找该工作流下的所有节点
                        listNode = node.searchNodeObject();
                        Iterator itNode = listNode.iterator();
						int intCounter = 0;
                        //循环获得节点信息
                        while (itNode.hasNext()) {
						  intCounter++;
                          node = (Node)itNode.next();
                          String strID = String.valueOf(node.getID());  //获得节点编号
                          String strName = node.getName();  //获得节点名称
                          String strState = node.getStatu();  //获得接单状态（0：开始节点； 1：中间节点； 2：结束节点）
                          String strType = new String();  //将节点状态转换成为文字信息
                          switch (node.getType()) {
                            case 0: strType = "开始节点";
                            break;
                            case 1: strType = "中间节点";
                            break;
                            case 2: strType = "结束节点";
                            break;
                            default: strType = "未知类型";
                          }
                          %>
                          <tr class="TableBgStyle" onClick="doSelect('<%=intCounter%>')" style="cursor:hand">
                            <td height="20" class="TableBgStyle"><div align="center">
                              <input name="ID" type="radio" checked  value="<%=strID%>" id="ID<%= intCounter %>" onClick="doSelect('<%=intCounter%>')">
                            </div></td>
                              <td class="TableBgStyle"><div align="center"><%=strID%></div></td>
                              <td class="TableBgStyle"><div align="center"><%=strName%>
                                <input name="Name" type="hidden" id="Name" value="<%=strName%>" />
                            </div></td>
                                <td><div align="center"><%=strState%></div></td>
                                <td><div align="center"><%=strType%></div></td>
                        </tr>
                              <%
                              }
                              %>
                      </table>
                          <br>
                            <input type="hidden" name="WorkflowID" value="<%=strWorkflowID%>"/>
                              <input name="Button" type="button" class="ButtonStyle" style="WIDTH:60pt" onclick="submitForm(this.form, 'Add')" value="新增">
                                <input name="Submit" type="submit" class="ButtonStyle" style="WIDTH:60pt" onclick="submitForm(this.form, 'ModNode')" value="配置">
                                  <input name="Submit" type="submit" class="ButtonStyle" style="WIDTH:60pt" onclick="submitForm(this.form, 'Del')" value="删除">
                                    <input name="Button" type="button" class="ButtonStyle" style="WIDTH:60pt" onClick="submitForm(this.form, 'QX')" value="权限配置">
                                      <input name="Reset" type="reset" class="ButtonStyle" style="WIDTH:60pt" value="重置">
                  </div>
                                    </form>
                             </body>
                          </html>
