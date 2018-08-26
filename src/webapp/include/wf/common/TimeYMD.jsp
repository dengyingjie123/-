<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
/*
 程序：李扬
 说明：显示时间控件的公共页面。
      该控件将显示 年 月 日 时 分， 其中年份将显示为初始年份前后十年的范围
      该页面需要传入两个参数
      参数1， 名称：ID
             说明：程序将自动生成名为此ID的隐藏域，用于保存控件所示的时间
                  程序中的Javascript脚本中的变量，都以此ID作为前缀，加以区分
                  注意在调用此页面的时候，ID不能重复
      参数2， 名称：Date
             说明：该参数定义了程序的初始时间
                  该参数是可选参数，如果没有定义初始化时间，则将当前时间作为初始化
 调用方法：在JSP页面中
         <jsp:include page="../../Time.jsp" flush="true">
           <jsp:param name="ID" value="scbgsj"/>
           <jsp:param name="Date" value="2005-3-9 2:39"/>
         </jsp:include>

 */
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
//从Request中获得ID，程序将自动生成名为此ID的隐藏域，用于保存控件所示的时间
String id = request.getParameter("ID")!=null?request.getParameter("ID"):"a";
//从Request中获取时间，如果没有定义初始化时间，则将当前时间作为初始化
String strTime = request.getParameter("Date")!=null?request.getParameter("Date"):"";
//System.out.println("init Time: " + strTime);
String strYear    =    new String();    //保存年份
String strMonth   =    new String();    //保存月份
String strDay     =    new String();    //保存日期



//初始化时间
if (strTime != null && !strTime.equals("")) {
  //用Request中获得的时间初始化时间控件
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  Date date = sdf.parse(strTime);
  Calendar cal = Calendar.getInstance();
  cal.setTime(date);

  strYear = String.valueOf(cal.get(Calendar.YEAR));            //获得初始化年份
  strMonth = String.valueOf(cal.get(Calendar.MONDAY) + 1);     //获得初始化月份
  strDay = String.valueOf(cal.get(Calendar.DATE));             //获得初始化日期
}
%>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><div align="center">
        <select name="<%=id%>_nian_Time" id="<%=id%>_nian_Time" onChange="<%=id%>Change()">
        </select>
    </div></td>
    <td><div align="center">年</div></td>
    <td><div align="center">
        <select name="<%=id%>_month_Time" id="<%=id%>_month_Time" onChange="<%=id%>Change()">
        </select>
    </div></td>
    <td><div align="center">月</div></td>
    <td><div align="center">
        <select name="<%=id%>_day_Time" id="<%=id%>_day_Time" onChange="<%=id%>Change()">
        </select>
    </div></td>
    <td><div align="center">日</div></td>
  </tr>
</table>
<input name="<%=id%>" id="<%=id%>"  type="hidden">
<script language="javascript">
function <%=id%>_ResetTime() {
  //重置时间
  <%=id%>initTime();
}
function <%=id%>Change() {
  //当时间变更，则更新ID隐藏域中的数据
  document.all.<%=id%>.value = document.all.<%=id%>_nian_Time.value + "-" +
                               document.all.<%=id%>_month_Time.value + "-" +
                               document.all.<%=id%>_day_Time.value;
}
function <%=id%>initTime() {
  // 初始化年份 Year
  var now = new Date();
  var thisYear = <%=strYear.equals("")?"now.getYear()":strYear%>;
  var <%=id%>_nian_Time = document.all.<%=id%>_nian_Time;
  var i = 0;
  var index = 0;
  for (i = 0; i < 20; i++) {
    var years = document.createElement("option");
    years.text = thisYear - 10 + i;
    years.value = thisYear - 10 + i;
    document.all.<%=id%>_nian_Time.add(years);
    if (years.value == thisYear) {
      index = <%=id%>_nian_Time.length;
    }
  }
  <%=id%>_nian_Time.options[index-1].selected = true;
  // 初始化月份 Month
  var thisMonth = <%=strMonth.equals("")?"now.getMonth() + 1":strMonth%>;
  var <%=id%>_month_Time = document.all.<%=id%>_month_Time;
  for (i = 0; i < 12; i++) {
    var months = document.createElement("option");
    months.text = i + 1;
    months.value = i + 1;
    <%=id%>_month_Time.add(months);
  }
  <%=id%>_month_Time.options[thisMonth-1].selected = true;
  // 初始化天 Day
  var thisDay = <%=strDay.equals("")?"now.getDate()":strDay%>;
  var <%=id%>_day_Time = document.all.<%=id%>_day_Time;
  for (i = 1; i <= 31; i++) {
    var days = document.createElement("option");
    days.text = i;
    days.value = i;
    <%=id%>_day_Time.add(days);
  }
  <%=id%>_day_Time.options[thisDay-1].selected = true;
}
<%=id%>initTime();  //初始化时间
<%=id%>Change();  //更新ID隐藏域中的数据

</script>
