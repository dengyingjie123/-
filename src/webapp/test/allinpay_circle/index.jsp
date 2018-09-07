<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action">
  <table width="100%">
	<tr>
      <td>URL</td>
      <td><input name="url" type="text" id="url" size="100" /></td>
    </tr>
    <tr>
      <td>Message</td>
      <td><textarea name="msgPlain" cols="100" rows="5" id="msgPlain"></textarea></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="button" name="button" id="button" value="Get" />
        <input type="button" name="button2" id="button2" value="Submit" />
      <input type="reset" name="button3" id="button3" value="Reset" /></td>
    </tr>
  </table>
</form>
</body>
</html>