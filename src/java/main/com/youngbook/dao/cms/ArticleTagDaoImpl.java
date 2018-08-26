package com.youngbook.dao.cms;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ArticleTagPO;
import com.youngbook.entity.po.cms.TagPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 10/31/2017.
 */
@Component("articleTagDao")
public class ArticleTagDaoImpl implements IArticleTagDao {

    public ArticleTagPO loadByArticleTagId(String articleTagId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(articleTagId)) {
            MyException.newInstance("无法获得标签编号").throwException();
        }

        ArticleTagPO articleTagPO = new ArticleTagPO();
        articleTagPO.setId(articleTagId);
        articleTagPO.setState(Config.STATE_CURRENT);

        articleTagPO = MySQLDao.load(articleTagPO, ArticleTagPO.class, conn);

        return articleTagPO;
    }

    public ArticleTagPO loadByArticleIdAndTagName(String articleId, String tagName, Connection conn) throws Exception {

        if (StringUtils.isEmpty(articleId)) {
            MyException.newInstance("无法获得文章编号").throwException();
        }

        if (StringUtils.isEmpty(tagName)) {
            MyException.newInstance("无法获得标签名称").throwException();
        }

//        ArticleTagPO articleTagPO = new ArticleTagPO();
//        articleTagPO.setId(articleTagId);
//        articleTagPO.setState(Config.STATE_CURRENT);
//
//        articleTagPO = MySQLDao.load(articleTagPO, ArticleTagPO.class, conn);


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadByArticleIdAndTagName", this);
        dbSQL.addParameter4All("tagName", tagName);
        dbSQL.addParameter4All("articleId", articleId);
        dbSQL.initSQL();

        List<ArticleTagPO> articleTagPOs = MySQLDao.search(dbSQL, ArticleTagPO.class, conn);

        if (articleTagPOs != null && articleTagPOs.size() == 1) {
            return articleTagPOs.get(0);
        }

        return null;
    }
}
