package com.youngbook.service.cms;

import com.youngbook.common.MyException;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.cms.IArticleDao;
import com.youngbook.dao.cms.IArticleTagDao;
import com.youngbook.dao.cms.ITagDao;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.cms.ArticleTagPO;
import com.youngbook.entity.po.cms.TagPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 10/31/2017.
 */
@Component("tagService")
public class TagService extends BaseService {

    @Autowired
    ITagDao tagDao;

    @Autowired
    IArticleDao articleDao;

    @Autowired
    IArticleTagDao articleTagDao;

    public TagPO insertOrUpdate(TagPO tagPO, String userId, Connection conn) throws Exception {

        return tagDao.insertOrUpdate(tagPO, userId, conn);

    }


    public ArticleTagPO newTag(String articleId, String tagName, String userId, Connection conn) throws Exception {

        TagPO tagPO = tagDao.loadByTagName(tagName, conn);

        if (tagPO == null) {
            tagPO = new TagPO();
            tagPO.setName(tagName);

            tagPO = tagDao.insertOrUpdate(tagPO, userId, conn);
        }

        ArticlePO articlePO = articleDao.loadByArticleId(articleId, conn);

        if (articlePO == null) {
            MyException.newInstance("无法获得文章信息", "articleId=" + articleId).throwException();
        }

        ArticleTagPO articleTagPO = articleTagDao.loadByArticleIdAndTagName(articleId, tagName, conn);

        if (articleTagPO != null) {
            return articleTagPO;
        }

        articleTagPO = new ArticleTagPO();
        articleTagPO.setTagId(tagPO.getId());
        articleTagPO.setArticleId(articlePO.getId());

        MySQLDao.insertOrUpdate(articleTagPO, userId, conn);

        return articleTagPO;

    }


    public void removeTag(String articleId, String tagName, String userId, Connection conn) throws Exception {

        ArticlePO articlePO = articleDao.loadByArticleId(articleId, conn);

        if (articlePO == null) {
            MyException.newInstance("无法获得文章信息", "articleId=" + articleId).throwException();
        }

        ArticleTagPO articleTagPO = articleTagDao.loadByArticleIdAndTagName(articleId, tagName, conn);

        if (articleTagPO != null) {

            MySQLDao.remove(articlePO, userId, conn);

        }
    }

}
