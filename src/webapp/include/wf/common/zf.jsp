<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<p align="center" class="SmallTitleStyle">**作废</p>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form action="" method="post" name="formYJ" id="formYJ">
      <table width="100%" border="0" cellpadding="1" cellspacing="1" class="TableStyle" >
        <tr>
          <td height="30"><div align="center">作废批准人：</div></td>
          <td><input name="textfield2" type="text" size="60"></td>
        </tr>
        <tr>
          <td rowspan="2" valign="top"><div align="center">作废原因：</div></td>
          <td height="30"><textarea name="textfield" cols="60" rows="10" wrap="VIRTUAL" class="InputeStyle"></textarea></td>
        </tr>
        <tr>
          <td height="30"><input type="button" name="Button" value="签名">
            <input type="button" name="Submit2" value="取消" onClick="window.close()"></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
window.focus();
</script>