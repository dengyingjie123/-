package com.youngbook.service.cms;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.cms.IArticleDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.vo.cms.ArticleVO;
import com.youngbook.entity.vo.cms.ColumnVO;
import com.youngbook.entity.wvo.cms.ArticleWVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/17/14
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("articleService")
public class ArticleService extends BaseService {
    @Autowired
    IArticleDao articleDao;

    public List<ArticlePO> getCircle(String bizId, String columnId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getCircle", this);
        dbSQL.addParameter4All("bizId", bizId);
        dbSQL.addParameter4All("columnId", columnId);
        dbSQL.initSQL();

        List<ArticlePO> list = MySQLDao.search(dbSQL, ArticlePO.class, conn);

        return list;
    }


    public int deleteCircle(String id, Connection conn) throws Exception {

        ArticlePO articlePO = loadArticlePOById(id, conn);
        if (articlePO != null) {
            MySQLDao.delete(articlePO, conn);
        }

        return 1;
    }

    public ArticlePO loadArticlePOById(String id, Connection conn) throws Exception {
        ArticlePO articlePO = new ArticlePO();
        articlePO.setId(id);
        articlePO.setState(Config.STATE_CURRENT);

        articlePO = MySQLDao.load(articlePO, ArticlePO.class, conn);

        return articlePO;

    }

    public ArticlePO loadArticlePOById(String id) throws Exception {

        Connection conn = Config.getConnection();
        try {
            return loadArticlePOById(id, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public ArticlePO circleSave(String content, String bizId, String columnId, Connection conn) throws Exception {
        ArticlePO articlePO = new ArticlePO();

        articlePO.setContent(content);
        articlePO.setBizId(bizId);
        articlePO.setColumnId(columnId);
        articlePO.setOrders(1);
        articlePO.setPublishedTime(TimeUtils.getNow());
        articlePO.setIsDisplay(3946);

        MySQLDao.insertOrUpdate(articlePO, bizId, conn);

        return articlePO;
    }

    /**
     * 列出数据
     *
     * @param article
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list(ArticleVO article, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        StringBuffer SQL = new StringBuffer();

        SQL.append("SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, ");
        SQL.append("a.publishedTime, a.content, a.orders, a.operateTime ");
        SQL.append(" FROM cms_article a, cms_column c WHERE a.columnId = c.id ");
        SQL.append("AND a.state = 0 AND c.state = 0");
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "publishedTime " + Database.ORDERBY_DESC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "orders " + Database.ORDERBY_ASC));
        Pager pager = Pager.query(SQL.toString(), article, conditions, request, queryType);
        return pager;
    }

    /**
     * 列出网站指定栏目的文章
     *
     * @param article    文章VO
     * @param conditions KV
     * @param request    请求
     * @param count      获取文章数
     * @param columnId   文章所属栏目
     * @return Pager
     * @throws Exception
     */
    public Pager list4WebsiteArticle(ArticleWVO article, List<KVObject> conditions, HttpServletRequest request, Integer count, String columnId) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql = "SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, a.publishedTime, a.content, a.orders " +
                "FROM cms_article a, cms_column c " +
                "WHERE a.columnId = c.id AND a.state = 0 AND a.isDisplay = 3946 AND c.state = 0 and c.id = '" + Database.encodeSQL(columnId) + "' limit 0," + count;
        Pager pager = Pager.query(sql, article, conditions, request, queryType);
        return pager;
    }

    public ColumnVO getColumnListArticle(ColumnVO columnVO, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sbSQL=new StringBuffer();
        sbSQL.append("select C.sid,c.id,c.state,c.OperatorId,c.OperateTime,c.ParentId,c.Name,c.Description ");
        sbSQL.append(" from cms_column C ");
        sbSQL.append("       LEFT JOIN cms_columnbelong B on B.columnId=C.id  ");
        sbSQL.append("      LEFT JOIN system_department D on B.departmentId=D.ID ");
        sbSQL.append("   where C.state=0 ");
        List<KVObject>  paramters=new ArrayList<KVObject>();
        List<ColumnVO> column = MySQLDao.search(sbSQL.toString(), paramters, ColumnVO.class, null, conn);

        if(column!=null&&column.size()==1){
            return column.get(0);
        }
        return null;
    }

    /**
     * 根据文章ID，查询文章，返回给网站
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<ArticleWVO> load4Article4W(String id,Connection conn) throws Exception {
        StringBuffer sbSQl=new StringBuffer();
        sbSQl.append("SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, a.publishedTime, a.content, a.orders ");
        sbSQl.append(   "FROM cms_article a, cms_column c " );
        sbSQl.append(   "WHERE a.columnId = c.id AND a.state = 0 AND c.state = 0 and a.columnId = ? and a.isDisplay = 3946 ");
        List<KVObject> parmeters=new ArrayList<KVObject>();
        parmeters=Database.addQueryKVObject(1,id,parmeters);
        List<ArticleWVO> articles = MySQLDao.search(sbSQl.toString(), parmeters, ArticleWVO.class, null, conn);

        return articles;
    }



    /**
     * 根据文章ID，查询文章
     *
     * @param article
     * @param conditions
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    public Pager load4WebsiteArticle(ArticleWVO article, List<KVObject> conditions, HttpServletRequest request, String id) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql = "SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, a.publishedTime, a.content, a.orders " +
                "FROM cms_article a, cms_column c " +
                "WHERE a.columnId = c.id AND a.state = 0 AND c.state = 0 and a.id = '" + Database.encodeSQL(id) + "'";
        Pager pager = Pager.query(sql, article, conditions, request, queryType);
        return pager;
    }

    /**
     * 插入数据和更新
     *
     * @param article
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ArticlePO article, UserPO user, Connection conn) throws Exception {


        if (article == null) {
            MyException.newInstance("无法获得文章内容").throwException();
        }

        if (article.getOrders() == Integer.MAX_VALUE) {
            article.setOrders(1);
        }


        int count = MySQLDao.insertOrUpdate(article, user.getId(), conn);

        return count;
    }


    public ArticlePO saveContent(String articleId, String content, String userId, Connection conn) throws Exception {

        ArticlePO articlePO = articleDao.loadByArticleId(articleId, conn);

        articlePO.setContent(content);

        MySQLDao.insertOrUpdate(articlePO, userId, conn);

        return articlePO;
    }

    /**
     * 删除数据
     *
     * @param article
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ArticlePO article, UserPO user, Connection conn) throws Exception {
        int count = 0;
        article = MySQLDao.load(article, ArticlePO.class);
        article.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(article, conn);
        if (count == 1) {
            article.setSid(MySQLDao.getMaxSid("cms_article", conn));
            article.setState(Config.STATE_DELETE);
            article.setOperateTime(TimeUtils.getNow());
            article.setOperatorId(user.getId());
            count = MySQLDao.insert(article, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据Id获取文章
     *
     * @return
     */
    public ArticleWVO loadArticle(String articleId, Connection conn) throws Exception {
        String SQL = "SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, a.publishedTime, a.content, a.orders " +
                "FROM cms_article a, cms_column c " +
                "WHERE a.columnId = c.id AND a.state = 0 AND c.state = 0 AND a.id='" + Database.encodeSQL(articleId) + "'";
        List<ArticleWVO> list = MySQLDao.query(SQL, ArticleWVO.class, null, conn);
        return list.get(0);
    }


    public List<ArticleVO> getListArticleVO(String bizId, String columnName, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("1F2A1712");
        dbSQL.addParameter4All("bizId", bizId);
        dbSQL.addParameter4All("columnName", columnName);
        dbSQL.initSQL();

        List<ArticleVO> list = MySQLDao.search(dbSQL, ArticleVO.class, conn);

        return list;
    }



    public ArticleWVO loadArticle(String articleId) throws Exception {

        Connection conn = Config.getConnection();
        try {
            String SQL = "SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, a.publishedTime, a.content, a.orders " +
                    "FROM cms_article a, cms_column c " +
                    "WHERE a.columnId = c.id AND a.state = 0 AND c.state = 0 AND a.id='" + Database.encodeSQL(articleId) + "'";
            List<ArticleWVO> list = MySQLDao.query(SQL, ArticleWVO.class, null, conn);
            if (list != null && list.size() == 1) {
                return list.get(0);
            }

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

        return null;
    }

    /**
     * 根据ID获取指定文章内容对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ArticlePO getArticlePo(String id) throws Exception {

        //创建数据对象
        ArticlePO a = new ArticlePO();
        a.setId(id);
        a.setState(Config.STATE_CURRENT);

        //根据条件获取数据
        ArticlePO ap = MySQLDao.load(a, ArticlePO.class);
        return ap;
    }

    /**
     * 根据ID获取指定文章列表
     *
     * @param columnId 栏目编号
     * @return
     * @throws Exception
     */
    public Pager getArticles(String columnId, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        ArticleVO article = new ArticleVO();
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT c.name columnName, a.sid, a.id, a.title, a.columnId, date_format(a.publishedTime,'%Y-%m-%d %H:%i:%S') as publishedTime, a.content, a.orders ");
        sbSQL.append(" FROM cms_article a, cms_column c ");
        sbSQL.append(" WHERE a.columnId = c.id AND a.state = 0 AND c.state = 0 and  a.columnId = ? and a.isDisplay = 3946 order by a.operateTime desc ");
        List<KVObject> parmeters = new ArrayList<KVObject>();
        parmeters = Database.addQueryKVObject(1, columnId, parmeters);
        Pager pager = Pager.search(sbSQL.toString(), parmeters, article, null, request, queryType);

        for (int i = 0; pager != null && i < pager.getTotal(); i++) {

            ArticleVO articleVO = (ArticleVO) pager.getData().get(i);

            String summaryHtml = articleVO.getContent().substring(0, 500);
            String summaryText = StringUtils.removeAllHtmlTags(articleVO.getContent().substring(0, 100));
            articleVO.setSummaryHtml(summaryHtml);
            articleVO.setSummaryText(summaryText);
        }

        return pager;
    }

    /**
     * 获取动态公告列表
     * @param columnId
     * @param request
     * @return
     * @throws Exception
     */
    public Pager getDynamicAnnouncements(String columnId, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        ArticleVO article = new ArticleVO();
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT a.sid, a.id, a.title, a.columnId, a.publishedTime ");
        sbSQL.append(" FROM cms_article a, cms_column c ");
        sbSQL.append(" WHERE a.columnId = c.id AND a.state = 0 AND c.state = 0 and a.columnId = ? and a.isDisplay = 3946 order by a.operateTime desc ");
        List<KVObject> parmeters = new ArrayList<KVObject>();
        parmeters = Database.addQueryKVObject(1, columnId, parmeters);
        Pager pager = Pager.search(sbSQL.toString(), parmeters, article, null, request, queryType);

        return pager;
    }

    /**
     * 根据ID获取指定文章详情
     *
     * @param articleId 栏目编号
     * @return
     * @throws Exception
     */
    public Pager getArticleDetail(String articleId, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        ArticleVO article = new ArticleVO();
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  SELECT");
        sbSQL.append("  c. NAME columnName,");
        sbSQL.append("   a.sid,");
        sbSQL.append("  a.id,");
        sbSQL.append("   a.title,");
        sbSQL.append("   a.columnId,");
        sbSQL.append("   date_format(a.publishedTime,'%Y-%m-%d %H:%i:%S') as publishedTime,");
        sbSQL.append("   a.content,");
        sbSQL.append("   a.orders ");
        sbSQL.append("  FROM ");
        sbSQL.append("   cms_article a, ");
        sbSQL.append("   cms_column c ");
        sbSQL.append("   WHERE ");
        sbSQL.append("   a.columnId = c.id ");
        sbSQL.append("   AND a.state = 0 ");
        sbSQL.append("    AND c.state = 0 ");
        sbSQL.append("    AND a.id= ? and a.isDisplay = 3946 ");
        List<KVObject> parmeters = new ArrayList<KVObject>();
        parmeters = Database.addQueryKVObject(1, articleId, parmeters);
        Pager pager = Pager.search(sbSQL.toString(), parmeters, article, null, request, queryType);
        return pager;
    }

}
