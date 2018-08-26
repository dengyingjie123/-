<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta content="telephone=yes" name="format-detection" />
    <meta name="apple-mobile-web-app-status-bar-style" content="white">
    <meta name="x5-fullscreen" content="true">
    <meta name="apple-touch-fullscreen" content="yes">
    <title>Document</title>
    <style>
        *{margin:0;padding:0;}
        .box{
            height:200px;
            width:100%;
            overflow: hidden;
        }
        .movebox{
            height:200px;
            width:9000px;
            padding:0;
            position:relative;
            left:0;
        }
        .movebox li{
            height:200px;
            float:left;
            list-style:none;
            font-size:30px;
            color:#fff;
        }

    </style>
    <script>
        window.onload = function(){

            var moveX,      //手指滑动距离
                endX,       //手指停止滑动时X轴坐标
                cout = 0,   //滑动计数器
                moveDir;    //滑动方向
            var movebox = document.querySelector(".movebox");   //滑动对象
            var Li = movebox.querySelectorAll("li");    //滑动对象item
            var width = parseInt(window.getComputedStyle(movebox.parentNode).width);    //滑动对象item的宽度

            movebox.style.width = (width*4) + "px"; //设置滑动盒子width
            for(var i = 0; i < Li.length; i++){
                Li[i].style.width = width + "px";   //设置滑动item的width，适应屏幕宽度
            }

            //触摸开始
            function boxTouchStart(e){
                var touch = e.touches[0];   //获取触摸对象
                startX = touch.pageX;   //获取触摸坐标
                endX = parseInt(movebox.style.webkitTransform.replace("translateX(", ""));  //获取每次触摸时滑动对象X轴的偏移值
            }

            function boxTouchMove(e){
                var touch = e.touches[0];
                moveX = touch.pageX - startX;   //手指水平方向移动的距离

                if(cout == 0 && moveX > 0){     //刚开始第一次向左滑动时
                    return false;
                }

                if(cout == 3 && moveX < 0){     //滑动到最后继续向右滑动时
                    return false;
                }

                movebox.style.webkitTransform = "translateX(" + (endX + moveX) + "px)"; //手指滑动时滑动对象随之滑动
            }

            function boxTouchEnd(e){
                moveDir = moveX < 0 ? true : false;     //滑动方向大于0表示向左滑动，小于0表示向右滑动
                //手指向左滑动
                if(moveDir){

                    if(cout<3){
                        movebox.style.webkitTransform = "translateX(" + (endX-width) + "px)";
                        cout++;
                    }
                    //手指向右滑动
                }else{
                    //滑动到初始状态时返回false
                    if(cout == 0){
                        return false;
                    }else{
                        movebox.style.webkitTransform = "translateX(" + (endX+width) + "px)";
                        cout--;
                    }
                }
            }

            //滑动对象事件绑定
            movebox.addEventListener("touchstart", boxTouchStart, false);
            movebox.addEventListener("touchmove", boxTouchMove, false);
            movebox.addEventListener("touchend", boxTouchEnd, false);
        }
    </script>
</head>

<body style="position:absolute;width:100%;overflow:hidden;">
<div class="box">
    <ul class="movebox" style="transition-duration:0.2s;transform: translateX(-0px);">
        <li style="background:red;">1</li>
        <li style="background:yellow">2</li>
        <li style="background:blue">3</li>
        <li style="background:green">4</li>
    </ul>
</div>
</body>

</html>