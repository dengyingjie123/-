/**
 * Created by zhang on 2015/9/29.
 */

/**
 * 加载底部文章栏目
 */
loadArticle();

/**
 * 发起请求
 */
function loadArticle() {
    var url = "/core/w2/cms/loadColumns";

    // 发起 POST 请求
    fweb.post(url, {}, function (data) {
        var html="";
        var  columns = data.returnValue['rows'];
        html +="<tr>";
        html +="<td><img src='/core/w2/img/area_about.gif'/></td>";
        html +="</tr>";
        for(var i = 0; i < columns.length; i ++) {
            html += "<tr> ";
            html += "<td class='footer-text'><a href='/core/w2/cms/getColumnAll?id="+columns[i]['id']+"&token=guidance_"+i+"'>"+columns[i]['name']+"</a></td>";
            html += "</tr> ";
        }
        $('#columnList').html(html);
    });
}


