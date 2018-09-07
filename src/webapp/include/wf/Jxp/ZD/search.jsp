<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询</title>
<script language="javascript">
function searchForm(form) {
  buildTime(form);
	/*
  window.alert("Start: " + form.KGSJStart.value + "\r" +
	             "End: " + form.KGSJEnd.value);
	*/
	form.submit();
}
function buildTime(form) {
  if (form.startYear.value != "" && form.startMonth.value != "" && form.startDay.value != "") {
    start = form.startYear.value+"-"+form.startMonth.value+"-"+form.startDay.value+" ";
    form.KGSJStart.value = start;
	}
  if (form.endYear.value != "" && form.endMonth.value != "" && form.endDay.value != "") {
    end = form.endYear.value+"-"+form.endMonth.value+"-"+form.endDay.value+" ";
    form.KGSJEnd.value = end;
	}
}
function selectSB() {
//选择一次设备和二次设备
  window.open("../SQ/selectSB.jsp","","width=550,height=500");
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
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr>
    <td height="20"><div align="center">
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="20" bgcolor="#9C9EE7"><div align="center" class="txt_white">检修票查询</div></td>
        </tr>
      </table>
      <br>
    </div></td>
  </tr>
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><form action="searchList.jsp" method="post" name="form" id="form">
              <div align="center"><strong>模糊查询</strong><br>
              </div>
              <table width="100%"  border="0" cellpadding="2" cellspacing="1" bgcolor="#009900">
                <tr class="tdbg">
                  <td><div align="center">编号</div></td>
                  <td><input name="YWID" type="text" id="YWID"></td>
                </tr>
                <tr class="tdbg">
                  <td><div align="center">申请单位</div></td>
                  <td><input name="SQDW" type="text" id="SQDW"></td>
                </tr>
                <tr class="tdbg">
                  <td><p align="center"><a href="javascript:selectSB()">选择一次设备</a><br>
                      <a href="javascript:selectSB()">选择二次设备</a>                  </p>
                    </td>
                  <td>
									<table style="border-collapse: collapse" bordercolor="#008000" width="100%" border="1" id="sbTable">
<!-- ----------------  Add Devices  -------------------- -->
          <tr id="title">
            <td><div align="center">序号</div></td>
            <td><div align="center">设备编号</div></td>
            <td><div align="center">厂站名称</div></td>
            <td><div align="center">设备类型</div></td>
            <td><div align="center">设备名称</div></td>
            <td><div align="center">操作</div></td>
          </tr>
        </table>
<!-- --------------------------------------------- -->				
									</td>
                </tr>
                <tr class="tdbg">
                  <td><div align="center">
                    <input type="checkbox" name="checkbox" value="checkbox">
                    开工时间</div></td>
                  <td>
									<select name="startYear" id="startYear">
									  <option></option>
                    <option value="2004">2004</option>
                    <option value="2005">2005</option>
                    <option value="2006">2006</option>
                  </select>
年
<select name="startMonth" id="startMonth">
  <option></option>
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
  <option></option>
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
日 至
<select name="endYear" id="endYear">
  <option></option>
  <option value="2004">2004</option>
  <option value="2005">2005</option>
  <option value="2006">2006</option>
</select>
年
<select name="endMonth" id="endMonth">
  <option></option>
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
  <option></option>
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
日</td>
                </tr>
                <tr class="tdbg">
                  <td><div align="center">工作内容</div></td>
                  <td><textarea name="GZNR" cols="50" rows="5" wrap="VIRTUAL" id="GZNR"></textarea></td>
                </tr>
              </table>
              <div align="center"><br>
                  <input type="button" name="Button" value="开始查询" onClick="searchForm(this.form)">
                  <input name="KGSJStart" type="hidden" id="KGSJStart">
                  <input name="KGSJEnd" type="hidden" id="KGSJEnd">
                  <input name="ViewID" type="hidden" id="ViewID" value="Form">
              </div>
            </form></td>
          </tr>
        </table></td>
        <td valign="top"><div align="center"><strong>快速查询</strong><br>
          <br>
          </div>
          <table width="200"  border="0" align="center" cellpadding="2" cellspacing="4">
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="searchList.jsp?ViewID=KGZ">开工中的检修票</a></div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="searchList.jsp?ViewID=YZX">已执行的检修票</a></div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="searchList.jsp?ViewID=YZF">已作废的检修票</a></div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="searchList.jsp?ViewID=ALL">所有检修票（按编号）</a></div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="searchList.jsp?ViewID=XSBTC">所有新设备投产（按编号）</a></div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="fsbg_search_spz.jsp">审批中的方式变更单</a> </div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="fsbg_search.jsp?ViewID=DDZX">调度执行的方式变更单</a></div></td>
          </tr>
          <tr>
            <td height="20" bgcolor="#9C9EE7"><div align="center"><a href="fsbg_search.jsp?ViewID=ALL">所有方式变更单</a></div></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
