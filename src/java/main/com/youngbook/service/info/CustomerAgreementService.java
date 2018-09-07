package com.youngbook.service.info;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.info.CustomerAgreementPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("customerAgreementService")
public class CustomerAgreementService extends BaseService {

    public CustomerAgreementPO loadCustomerAgreementPO(String customerId, String agreementId, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(customerId, agreementId)) {
            MyException.newInstance("查询客户同意协议有误").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadCustomerAgreementPO", this);
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.addParameter4All("agreementId", agreementId);
        dbSQL.initSQL();

        List<CustomerAgreementPO> list = MySQLDao.search(dbSQL, CustomerAgreementPO.class, conn);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 保存客户与协议的关系
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月4日
     *
     * @param customerId
     * @param agreementId
     * @param agreementName
     *
     * @return
     */
    public void saveCustomerAgreement(String customerId, String agreementId, String agreementName, Connection conn) throws Exception {

        // 构造实例
        CustomerAgreementPO agreementPO = new CustomerAgreementPO();
        agreementPO.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        agreementPO.setOperateTime(TimeUtils.getNow());

        agreementPO.setCustomerId(customerId);
        agreementPO.setAgreementId(agreementId);
        agreementPO.setAgreementName(agreementName);

        MySQLDao.insertOrUpdate(agreementPO, conn);
    }

    /**
     * 增加用户同意的协议
     * @param conn
     * @return int
     * @throws Exception
     */
    public int insertCustomerAgreement(CustomerAgreementPO customerAgreement, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerAgreement.getId().equals("")) {
            customerAgreement.setSid(MySQLDao.getMaxSid("info_customeragreement", conn));
            customerAgreement.setId(IdUtils.getUUID32());
            customerAgreement.setState(Config.STATE_CURRENT);
            customerAgreement.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerAgreement.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerAgreement, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }


}
