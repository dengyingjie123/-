/**
 * Created by zhang on 2015/9/29.
 */



/**
 * 发起请求
 */
function loadArticle(id) {
    var url = "/core/w2/cms/loadArticleVO?id="+id;

    // 发起 POST 请求
    fweb.post(url, {}, function (response) {
        $('#detail').attr("style","display:none");
        $('#new_detail_id').attr("style","display:block");
        var  article = response.returnValue;
        var title = article.title;
        $('#news_title').html(title);
        var publishedTime = article.publishedTime;
        $('#news_time').html(publishedTime);
        var content = article.content;
        $('#news_content').html(content);
    });
}


