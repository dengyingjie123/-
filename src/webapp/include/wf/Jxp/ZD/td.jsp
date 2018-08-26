<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%
int intID = Integer.parseInt(request.getParameter("ID"));
NewJxp jxp = new NewJxp();
jxp.setID(intID);
jxp = jxp.BuildObject();

String strUserName = (String)session.getAttribute("xingming");
Calendar cTime = Tools.getDate(Tools.getTime(), "yyyy-MM-dd HH:m:s");

int intYear = cTime.get(Calendar.YEAR);
int intMonth = cTime.get(Calendar.MONTH)+1;
int intDay = cTime.get(Calendar.DATE);
int intHour = cTime.get(Calendar.HOUR);
int intMinute = cTime.get(Calendar.MINUTE);
int intAMPM = cTime.get(Calendar.AM_PM);
if (intAMPM == Calendar.PM) {
  intHour += 12;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>停电</title>
<script type="" language="javascript">
<%
out.println("var year = '" + intYear + "';");
out.println("var month = '" + intMonth + "';");
out.println("var day = '" + intDay + "';");
out.println("var hour = '" + intHour + "';");
out.println("var minute = '" + intMinute + "';");
%>
var time;
function submitForm(form) {
  buildTime(form);
  opener.resetTD("TDSJ");
  opener.modifyTD("TDSJ", time);
  opener.resetTD("TDSLR");
  opener.modifyTD("TDSLR", form.TDSLR.value);
  opener.resetTD("TDDDY");
  opener.modifyTD("TDDDY", document.all.TDDDY.innerText);
  opener.updateJxp();
  window.close();
}
function buildTime(form) {
  time = "";
  time = form.year.value+"-"+form.month.value+"-"+form.day.value+" "+form.hour.value+":"+form.minute.value+":00";
}
function setTime() {
  for(i = 0; i < document.form.year.length; i++) {
    if (document.form.year[i].value == year) {
      document.form.year[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.month.length; i++) {
    if (document.form.month[i].value == month) {
      document.form.month[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.day.length; i++) {
    if (document.form.day[i].value == day) {
      document.form.day[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.hour.length; i++) {
    if (document.form.hour[i].value == hour) {
      document.form.hour[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.minute.length; i++) {
    if (document.form.minute[i].value == minute) {
      document.form.minute[i].selected = true;
      break;
    }
  }
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" id="form" method="post" action="">
      <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
        <tr class="tdbg">
          <td>申请单位</td>
          <td><%=jxp.getSQDW() %></td>
        </tr>
        <tr class="tdbg">
          <td>设备名称</td>
          <td><%=jxp.getSBMC() %></td>
        </tr>
        <tr class="tdbg">
          <td>停电时间</td>
          <td><select name="year" id="year">
            <option value="2003">2003</option>
            <option value="2004">2004</option>
            <option value="2005">2005</option>
            <option value="2006">2006</option>
          </select>
年
<select name="month" id="month">
  <option value="1">01</option>
  <option value="2">02</option>
  <option value="3">03</option>
  <option value="4">04</option>
  <option value="5">05</option>
  <option value="6">06</option>
  <option value="7">07</option>
  <option value="8">08</option>
  <option value="9">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
</select>
月
<select name="day" id="day">
  <option value="1">01</option>
  <option value="2">02</option>
  <option value="3">03</option>
  <option value="4">04</option>
  <option value="5">05</option>
  <option value="6">06</option>
  <option value="7">07</option>
  <option value="8">08</option>
  <option value="9">09</option>
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
<select name="hour" id="hour">
  <option value="0">00</option>
  <option value="1">01</option>
  <option value="2">02</option>
  <option value="3">03</option>
  <option value="4">04</option>
  <option value="5">05</option>
  <option value="6">06</option>
  <option value="7">07</option>
  <option value="8">08</option>
  <option value="9">09</option>
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
<select name="minute" id="minute">
  <option value="0">00</option>
  <option value="1">01</option>
  <option value="2">02</option>
  <option value="3">03</option>
  <option value="4">04</option>
  <option value="5">05</option>
  <option value="6">06</option>
  <option value="7">07</option>
  <option value="8">08</option>
  <option value="9">09</option>
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
        <tr class="tdbg">
          <td>受令人</td>
          <td><input name="TDSLR" type="text" id="TDSLR"></td>
        </tr>
        <tr class="tdbg">
          <td>中心调度员</td>
          <td id="TDDDY"><%=strUserName %></td>
        </tr>
        <tr class="tdbg">
          <td colspan="2"><div align="center">
              <input type="button" name="Button" value="确定" onClick="submitForm(this.form)">
&nbsp;&nbsp;              
<input type="button" name="Button" value="取消" onClick="window.close()">
          </div></td>
          </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
<script language="javascript">
setTime();
</script>
</html>
