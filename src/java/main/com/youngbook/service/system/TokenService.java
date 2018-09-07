package com.youngbook.service.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.system.ITokenDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.TokenBizType;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.service.BaseService;
import com.youngbook.service.customer.SalemanOuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("tokenService")
public class TokenService extends BaseService {

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    ITokenDao tokenDao;

    /**
     * 通过客户ID和IP地址来创建一个新的 Token(for 微厚币)
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月7日
     *
     * @return
     * @throws Exception
     */
    public TokenPO newToken(String bizId, String bizType, String ip, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(bizId, bizType, ip)) {
            MyException.newInstance("参数不完整", "bizId="+bizId+"&bizType="+bizType+"&ip=" + ip).throwException();
        }

        String newTokenString = IdUtils.getUUID32();

        // 校验参数
        if(StringUtils.isEmpty(ip) || conn == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }


        /**
         * 设置过期时间
         */
        // 当前时间
        String now = TimeUtils.getNow();
        String expired = "";




        // 客户登录
        if (bizType.equals(TokenBizType.CustomerLoginToken)) {
            // 查询客户
            CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(bizId, conn);

            if(personalPO == null) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "用户不存在").throwException();
            }

            expired = TimeUtils.getTime(now, Config.getSystemConfigInt("system.customer.login.timeout.second"), TimeUtils.SECOND);
        }
        // 销售登录
        else if (bizType.equals(TokenBizType.UserLoginToken)) {
            // 查询客户
            UserPO userPO = userDao.loadUserByUserId(bizId, conn);
            if(userPO == null) {
                MyException.newInstance("销售不存在", "userId=" + bizId).throwException();
            }
        }
        // 验证码
        else if (bizType.equals(TokenBizType.MobileCode)) {

            String isDebug = Config.getSystemConfig("system.debug");
            if (!StringUtils.isEmpty(isDebug) && isDebug.equals("1")) {
                newTokenString = "872038";
            }
            else {
                newTokenString = String.valueOf(NumberUtils.randomNumbers(6));
            }

            expired = TimeUtils.getTime(now, Config.getSystemConfigInt("system.mobileCode.timeout.second"), TimeUtils.SECOND);

        }
        else {
            MyException.newInstance("bizType数据错误", "bizType=" + bizType).throwException();
        }





        // 查询 Token
        List<TokenPO> tokens = this.loadByBizId(bizId);
        // 把客户的所有 Token 清除
        for(TokenPO token : tokens) {
            if(this.cancelToken(token, conn) != 1) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据删除失败").throwException();
            }
        }




        // 生成新的 Token
        TokenPO tokenPO = new TokenPO();
        tokenPO.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        tokenPO.setOperateTime(now);
        tokenPO.setState(Config.STATE_CURRENT);
        tokenPO.setToken(newTokenString);
        tokenPO.setIp(ip);
        tokenPO.setExpiredTime(expired);
        tokenPO.setBizType(bizType);
        tokenPO.setBizId(bizId);

        String checkCode = StringUtils.md5(newTokenString + bizId);
        tokenPO.setCheckCode(checkCode);

        // 插入新 Token
        Integer count = MySQLDao.insertOrUpdate(tokenPO, conn);
        return count == 1 ? tokenPO : null;
    }


    /**
     * （根据用户ID）查询某个客户的 Token(可用于销售APP)
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月7日
     *
     * @param customerId
     * @return
     */
    public List<TokenPO> loadByBizId(String customerId) throws Exception {

        String sql = "select * from system_token t where t.state = 0 and t.bizId = '" + customerId + "'";
        List<TokenPO> list = MySQLDao.query(sql, TokenPO.class, new ArrayList<KVObject>());
        return list;

    }

    /**
     * 根据 token 查询
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月19日
     *
     * @param token
     * @return
     */
    public TokenPO loadTokenPOByToken(String token, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("ED2E1809");
        dbSQL.addParameter4All("loginToken", token);
        dbSQL.initSQL();

        List<TokenPO> list = MySQLDao.search(dbSQL, TokenPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

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

        int i = tokenDao.checkAndDestroyToken(tokenString, bizId, bizType, ip, conn);

        return i;
    }

    /**
     * token校验
     * 返回布尔值
     * 用法：new TokenService().validateToken()
     *
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015-12-5
     *
     * @author quan.zeng
     * @return Boolean
     * @throws Exception
     *
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015-12-6
     * 描述：修改返回值
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015-12-9
     * 描述：失效token状态改为2
     *
     */
    public TokenPO checkAndRenewToken(TokenPO tokenPO, Connection conn) throws Exception {

        if(tokenPO == null || StringUtils.isEmpty(tokenPO.getToken())){
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_NOT_CORRECT, "您需要登录才可进一步操作").throwException();
        }

        tokenPO = MySQLDao.load(tokenPO, TokenPO.class, conn);

        if (tokenPO == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_NOT_CORRECT, "您需要登录才可进一步操作").throwException();
        }


        String nowTimeStr = TimeUtils.getNow();
        if(TimeUtils.getSecondDifference(nowTimeStr,tokenPO.getExpiredTime(),"yyyy-MM-dd HH:mm:ss") < 0){
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_IS_INVALID, "您的登录已过期").throwException();
        }


        String expiredTime = TimeUtils.getTime(nowTimeStr, Config.getSystemConfigInt("system.login.timeout.second"), TimeUtils.SECOND);
        tokenPO.setExpiredTime(expiredTime);
        tokenPO.setOperateTime(nowTimeStr);
        tokenPO.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        MySQLDao.update(tokenPO,conn);  //更改失效时间

        return tokenPO;
    }

    public TokenPO checkAndRenewToken(String tokenString, String bizId, String bizType, String ip, Connection conn) throws Exception {

        if (StringUtils.isEmpty(tokenString)) {
            MyException.newInstance("token数据为空", "tokenString="+tokenString).throwException();
        }

        TokenPO tokenPO = new TokenPO();
        tokenPO.setToken(tokenString);
        tokenPO.setState(Config.STATE_CURRENT);
        tokenPO.setBizId(bizId);
        tokenPO.setBizType(bizType);
        tokenPO.setIp(ip);


        return checkAndRenewToken(tokenPO, conn);
    }


    /**
     * 注销 token 对象，在数据库里将此 token 删除
     *
     * 修改：邓超
     * 内容：删除数据不直接删除，把状态改为 2
     *
     * @param token
     * @param conn
     * @return
     */
    public int cancelToken(TokenPO token, Connection conn) throws Exception {

        if (token == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_NOT_CORRECT, "请检查参数是否正确！").throwException();
        }

        int count = MySQLDao.remove(token, Config.getDefaultOperatorId(), conn);

        return count;
    }

}
