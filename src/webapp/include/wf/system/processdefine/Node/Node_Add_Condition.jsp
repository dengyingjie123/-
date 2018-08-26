<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：新增节点页面，记录新增节点进入条件和离开条件
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<%//Session Bean 用于保存新增节点的信息 %>
<jsp:useBean id="newNode" class="com.youngbook.common.wf.processdefine.Node" scope="session">
</jsp:useBean>

<%
try {
//获得请求
String strActions = request.getParameter("Actions");
if (strActions != null && strActions.equals("AddCondition")) {
  //获得节点进入条件
  String strIn = request.getParameter("Condition_In");
  //获得节点离开条件
  String strOut = request.getParameter("Condition_Out");
  if (strIn != null && strOut != null) {
    newNode.setCondition_In(Tools.Encode(Tools.toUTF8(strIn)));
    newNode.setCondition_Out(Tools.Encode(Tools.toUTF8(strOut)));
  }

  //新增节点的部分信息保存以后，转向下一页面
%>
<jsp:forward page="Node_Add_FieldStatu.jsp"></jsp:forward>
<%
}
%>

<%
//用于显示该工作流下所有的业务数据，供用户配置
BizData bd = new BizData();
//设置工作流编号
bd.setWorkflowID(newNode.getWorkflowID());
//根据工作流编号，查出该工作流下所有的业务数据
List listElem = bd.search();
if (listElem != null && listElem.size() > 0) {
  Element elem = (Element)listElem.get(0);
  bd = bd.buildObject(elem);
}
//将业务数据的字段信息保存到List中，供用户配置
List listBF = bd.getBizField();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增条件</title>
<script src="../../script/CheckText.js" type=""></script>
<script language="JavaScript" type="">
function submitForm(form) {
  form.action = "Node_Add_Condition.jsp";
  form.submit();
}
function checkForm(form) {
  /*
  if (!checkFormItem(form.Condition_In, "C", 0, 0, 1, "节点进入条件"))
  return false;
  else if (!checkFormItem(form.Condition_Out, "C", 0, 0, 1, "节点离开条件"))
  return false;
  else
  */
  return true;
}

//判断是否选择了条件，若没有的话则不能输入条件及关系
function checkSelect(intCount){
  if(eval("form.Condit" + intCount + ".value") == "0000"){
    if((eval("form.inputCon" + intCount + ".value") !=null)||
    (eval("form.Relation" + intCount + ".value") !="0000")){
      window.alert("必须选择条件后才能输入条件、选择关系！");
    }
  }
}

//得到表框中所选的条件
function GetData(intCount,biaoji){
  var strName = "";
  var strCon = "";
  var strRel = "";
  var strInput = "";
  var strFieldName = "";
  for(var i=1;i<=intCount;i++){
    if(eval("form.Condit" + i + ".value") != "0000"){
      strCon = eval("form.Condit" + i + ".value")+" "; //得到选择条件列表框的值

      if((eval("form.Condit" + i + ".value") == "NULL")||
      (eval("form.Condit" + i + ".value") == "NOT NULL")){
        strFieldName = eval("form.FieldName" + i + ".value")+" IS ";  //如果选择条件的值为NULL或NOT NULL，则字段名后要跟is
      }else{
        strFieldName = eval("form.FieldName" + i + ".value")+" "; //若不是NULL或NOT NULL，字段名后加空格
      }
      if(eval("form.inputCon" + i + ".value") !=null){  //若选择条件为模糊匹配，则输入条件的值应加上‘%%’
        strInput =eval("form.inputCon" + i + ".value");
        if(eval("form.Condit" + i + ".value") == "like"){
          strInput = "'%"+strInput+"%'";
        }
        if(eval("form.Condit" + i + ".value") == "精确匹配"){ //若选择条件为精确匹配，则输入条件的值应加上‘’
          strFieldName = eval("form.FieldName" + i + ".value"); //同时选择条件的值为“=”
          strCon = "=";
          strInput = "'"+strInput+"'";
        }
      }
      if(eval("form.Relation" + i + ".value") == "0000"){
        strRel = "";                                        //若没有选择关系则赋值为空字符串
      }else{
        strRel = eval("form.Relation" + i + ".value");
      }
      strName += strFieldName+strCon+strInput+" "+ strRel+" ";  //将所选中的各个值组合成为输入或输出条件
    }

  }

//将所得到的值根据标记输入到进入条件或离开条件文本域中
  if(biaoji == "in"){
    form1.Condition_In.value = strName;
  }else if(biaoji == "out"){
    form1.Condition_Out.value = strName;
  }
}
</script>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>

<form name="form1" method="post" action="">
      <div align="center">
        <p><font style="font-size:12pt "><strong>配置流转条件</strong></font></p>
		    <table width="585"  border="0" cellspacing="1" cellpadding="0" class="TableINBgStyle">
              <tr class="TableBgStyle">
                <td width="16%" class="TableTdBgBlueStyle"><div align="center">进入条件：</div></td>
                <td width="84%" class="TableTdBgStyle"><textarea name="Condition_In" cols="70" rows="4" wrap="VIRTUAL" class="TextFieldStyle" id="ConditionIn"></textarea></td>
              </tr>
              <tr class="TableBgStyle">
                <td><div align="center">离开条件:</div></td>
                <td class="TableTdBgStyle"><textarea name="Condition_Out" cols="70" rows="5" wrap="VIRTUAL" class="TextFieldStyle" id="ConditionOut"></textarea></td>
              </tr>
            </table>
        <br>
          <input type="hidden" name="Actions" id="Actions" value="AddCondition"/>
          <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="submitForm(this.form)" value="下一步">
          <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重写">
      </div>
	  </form>
	  <br>
	<form name="form" method="post" action="">
	  <center><font style="font-size:12pt "><strong>条件配置</strong></font><br><br>
        <div align="center">
		  <table width="585"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
                <tr class="DateListTableHeadStyle">
                  <td width="14%" height="20"><div align="center">字段</div></td>
                  <td width="17%"><div align="center">名称</div></td>
                  <td width="14%"><div align="center">类型</div></td>
                  <td width="19%"><div align="center">选择条件</div></td>
                  <td width="19%"><div align="center">输入条件</div></td>
                  <td width="17%"><div align="center">关系</div></td>
                </tr>

                <%
                int intCount = 0;
                List listModField = newNode.getBizField();
                Iterator itBF = listBF.iterator();
                while (itBF.hasNext()) {
                  intCount++;
                  BizField bf = (BizField)itBF.next();
                  int intID = newNode.getWorkflowID();
                  String strFieldName = bf.getName();
                  String strType = bf.getType();
                  String strTitle = bf.getTitle();
                  %>
                  <tr class="TableBgStyle">
                    <td class="TableTdBgStyle"><div align="center"><%=strFieldName%>
                      <input type="hidden" name="FieldName<%=intCount%>" id="<%=intCount%>" value="<%=strFieldName%>"/></div></td>
                      <td class="TableTdBgStyle"><div align="center"><%=strTitle%></div></td>
                      <td><div align="center"><%=strType%></div></td>
                      <td class="TableTdBgStyle"><div align="center">
                    <select name="Condit<%=intCount%>" class="TextStyle" id="<%=intCount%>" style="width:70pt">
					<option value="0000" selected></option>

<%
	if(strType.equals("NUMBER")){
%>
                        <option value=">" > > </option>
                        <option value="<" > < </option>
						<option value="=" > = </option>
						<option value="!=" > != </option>
						<option value=">=" > >= </option>
						<option value="<=" > <= </option>
						<option value="NOT NULL" > NOT NULL </option>
<%
}
 else{
%>

						<option value="NULL" > NULL </option>
						<option value="NOT NULL" > NOT NULL </option>
						<option value="like" >模糊匹配</option>
						<option value="精确匹配" >精确匹配</option>
<%
}
%>
                    </select>
                  </div></td>
				   <td class="TableTdBgStyle"><div align="center">
				   	<input type="text" name="inputCon<%=intCount%>" id="<%=intCount%>" class="TextStyle" style="width:60pt" onclick="checkSelect(<%=intCount%>)">
                  </div></td>
				   <td class="TableTdBgStyle"><div align="center">
                    <select name="Relation<%=intCount%>" class="TextStyle" id="<%=intCount%>" onclick="checkSelect(<%=intCount%>)">
						<option value="0000" selected></option>
                        <option value="AND">AND</option>
                        <option value="OR">OR</option>
                    </select>
                  </div></td>
                </tr>
<%
}
%>
              </table>
          <br>
          <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="GetData(<%=intCount%>,'in')" value="进入条件">
		  <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="GetData(<%=intCount%>,'out')" value="离开条件">
            <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重写">
        </div>
    <br>

    </form>

<blockquote>&nbsp;</blockquote>
</body>
</html>
<%
}
catch (Exception e) {
  out.println(e.getMessage());
  out.println("<a href = 'Node_SelectWorkflow.jsp'>返回</a>");
}
%>
