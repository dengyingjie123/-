<%@ page import="com.youngbook.common.config.Config" %>
<!doctype html>
<html style="">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>尝试写插件</title>
    <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
    <style>
        .ui-loader-default{ display:none}
        .ui-mobile-viewport{ border:none;}
        .ui-page {padding: 0; margin: 0; outline: 0}
        #lunbobox {
            width:100%;
            height: 600px;
            overflow: hidden;
            position:relative;
        }
        .lunbo {
            width:100%;
            overflow: hidden;
        }
        .lunbo img {
            width:100%;
            display: block;
            position:absolute;
            top:0px;
            left:0px;
        }
        #lunbobox ul {
            width:285px;
            position:absolute;
            top: 90%;
            right:0px;
            z-index:5;
        }
        #lunbobox ul li {
            cursor:pointer;
            width:15px;
            height:15px;
            border-radius: 15px;
            border:1px solid #cccccc;
            float:left;
            list-style:none;
            background:#cccccc;
            text-align:center;
            margin:0px 5px 0px 0px;
        }
        #lunbobox ul li.current{
            background-color: #999;
            border: 1px solid #fff;
        }
        #toleft {
            display:none;
            width:100px;
            height:100px;
            font-size:100px;
            line-height:100px;
            text-align:center;
            color:#f4f4f4;
            position:absolute;
            top:90px;
            left:12px;
            cursor:pointer;
            z-index:99;
            opacity:0.4;
        }
        #toright {
            display:none;
            width:100px;
            height:100px;
            font-size:100px;
            line-height:100px;
            text-align:center;
            color:#f4f4f4;
            position:absolute;
            top:90px;
            right:0px;
            cursor:pointer;
            z-index:99;
            opacity:0.4;
        }
    </style>
</head>
<body>
<div class="middle_right">
    <div id="lunbobox">
        <div id="toleft">&lt;</div>
        <div class="lunbo">
            <a href="#"><img src="<%=Config.getWebDehecircle()%>/include/img/test01.png"></a>
            <a href="#"><img src="<%=Config.getWebDehecircle()%>/include/img/test02.png"></a>
            <a href="#"><img src="<%=Config.getWebDehecircle()%>/include/img/test03.png"></a>
            <a href="#"><img src="<%=Config.getWebDehecircle()%>/include/img/test04.png"></a>
            <a href="#"><img src="<%=Config.getWebDehecircle()%>/include/img/test05.png"></a>
        </div>
        <div id="toright">&gt;</div>
        <ul>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
        <span></span>
    </div>
</div>
<script src="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script>
    $(function() {
        $('#toright').hover(function() {
            $("#toleft").hide()
        }, function() {
            $("#toleft").show()
        })
        $('#toleft').hover(function() {
            $("#toright").hide()
        }, function() {
            $("#toright").show()
        })
    })
    var t;
    var index = 0;
    var index_len = $('.lunbo a').length;
    var index_number = index_len - 1;
    console.log(index_len);
    //自动播放
    t = setInterval(play, 3000)
    function play() {
        index++;
        if (index > 4) {
            index = 0
        }
        $("#lunbobox ul li").eq(index).addClass('current').siblings().removeClass('current');

        $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
    };

    //点击鼠标 图片切换
    $("#lunbobox ul li").click(function() {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $(this).index();
        $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
    });

    //上一张、下一张切换
    $("#toleft").click(function() {
        index--;
        if (index <= 0)
        {
            index = 4
        }
        console.log(index);
        $("#lunbobox ul li").eq(index).addClass('current').siblings().removeClass('current');

        $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
    });

    $("#toright").click(function() {
        index++;
        if (index > 4) {
            index = 0
        }
        console.log(index);
        $(this).css({
            "opacity": "0.5"
        })
        $("#lunbobox ul li").eq(index).addClass('current').siblings().removeClass('current');
        $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
    });
    $("#toleft,#toright").hover(function() {
            $(this).css({
                "color": "black"
            })
        },
        function() {
            $(this).css({
                "opacity": "0.3",
                "color": ""
            })
        })


    //鼠标移进  移出  轮播停止/继续进行
    $("#lunbobox ul li,.lunbo a img,#toright,#toleft ").hover(
//鼠标移进
        function() {
            $('#toright,#toleft').show()
            clearInterval(t);
        },
//鼠标移开
        function() {
            t = setInterval(play, 3000)

            function play() {
                index++;
                if (index > 4) {
                    index = 0
                }
                $("#lunbobox ul li").eq(index).addClass('current').siblings().removeClass('current');
                $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
            }
        })


    // 手机端左右滑动
    $("#lunbobox").on("swipeleft",function(){
        console.log('向左滑动');
        $('.toright').click();
        index--;
        if (index <= 0)
        {
            index = 4
        }
        console.log(index);
        $("#lunbobox ul li").eq(index).addClass('current').siblings().removeClass('current');

        $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
    });
    $("#lunbobox").on("swiperight",function(){
        console.log('向右滑动');
        $('.toleft').click();
        index++;
        if (index > 4) {
            index = 0
        }
        console.log(index);
        $(this).css({
            "opacity": "0.5"
        })
        $("#lunbobox ul li").eq(index).addClass('current').siblings().removeClass('current');
        $(".lunbo a ").eq(index).fadeIn(1000).siblings().fadeOut(1000);
    });
</script>

</body>
</html>