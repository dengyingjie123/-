package com.youngbook.action.cms;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.vo.cms.ArticleMenuVO;
import com.youngbook.entity.vo.cms.ArticleVO;
import com.youngbook.entity.vo.cms.ColumnVO;
import com.youngbook.entity.wvo.cms.ArticleWVO;
import com.youngbook.service.cms.ArticleService;
import com.youngbook.service.cms.ColumnService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Lee on 2/8/2017.
 */
public class ArticleAction extends BaseAction {

    private ArticlePO article = new ArticlePO();
    private ArticleVO articleVO = new ArticleVO();
    private ArticleWVO articleWVO = new ArticleWVO();
    private ColumnVO columnVO = new ColumnVO();

    @Autowired
    ArticleService articleService;
//
    @Autowired
    ColumnService columnService;

    public String getCircle() throws Exception {
        String bizId = getHttpRequestParameter("bizId");
        String columnId = getHttpRequestParameter("columnId");


        List<ArticlePO> articlePOs = articleService.getCircle(bizId, columnId, getConnection());

        if (articlePOs != null) {
            getResult().setReturnValue(articlePOs);
        }

        return SUCCESS;
    }

    public String deleteCircle() throws Exception {

        String articleId = getHttpRequestParameter("articleId");

        int count = articleService.deleteCircle(articleId, getConnection());

        return SUCCESS;
    }

    public String saveArticlePO() throws Exception {

        String bizId = getHttpRequestParameter("bizId");
        String columnId = getHttpRequestParameter("columnId");
        String content = getHttpRequestParameter("content");

        ArticlePO articlePO = articleService.saveArticlePO(content, bizId, columnId, getConnection());

        if (articlePO != null) {
            getResult().setReturnValue(articlePO);
        }

        return SUCCESS;
    }

    @Permission(require = "内容管理-文章管理-新增")
    public String saveProperties() throws Exception {

        // 保存文章属性，不保存文章内容
        article.setContent(null);

        articleService.insertOrUpdate(article, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String saveContent() throws Exception {

        articleService.saveContent(article.getId(), article.getContent(), getLoginUser().getId(), getConnection());

        return SUCCESS;
    }


    @Permission(require = "内容管理-文章管理-删除")
    public String delete() throws Exception {
        articleService.delete(article, getLoginUser(), getConnection());
        return SUCCESS;
    }


    @Permission(require = "内容管理-文章管理-修改")
    public String load() throws Exception {
        article.setState(Config.STATE_CURRENT);
        article = MySQLDao.load(article, ArticlePO.class);
        getResult().setReturnValue(article.toJsonObject4Form());
        return SUCCESS;
    }


    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        Pager pager = articleService.list(articleVO, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    public String getColumnListArticle() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        Connection conn=getConnection();
        //获取栏目
        List<ColumnVO> column = columnService.loadColumn4W(conn);
        if(column!=null&&column.size()>0){
            request.setAttribute("column", column);
        }
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        if(!StringUtils.isEmpty(id)) {
            List<ArticleWVO> articles = articleService.load4Article4W(id, conn);
            request.setAttribute("articles",articles);
        }
        request.setAttribute("token",token);
        return SUCCESS;
    }

    public String loadArticleVO() throws  Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        Connection conn=getConnection();
        //根据id获取文章
        String id = request.getParameter("id");
        if(!StringUtils.isEmpty(id)) {
            ArticleWVO articleWVO = articleService.loadArticle(id, conn);
            getResult().setReturnValue(articleWVO.toJsonObject());
        }
        return SUCCESS;
    }

    public String getListArticleVO() throws Exception {

        String bizId = getHttpRequestParameter("bizId");
        String columnName = getHttpRequestParameter("columnName");

        ArticleVO articleVO = new ArticleVO();
        articleVO.setBizId(bizId);
        articleVO.setColumnName(columnName);

        List<ArticleVO> list = articleService.getListArticleVO(articleVO, getConnection());

        if (list != null) {
            getResult().setReturnValue(list);
        }

        return SUCCESS;
    }


    public String list4WebsiteArticle() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        if(request.getParameter("count") != null && !request.getParameter("count").equals("") && request.getParameter("column") != null && !request.getParameter("column").equals("")) {

            Integer count = Integer.parseInt(request.getParameter("count"));
            count = count <= 50 ? count : 50;
            // 指定栏目
            String columnId = request.getParameter("column");
            // 读取
            List<KVObject> conditions =new ArrayList<KVObject>();
            Pager pager = articleService.list4WebsiteArticle(articleWVO, conditions, request, count, columnId);
            getResult().setReturnValue(pager.toJsonObject());
        }
        return SUCCESS;
    }


    public String load4W() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            String id = request.getParameter("id");
            List<KVObject> conditions =new ArrayList<KVObject>();
            Pager pager = articleService.load4WebsiteArticle(articleWVO, conditions, request, id);
            getResult().setReturnValue(pager.toJsonObject());
        }
        return SUCCESS;
    }


    public String loadNews() throws Exception {
        StringBuffer sbArticleInfo = new StringBuffer();
        sbArticleInfo.append("select * from cms_article where state= 0 and ColumnId = (select V from system_kv where K='17194')  LIMIT 5");
        Pager pager = MySQLDao.query(sbArticleInfo.toString(), ArticleVO.class, 1, 5, getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    public String loadWArticle() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            String id = request.getParameter("id");
            articleWVO= articleService.loadArticle(id,getConnection());
            getResult().setReturnValue(articleWVO.toJsonObject4Form());
        }
        return SUCCESS;
    }


    public String listMenuData() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql ="SELECT id, name text from cms_column where state = 0";
        List<ArticleMenuVO> list= MySQLDao.query(sql, ArticleMenuVO.class, null);
        JSONArray array = new JSONArray();
        for(ArticleMenuVO vo : list){
            array.add(vo.toJsonObject4Form());
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }


    public String getPagerArticleVO() throws Exception{
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String columnId = HttpUtils.getParameter(request, "columnId");
        // 校验参数合法性
        if(StringUtils.isEmpty(columnId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        ArticleVO articleVO = new ArticleVO();
        articleVO.setColumnId(columnId);

        Pager pager = Pager.getInstance(getRequest());
        //执行查询
        pager = articleService.getPagerArticleVO(articleVO, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return  SUCCESS;
    }


    public String getDynamicAnnouncements() throws Exception {
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取系统定义的公告栏目 ID
        String announcementColumnId = HttpUtils.getParameter(request, "columnId");
        // 校验栏目 ID
        if(StringUtils.isEmpty(announcementColumnId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "没有获取到动态公告的栏目 ID").throwException();
        }
        // 查询数据
        Pager pager = articleService.getDynamicAnnouncements(announcementColumnId, request);
        // 响应
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    public String getArticleDetail() throws Exception{
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String articleId = HttpUtils.getParameter(request, "articleId");
        // 校验参数合法性
        if(StringUtils.isEmpty(articleId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        //执行查询
        Pager pager = articleService.getArticleDetail(articleId, request);
        getResult().setReturnValue(pager.toJsonObject());
        return  SUCCESS;
    }


    /**
     * @description
     * 获取新闻
     * @author 胡超怡
     *
     * @date 2018/12/18 17:16
     * @return java.lang.String
     * @throws Exception
     */
    public String insertNews() throws Exception {

        /**
         *  获取url地址的网页html
         */
        Document doc = Jsoup.connect("http://fund.eastmoney.com/a/cjjyw.html").userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();


        /**
         * @description
         * 获取指定标签的所有内容
         * @author 胡超怡
         *
         * @date 2018/12/18 15:38
         * @param css   class名
         */
        Elements selectsContent = doc.select("div.infos")
                .select("ul")
                .select("li")
                .select("a");

        Elements selectsTime = doc.select("div.infos")
                .select("ul")
                .select("li")
                .select("span");




        /**
         * @description
         * 获取一个
         * @author 胡超怡
         *
         * @date 2018/12/18 15:53
         * @return newsSet 新闻集合
         * @throws Exception
         */
        HashSet<ArticlePO> newsSet = new HashSet<>();
        String[] times = selectsTime.html().split("\n");


        /**
         * 循环获得jsoup得到的数据
         */
        int i = 0;
        for (Element a : selectsContent) {

            String url = a.attr("href");
            String titles = a.attr("title");
            String contentNews = getContentByURL("http://fund.eastmoney.com/a/" + url).html();


            /**
             * 给新闻实体添加数据
             */
            ArticlePO newsPO = new ArticlePO();
            newsPO.setSource("天天基金网");
            newsPO.setTitle(titles);
            newsPO.setNewsTime(times[i]);
            newsPO.setContent(contentNews);
            newsSet.add(newsPO);
            i++;
        }


        /**
         * 调用service
         */
        HashSet<ArticlePO> newsPO = articleService.insertNews(newsSet,getConnection());


        return SUCCESS;
    }


    /**
     * @description 根据url获取内容
     *
     * @author 胡超怡
     *
     * @date 2018/12/18 17:27
     * @param url
     * @return java.lang.String
     * @throws Exception
     */
    public Elements getContentByURL(String url) throws Exception{

        //1.获取url地址的网页html
        Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();

        return doc.select("div.Body").select("p");
    }


    /**
     * @description 日期格式转换
     * 字符串转换为date
     * @author 胡超怡
     *
     * @date 2018/12/18 17:24
     * @param strDate
     * @return java.util.Date
     * @throws Exception
     */
    public Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    public ArticlePO getArticle() {return article;}
    public void setArticle(ArticlePO article) {this.article = article;}

    public ArticleVO getArticleVO() {return articleVO;}
    public void setArticleVO(ArticleVO articleVO) {this.articleVO = articleVO;}

    public ArticleWVO getArticleWVO() {return articleWVO;}
    public void setArticleWVO(ArticleWVO articleWVO) {this.articleWVO = articleWVO;}

}
