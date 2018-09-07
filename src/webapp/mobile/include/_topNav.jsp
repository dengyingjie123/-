<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<script type="text/javascript">
    $(function(){
        $('#topHeadText').on('click',function(){
            $('body').animate({scrollTop:0},500);
        });        
    });
</script>
<style>
    #topHeadText{
        cursor: pointer;
    }
    .topNav{
        border: 1px solid #456f9a;
        background: #522487;
        color: #fff;
        font-weight: bold;
        text-shadow: 0 1px 0 #3e6790;
        background-image: linear-gradient( #522487, #7A46B6);
    }
</style>
<!-- 顶部导航栏开始 -->
<div class="topNav" data-role="header" data-position="fixed" data-theme="a">    
    <div class="ui-grid-b">
        <div class="ui-block-a">
        	<a data-role="button" id="topNavBackBtn" data-rel="back" class="ui-btn ui-icon-arrow-l ui-btn-icon-left" style="border-radius: 20px;
border: 1px solid #044062;
background: #7136B5;
font-weight: bold;
color: #fff;
text-shadow: 0 1px 0 #194b7e;">
          		  返回
        	</a>
        </div>
        <div class="ui-block-b" style="text-align:center;">
            <h3 style="margin-bottom: 0px;margin-top: 10px;" id="topHeadText">厚币财富</h3>
        </div>
        <div class="ui-block-c" style="text-align:right;">
            <a data-role="none"  href="#menuManage" class="ui-btn ui-icon-bullets ui-btn-icon-left" style="border-radius: 20px;
border: 1px solid #044062;
background: #7136B5;
font-weight: bold;
color: #fff;
text-shadow: 0 1px 0 #194b7e;">
          		  工具
        	</a>
        </div>
   </div>   
   

</div>