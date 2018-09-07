<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：主页面
 * 描述：工作流主页面
 * 时间：2004/12/06 16:10:51
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.NewJxp" %>
<%@ page import="com.ydtf.dmis.jxp.JxpSb" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="java.util.*;" %>
<%
String strDwmc=(String)session.getAttribute("dangwei");  //获得单位名称
String strXM=(String)session.getAttribute("xingming");  //获得操作员姓名
String strType = request.getParameter("Type");  //获得检修票类型，1：检修票申请，2：新设备投产申请


int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));  //获得工作流编号
int intCurrNodeID = 1;  //Integer.parseInt(request.getParameter("CurrNodeID"));  //获得当前节点编号
int intRouteListID = 0;  //Integer.parseInt(request.getParameter("RouteListID"));  //获得RouteList编号
String strYWID = new String();  //获得业务编号
String strUserID = strXM;
String strSQSJ = new String();
NewJxp objNewJxp = new NewJxp();
if (!objNewJxp.isEmptySQSJ()) {
  strSQSJ = objNewJxp.getSQSJ();
}
else {
  strSQSJ = Tools.getTime();
}
if (request.getParameter("YWID") != null && !request.getParameter("YWID").equals("")) {
//如果YWID不为空时，该检修票处于修改状态
//根据YWID查出该检修票详细信息
  strYWID = request.getParameter("YWID");
	//设置检修票编号（不是票号）
  objNewJxp.setID(Integer.parseInt(strYWID));
  List listNewJxp = objNewJxp.queryExact();
  objNewJxp = (NewJxp)listNewJxp.get(0);
  //查询设备
  JxpSb jxpsb = new JxpSb();
  jxpsb.setUNID(String.valueOf(objNewJxp.getID()));
  //设置设备
  objNewJxp.setJxpSb(jxpsb.queryExact());
  //设置类型
  strType = objNewJxp.getTYPE();
	//获得当前检修票的RouteList
	//如果该检修票不处于申请、本单位审核这两个节点，则该检修票不能修改
  List listRL = ClientApplications.getActiveYW(strYWID, intWorkflowID);
  boolean boolCanEdit = true;  //标示该检修票是否可以修改
  int i = 0;
  for (i = 0; listRL != null && i < listRL.size(); i++) {
    RouteList rl = (RouteList)listRL.get(i);
    if (rl.getCurrentNode() > 2) {
      boolCanEdit = false;
      break;
    }
  }
  if (i == 0) {
    boolCanEdit = false;
  }
  if (!boolCanEdit) {
    throw new Exception("该检修票在审核中，不能被修改！");
  }
  //设置javascript变量
  out.println("<script language='javascript'>");
  out.println("var isEdit = 1;");
  out.println("</script>");
}
else {
  strYWID = Integer.toString(NewJxp.getMaxID());
  out.println("<script language='javascript'>");
  out.println("var isEdit = 0;");
  out.println("</script>");
}

//--------  设置Title
String strTitle = new String();
if (strType.equals("1")) {
  strTitle = "检修票申请";
}
else if (strType.equals("2")) {
  strTitle = "新设备投产申请";
}
//-----------
%>

<html>
<head>
<title>检修票申请</title>
</head><STYLE type=text/css>A:link {
	TEXT-DECORATION: none
}
A:visited {
	TEXT-DECORATION: none
}
A:active {
	TEXT-DECORATION: none
}
A:hover {
	TEXT-DECORATION: underline
}
A.a01:link {
	TEXT-DECORATION: underline
}
A.a01:visited {
	TEXT-DECORATION: underline
}
A.a01:active {
	TEXT-DECORATION: underline
}
A.a01:hover {
	TEXT-DECORATION: underline
}
TABLE {
	FONT-SIZE: 9pt
}
TR {
	FONT-SIZE: 9pt; LINE-HEIGHT: 1.5
}
TD {
	FONT-SIZE: 9pt; LINE-HEIGHT: 1.5; FONT-FAMILY: "宋体"
}
.p9 {
	FONT-SIZE: 9pt
}
.p10 {
	FONT-SIZE: 10pt
}
</STYLE>
<script src="../script/common.js"></script>
<script language="javascript">
var type = "<%=strType%>";
function submitForm(form, target) {
  if (checkForm(form)) {
		if (target == "SaveForward") {
			if (isEdit == 1) {
				form.action = "saveSQ.jsp";
			}
			else {
				form.TargetURL.value = "/jxp/SQ/Done.jsp";
				form.action = "/workflow/WorkflowService";
				form.ServiceType.value = "SaveForward";
				if (type == "2") {
			    form.GZNR.value = "新设备投产：" + form.GZNR.value;
			  }
			}
			buildTime(form);
			addField();
			form.submit();
		}
	}
}
function buildTime(form) {
    start = form.startYear.value+"-"+form.startMonth.value+"-"+form.startDay.value+" ";
    start += form.startHour.value+":"+form.startMinute.value+":00";
    form.SQTDSJ.value = start;

    end = form.endYear.value+"-"+form.endMonth.value+"-"+form.endDay.value+" ";
    end += form.endHour.value+":"+form.endMinute.value+":00";
    form.SQFDSJ.value = end;
}
function openWindow() {
  window.open("selectSB.jsp","","width=550,height=500");
}
function addSB(sbid, czmc, sblx, sbmc) {
    var trTitle = document.getElementById("title");
    var table = trTitle.parentNode;
    var tr = document.createElement("tr");
    var tdIndex = document.createElement("td");
    var tdSBID = document.createElement("td");
    var tdCZMC = document.createElement("td");
    var tdSBLX = document.createElement("td");
    var tdSBMC = document.createElement("td");
    var tdOp = document.createElement("td");
    tdSBID.innerText = sbid;
    tdCZMC.innerText = czmc;
    tdSBLX.innerText = sblx;
    tdSBMC.innerText = sbmc;
    tr.appendChild(tdIndex);
    tr.appendChild(tdSBID);
    tr.appendChild(tdCZMC);
    tr.appendChild(tdSBLX);
    tr.appendChild(tdSBMC);
    tr.appendChild(tdOp);
    table.appendChild(tr);
    syncSBTable();
  }
function deleteSB(index) {
  var trTitle = document.getElementById("title");
  var table = trTitle.parentNode;
  table.deleteRow(index);
  syncSBTable();
}
function syncSBTable() {
  var count=1;
  for (i=1; i < document.all.sbTable.rows.length; i++) {
    document.all.sbTable.rows(i).cells(0).innerText = count;
    document.all.sbTable.rows(i).cells(5).innerHTML = "<a href='javascript:deleteSB("+count+")'>删除</a>";
    count++;
  }
}
  function addField() {
    //sbid, unit, fixclass,fixing
    document.all.sbField.rows(0).cells(0).innerHTML = "";
    document.all.form.SBMC.value = "";
    for (i = 1; i < document.all.sbTable.rows.length; i++) {
      for (j = 1; j < document.all.sbTable.rows(i).cells.length - 1; j++) {
        var name = "";
        switch (j) {
          case 1: name = "SBID"; break;
          case 2: name = "UNIT"; break;
          case 3: name = "FIXCLASS"; break;
          case 4: name = "FIXING"; break;
        }
        value = document.all.sbTable.rows(i).cells(j).innerText;
        if (j == 4) {
          document.all.form.SBMC.value += value + " ";
        }
        document.all.sbField.rows(0).cells(0).innerHTML += '<input type="hidden" name="'+name+'" value="'+value+'">';
      }
    }
  }

function checkForm(form) {
  if (Trim(form.SQDW.value) == "") {
	  window.alert("请输入申请单位");
		return false
	}
	else if (Trim(form.SQR.value) =="") {
	  window.alert("请输入申请人姓名");
		return false;
	}
	else if (Trim(form.TDLXR.value) == "") {
	  window.alert("请输入停电联系人");
		return false;
	}
	else if (Trim(form.LXDH.value) == "") {
	  window.alert("请输入联系电话");
	  return false;
	}
	else if (Trim(form.GZNR.value) == "") {
	  window.alert("请输入工作内容");
	  return false;
	}
	else if (Trim(form.TDFW.value) == "") {
	  window.alert("请输入停电范围");
	  return false;
	}
	else if (Trim(form.SQDWYJ.value) == "") {
	  window.alert("请输入申请单位意见");
	  return false;
	}
	else if (document.all.sbTable.rows.length < 2) {
	  window.alert("请输入设备");
		return false;
	}
	else {
	  return true;
	}
}
</script>

<BODY text=#000000 bgColor=#ffffff leftMargin=0 topMargin=0>
<TABLE style="FONT-SIZE: 11pt; FONT-FAMILY: 宋体" cellSpacing=0 cellPadding=0
width="100%" border=0>
  <TBODY>
  <TR vAlign=top>
    <TD
    style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
    vAlign="center" width="15%"><IMG height=83 src="../image/T1.jpg"
      width="100%" border=0></TD>
    <TD vAlign="center" width="88%">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR vAlign=top>
          <TD
          style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
          vAlign=center width="100%" height=40><IMG
            src="../image/T1_WebDD.jpg" border=0></TD></TR>
        <TR vAlign=top>
          <TD
          style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
          vAlign=center width="100%" bgColor=#339933 height=25>
            <TABLE style="FONT-SIZE: 11pt; FONT-FAMILY: 宋体" cellSpacing=0
            cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD align=middle width="10%"><A
                  href="index.jsp"><FONT
                  color=white>主页</FONT></A></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"><A
                  href="search.jsp"><FONT
                  color=white>检修票查询</FONT></A></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"></TD></TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width="100%" bgColor=#5f5f5f height=20>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR vAlign=top>
                <TD width="58%" colSpan=2><FONT
                  color=#ffffff>Welcome：</FONT><FONT
                  color=#ffffff><%=(String)session.getAttribute("dangwei")%></FONT> <FONT
                  color=#ffffff><%=(String)session.getAttribute("xingming")%></FONT> <FONT
                  color=#ffffff><%=Tools.getTime()%></FONT></TD>
                <TD width="42%">
                  <DIV align=right><A href="http://localhost/JXP_SQ.htm"
                  target=_blank><FONT color=#ffffff>帮助</FONT></A>
				  <A onclick='F_URL = "/" + document.forms[0].SYS_Path.value + "/User.nsf/ModifySQUserPassword?OpenForm&amp;UserID=" + document.forms[0].SYS_UserID.value&#13;width=400;height=250;LeftNum = (screen.width - width)/2;TopNum = (screen.height - height)/2;&#13;TX = "left=" + LeftNum + ",top=" + TopNum + ",width=" + width + ",height=" + height + ",status=no,scrollbars=no,resizable=yes";&#13;window.open(F_URL,"",TX);&#13;return false;'
                  href="http://localhost/DMIS/WebDD.nsf/"><FONT
                  color=#ffffff>修改密码</FONT></A> <A
                  onclick='SetCookie("SYS_USERUNID","")&#13;window.location.href = "/" + document.forms[0].SYS_Path.value + "/WebDD.nsf/Login?OpenForm"&#13;return false;'
                  href="loginout.jsp"><FONT
                  color=#ffffff>退出系统</FONT></A> <FONT
                color=#ffffff></FONT></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" width="100%" bgcolor="#9F9FE0"><div align="center"><font color="#FFFFFF"><%=strTitle%></font><font size="2">
</table>

<form action="" method="post" name="form" id="form">
  <table style="border-collapse: collapse" bordercolor="#008000" width="100%" border="1">
    <tr>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"SQDW") %></div></td>
      <td><input type = "text" name="SQDW" value="<%=objNewJxp.isEmptySQDW()? strDwmc : objNewJxp.getSQDW()%>" readonly></td>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"SQR") %></div></td>
      <td><input type = "text" name="SQR" value="<%=objNewJxp.isEmptySQR()? strXM : objNewJxp.getSQR()%>" readonly></td>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"TDLXR") %></div></td>
      <td><input type = "text" name="TDLXR" value="<%=objNewJxp.isEmptyTDLXR()? "" : objNewJxp.getTDLXR()%>" ></td>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"LXDH") %></div></td>
      <td><input type = "text" name="LXDH" value="<%=objNewJxp.isEmptyLXDH()? "" : objNewJxp.getLXDH()%>" ></td>
    </tr>
    <tr>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"SBMC") %></div></td>
      <td colspan="7">
        <table style="border-collapse: collapse" bordercolor="#008000" width="100%" border="1" id="sbTable">
          <tr id="title">
            <td><div align="center">序号</div></td>
            <td><div align="center">设备编号</div></td>
            <td><div align="center">厂站名称</div></td>
            <td><div align="center">设备类型</div></td>
            <td><div align="center">设备名称</div></td>
            <td><div align="center">操作</div></td>
          </tr>
          <%
          List listJxpSb = objNewJxp.getJxpSb();
          for (int i = 0; listJxpSb != null && i < listJxpSb.size(); i++) {
            JxpSb jxpsb = (JxpSb)listJxpSb.get(i);
            %>
            <tr>
              <td><%=i+1 %></td>
              <td><%=jxpsb.getSBID()%></td>
              <td><%=jxpsb.getUNIT() %></td>
              <td><%=jxpsb.getFIXCLASS() %></td>
              <td><%=jxpsb.getFIXING() %></td>
              <td><a href="javascript:deleteSB(<%=i+1%>)">删除</a></td>
            </tr>
            <%
            }
          %>
        </table>
        <br>
        <input type="button" name="Button" value="添加设备" onclick="openWindow()">
        <input name="SBMC" type="hidden" id="SBMC" value="test设备">
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="sbField">
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"GZNR") %></div></td>
      <td colspan="7"><textarea name="GZNR" cols="60" rows="5" wrap="VIRTUAL" id="GZNR"><%=objNewJxp.getGZNR()%></textarea></td>
    </tr>
    <tr>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"TDFW") %></div></td>
      <td colspan="7"><textarea name="TDFW" cols="60" rows="5" wrap="VIRTUAL" id="TDFW"><%=objNewJxp.getTDFW()%></textarea></td>
    </tr>
    <tr>
      <td><div align="center"><%= ClientApplications.getFieldTitle(1,"SQDWYJ") %></div></td>
      <td colspan="7"><textarea name="SQDWYJ" cols="60" rows="5" wrap="VIRTUAL" id="SQDWYJ"><%=objNewJxp.getSQDWYJ()%></textarea></td>
    </tr>
    <tr>
      <td><div align="center">计划工作时间</div></td>
      <td colspan="3"><select name="startYear" id="startYear">
        <option value="2004">2004</option>
        <option value="2005">2005</option>
        <option value="2006">2006</option>
      </select>
年
<select name="startMonth" id="startMonth">
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
</select>
月
<select name="startDay" id="startDay">
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
  <option value="24">24</option>
  <option value="25">25</option>
  <option value="26">26</option>
  <option value="27">27</option>
  <option value="28">28</option>
  <option value="29">29</option>
  <option value="30">30</option>
  <option value="31">31</option>
</select>
日
<select name="startHour" id="startHour">
  <option value="00">00</option>
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
</select>
时
<select name="startMinute" id="startMinute">
  <option value="00">00</option>
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
  <option value="24">24</option>
  <option value="25">25</option>
  <option value="26">26</option>
  <option value="27">27</option>
  <option value="28">28</option>
  <option value="29">29</option>
  <option value="30">30</option>
  <option value="31">31</option>
  <option value="32">32</option>
  <option value="33">33</option>
  <option value="34">34</option>
  <option value="35">35</option>
  <option value="36">36</option>
  <option value="37">37</option>
  <option value="38">38</option>
  <option value="39">39</option>
  <option value="40">40</option>
  <option value="41">41</option>
  <option value="42">42</option>
  <option value="43">43</option>
  <option value="44">44</option>
  <option value="45">45</option>
  <option value="46">46</option>
  <option value="47">47</option>
  <option value="48">48</option>
  <option value="49">49</option>
  <option value="50">50</option>
  <option value="51">51</option>
  <option value="52">52</option>
  <option value="53">53</option>
  <option value="54">54</option>
  <option value="55">55</option>
  <option value="56">56</option>
  <option value="57">57</option>
  <option value="58">58</option>
  <option value="59">59</option>
</select>
分</td>
      <td><div align="center">至</div></td>
      <td colspan="3"><select name="endYear" id="endYear">
        <option value="2004">2004</option>
        <option value="2005">2005</option>
        <option value="2006">2006</option>
      </select>
年
<select name="endMonth" id="endMonth">
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
</select>
月
<select name="endDay" id="endDay">
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
  <option value="24">24</option>
  <option value="25">25</option>
  <option value="26">26</option>
  <option value="27">27</option>
  <option value="28">28</option>
  <option value="29">29</option>
  <option value="30">30</option>
  <option value="31">31</option>
</select>
日
<select name="endHour" id="endHour">
  <option value="00">00</option>
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
</select>
时
<select name="endMinute" id="endMinute">
  <option value="00">00</option>
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
  <option value="24">24</option>
  <option value="25">25</option>
  <option value="26">26</option>
  <option value="27">27</option>
  <option value="28">28</option>
  <option value="29">29</option>
  <option value="30">30</option>
  <option value="31">31</option>
  <option value="32">32</option>
  <option value="33">33</option>
  <option value="34">34</option>
  <option value="35">35</option>
  <option value="36">36</option>
  <option value="37">37</option>
  <option value="38">38</option>
  <option value="39">39</option>
  <option value="40">40</option>
  <option value="41">41</option>
  <option value="42">42</option>
  <option value="43">43</option>
  <option value="44">44</option>
  <option value="45">45</option>
  <option value="46">46</option>
  <option value="47">47</option>
  <option value="48">48</option>
  <option value="49">49</option>
  <option value="50">50</option>
  <option value="51">51</option>
  <option value="52">52</option>
  <option value="53">53</option>
  <option value="54">54</option>
  <option value="55">55</option>
  <option value="56">56</option>
  <option value="57">57</option>
  <option value="58">58</option>
  <option value="59">59</option>
</select>
分</td>
    </tr>
    <tr>
      <td><div align="center">附件</div></td>
      <td colspan="7"><select name="File" id="File">
        <option value="0" selected>无附件</option>
        <option value="1">提交后上传附件</option>
      </select>
        <input name="PH" type="hidden" id="PH" value="<%=objNewJxp.getPH()%>">
        <input name="SQSJ" type="hidden" id="SQSJ" value="<%=strSQSJ%>">
        <input name="SQTDSJ" type="hidden" id="SQTDSJ" value="">
        <input name="SQFDSJ" type="hidden" id="SQFDSJ" value="">
        <input name="TYPE" type="hidden" id="TYPE" value="<%=strType%>"> </td>
    </tr>
  </table>
  <br>
  <div align="center">
    <input name="NextNode" type="hidden" id="NextNode" value="2">
    <input name="TargetURL" type="hidden" id="TargetURL">
    <input name="ServiceType" type="hidden" id="ServiceType">
    <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.jxp.NewJxp">
    <input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
    <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
    <input name="YWID" type="hidden" value="<%=strYWID%>"/>
    <input name="ID" type="hidden" value="<%=strYWID%>"/>
    <input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
    <input name="Participant" type="hidden" value="<%=strUserID%>" id="Participant">
<br>
    <input name="BDWSH" type="button" id="BDWSH" onClick="submitForm(this.form, 'SaveForward')" value="提交本单位审核">
    <input type="reset" name="Reset" value="重写">
  </div>

</form>
</body>
</html>
<script language="javascript">
if (isEdit == 1) {
  document.form.BDWSH.value='保存';
}
</script>