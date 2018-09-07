<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/3
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>厚币财富</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <div id="imgs<%=token%>" style="overflow:hidden">
            <img  id='im<%=token%>' onload="AutoResizeImage(400,400,this)" />
        </div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSearchFileDownload<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-search">下载</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('filesPerview<%=token%>')">取消</a>
    </div>
</div>
<script type="application/javascript">
    function AutoResizeImage(maxWidth,maxHeight,objImg){
        var img = new Image();
        img.src = objImg.src;
        var hRatio;
        var wRatio;
        var Ratio = 1;
        var w = img.width;
        var h = img.height;
        wRatio = maxWidth / w;
        hRatio = maxHeight / h;
        if (maxWidth ==0 && maxHeight==0){
            Ratio = 1;
        }else if (maxWidth==0){//
            if (hRatio<1) Ratio = hRatio;
        }else if (maxHeight==0){
            if (wRatio<1) Ratio = wRatio;
        }else if (wRatio<1 || hRatio<1){
            Ratio = (wRatio<=hRatio?wRatio:hRatio);
        }
        if (Ratio<1){
            w = w * Ratio;
            h = h * Ratio;
        }
        objImg.height = h;
        objImg.width = w;
    }
</script>
</body>
</html>