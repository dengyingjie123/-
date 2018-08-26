<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<jsp:include page="./include/_head.jsp"></jsp:include>
<style type="text/css">
    .pinfo{
        font-size: 1em;
    }
</style>
<div data-role="page" id="home">
	<jsp:include page="./include/_topNav.jsp"></jsp:include>
	<!-- 首页主要页面加载区 -->
    
    <!--执行index要加载的js-->
    <script type="application/javascript" charset="utf-8">
        $(".knob").knob();
        $('.knob').closest('.ui-body-inherit').removeClass('ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset').css({'display':'inline-block','margin-left':'0.3em'});
        $(function(){
            $('#topNavBackBtn').hide();
            $('.show-page-loading-msg').on( "click",function() {    
                var $this = $( this ),
                    theme = $this.jqmData( "theme" ) || $.mobile.loader.prototype.options.theme,
                    msgText = $this.jqmData( "msgtext" ) || $.mobile.loader.prototype.options.text,
                    textVisible = $this.jqmData( "textvisible" ) || $.mobile.loader.prototype.options.textVisible,
                    textonly = !!$this.jqmData( "textonly" );
                    html = $this.jqmData( "html" ) || "";
                $.mobile.loading( "show", {
                        text: msgText,
                        textVisible: textVisible,
                        theme: theme,
                        textonly: textonly,
                        html: html
                });
                setTimeout(function(){
                    $.mobile.loading( "hide" );
                    var $ulli=$('<li class="ui-li-static ui-body-inherit ui-first-child ui-last-child"><div class="ui-grid-b"><p style="font-size: 1em;border-bottom: 1px solid #CCC;padding-left: 20px;padding-bottom: 10px;"><a href="productionDetail.jsp" style="text-decoration: none;">项目名字测试</a></p></div><div class="ui-grid-b"><div class="ui-block-a" style="border-right: 1px solid #EEE;"><div class="ui-grid-a"><div style="border-bottom: solid 1px #EEE;padding: 5px;font-size: 0.75em;">进度:<img src="./images/index/circle.png"></div><div><p>规模:<span>1000万</span></p></div></div></div><div class="ui-block-b" style="text-align: center;border-right: 1px solid #EEE;"> <p><span>产品期限：</span></p><div>90天</div><p></p></div><div class="ui-block-c" style="text-align: center;"><p>  <span>预期收益：</span></p><div>20万-40万</div><p></p></div></div></li>');
                    $('.XianMuXinXi').append($ulli);
                    $('.XianMuXinXi').listview("refresh");
                },2000);
            });
            $(".knob").knob();
            $('.knob').closest('.ui-body-inherit').removeClass('ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset').css({'display':'inline-block','margin-left':'0.3em'});
        });
    </script>
	<div role="main" class="ui-content">
		<ul data-role="listview" class="ui-listview XianMuXinXi">
            <s:iterator value="products">
                <li class="ui-li-static ui-body-inherit ui-first-child ui-last-child">
                    <div class="ui-grid-b">
                        <p style="font-size: 1em;border-bottom: 1px solid #CCC;padding-bottom: 10px;">
                            <a href="mobile/Index_product.action?sid=<s:property value="sid"/>" style="text-decoration: none;" class="ProductTitle" data-ajax="false"><s:property value="name"/></a>
                        </p>
                    </div>
                    <div class="ui-grid-b">
                        <div class="ui-block-a" style="border-right: 1px solid #EEE;">
                            <div class="ui-grid-a">
                                <div style="border-bottom: solid 1px #EEE;padding: 5px;font-size: 0.9em;">
                                    进度:<input class="knob" data-width="25" data-height="25" data-displayinput="false" data-thickness=.3 data-readOnly="true" value="<s:property value="progress"/>"/>
                                </div>
                                <div>
                                    <p style="font-size:0.9em;">
                                        规模:<span><s:property value="size"/>万</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="ui-block-b" style="text-align: center;border-right: 1px solid #EEE;">
                            <p style="font-size:0.9em;">
                                <span>产品期限：</span>
                            </p><div><s:property value="timeLimit"/>天</div>
                            <p></p>
                        </div>
                        <div class="ui-block-c" style="text-align: center;">
                            <p style="font-size:0.9em;">
                                <span>预期收益：</span>
                            </p><div><s:property value="lowestYield"/>-<s:property value="highestYield"/></div>
                            <p></p>
                        </div>
                    </div>
                </li>
            </s:iterator>

        </ul>
       <div style="text-align:center;width:100%;margin-top:30px;">
            <button class="ui-btn ui-icon-refresh ui-btn-icon-left show-page-loading-msg" data-textonly="false" data-textvisible="true" data-msgtext="正在加载" data-inline="true">加载更多</button>
        </div>
	</div>
	<jsp:include page="./include/_panel.jsp"></jsp:include>
</div>