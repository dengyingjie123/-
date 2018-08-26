/**
 * Created by 邓超
 * Date 2015-5-5
 *
 * 这是一个易用的的弹窗插件，用于小框正确或错误提示、加载中提示
 */

var windowWidth, windowHeight, popstatus, outTime;

/*
 * 获取浏览器的高度、宽度
 */
function whWindow() {
    windowWidth = $(window).width();
    windowHeight = $(window).height();
}

/*
 * 监听浏览器窗体
 */
$(window).resize(function () {
    whWindow();
    popStatuRe();
});
whWindow();

function popStatus(status, html, timeout) {
    popStatus(status, html, timeout, undefined, undefined, undefined);
}

/**
 * 压入提示
 * @param status                类型：1、正确，2、提示，3、加载中，4、失败
 * @param html                  提示信息：HTML 代码
 * @param timeout               超时时间：以秒为单位
 * @param url                   跳转页面：不使用则留空
 * @param isOpenWindow          是否新窗口的方式打开链接
 * @param HopeAlert_bremove     使用遮罩：不使用则留空
 */
function popStatus(status, html, timeout, url, isOpenWindow, HopeAlert_bremove) {
    //请求超时时间
    var timeous = 20;
    clearTimeout(popstatus);
    clearTimeout(outTime);
    $('body #HopeAlert_wstatus').remove();
    $('body #HopeAlert_bremove').remove();
    if (status == 1) {
        $('body').append('<div id="HopeAlert_wstatus"><div class="HopeAlert_wstatus_s HopeAlert_wstatus_s1"></div><span class="HopeAlert_wstatus_f">' + html + '</span></div>');
    } else if (status == 2) {
        $('body').append('<div id="HopeAlert_wstatus"><div class="HopeAlert_wstatus_s HopeAlert_wstatus_s2"></div><span class="HopeAlert_wstatus_f">' + html + '</span></div>');
    } else if (status == 3) {
        $('body').append('<div id="HopeAlert_wstatus"><div class="HopeAlert_wstatus_s HopeAlert_wstatus_s3"></div><span class="HopeAlert_wstatus_f">' + html + '</span></div>');
    } else {
        $('body').append('<div id="HopeAlert_wstatus"><div class="HopeAlert_wstatus_s HopeAlert_wstatus_s4"></div><span class="HopeAlert_wstatus_f">' + html + '</span></div>');
    }
    popStatuRe();
    //是否使用遮照
    if (HopeAlert_bremove) {
        $('body').append('<div id="HopeAlert_bremove" />');
    }
    if (!url) {
        url = 0;
    }
    //抖动
//    if (status == 2 || status == 4) {
//        var sw = (windowWidth / 2) - ($('#HopeAlert_wstatus').width() + 18) / 2;
//        var sh = (windowHeight / 2) - ($('#HopeAlert_wstatus').height() + 18) / 2;
//        $('body #HopeAlert_wstatus').animate({left: sw - 15 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({left: sw + 30 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({left: sw - 30 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({left: sw + 30 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({left: sw + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({top: sh - 15 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({top: sh + 30 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({top: sh - 30 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({top: sh + 30 + 'px'}, 100);
//        $('body #HopeAlert_wstatus').animate({top: sh + 'px'}, 100);
//    }
    popstatus = setTimeout(function () {
        //判断是否有跳转地址
        if (url != 0) {
            if (url == '?') {
                reloads();
            } else {
                if(isOpenWindow) {
                    window.open(url);
                } else {
                    location.href = url;
                }
            }
        }
        $('body #HopeAlert_wstatus').remove();
        $('body #HopeAlert_bremove').eq(-1).remove();
    }, timeout * 1000);
    //超时时间设置
    if (timeout >= timeous) {
        outTime = setTimeout(function () {
            if ($('body #HopeAlert_wstatus')) {
                clearTimeout(popstatus);
                $('body #HopeAlert_wstatus').remove();
                popStatus(4, '请求超时，请检查地址是否可用！', 3, '', true);
            }
        }, timeout * 1000 - 1000);
    }
}

/**
 * 窗体监听发生变化后刷新弹窗所在位置
 */
function popStatuRe() {
    $('body #HopeAlert_wstatus').css({
        'left': (windowWidth / 2) - ($('#HopeAlert_wstatus').width() + 18) / 2 + 'px',
        'top': (windowHeight / 2) - ($('#HopeAlert_wstatus').height() + 18) / 2 + 'px'
    });
}