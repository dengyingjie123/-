<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <head>
    <base href="<%=basePath%>">
    <title>厚币财富</title>
   	<meta http-equiv="X-UA-Compatible" content="IE=edgeh">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<meta name="viewport" content="width=device-width, initial-scale=1" />  
	<meta http-equiv="keywords" content="Colorbox">
	<meta http-equiv="description" content="Colorbox">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="<%=Config.getWebRoot() %>/include/mobile/plugin/jquery-mobile/css/jquery.mobile-1.4.4.css" type="text/css" rel="stylesheet"/>
	
	<script src="<%=Config.getWebRoot() %>/include/mobile/plugin/jquery/jquery-1.7.2.min.js" type="text/javascript" language="javascript"></script>
	<script src="<%=Config.getWebRoot() %>/include/mobile/plugin/knob/jquery.knob.js" type="text/javascript" language="javascript"></script>
	
	<script src="<%=Config.getWebRoot() %>/include/mobile/plugin/jquery-mobile/js/jquery.mobile-1.4.4.min.js" type="text/javascript" language="javascript"></script>
   
     <!-- 新 Bootstrap 核心 CSS 文件 -->
     <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">


     <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
     <script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
     
     <script type="text/javascript">
       $(function(){
            //初始化滑动删除元素函数
           $(document).on( "swipeleft","body",function( event ) {
               $( "#menuManage" ).panel( "open");
           });
       });
     </script>
    <style type="text/css">
        .ProductTitle{
            font-size: 1.2em;
        }
    </style>
  </head>
