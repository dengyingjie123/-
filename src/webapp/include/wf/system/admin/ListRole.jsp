<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：角色配置页面，列出所有角色
 */
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>工作流基本信息</title>
<script language="javascript">
function submitForm(form,str){
 if(str == "modify"){
  if(isCheckedUnique(form,"ID")){
   form.action = "RoleModify.jsp";
   form.target = "_self";
   form.submit();
   }
 }
 if(str == "delete"){
   if(isCheckedUnique(form,"ID")){
     if (confirm("是否确定删除此角色？")) {
       form.action = "RoleDelDone.jsp";
       form.target = "_self";
       form.submit();
     }
   }
 }
 if(str == "add"){
   form.action = "RoleAdd.jsp";
   form.target = "_self";
   form.submit();
 }
}


function doSelect(intCounter) {
  if (document.all("ID" + intCounter).checked == false) {
    document.all("ID" + intCounter).checked = true;
  }
}

//检查是否选择了唯一的复选框
function isCheckedUnique(form, CheckBoxName) {
  var selectedCount = 0;
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i];
    if (element.name.indexOf(CheckBoxName) >= 0 &&
    	element.name.indexOf("SELECTALL") < 0) {
      if (element.checked) {
        selectedCount = selectedCount + 1;
      }
    }
  }
  if (selectedCount != 1) {
    alert("输入错误，请做出唯一一项选择！");
    return false;
  }
  return true;
}
</script>
<link href="../style/common.css" rel="stylesheet" type="text/css">
</head>

<body topMargin="5" leftMargin="5">
<table align="center" width="90%" border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td height="10"></td>
  </tr>
  <tr>
    <td align="center" height="20"><font style="font-size:12pt"><strong><br>角色管理</strong></font></td>
  </tr>
  <tr>
    <td><br>
<form name="form" method="post" id="form" action="">
  <div align="center">
    <table width="440"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
          <tr class="DateListTableHeadStyle">
            <td width="32" height="20"><div align="center">选择 </div></td>
            <td width="95" align="center">编号</td>
            <td width="306" align="center">角色名称</td>
          </tr>
          <%

          Role role = new Role();
          //查询所有角色
          List listRole = role.query();
          Iterator itRole = listRole.iterator();
          int intCounter = 0;
          while(itRole.hasNext()){
            intCounter++;
            role = (Role)itRole.next();
            int intID = role.getID();  //角色编号
            String strRoleName = role.getRoleName();  //角色名称
            %>
          <tr class="TableBgStyle" style="cursor:hand" onClick="doSelect('<%=intCounter%>')">
            <td class="TableTdBgStyle" height="20">
              <div align="center">
                <input name="ID" checked id="ID<%= intCounter %>" type="radio" value="<%=role.getID() %>" onClick="doSelect('<%=intCounter%>')">
              </div></td>
            <td width="95" align="center"><%=intID%></td>
            <td width="306" align="center"><%=strRoleName%></td>
          </tr>
          <%
        }
        %>
        </table>
    <br>
      <input name="RequestID" type="hidden" id="RequestID">
      <input name="button" type="button" class="ButtonStyle" id="add" style="width:60pt" value="新增" onClick="submitForm(this.form, 'add')">
      <input name="button" type="button" class="ButtonStyle" id="modify" style="width:60pt" value="修改" onClick="submitForm(this.form, 'modify')">
      <input name="button1" type="button" class="ButtonStyle" id="delete" style="width:60pt" value="删除" onClick="submitForm(this.form, 'delete')">
      <input name="button" type="reset" class="ButtonStyle" style="width:60pt" value="重写">
      <br>
  </div>
      </form>
</td>
</tr></table>
</body>
</html>
