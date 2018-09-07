package com.youngbook.dao.system;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.ShareLinkPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 1/6/2017.
 */
@Component("shareLinkDaoImpl")
public class ShareLinkDaoImpl {

    public static void main(String[] args) throws Exception {
        ShareLinkDaoImpl shareLinkDao = new ShareLinkDaoImpl();
        shareLinkDao.test();
    }

    public void test() throws Exception {

        ShareLinkPO link = new ShareLinkPO();
        link.setId("EE0F12A17DB54F3DA06CC892331FDF12");
        link.setName("123++++");

        Connection conn = Config.getConnection();

        try {
            MySQLDao.insertOrUpdate(link, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }



    }
}
