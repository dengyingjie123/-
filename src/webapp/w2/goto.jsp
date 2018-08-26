<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.service.customer.CustomerPersonalService" %><%
    String url = "/core/w2/index.jsp";
    if (request.getAttribute("url") != null) {
        url = "/core" + request.getAttribute("url");
    }

%>
<script>
    window.location="<%=url%>";
</script>
正在加载，请稍候……<br>
若长时间无响应，则点击这里返回<a href="/core/w2/">首页</a>