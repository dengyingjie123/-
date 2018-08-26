<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
    language="java" %>
<%
    String token = request.getParameter("token") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Untitled Document</title>

</head>

<body>
<div style="padding:5px;">
    <div class="easyui-panel" title="报表" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="5">
            <tr>
                <td>
                	<table width="100%" border="0" cellpadding="3">
                	    <tr>
                	        <td>开始时间：</td>
                	        <td><input type="text" class="easyui-datebox" id="search_SpendRate_Start<%=token %>" style="width:90px;" editable="false" />&nbsp;&nbsp;结束时间：<input type="text" class="easyui-datebox" id="search_SpendRate_End<%=token %>" style="width:90px;" editable="false" /></td>
            	        </tr>
                	    <tr>
                	        <td>部门：</td>
                	        <td><input type="text" class="easyui-combotree" id="search_SpendRate_Department<%=token %>" style="width:200px;" editable="false" /></td>
            	        </tr>
                	    <tr>
                	        <td>类别：</td>
                	        <td><input type="text" class="easyui-combotree" id="search_SpendRate_Type<%=token %>" style="width:150px;" editable="false" /></td>
            	        </tr>
                        <tr>
                	        <td colspan="2">
                            	<a id="btnSearchSpendRate<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                                <a id="btnResetSpendRate<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                                <!-- <a id="btnSearchSpendRate1Month<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">近一月</a>
                                <a id="btnSearchSpendRate3Month<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">近三月</a> -->
                            </td>
               	        </tr>
                	    <tr>
                	        <td colspan="2"><div id="SpendRateContainer<%=token %>" style="min-width: 480px; height: 480px; max-width: 700px; margin: 0 auto"></div></td>
               	        </tr>
   	            </table></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>