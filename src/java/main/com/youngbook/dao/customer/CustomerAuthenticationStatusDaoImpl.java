package com.youngbook.dao.customer;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatus;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatusPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("customerAuthenticationStatusDao")
public class CustomerAuthenticationStatusDaoImpl implements ICustomerAuthenticationStatusDao {

    /**
     * 新增方法和更新方法
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param customerAuthenticationStatus
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CustomerAuthenticationStatusPO customerAuthenticationStatus, UserPO user, Connection conn) throws Exception {
        Integer count = MySQLDao.insertOrUpdate(customerAuthenticationStatus, user.getOperatorId(), conn);
        return count;
    }

    /**
     * 根据用户获取用户的状态对象
     *
     * 修改：邓超
     * 内容：从 CustomerPersonalService 移动并修改代码
     * 时间：2016年5月18日
     *
     * @param id
     * @return
     */
    public CustomerAuthenticationStatusPO loadByCustomerId(String id, Connection conn) throws Exception {
        CustomerAuthenticationStatusPO statusPO = new CustomerAuthenticationStatusPO();
        statusPO.setState(Config.STATE_CURRENT);
        statusPO.setCustomerId(id);
        statusPO = MySQLDao.load(statusPO, CustomerAuthenticationStatusPO.class, conn);
        return statusPO;
    }

    /**
     * 交易平台请求的 Service，保存用户的邮箱认证状态
     * 前提是网站的 Customer 已经登录
     * 用法：在 Action 中调用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @author 邓超
     * @param customerId
     * @param conn
     * @return 执行数据记录的行数
     * @throws Exception
     */
    public Integer saveAuthenticationStatus(String customerId, Integer authenticationType, Connection conn) throws Exception {

        // 当前时间
        String time = TimeUtils.getNow();

        // 尝试通过客户 ID 查询客户的认证记录
        CustomerAuthenticationStatusPO statusPO = new CustomerAuthenticationStatusPO();
        statusPO.setCustomerId(customerId);
        statusPO.setState(Config.STATE_CURRENT);
        statusPO = MySQLDao.load(statusPO, CustomerAuthenticationStatusPO.class, conn);

        if(statusPO == null) {
            statusPO = new CustomerAuthenticationStatusPO();
            statusPO.setCustomerId(customerId);
            statusPO.setState(Config.STATE_CURRENT);
        }

        // 银行卡认证
        if(authenticationType == CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT) {
            statusPO.setAccountStatus(1);
            statusPO.setAccountTime(time);
        }
        // 邮箱认证
        if(authenticationType == CustomerAuthenticationStatus.AUTH_TYPE_EMAIL) {
            statusPO.setEmailStatus(1);
            statusPO.setEmailTime(time);
        }
        // 现场见面认证
        if(authenticationType == CustomerAuthenticationStatus.AUTH_TYPE_FACE) {
            statusPO.setFaceStatus(1);
            statusPO.setFaceTime(time);
        }
        // 手机认证
        if(authenticationType == CustomerAuthenticationStatus.AUTH_TYPE_MOBILE) {
            statusPO.setMobileStatus(1);
            statusPO.setMobileTime(time);
        }
        // 安全问题认证
        if(authenticationType == CustomerAuthenticationStatus.AUTH_TYPE_QA) {
            statusPO.setQaStatus(1);
            statusPO.setQaTime(time);
        }
        // 视频认证
        if(authenticationType == CustomerAuthenticationStatus.AUTH_TYPE_VIDEO) {
            statusPO.setVideoStatus(1);
            statusPO.setVideoTime(time);
        }

        // 新增或修改
        Integer count = MySQLDao.insertOrUpdate(statusPO, conn);
        return count;
    }
}
