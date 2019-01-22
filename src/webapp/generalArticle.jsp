<%@ page import="com.youngbook.common.config.Config" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/1/22
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>

</head>
<body>

<script type="text/javascript">
    //FD8A58B4AAB74750BF4ABC6461CEB604
    //171C6186C24D4C088766C9BA9E0A6A84
    var all = location.href;
    var b = all.substring(all.indexOf("?"));
    console.log(b)
    var url = "/core/cms/Article_getArticlePO.action"+b;
    $(function () {
        fw.get(url, null, function (data) {
                var content = data.returnValue[0].content;
                var title = data.returnValue[0].title;
                $("body").html(content);
                $("title").html(title);
            },null
        )
    });
</script>
</body>
</html>
