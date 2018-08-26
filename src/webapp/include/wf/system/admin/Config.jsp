<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.Config" %>
<%@ page import="com.youngbook.common.wf.common.Tools" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="javax.servlet.http.*" %>

<%
//根据后台Config.java类的方法得到页面信息
  Config workflowConfig = new Config();
  workflowConfig.Read();
%>
<html>
<script language="javascript">
function submitForm(form,str){

 if(str == "save"){
  if(checkForm(form)){
  	if(checkPwd(form)){
   form.action="SaveConfig.jsp";
   form.target="_self";
   form.submit();
   }
  }
 }

//判断连接测试，若连接测试成功则弹出连接测试成功窗口
 if(str == "TestLink"){
  if(checkForm(form)){
  form.hidden.value="TestLink";
  form.action="TestLink.jsp";
  form.target="TestLink";
	window.open('','TestLink','width=400,height=250');
  form.submit();
 }
 }
}
//检查表单数据，数据库地址、用户名、密码、Sid项不能为空
function checkForm(form){
 if((form.Addr.value.length == 0)||
    (form.User.value.length == 0)||
    (form.Pwd.value.length == 0)||
    (form.Sid.value.length == 0)){
 alert("数据库地址、用户名、密码、Sid必须填写!");
 return false;
 }
 else
  return true;
}

//检查确认密码与管理员密码是否一致
function checkPwd(form){
	if(form.AdminPwd.value != form.CheckPwd.value){
		alert("确认密码与管理员密码不一致，请重新输入！");
		return false;
	}
	else
		return true;
}

</script>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<body>
<form action="Config.jsp" method="post">
<p><br></p>
  <center><font style="font-size:12pt"><strong>环境配置</strong></font>
            <br><br>
				<table width="48%"  class="TabledStyle"  border=0 align="center" cellpadding=0 cellspacing=1>
				<tr class="TableBgStyle">
                      <td width="51%" height="25" align="right" class="TableBgStyle">管理员密码：</td>
                      <td><input name="AdminPwd" type="password" class="TextStyle" id="AdminPwd" style="WIDTH:100pt" value="<%=workflowConfig.getAdminPwd()%>">
                      </td>
                    </tr>
				<tr class="TableBgStyle">
                      <td width="51%" height="25" align="right" class="TableBgStyle">密码确认：</td>
                      <td><input name="CheckPwd" type="password" class="TextStyle" id="CheckPwd" style="WIDTH:100pt" value="<%=workflowConfig.getAdminPwd()%>">
                      </td>
                    </tr>
                <tr class="TableBgStyle">
                      <td height="25" align="right"  >数据库地址：</td>
                      <td>
                        <input name="Addr" type="text" class="TextStyle" id="Addr" style="WIDTH:100pt"value="<%=workflowConfig.getDBAddr()%>">
                      </td>
                    </tr>
                    <tr class="TableBgStyle">
                      <td height="25" align="right">数据库用户名：</td>
                      <td>
                         <input name="User" type="text" class="TextStyle" id="User" style="WIDTH:100pt" value="<%=workflowConfig.getDBUserName()%>">
                      </td>
                    </tr>
                    <tr class="TableBgStyle">
                      <td height="25" align="right">数据库密码：</td>
                      <td>
                         <input name="Pwd" type="password" class="TextStyle" id="Pwd" style="WIDTH:100pt" value="<%=workflowConfig.getDBPwd()%>">
                      </td>
                    </tr>
                    <tr class="TableBgStyle">
                      <td height="25" align="right" class="TableBgStyle">数据库SID：</td>
                      <td>
                         <input name="Sid" type="text" class="TextStyle" id="Sid" style="WIDTH:100pt" value="<%=workflowConfig.getSid()%>">
                      </td>
                    </tr>
                    <tr class="TableBgStyle">
                      <td height="25" align="right" class="TableBgStyle">用户信息表名：</td>
                      <td>
                        <input name="TableName" type="text" class="TextStyle" id="TableName" style="WIDTH:100pt" value="<%=workflowConfig.getUserTable()%>">
                      </td>
                    </tr>
                    <tr class="TableBgStyle">
                      <td height="25" align="right" class="TableBgStyle">用户名字段名称：</td>
                      <td><input name="UserName" type="text" class="TextStyle" id="UserName" style="WIDTH:100pt" value="<%=workflowConfig.getUserName()%>">                      </td>
                    </tr>
                    <tr class="TableBgStyle">
                      <td height="25" align="right" class="TableBgStyle">用户真实姓名字段名称：</td>
                      <td><input name="TrueName" type="text" class="TextStyle" id="TrueName" style="WIDTH:100pt" value="<%=workflowConfig.getTrueName()%>">
                      </td>
                    </tr>
                  </table>
              <div align="center"><br>
                  <input type="hidden" id="hidden" name="hidden" value="">
                  <input name="Submit" type="submit" class="ButtonStyle" style="WIDTH:60pt" onclick="submitForm(this.form,'TestLink')" value="连接测试">
                  <input type="button" style="WIDTH:60pt" value="保存" class="ButtonStyle" onclick="submitForm(this.form,'save')">
              </div>
</form>
</body>
</html>

