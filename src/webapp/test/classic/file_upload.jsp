<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
<form action="<%=Config.getWebRoot()%>/system/FileUpload_upload?files.moduleId=5458" method="post" enctype="multipart/form-data" name="form1" id="form1">
  <table border="1">
    <tr>
      <td>uploadFileName</td>
      <td><label>
        <input name="name" type="text" id="name" value="abc" />
      </label></td>
    </tr>
    <tr>
      <td>upload</td>
      <td><label>
        <input type="file" name="upload" id="upload" />
      </label></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="submit" name="button" id="button" value="Submit" /></td>
    </tr>
  </table>
</form>
</body>
</html>