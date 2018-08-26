<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.entity.po.system.LogPO" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.service.system.LogService" %>
<%@ page import="sun.rmi.runtime.Log" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.service.system.SystemFinanceSimpleRecordService" %>
<%@ page import="com.youngbook.entity.po.system.SystemFinanceSimpleRecordAccountName" %>
<%

    String order_pay_code = request.getParameter("order_pay_code");
    String order_pay_error = request.getParameter("order_pay_error");

    String parameters = com.youngbook.common.utils.HttpUtils.getParametersStringValue(request);
    LogService logService = Config.getBeanByName("logService", LogService.class);



    logService.save("富友支付", "富友支付-网关支付-返回", parameters);

    String message = "操作成功";
    if (!StringUtils.isEmpty(order_pay_code) && !order_pay_code.equals("0000")) {
        message = "操作失败，富友返回值【"+order_pay_code+"】【"+order_pay_error+"】";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
请求结果：<%=message%>
</body>
</html>