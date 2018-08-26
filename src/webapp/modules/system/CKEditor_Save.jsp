<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String token = request.getParameter("token");
%>
<html>
<head>
  <title>标题</title>
</head>
<body>
<div class="easyui-layout" fit="true">
  <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
    <form id="formCKEditor<%=token %>" name="formCKEditor" action="" method="post">
      <table border="0" cellspacing="5" cellpadding="0">
        <tr>
            <td><textarea type="text" name="ckeditor" id="ckeditor<%=token%>"  style="width:400px" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
    <a id="btnCKEditorSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CKEditorWindow<%=token%>')">取消</a>
  </div>
</div>
</body>
</html>