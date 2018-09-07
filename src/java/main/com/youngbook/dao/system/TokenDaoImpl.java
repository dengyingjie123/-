package com.youngbook.dao.system;

import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.TokenPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 7/1/2018.
 */
@Component("tokenDao")
public class TokenDaoImpl implements ITokenDao {

    /**
     *
     * @param tokenString
     * @param bizId
     * @param ip
     * @param conn
     * @return 0：成功
     * @throws Exception
     */
    public int checkAndDestroyToken(String tokenString, String bizId, String bizType, String ip, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(tokenString, bizId, bizType, ip)) {
            MyException.newInstance("验证token失效","tokenString="+tokenString+"&bizType="+bizType+"&bizId="+bizId+"&ip="+ip).throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("1A7E1807");
        dbSQL.addParameter4All("token", tokenString);
        dbSQL.addParameter4All("bizId", bizId);
        dbSQL.addParameter4All("bizType", bizType);
        dbSQL.addParameter4All("ip", ip);
        dbSQL.addParameter4All("expiredTime", TimeUtils.getNow());
        dbSQL.initSQL();

        List<TokenPO> listTokenPO = MySQLDao.search(dbSQL, TokenPO.class, conn);

        if (listTokenPO == null || listTokenPO.size() != 1) {
            MyException.newInstance("验证token失效","tokenString="+tokenString+"&bizId="+bizId+"&ip="+ip).throwException();
        }

        TokenPO tokenPO = listTokenPO.get(0);
        tokenPO.setUsedTime(TimeUtils.getNow());
        tokenPO.setIp(ip);

        MySQLDao.insertOrUpdate(tokenPO, conn);

        return 0;
    }
}
