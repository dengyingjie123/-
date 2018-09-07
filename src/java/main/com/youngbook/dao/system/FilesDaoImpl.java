package com.youngbook.dao.system;

import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.FilesPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 10/27/2017.
 */
@Component("filesDao")
public class FilesDaoImpl implements IFilesDao {


    public FilesPO loadByModuleBizId(String moduleId, String bizId, Connection conn) throws Exception {

        if (!StringUtils.isEmptyAny(moduleId, bizId)) {

            DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadByModuleBizId", this);
            dbSQL.addParameter4All("moduleId", moduleId);
            dbSQL.addParameter4All("bizId", bizId);
            dbSQL.initSQL();

            List<FilesPO> list = MySQLDao.search(dbSQL, FilesPO.class, conn);

            if (list != null && list.size() == 1) {
                return list.get(0);
            }

        }

        return null;
    }

}
