package com.youngbook.dao.cms;

import com.youngbook.entity.po.cms.ArticlePO;

import java.sql.Connection;

/**
 * Created by Lee on 10/31/2017.
 */
public interface IArticleDao {
    public ArticlePO loadByArticleId(String articleId, Connection conn) throws Exception;
}
