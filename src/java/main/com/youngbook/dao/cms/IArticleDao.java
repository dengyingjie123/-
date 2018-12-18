package com.youngbook.dao.cms;

import com.youngbook.common.Pager;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.vo.cms.ArticleVO;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Lee on 10/31/2017.
 */
public interface IArticleDao {
    public List<ArticleVO> getListArticleVO(ArticleVO articleVO,  Connection conn) throws Exception;
    public ArticlePO loadByArticleId(String articleId, Connection conn) throws Exception;
    public Pager getPagerArticleVO(ArticleVO articleVO, int currentPage, int showRowCount, Connection conn) throws Exception;

    void insertNews(ArticlePO newsPO, Connection conn) throws Exception;
}
