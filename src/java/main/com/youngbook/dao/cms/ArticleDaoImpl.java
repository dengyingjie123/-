package com.youngbook.dao.cms;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.vo.cms.ArticleVO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 10/31/2017.
 */
@Component("articleDao")
public class ArticleDaoImpl implements IArticleDao {

    public List<ArticleVO> getListArticleVO(ArticleVO articleVO,  Connection conn) throws Exception {


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("1F2A1712");
        dbSQL.addParameter4All("bizId", articleVO.getBizId());
        dbSQL.addParameter4All("columnId", articleVO.getColumnId());
        dbSQL.addParameter4All("columnName", articleVO.getColumnName());
        dbSQL.initSQL();

        List<ArticleVO> list = MySQLDao.search(dbSQL, ArticleVO.class, conn);


        for (int i = 0; list != null && i < list.size(); i++) {

            ArticleVO temp = list.get(i);

            int summaryLength = temp.getContent().length() > 500 ? 500 : temp.getContent().length();

            String summaryHtml = temp.getContent().substring(0, summaryLength);
            String summaryText = StringUtils.removeAllHtmlTags(temp.getContent().substring(0, summaryLength));
            temp.setSummaryHtml(summaryHtml);
            temp.setSummaryText(summaryText);
        }

        return list;
    }


    public Pager getPagerArticleVO(ArticleVO articleVO, int currentPage, int showRowCount, Connection conn) throws Exception {


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("1F2A1712");
        dbSQL.addParameter4All("bizId", articleVO.getBizId());
        dbSQL.addParameter4All("columnId", articleVO.getColumnId());
        dbSQL.addParameter4All("columnName", articleVO.getColumnName());
        dbSQL.initSQL();


        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), articleVO, null, currentPage, showRowCount, queryType, conn);

        List<ArticleVO> list = MySQLDao.search(dbSQL, ArticleVO.class, conn);


        for (int i = 0; pager != null && i < pager.getData().size(); i++) {

            ArticleVO temp = (ArticleVO)pager.getData().get(i);

            String summaryHtml = temp.getContent().substring(0, 500);
            String summaryText = StringUtils.removeAllHtmlTags(temp.getContent().substring(0, 100));
            temp.setSummaryHtml(summaryHtml);
            temp.setSummaryText(summaryText);
        }

        return pager;
    }


    /**
     * @description 跳转接口
     *
     * @author 胡超怡
     * @date 2019/1/22 15:21
     * @param articlePO
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.cms.ArticlePO>
     * @throws Exception
     */
    @Override
    public List<ArticlePO> getArticlePO(ArticlePO articlePO, Connection conn) throws Exception {

        List<ArticlePO> search = MySQLDao.search(articlePO, ArticlePO.class, conn);

        return search;
    }

    public ArticlePO loadByArticleId(String articleId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(articleId)) {
            MyException.newInstance("无法获得文章编号").throwException();
        }

        ArticlePO articlePO = new ArticlePO();
        articlePO.setId(articleId);
        articlePO.setState(Config.STATE_CURRENT);

        articlePO = MySQLDao.load(articlePO, ArticlePO.class, conn);

        return articlePO;
    }
}
