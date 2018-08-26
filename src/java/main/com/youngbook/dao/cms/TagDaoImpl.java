package com.youngbook.dao.cms;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.cms.ArticleTagPO;
import com.youngbook.entity.po.cms.TagPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 10/31/2017.
 */
@Component("tagDao")
public class TagDaoImpl implements ITagDao {

    public TagPO loadByTagId(String tagId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(tagId)) {
            MyException.newInstance("无法获得标签编号").throwException();
        }

        TagPO tagPO = new TagPO();
        tagPO.setId(tagId);
        tagPO.setState(Config.STATE_CURRENT);

        tagPO = MySQLDao.load(tagPO, TagPO.class, conn);

        return tagPO;
    }

    public TagPO loadByTagName(String tagName, Connection conn) throws Exception {

        if (StringUtils.isEmpty(tagName)) {
            MyException.newInstance("无法获得标签名称").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadByTagName", this);
        dbSQL.addParameter4All("tagName", tagName);
        dbSQL.initSQL();

        List<TagPO> list = MySQLDao.search(dbSQL, TagPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }


        return null;
    }

    public TagPO insertOrUpdate(TagPO tagPO, String userId, Connection conn) throws Exception {

        if (tagPO == null) {
            MyException.newInstance("无法获得标签信息").throwException();
        }

        if (StringUtils.isEmpty(tagPO.getName())) {
            MyException.newInstance("无法获得标签名称").throwException();
        }

        MySQLDao.insertOrUpdate(tagPO, userId, conn);

        return tagPO;

    }
}
