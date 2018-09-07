<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<style>
    body{
        font-size: 0.75em;
    }
</style>
<jsp:include page="./include/_head.jsp"></jsp:include>
    
<script type="text/javascript">
    $(function(){
        $('#saveBtn').on('click',function(){
            $('#testbtn').click();
        });
    });    
</script>
    
<div data-role="page" id="detail" data-add-back-btn="true">
	<jsp:include page="./include/_topNav.jsp"></jsp:include>
	<!-- 首页主要页面加载区 -->
	<script type="text/javascript">
        $(function(){
            $('#topHeadText').html('产品详情');
        });
    </script>
	 
	 <div role="main" class="ui-content" style="background-color: #EEE;padding: 0em;">
         <div class="ui-grid-a" style="background: #FFF;">    
             <div class="ui-block-a" style="width: 20%;">
                <div style="width: 100%;height: 6em;padding: 1em;text-align: center;">
                    <!--<img src="<%=Config.getWebRoot() %>/mobile/images/detail/118.png" style="width: 100%;height: 100%;">-->
                 </div>
                 <div style="width: 100%;height: 6em;padding: 1em;text-align: center;">
                    <!--<img src="<%=Config.getWebRoot() %>/mobile/images/detail/118.png" style="width: 100%;height: 100%;">-->
                 </div>
             </div>
             <div class="ui-block-b" style="width:80%;padding-left: 1em;">
                <h4 style="padding:0 0 0.5em 0.75em;border-bottom: 1px solid #8C8888;color:black;font-weight:bold;">${product.name}</h4>
                <div>
                    <p style="color: rgb(211, 200, 200);">
                        产品期限：
                        <span style="color: black;font-weight: bold;">${product.timeLimit}</span>
                    </p>
                    <p style="color: rgb(211, 200, 200);">
                        预期收益：
                        <span style="color: black;font-weight: bold;">${product.lowestYield}-${product.highestYield}</span>
                    </p>
                    <p style="color: rgb(211, 200, 200);">
                        规模：
                        <span style="color: black;font-weight: bold;">${product.size}</span>
                    </p>
                </div>
             </div>            
         </div>
         <div class="ui-grid-a" style="background: #FFF;margin-top: 1em;">
            <h4 style="padding: 0 0 0.5em 0.75em;border-bottom: 1px solid #CCC;color: black;font-weight: bold;">详情</h4>
             <div style="padding-left:1em;">
                <p style="color: rgb(211, 200, 200);">
                    <span style="color: black;font-weight: bold;">${article.content}</span>
                </p>
            </div>
         </div>
         
        <div style="text-align:center;border-top: 1px solid #A9A7D5;padding: 10px;">
            <a href="#popupLogin" data-rel="popup" data-position-to="window" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all" data-transition="slidedown" aria-haspopup="true" aria-owns="popupLogin" aria-expanded="false">
                预定
                <div data-role="popup" id="popupLogin" data-theme="a" class="ui-corner-all">
                    <form>
                        <div style="padding:10px 20px;">
                            <h3>请输入金额</h3>
                            <input type="text" name="jine" id="jine" value="" placeholder="请输入金额" data-theme="a">
                            <button type="submit" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-check">确定</button>
                        </div>
                    </form>
                </div>
            </a>
        </div>
	 </div>
     <jsp:include page="./include/_panel.jsp"></jsp:include>
</div>