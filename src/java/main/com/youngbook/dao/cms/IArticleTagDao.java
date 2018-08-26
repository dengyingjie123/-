package com.youngbook.dao.cms;

import com.youngbook.entity.po.cms.ArticleTagPO;

import java.sql.Connection;

/**
 * Created by Lee on 10/31/2017.
 */
public interface IArticleTagDao {
    public ArticleTagPO loadByArticleTagId(String articleTagId, Connection conn) throws Exception;
    public ArticleTagPO loadByArticleIdAndTagName(String articleId, String tagName, Connection conn) throws Exception;
}
