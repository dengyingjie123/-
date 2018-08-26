package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.CodePO;
import com.youngbook.service.BaseService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CaptchaService extends BaseService {

    /**
     * 校验验证码
     * 返回布尔值
     * 用法：new CaptchaService().validateCode()
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return Boolean
     * @throws Exception
     */
    public Boolean validateCode(String code, String u, Connection conn) throws Exception {

        if (conn == null) {
            throw new Exception("校验码验证失败，无法获得数据库连接");
        }

        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(u)) {
            return false;
        }



        String now = TimeUtils.getNow();
        String sql = "SELECT * FROM system_code code WHERE code.UsedTime is null and code.code = ? AND code.type = ? AND code.expiredTime > '" + now + "'";

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, code, parameters);
        parameters = Database.addQueryKVObject(2, u, parameters);

        List<CodePO> list = MySQLDao.search(sql, parameters, CodePO.class, new ArrayList<KVObject>(), conn);

        if(list != null && list.size() == 1) {
            CodePO codePO = list.get(0);
            codePO.setUsedTime(now);
            MySQLDao.update(codePO, conn);

            return true;
        }

        return false;
    }

}
