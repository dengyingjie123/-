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

    //获取href路径
    var index = location.href;
    //通过？定位加截取字符串
    var params = index.substring(index.indexOf("?"));
    //拼接字符串
    var url = "/core/cms/Article_getArticlePO.action"+params;
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
