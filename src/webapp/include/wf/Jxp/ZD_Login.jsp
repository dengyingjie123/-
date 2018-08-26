<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script language="javascript">
 function Login(){
   document.forms[0].XM.value=document.forms[0].UserName.options[document.forms[0].UserName.selectedIndex].innerText;
   document.forms[0].submit();
 }
</script>
<title>检修票管理系统</title><meta http-equiv='pragma' content='no-cache'>
<script language="JavaScript" type="text/javascript">
<!--
document._domino_target = "_self";
function _doClick(v, o, t, h) {
  var form = document._Login;
  if (form.onsubmit) {
     var retVal = form.onsubmit();
     if (typeof retVal == "boolean" && retVal == false)
       return false;
  }
  var target = document._domino_target;
  if (o.href != null) {
    if (o.target != null)
       target = o.target;
  } else {
    if (t != null)
      target = t;
  }
  form.target = target;
  form.__Click.value = v;
  if (h != null)
    form.action += h;
  form.submit();
  return false;
}
// -->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body text="#000000" bgcolor="#FFFFFF">

<form method="post" action="ZD_Login1.jsp" name="_Login">

<table border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td height=200 width="924" valign="middle"><div align="center"><img src="image/0.jpg" width="545" height="95" alt=""></div></td></tr>

<tr valign="top"><td width="924" valign="middle"><div align="center">
<table cellpadding=2 cellspacing=0 bordercolor=green border="1">
<tr valign="top"><td bgcolor=#F5EFD3 height=160 width="302" valign="middle"><div align="center">
<table border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td width="227" valign="middle"><div align="center"></div></td><td width="76" valign="middle"><img src="image/ecblank.gif" border="0" height="1" width="1" alt=""></td></tr>

<tr valign="top"><td width="227" valign="middle"><div align="right">部门：
<select name="Dept" onchange="Class_onchange(document.forms[0].Dept,document.forms[0].UserName)" style="width=120"></select>
</div></td><td height=40 width="76" valign="middle"><img src="image/ecblank.gif" border="0" height="1" width="1" alt=""></td></tr>

<tr valign="top"><td width="227" valign="middle"><div align="right">用户名：
<select name="UserName" style="width=120"></select>
</div></td><td height=40 width="76" valign="middle"><img src="image/ecblank.gif" border="0" height="1" width="1" alt=""></td></tr>
<input type="hidden" name="XM">
</tr>
<tr valign="top"><td width="227" valign="middle"><div align="right">密码：
<input name="Password" value="" type="password" style="width=120"></div></td><td height=40 width="76" valign="middle"><div align=center>
  <Input type=button value="确  认"  onclick="javascript:Login()" size=10></div></td></tr>
</table>
</div></td></tr>
</table>
</div></td></tr>

<tr valign="top"><td height=50 width="924"><img src="image/ecblank.gif" border="0" height="1" width="1" alt=""></td></tr>

<tr valign="top"><td width="924" valign="middle"><div align="center"><hr width="378" size="3" align="center" style="color:#800000; "></div></td></tr>
</table>


<SCRIPT language=JavaScript>

var DeptArr     = new Array(0);
var UserIDArr   = new Array(0);
var UserNameArr = new Array(0);
DeptArr[0] = "请选择";UserIDArr[0] = " ";UserNameArr[0] = " ";
<%
sys_user user=new sys_user ();
List ls=user.query();
Iterator itls=ls.iterator();
int i=1;
while (itls.hasNext()){
  sys_user user1=(sys_user)itls.next();
  out.println("DeptArr["+i+"]='"+user1.getDEPT().trim()+"';UserIDArr["+i+"]='"+user1.getUSERID().trim() +"';UserNameArr["+i+"]='"+user1.getXM().trim()+"';");
  i++;
}
%>



var UDeptArr = Array(0)
var UDeptArr_Count = 0

function Array_Including(Arr,Str){
  Array_Including = false
  for(i=0;i<Arr.length;i++){
    if(Arr[i] == Str) Array_Including = true
  }
}
function Class_onchange(ClassItem,ObjItem){
  ClassStr  = ClassItem.value;
  for(i = 0; i<1000; i++) ObjItem.remove(0);
  for(i = 0; i<DeptArr.length; i++){
    if(DeptArr[i] == ClassStr){
      TmpItem = document.createElement('Option');
      TmpItem.value = UserIDArr[i];
      TmpItem.text  = UserNameArr[i];
      ObjItem.add(TmpItem);
    }
  }
}

for(i=0;i<DeptArr.length;i++){
  var AddFlag = true
  for(j=0;j<UDeptArr.length;j++){
    if(UDeptArr[j] == DeptArr[i]) AddFlag = false
  }
  if(AddFlag){
    UDeptArr[UDeptArr_Count] = DeptArr[i]
    UDeptArr_Count++
  }
}
ObjItem = document.forms[0].Dept
for(i = 0; i < UDeptArr_Count; i++){
    TmpItem = document.createElement('Option');
    TmpItem.value = UDeptArr[i];
    TmpItem.text  = UDeptArr[i];
    ObjItem.add(TmpItem);
}
//Class_onchange(document.forms[0].Dept,document.forms[0].UserName)
</SCRIPT>
</form>
</body>
</html>
