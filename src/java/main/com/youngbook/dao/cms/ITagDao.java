package com.youngbook.dao.cms;

import com.youngbook.entity.po.cms.TagPO;

import java.sql.Connection;

/**
 * Created by Lee on 10/31/2017.
 */
public interface ITagDao {
    public TagPO loadByTagId(String tagId, Connection conn) throws Exception;
    public TagPO loadByTagName(String tagName, Connection conn) throws Exception;
    public TagPO insertOrUpdate(TagPO tagPO, String userId, Connection conn) throws Exception;
}
