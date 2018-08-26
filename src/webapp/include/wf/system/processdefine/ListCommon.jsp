<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>

<script language="javascript">

//判断表单是否做了唯一选择
function doSelect(intCounter) {
  document.all("WorkFlowID" + intCounter).checked = true;
}
</script>
<link href="../style/common.css" rel="stylesheet" type="text/css">
      <br>
      <table class="TabledStyle"  border=0 align="center" cellpadding=0 cellspacing=1>
            <tr class="DateListTableHeadStyle" align="center">
              <td width="41" height="20"><div align="center">选中</div></td>
              <td width="80"><div align="center">编号</div></td>
              <td width="93"><div align="center">名称</div></td>
              <td width="93"><div align="center">说明</div></td>
              <td width="93"><div align="center">创建日期</div></td>
              <td width="150" align="center">记录用户动作</td>
              <td width="160"><div align="center">记录数据历史</div></td>
            </tr>
            <%
//根据ProcessInfo.java中的searchObject()方法循环查询出存在的工作流信息
           ProcessInfo processInfo = new ProcessInfo();
           List listDef = processInfo.searchObject();
           Iterator itDef = listDef.iterator();
           int intCounter = 0;
           while(itDef.hasNext()){
             intCounter++;
             processInfo = (ProcessInfo)itDef.next();
             String strID = String.valueOf(processInfo.getID());
             String strName = processInfo.getName();
             String strInfo = processInfo.getInfo();
             String strDate = processInfo.getCreateDate();
             boolean bolSaveAction = processInfo.getSaveAction();
             boolean bolSaveHistory = processInfo.getSaveHistory();
			 String strSaveAction=bolSaveAction?"是":"否";
			 String strSaveHistory=bolSaveHistory?"是":"否";
			 %>
            <tr style="cursor:hand" onClick="doSelect('<%=intCounter%>')">
              <td height="25" class="TableBgStyle"><div align="center">
                <input name="WorkflowID" checked id="WorkFlowID<%= intCounter %>" type="radio" value="<%= processInfo.getID() %>" onClick="doSelect('<%=intCounter%>')">
				<input type="hidden" name="searchID<%= intCounter %>" value="<%= strID %>">
              </div></td>
              <td width="80" class="TableBgStyle"><div align="center"><%=strID%></div></td>
              <td width="93" class="TableBgStyle"><div align="center"><%=strName%></div></td>
              <td width="93" class="TableBgStyle"><div align="center"><%=strInfo%></div></td>
              <td width="93" class="TableBgStyle"><div align="center"><%=strDate%><br></div></td>
              <td width="150" class="TableBgStyle"><div align="center"><%=strSaveAction%></div></td>
              <td width="160" class="TableBgStyle"><div align="center"><%=strSaveHistory%></div></td>
            </tr>
            <%
        }
        %>
          </table>

	  <input type="hidden" name="intCounter" value="<%= intCounter %>">
