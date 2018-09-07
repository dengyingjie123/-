<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>云南电网设备检修申请系统</title><meta http-equiv='pragma' content='no-cache'>
<script language="javascript">

function Add(){ //增加
  for(var i = 0; i < document.forms[0].UserName.length; i++){
    var AddFlag = 1;
    if (document.forms[0].UserName.options[i].selected){
      var Text  = document.forms[0].UserName.options[i].text;
      var Value = document.forms[0].UserName.options[i].value;
      for (var j = 0; j < document.forms[0].SetItem.length; j++){
        if (document.forms[0].SetItem.options[j].text == Text){
          AddFlag = 0;
        }
      }
      if(AddFlag){
        Item = document.createElement('Option');
        Item.text  = Text;
        Item.value = Value;
        document.forms[0].SetItem.add(Item);
      }
    }
  }
}

function ok() { //确定
  var value='';
  for(var i = 0;i < document.forms[0].SetItem.length;i++){
    value   += document.forms[0].SetItem.options[i].value + '|';
  }
  if (value=="")
  {
	alert("请选择要转发的相关单位！");
  }
  else
  {
	value="|"+value;
	opener.setXGDW(value);
	opener.fsSubmitForm();
	window.close();
  }

}
function Delete(){ //删除
  for (var i = 0;i<document.forms[0].SetItem.length;i++){
    if (document.forms[0].SetItem.options[i].selected){
      document.forms[0].SetItem.remove(i);
      i--;
    }
  }
}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=10 leftmargin=5>
<form  method="post" action="SQ_Login1.jsp" name="_Login">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="3"><div align="center">选择相关单位</div></td>
  </tr>
  <tr>
    <td><div align="center">分类</div></td>
    <td rowspan="4"><div align="center">
	  <input type="button" onclick="Add()" value="增加"><br>
<br>

<input type="button" onclick="Delete()" value="删除">
	</div></td>
    <td><div align="center">已选单位</div></td>
  </tr>
  <tr>
    <td><div align="center"> <select name="dwmc" style="width=180" onchange="Class_onchange(document.forms[0].dwmc,document.forms[0].UserName)" >
	  </select></div></td>
    <td rowspan="3"><div align="center"><select size="13" multiple name="SetItem" style="width=180">
</select></div></td>
  </tr>
  <tr>
    <td><div align="center">单位名称</div></td>
  </tr>
  <tr>
    <td><div align="center"><select size="10" multiple name="UserName" style="width=180">
</select></div></td>
  </tr>
  <tr>
    <td colspan="3"><div align="center"> <Input type=button onclick="ok()" value="确    认" size=10><Input type=button onclick="window.close()" value="取    消" size=10></div></td>
  </tr>
</table>
<SCRIPT language=JavaScript>
document.forms[0].action="SQ_Login1.jsp"
var DWMC     = new Array(0);
var UserUNID = new Array(0);
var UserName = new Array(0);
DWMC[0] = "请选择";UserUNID[0] = " ";UserName[0] = " ";
<%
com.ydtf.dmis.jxp.sys_sqdw ssqdw=new sys_sqdw();
List lsdw=ssqdw.query();
Iterator itdw=lsdw.iterator();
int i=1;
while (itdw.hasNext())
{
  sys_sqdw sqdw=(sys_sqdw)itdw.next();
  out.println("DWMC["+i+"]='"+sqdw.getDWLB().trim()+"';UserUNID["+i+"]='"+sqdw.getDWMC().trim() +"';UserName["+i+"]='"+sqdw.getDWMC().trim()+"';");

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
