<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
/*
 ��������
 ˵������ʾʱ��ؼ��Ĺ���ҳ�档
      �ÿؼ�����ʾ �� �� �� ʱ �֣� ������ݽ���ʾΪ��ʼ���ǰ��ʮ��ķ�Χ
      ��ҳ����Ҫ������������
      ����1�� ���ƣ�ID
             ˵���������Զ�������Ϊ��ID�����������ڱ���ؼ���ʾ��ʱ��
                  �����е�Javascript�ű��еı��������Դ�ID��Ϊǰ׺����������
                  ע���ڵ��ô�ҳ���ʱ��ID�����ظ�
      ����2�� ���ƣ�Date
             ˵�����ò��������˳���ĳ�ʼʱ��
                  �ò����ǿ�ѡ���������û�ж����ʼ��ʱ�䣬�򽫵�ǰʱ����Ϊ��ʼ��
 ���÷�������JSPҳ����
         <jsp:include page="../../Time.jsp" flush="true">
           <jsp:param name="ID" value="scbgsj"/>
           <jsp:param name="Date" value="2005-3-9 2:39"/>
         </jsp:include>

 */
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
//��Request�л��ID�������Զ�������Ϊ��ID�����������ڱ���ؼ���ʾ��ʱ��
String id = request.getParameter("ID")!=null?request.getParameter("ID"):"a";
//��Request�л�ȡʱ�䣬���û�ж����ʼ��ʱ�䣬�򽫵�ǰʱ����Ϊ��ʼ��
String strTime = request.getParameter("Date")!=null?request.getParameter("Date"):"";
//System.out.println("init Time: " + strTime);
String strYear    =    new String();    //�������
String strMonth   =    new String();    //�����·�
String strDay     =    new String();    //��������



//��ʼ��ʱ��
if (strTime != null && !strTime.equals("")) {
  //��Request�л�õ�ʱ���ʼ��ʱ��ؼ�
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  Date date = sdf.parse(strTime);
  Calendar cal = Calendar.getInstance();
  cal.setTime(date);

  strYear = String.valueOf(cal.get(Calendar.YEAR));            //��ó�ʼ�����
  strMonth = String.valueOf(cal.get(Calendar.MONDAY) + 1);     //��ó�ʼ���·�
  strDay = String.valueOf(cal.get(Calendar.DATE));             //��ó�ʼ������
}
%>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><div align="center">
        <select name="<%=id%>_nian_Time" id="<%=id%>_nian_Time" onChange="<%=id%>Change()">
        </select>
    </div></td>
    <td><div align="center">��</div></td>
    <td><div align="center">
        <select name="<%=id%>_month_Time" id="<%=id%>_month_Time" onChange="<%=id%>Change()">
        </select>
    </div></td>
    <td><div align="center">��</div></td>
    <td><div align="center">
        <select name="<%=id%>_day_Time" id="<%=id%>_day_Time" onChange="<%=id%>Change()">
        </select>
    </div></td>
    <td><div align="center">��</div></td>
  </tr>
</table>
<input name="<%=id%>" id="<%=id%>"  type="hidden">
<script language="javascript">
function <%=id%>_ResetTime() {
  //����ʱ��
  <%=id%>initTime();
}
function <%=id%>Change() {
  //��ʱ�����������ID�������е�����
  document.all.<%=id%>.value = document.all.<%=id%>_nian_Time.value + "-" +
                               document.all.<%=id%>_month_Time.value + "-" +
                               document.all.<%=id%>_day_Time.value;
}
function <%=id%>initTime() {
  // ��ʼ����� Year
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
  // ��ʼ���·� Month
  var thisMonth = <%=strMonth.equals("")?"now.getMonth() + 1":strMonth%>;
  var <%=id%>_month_Time = document.all.<%=id%>_month_Time;
  for (i = 0; i < 12; i++) {
    var months = document.createElement("option");
    months.text = i + 1;
    months.value = i + 1;
    <%=id%>_month_Time.add(months);
  }
  <%=id%>_month_Time.options[thisMonth-1].selected = true;
  // ��ʼ���� Day
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
<%=id%>initTime();  //��ʼ��ʱ��
<%=id%>Change();  //����ID�������е�����

</script>
