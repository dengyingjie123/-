package com.youngbook.dao.cms;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ArticlePO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 10/31/2017.
 */
@Component("articleDao")
public class ArticleDaoImpl implements IArticleDao {

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
