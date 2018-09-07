<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%

String strStart = request.getParameter("start");
String strEnd = request.getParameter("end");  //2004-12-21 15:58:46
if (strStart == null || strStart.equals("")) {
  strStart = Tools.getTime();
}
if (strEnd == null || strEnd.equals("")) {
  strEnd = Tools.getTime();
}

Calendar cStart = Tools.getDate(strStart, "yyyy-MM-dd HH:m:s");
Calendar cEnd = Tools.getDate(strEnd, "yyyy-MM-dd HH:m:s");

int intSYear = cStart.get(Calendar.YEAR);
int intSMonth = cStart.get(Calendar.MONTH)+1;
int intSDay = cStart.get(Calendar.DATE);
int intSHour = cStart.get(Calendar.HOUR);
int intSMinute = cStart.get(Calendar.MINUTE);
int intSAMPM = cStart.get(Calendar.AM_PM);
if (intSAMPM == Calendar.PM) {
  intSHour += 12;
}
int intEYear = cEnd.get(Calendar.YEAR);
int intEMonth = cEnd.get(Calendar.MONTH)+1;
int intEDay = cEnd.get(Calendar.DATE);
int intEHour = cEnd.get(Calendar.HOUR);
int intEMinute = cEnd.get(Calendar.MINUTE);
int intEAMPM = cEnd.get(Calendar.AM_PM);
if (intEAMPM == Calendar.PM) {
  intEHour += 12;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>批准停复电时间</title>
<script language="javascript">
var sYear = "<%=intSYear %>";
var sMonth = "<%=intSMonth %>";
var sDay = "<%=intSDay %>";
var sHour = "<%=intSHour %>";
var sMinute = "<%=intSMinute %>";
var eYear = "<%=intEYear %>";
var eMonth = "<%=intEMonth %>";
var eDay = "<%=intEDay %>";
var eHour = "<%=intEHour %>";
var eMinute = "<%=intEMinute %>";
var id = '<%=request.getParameter("ID")%>';
var start = "";
var end = "";
function setStartTime() {
  for(i = 0; i < document.form.startYear.length; i++) {
    if (document.form.startYear[i].value == sYear) {
      document.form.startYear[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.startMonth.length; i++) {
    if (document.form.startMonth[i].value == sMonth) {
      document.form.startMonth[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.startDay.length; i++) {
    if (document.form.startDay[i].value == sDay) {
      document.form.startDay[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.startHour.length; i++) {
    if (document.form.startHour[i].value == sHour) {
      document.form.startHour[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.startMinute.length; i++) {
    if (document.form.startMinute[i].value == sMinute) {
      document.form.startMinute[i].selected = true;
      break;
    }
  }
}
function setEndTime() {
  for(i = 0; i < document.form.endYear.length; i++) {
    if (document.form.endYear[i].value == eYear) {
      document.form.endYear[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.endMonth.length; i++) {
    if (document.form.endMonth[i].value == eMonth) {
      document.form.endMonth[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.endDay.length; i++) {
    if (document.form.endDay[i].value == eDay) {
      document.form.endDay[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.endHour.length; i++) {
    if (document.form.endHour[i].value == eHour) {
      document.form.endHour[i].selected = true;
      break;
    }
  }
  for(i = 0; i < document.form.endMinute.length; i++) {
    if (document.form.endMinute[i].value == eMinute) {
      document.form.endMinute[i].selected = true;
      break;
    }
  }
}
function buildTime(form) {
    start = form.startYear.value+"-"+form.startMonth.value+"-"+form.startDay.value+" ";
    start += form.startHour.value+":"+form.startMinute.value+":00";

    end = form.endYear.value+"-"+form.endMonth.value+"-"+form.endDay.value+" ";
    end += form.endHour.value+":"+form.endMinute.value+":00";
}
function submitForm(form) {
  buildTime(form);
	opener.buildTime("PZTDSJ", start);
	opener.buildTime("PZFDSJ", end);
	window.close();
}
</script>
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" method="post" action="" id="form">
      <table width="100%"  border="1">
        <tr>
          <td><div align="center">开始时间</div></td>
        </tr>
        <tr>
          <td><div align="center">
            <select name="startYear" id="startYear">
              <option value="2003">2003</option>
                <option value="2004">2004</option>
                <option value="2005">2005</option>
                <option value="2006">2006</option>
            </select>
  年
  <select name="startMonth" id="startMonth">
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
  <select name="startDay" id="startDay">
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
  <select name="startHour" id="startHour">
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
  <select name="startMinute" id="startMinute">
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
  分</div></td>
        </tr>
        <tr>
          <td><div align="center">结束时间</div></td>
        </tr>
        <tr>
          <td><div align="center">
            <select name="endYear" id="endYear">
              <option value="2004">2004</option>
              <option value="2005">2005</option>
              <option value="2006">2006</option>
            </select>
年
<select name="endMonth" id="endMonth">
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
<select name="endDay" id="endDay">
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
<select name="endHour" id="endHour">
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
<select name="endMinute" id="endMinute">
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
分</div></td>
        </tr>
        <tr>
          <td><div align="center">
            <input type="button" name="Button" value="确定" onClick="submitForm(this.form)">
&nbsp;&nbsp;&nbsp;
<input type="button" name="Button" value="取消" onClick="javascript:window.close()">
          </div></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
<script language="javascript">
setStartTime();
setEndTime();
</script>
</html>
