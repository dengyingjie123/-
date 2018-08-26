<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>云南电网设备检修申请系统</title><meta http-equiv='pragma' content='no-cache'>
<script language="javascript">
function Login(){
document.forms[0].XM.value=document.forms[0].UserName.options[document.forms[0].UserName.selectedIndex].innerText;
document.forms[0].submit();
}

</script>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=10 leftmargin=5>

<form  method="post" action="SQ_Login1.jsp" name="_Login">
<input type="hidden" name="__Click" value="0"><font size="2" color="#ff0000">
<input name="HTTP_COOKIE" value="" size=1 type=hidden></font>
<font color="#ff0000">
<input name="ErrorCode" value="" size=1 type=hidden></font><font size="2" face="宋体">
<input name="Remote_Addr" value="127.0.0.1" size = 1 type = hidden></font><font size="2">
<input name="SaveOptions" value="0" size = 1 type = hidden></font><div align=center><img border=0 src=image/T1_WebDD.jpg></div>
&nbsp<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td width="100%" bgcolor="#81C097">&nbsp</td></tr>

<tr valign="top"><td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" height=400 width="100%" valign="middle"><div align="center">
<table border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td width="378"><div align="center">
<table cellpadding=2 cellspacing=0 bordercolor=green border="1">
<tr valign="top"><td bgcolor=#ABD8C6 width="227"><div align="center"><font size="2" color="#FF4070">用户登录（检修申请）</font></div></td></tr>

<tr valign="top"><td width="227" valign="middle">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td bgcolor=FFDA6E width="100%"><div align="center"><font size="2">单位名称</font></div></td></tr>

<tr valign="top"><td bgcolor=FFDA6E width="100%"><div align="center"><font size="2">
<select name="dwmc" onchange="Class_onchange(document.forms[0].dwmc,document.forms[0].UserName)" style="width=180"></select>
</font></div></td></tr>

<tr valign="top"><td bgcolor=FFDA6E width="100%"><div align="center"><font size="2">用户名</font></div></td></tr>

<tr valign="top"><td bgcolor=FFDA6E width="100%"><div align="center"><font size="2">
<select name="UserName" style="width=180"></select>
</font></div></td></tr>

<tr valign="top"><td bgcolor=FFDA6E width="100%"><div align="center"><font size="2">密　码</font></div></td></tr>

<tr valign="top"><td bgcolor=FFDA6E width="100%"><div align="center"><font size="2">
<input name="Password" value="" type="password" style="width=180"></font></div></td></tr>
<input type="hidden" value="" name="XM"/>

<tr valign="top"><td bgcolor=FFDA6E height=30 width="100%" valign="middle"><div align=center><Input type=button onclick="Login()"
value="确    认" size=10></div></td></tr>
</table>
</td></tr>
</table>
</div></td></tr>

<tr valign="top"><td height=30 width="378"><img src="/icons/ecblank.gif" border="0" height="1" width="1" alt=""></td></tr>

<tr valign="top"><td width="378"><div align=center style="line-height:1.5">注：使用本系统之前，请先点击<a href=/DMIS/User.nsf/LoginSQDW?OpenForm><font color=blue><远程上报用户管理></font></a>建立本单位用户</div></td></tr>

<tr valign="top"><td width="378">&nbsp</td></tr>

<tr valign="top"><td width="378"><div align=center style="line-height:1.5"><a href=/JXP_SQ.htm><font color=blue><检修票申请使用手册></font></a></div><br>
<div align=center>请使用1024*768分辨率</div></td></tr>
</table>
</div></td></tr>
</table>
<br><br><div align="center"><font face="宋体">云南电力 2003 . 01</font><SCRIPT language=JavaScript>
document.forms[0].action="SQ_Login1.jsp"
var DWMC     = new Array(0);
var UserUNID = new Array(0);
var UserName = new Array(0);
DWMC[0] = "请选择";UserUNID[0] = " ";UserName[0] = " ";
<%
com.ydtf.dmis.jxp.sys_squser suser=new sys_squser();
List lsuser=suser.query();
Iterator ituser=lsuser.iterator();
int i=1;
while (ituser.hasNext())
{
  sys_squser squser=(sys_squser)ituser.next();
  out.println("DWMC["+i+"]='"+squser.getDWMC()+"';UserUNID["+i+"]='"+squser.getUNID()+"';UserName["+i+"]='"+squser.getXM()+"';");

  i++;
}
%>


var UDWMCArr = Array(0)
var UDWMCArr_Count = 0
function Class_onchange(ClassItem,ObjItem){
  ClassStr  = ClassItem.value;
  for(i = 0; i<1000; i++) ObjItem.remove(0);
  for(i = 0; i<DWMC.length; i++){
    if(DWMC[i] == ClassStr){
      TmpItem = document.createElement('Option');
      TmpItem.value = UserUNID[i];
      TmpItem.text  = UserName[i];
      ObjItem.add(TmpItem);
    }
  }
}
//提取单位名称数组
for(i=0;i<DWMC.length;i++){
  var AddFlag = true
  for(j=0;j<UDWMCArr.length;j++){
    if(UDWMCArr[j] == DWMC[i]) AddFlag = false
  }
  if(AddFlag){
    UDWMCArr[UDWMCArr_Count] = DWMC[i]
    UDWMCArr_Count++
  }
}
ObjItem = document.forms[0].dwmc
for(i = 0; i < UDWMCArr_Count; i++){
    TmpItem = document.createElement('Option');
    TmpItem.value = UDWMCArr[i];
    TmpItem.text  = UDWMCArr[i];
    ObjItem.add(TmpItem);
}
</SCRIPT>
</div><font size="2" color="#0021bf"></font></form>
</body>
</html>
