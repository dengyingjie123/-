package com.youngbook.dao.system;

import com.youngbook.entity.po.system.FilesPO;

import java.sql.Connection;

/**
 * Created by Lee on 10/27/2017.
 */
public interface IFilesDao {
    public FilesPO loadByModuleBizId(String moduleId, String bizId, Connection conn) throws Exception;
}
