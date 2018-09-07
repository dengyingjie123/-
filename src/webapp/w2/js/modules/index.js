/**
 * Created by zhang on 2015/9/29.
 */

$(function() {
    $('#carousel-884809').carousel({
        interval: 8000
})
});

    function factorover(obj1) {

    $('#'+obj1).attr("style","display:block");
}
;
function factorout(obj2) {
    $('#'+obj2).attr("style","display:none");

}
;
/**
 * 创建人：张舜清
 * 时间：2015年8月18日14:29:24
 *
 * @param minMoney 最小金额
 * @param maxMoney 最大金额
 * @param minExpiringDay 最小天数
 * @param maxExpiringDay 最大天数
 * @param minExpectedYield 最小年化率
 * @param maxExpectedYield 最大年化率
 */
function loadProductionList(minMoney, maxMoney, minExpiringDay, maxExpiringDay, minExpectedYield, maxExpectedYield) {
    var params = {
        'minMoney': minMoney,
        'maxMoney': maxMoney,
        'minExpiringDay': minExpiringDay,
        'maxExpiringDay': maxExpiringDay,
        'minExpectedYield': minExpectedYield,
        'maxExpectedYield': maxExpectedYield
    }
    var url = '/core/w2/index/queryList';
    fweb.post(url, params, function (response) {
        var data = response.returnValue;
        var html = "";
        // 判断后台返回的对应查询数据有没有，如果没有页面显示提示
        if (data == "0") {
            html += "<tr class='otherLine'>";
            html += "<td colspan='6'>没有符合条件的产品</td>";
            html += "</tr>";
        }
        // 否则进入组装数据
        else {
            for (var i = 0; i < data.length; i++) {
                var ExpectedYield = "";
                if (data[i]['maxExpectedYield'] == data[i]['minExpectedYield']) {
                    ExpectedYield = "<td><span class='font-pink-24'>" + data[i]['maxExpectedYield'] + "</span><span class='font-pink-14'>%</span></td>";
                }//不相等
                else {
                    ExpectedYield = "<td><span class='font-pink-24'>" + data[i]['minExpectedYield'] + "</span><span class='font-pink-14'>%</span>-<span class='font-pink-24'>" + data[i]['maxExpectedYield'] + "</span><span class='font-pink-14'>%</span></td>";
                }
                html += "<tr class='otherLine'>";
                html += "<td><a href='/core/w2/production/productionDetail?productionWVO.id=" + data[i]['id'] + "' target='_blank' class='t'>" + data[i]['websiteDisplayName'] + "</a></td>"
                html += ExpectedYield;
                html += "<td>￥" + data[i]['size'] + "</td>";
                var InvestTermView = data[i]['investTermView'] == null ? "&nbsp;" : data[i]['investTermView'];
                html += "<td><span class='font-14'>" + InvestTermView + "</span></td>";
                //获取销售金额
                var saleMoney = data[i]['saleMoney'];
                //获取配额
                var size = data[i]['size'];
                //计算进度
                var process = parseInt(saleMoney) / parseInt(size)* 100;
                html += "<td width='120'>"  + process.toFixed(2) + "%</td>";

                if(parseFloat(process.toFixed(2))<100) {
                    html += "<td width='160'><a href='/core/w2/production/productionDetail?productionWVO.id=" + data[i]['id'] + "' ><img src='/core/w2/img/bg_investment.gif'/></a></td>";
                }else{
                    html += "<td width='160'><button class='btns-over'></button></td>";

                }
                html += "</tr>";
            }
        }
        $('#puductionListID').html(html);
        // 转换货币格式

        // 进度转圈动画
        $('.chart').easyPieChart({
            size: 50,
            lineWidth: 3,
            barColor: '#c5acd2',
            trackColor: '#dddddd',
            scaleColor: '#ffffff',
            onStep: function (from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });
    }, null)
}

/**
 * 创建人：张舜清
 * 时间：2015年8月20日20:31:36
 * 内容：首页默认查询出产品
 */
function loadDefaultProductShowOnIndex() {
    // 组装请求参数到 URL
    var url = "/core/w2/production/mutilQuery";
    // 发起 POST 请求
    fweb.post(url, null, function(response) {
        // 组装 HTML
        var data = response.returnValue['rows'];
        var html = "";
        for(var i = 0; i < data.length; i ++) {
            //判断最大收益率是都等于最小收益率
            var ExpectedYield = "";
            if (data[i]['maxExpectedYield'] == data[i]['minExpectedYield']) {
                ExpectedYield = "<td><span class='font-pink-24'>" + data[i]['maxExpectedYield'] + "</span><span class='font-pink-14'>%</span></td>";
            }//不相等
            else {
                ExpectedYield = "<td><span class='font-pink-24'>" + data[i]['minExpectedYield'] + "</span><span class='font-pink-14'>%</span>-<span class='font-pink-24'>" + data[i]['maxExpectedYield'] + "</span><span class='font-pink-14'>%</span></td>";
            }
            html += "<tr class='otherLine'>";
            html += "<td><a href='/core/w2/production/productionDetail?productionWVO.id=" + data[i]['id'] + "' target='_blank' class='t'>" + data[i]['websiteDisplayName'] + "</a></td>"
            html += ExpectedYield;
            html += "<td>￥" + data[i]['size'] + "</td>";
            var InvestTermView = data[i]['investTermView'] == null ? "&nbsp;" : data[i]['investTermView'];
            html += "<td><span class='font-14'>" + InvestTermView + "</span></td>";
            //获取销售金额
            var saleMoney = data[i]['saleMoney'];
            //获取配额
            var size = data[i]['size'];
            //计算进度
            var process = parseInt(saleMoney) / parseInt(size) * 100;
            html += "<td width='120'>" + process.toFixed(2) + "%</td>";
            if(parseFloat(process.toFixed(2))<100) {
                html += "<td width='160'><a href='/core/w2/production/productionDetail?productionWVO.id=" + data[i]['id'] + "' ><img src='/core/w2/img/bg_investment.gif'/></a></td>";
            }else{
                html += "<td width='160'><button class='btns-over'>已售罄</button></td>";

            }
            html += "</tr>";
        }
        $('#puductionListID').html(html);
        // 转换货币格式

        // 进度转圈动画
        $('.chart').easyPieChart({
            size: 50,
            lineWidth: 3,
            barColor: '#c5acd2',
            trackColor: '#dddddd',
            scaleColor: '#ffffff',
            onStep: function (from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });
    });
}
window.onload = function () {
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-51407249-5', 'auto');
    ga('send', 'pageview');

    (function () {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?f7cf3929e6483d26ff77ceebc594fd42";
        hm.async = true;
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
};
// 投资计划货币格式转换
//    $('.money').formatCurrency();
//    $('.productionSize').formatCurrency();

// 项目进度转圈动画
//    $(function () {
//        //create instance
//        $('.chart').easyPieChart({
//            size: 50,
//            lineWidth: 3,
//            barColor: '#c5acd2',
//            trackColor: '#dddddd',
//            scaleColor: '#ffffff',
//            onStep: function (from, to, percent) {
//                $(this.el).find('.percent').text(Math.round(percent));
//            }
//        });
//    });

// Web首页显示管理资金、投资人数、累计盈利数据
function statistics() {
    var url = "/core/w2/system/Statistics_statisticsForWeb";
    fweb.post(url, "", function (date) {
        date = date["returnValue"];
        $("#investment").text(date["Investment"]);
        $("#money").text(date["Money"]);
        $("#profit").text(date["Profit"]);
    }, null);
}
//    statistics();
//    loadDefaultProductShowOnIndex();