<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">

      <div  style="text-align:center;"  border="false"  >
          <table border="0" cellpadding="3" cellspacing="0">
              <tr>
                  <td>部门</td>
                  <td><input type="text" class="easyui-combotree" id="search_Department<%=token %>" style="width:150px;" editable="false" /></td>
                  <td>收支</td>
                  <td><input type="text" class="easyui-combotree" id="search_InOrOut<%=token %>" style="width:80px;" editable="false" /></td>
                  <td>账务开始时间</td>
                  <td><input type="text" class="easyui-datebox" id="search_MoneyTime_Start<%=token %>" style="width:90px;" editable="false" /></td>
                  <td>账务结束时间</td>
                  <td><input type="text" class="easyui-datebox" id="search_MoneyTime_End<%=token %>" style="width:90px;" editable="false" /></td>

              </tr>
          </table>
      </div>


          <table id="TravelExpenseDetailGuanLiTable<%=token%>" style="width:auto;height: auto"  ></table>




</div>
</body>
</html>