<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
  <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
  <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
  <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
  <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
  <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.css">
  <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.print.css" media='print'>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/third-party/accounting.min.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/system/ConfigClass.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/boot.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/highcharts4/js/highcharts.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lib/moment.min.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.min.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lang/zh-cn.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/callcenter/callcenter.js"></script>


  <%--呼叫中心--%>
  <link href="<%=Config.getWebRoot()%>/include/framework/7moor/edb_bar/css/pages.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/framework/7moor/edb_bar/js/icallcenter/global.js"></script>
  <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/framework/7moor/edb_bar/hojo/hojo.js" djConfig="isDebug:false, parseOnLoad:false"></script>
  <script type="text/javascript">
    function get() {
        var url = WEB_ROOT + '/pay/AllinpayCircle_getMessage';
        fw.post(url, null, function(data){

            console.log(data);

            fw.textSetValue('msgPlain', data['msgPlain']);
        }, null);
    }

    function doSubmit() {
        $('#form1').submit();
    }
  </script>
</head>

<body>
<form id="form1" name="form1" method="post" action="http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action">
  <table width="100%">
	<tr>
      <td>URL</td>
      <td><input name="url" type="text" id="url" size="100" value="http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action" /></td>
    </tr>
    <tr>
      <td>Message</td>
      <td><textarea name="msgPlain" cols="100" rows="5" id="msgPlain"></textarea></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="button" name="button" id="button" value="Get" onclick="get()" />
        <input type="button" name="button2" id="button2" value="Submit" onclick="doSubmit()" />
      <input type="reset" name="button3" id="button3" value="Reset" /></td>
    </tr>
  </table>
</form>
</body>
</html>