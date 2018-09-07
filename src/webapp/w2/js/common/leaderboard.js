/**
 * 请求数据
 *
 * 作者：邓超
 * 内容：创建代码
 * 时间：2016年6月29日
 */
function reqDate(index) {
    // 定义 url
    var url = '/core/api/sale/Salesman_getLeaderboards';

    // 根据点击的不同栏目，获取数据
    if(index == 0) {
        var params = { 'dateType' : 'date' }
    } else if (index == 1) {
        var params = { 'dateType' : 'month' }
    } else {
        alert('对不起，页面出现错误，请联系系统管理员！');
        return ;
    }

    // 请求数据
    $(".loading_box").show();
    fweb.post(url, params, function(resp){
        // 结果集
        var resultArr = resp.returnValue;
        // 构建 HTML
        var html = buildHTML(resultArr);
        // 渲染到 DOM
        if(index == 0) {
            $('.tab-cnt-item .day-record').html(html);
        } else {
            $('.tab-cnt-item .month-record').html(html);
        }
        $(".loading_box").hide();
    }, function(resp){
        $(".loading_box").hide();
        alert('对不起，数据加载失败，请检查网络连接！');
        return ;
    });
}

/**
 * 将请求到的数据填充成 HTML
 *
 * 作者：邓超
 * 内容：创建代码
 * 时间：2016年6月29日
 *
 */
function buildHTML(resultArr) {

    var html = "<ul>";
    for(var i = 0; i < resultArr.length; i ++) {
        // 单个结果
        var result = resultArr[i];
        var departmentName = result['departmentName'];      // 所属部门
        var fixedSalesMoney = result['fixedSalesMoney'];    // 固定收益（小额产品）销售统计
        var floatSalesMoney = result['floatSalesMoney'];    // 浮动收益（大额产品）销售统计
        var salemanName = result['salemanName'];            // 销售人员名称
        var totalSalesMoney = result['totalSalesMoney'];    // 大小额产品销售总额
        // 组装 HTML
        var indexOut = i + 1;
        if(i < 3) {
            html += "<li class='rank-text-" + indexOut + "'>";
            html += "   <i class='rank-" + indexOut + "'></i>";
            html += "   <div class='results-info'><span class='name'>" + salemanName + "</span><span class='company-name'>" + departmentName + "</span> </div>";
            html += "   <div class='results-info2'><span class='name'>" + totalSalesMoney + "</span><span class='smallandbig-count'>小额：" + fixedSalesMoney + "</span><span class='smallandbig-count'> 大额：" + floatSalesMoney + "</span></div>";
            html += "</li>";
        } else {
            html += "<li>";
            html += "   <i class='rank-number'>" + indexOut + "</i>";
            html += "   <div class='results-info'><span class='name'>" + salemanName + "</span><span class='company-name'>" + departmentName + "</span> </div>";
            html += "   <div class='results-info2'><span class='name'>" + totalSalesMoney + "</span><span class='smallandbig-count'>小额：" + fixedSalesMoney + "</span><span class='smallandbig-count'> 大额：" + floatSalesMoney + "</span></div>";
            html += "</li>";
        }
    }
    html += "</ul>";

    return html;

}