package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentDetailPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentStatus;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentType;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.vo.customer.CustomerMoneyVO;
import com.youngbook.entity.vo.customer.CustomerRefundVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.allinpay.AllinpayBatchPaymentDetailService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by 张舜清 on 2015/8/25.
 */
public class CustomerRefundService extends BaseService {

    /**
     * 创建人：张舜清：
     * 时间：2015年8月26日14:33:56
     * 内容：新增操作
     *
     * @param customerRefund 客户退款PO对象
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    public CustomerRefundPO insert(CustomerRefundPO customerRefund ,Connection conn) throws Exception {

        if (customerRefund == null) {
            throw new Exception("保存退款数据失败");
        }

        customerRefund.setSid(MySQLDao.getMaxSid("crm_customerrefund",conn));
        customerRefund.setId(IdUtils.getUUID32());
        customerRefund.setState(Config.STATE_CURRENT);
        customerRefund.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customerRefund.setOperateTime(TimeUtils.getNow());
        int count = MySQLDao.insert(customerRefund,conn);
        if (count != 1){
            throw new Exception("保存退款数据失败");
        }

        return customerRefund;
    }

    public CustomerRefundPO dealRefund(String customerId, double money, String callbackId, String orderId, String reason, String ip, Connection conn) throws Exception {

        CustomerRefundPO customerRefund = new CustomerRefundPO();
        customerRefund.setCustomerId(customerId);
        customerRefund.setMoney(money);
        customerRefund.setCreatedDatetime(TimeUtils.getNow());
        customerRefund.setStates(CustomerRefundStatus.Failed);
        customerRefund.setBackId(callbackId);
        customerRefund.setOrderId(orderId);
        customerRefund.setCustomerIP(ip);
        customerRefund.setReason(reason);
        customerRefund = this.insert(customerRefund, conn);
        return customerRefund;
    }

    public void setRefundStatus(String id, String status) throws Exception {
        CustomerRefundPO refund = new CustomerRefundPO();
        refund.setId(id);
        refund.setState(Config.STATE_CURRENT);
        refund = MySQLDao.load(refund, CustomerRefundPO.class);

        refund.setStates(status);

        insertOrUpdate(refund, "");
    }

    public CustomerRefundPO insertOrUpdate(CustomerRefundPO po, String userId) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return insertOrUpdate(po, userId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public CustomerRefundPO insertOrUpdate(CustomerRefundPO po, String userId, Connection conn) throws Exception {

        int count = 0;
        // 新增
        if (po.getId().equals("")) {
            po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPayment", conn));
            po.setId(IdUtils.getUUID32());
            po.setState(Config.STATE_CURRENT);
            po.setOperatorId(userId);
            po.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(po, conn);
        }
        // 更新
        else {
            CustomerRefundPO temp = new CustomerRefundPO();
            temp.setSid(po.getSid());
            temp = MySQLDao.load(temp, CustomerRefundPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                po.setSid(MySQLDao.getMaxSid("crm_customerrefund", conn));
                po.setState(Config.STATE_CURRENT);
                po.setOperatorId(userId);
                po.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(po, conn);

                po = MySQLDao.load(po, CustomerRefundPO.class, conn);
            }
        }

        if (count != 1) {
            throw new Exception("更新数据CustomerRefundPO失败");
        }

        return po;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月26日14:33:16
     * 内容：关联查询出数据，vo作为存放对象
     *
     * @param customerRefundVO 客户退款VO对象
     * @param conditions 数据库连接
     * @return
     * @throws Exception
     */
    public Pager list(CustomerRefundVO customerRefundVO, List<KVObject> conditions) throws Exception{
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();

        StringBuffer sqlDB = new StringBuffer();

        //组装SQL语句
        sqlDB.append(" SELECT ");
        sqlDB.append("     refund.sid, ");
        sqlDB.append("     refund.id, ");
        sqlDB.append("     refund.state, ");
        sqlDB.append("     refund.operatorId, ");
        sqlDB.append("     refund.operateTime, ");
        sqlDB.append("     refund.customerId, ");
        sqlDB.append("     refund.customerIP, ");
        sqlDB.append("     refund.money, ");
        sqlDB.append("     refund.createdDatetime, ");
        sqlDB.append("     CASE states ");
        sqlDB.append(" WHEN 0 THEN ");
        sqlDB.append("     '未退款' ");
        sqlDB.append(" WHEN 1 THEN ");
        sqlDB.append("     '已退款' ");
        sqlDB.append(" WHEN 2 THEN ");
        sqlDB.append("     '已接受' ");
        sqlDB.append(" END states, ");
        sqlDB.append("  refund.refundDatetime, ");
        sqlDB.append("  refund.refundAmount, ");
        sqlDB.append("  refund.backID, ");
        sqlDB.append("  refund.OrderID, ");
        sqlDB.append("  refund.Reason, ");
        sqlDB.append("  personal.`Name` AS customerName, ");
        sqlDB.append("  personal.Mobile AS mobile ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_customerrefund refund, ");
        sqlDB.append("     crm_customerpersonal personal ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND refund.state = 0 ");
        sqlDB.append(" AND personal.state = 0 ");
        sqlDB.append(" AND refund.customerId = personal.id ");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象
        Pager pager = Pager.query(sqlDB.toString(), customerRefundVO, conditions, request, queryType);
        return pager;
    }


    /**
     * 创建人：张舜清
     * 时间：2015年8月26日14:31:11
     * 内容：第一步查询出对应的退款记录数据
     *      第二部查询该客户有木有绑定银行卡
     *      第三步对查询出的退款记录进行自定义规格数据操作，第四步新增通联批量代付明细表
     *
     * @param request HttpServletRequest请求
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    public int refund(HttpServletRequest request,Connection conn)throws Exception{
        int count = 0;

        String id = request.getParameter("id");
        CustomerRefundPO customerRefund = new CustomerRefundPO();
        customerRefund.setId(id);
        customerRefund.setState(Config.STATE_CURRENT);
        customerRefund = MySQLDao.load(customerRefund,CustomerRefundPO.class,conn);
        if (customerRefund == null){
            // 返回2表示找不到退款记录
            return 2;
        }

        // 查出客户的银行卡
        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setCustomerId(customerRefund.getCustomerId());
        customerAccount.setState(Config.STATE_CURRENT);
        List<CustomerAccountPO> customerAccountPOList = MySQLDao.query(customerAccount,CustomerAccountPO.class,conn);
        if (customerAccountPOList.size() == 0){
            // 返回3表示客户未绑定银行卡
            return 3;
        }

        // 以上数据没问题后对本条记录进行操作
        customerRefund.setState(Config.STATE_UPDATE);
        MySQLDao.update(customerRefund,conn);
        customerRefund.setSid(MySQLDao.getMaxSid("crm_CustomerRefund",conn));
        customerRefund.setState(Config.STATE_CURRENT);
        customerRefund.setOperateTime(TimeUtils.getNow());
        customerRefund.setStates(CustomerRefundStatus.Accepted);
        customerRefund.setRefundAmount(customerRefund.getMoney());
        customerRefund.setRefundDatetime(TimeUtils.getNow());
        MySQLDao.insert(customerRefund,conn);

        // 插入通联批量代付明细表
        AllinpayBatchPaymentDetailPO allinpayBatchPaymentDetail = new AllinpayBatchPaymentDetailPO();
        CustomerWithdrawService customerWithdrawService = new CustomerWithdrawService();
        allinpayBatchPaymentDetail.setSn(StringUtils.buildNumberString(MySQLDao.getSequence("SN"), 4));
//        allinpayBatchPaymentDetail.setBank_code(Config4W.BANK_CODE);   //招商的测试的银行代码 308
        allinpayBatchPaymentDetail.setAccount_type("00");//00银行卡 ,01存折
        allinpayBatchPaymentDetail.setAccount_no(customerAccountPOList.get(0).getNumber());//银行卡号
        allinpayBatchPaymentDetail.setAccount_name(customerAccountPOList.get(0).getName());//银行账户名字
        allinpayBatchPaymentDetail.setBank_name(customerAccountPOList.get(0).getBankBranchName());//开户名称 //可空
        allinpayBatchPaymentDetail.setAccount_pror(0);//账户属性  0私人
        allinpayBatchPaymentDetail.setAmount((int)customerRefund.getMoney()*100);//金额
        allinpayBatchPaymentDetail.setBizId(customerRefund.getId());//业务Id
        allinpayBatchPaymentDetail.setBizType(AllinpayBatchPaymentType.Refund);//退款类型
        allinpayBatchPaymentDetail.setStatus(AllinpayBatchPaymentStatus.UNFINISH);

        count = customerWithdrawService.insertAllinpaybatchPaymentDetail(allinpayBatchPaymentDetail,conn);

        return count;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月26日14:29:50
     * 内容：第一步查询出退款对应的数据，进行自定义规格的数据操作，然后第二步写入日志，AllinpayBatchPaymentService调用
     *
     * @param list 通联明细批量代付集合
     * @param conn 数据库连接
     * @throws Exception
     */
    public void executionRefundType(List<AllinpayBatchPaymentDetailPO> list, Connection conn) throws Exception {
        try {
            AllinpayBatchPaymentDetailService detailService = new AllinpayBatchPaymentDetailService();
            AllinpayBatchPaymentDetailPO temp = detailService.getCustomerAccountById(list.get(0).getBizId(), conn);
            //更改明细表中的业务类型 改为1 退款成功
            int tempCount = 0;
            temp.setState(Config.STATE_UPDATE);
            tempCount = MySQLDao.update(temp, conn);
            if (tempCount == 1) {
                temp.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail"));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                temp.setOperateTime(TimeUtils.getNow());
                temp.setStatus(Config4Status.STATUS_CUSTOMER_PAYMENT_SUCCESS);
                MySQLDao.insert(temp, conn);
            }

            // 新增客户日志
            CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();
            customerMoneyLog.setBizId(temp.getBizId());
            customerMoneyLog.setState(Config.STATE_CURRENT);
            customerMoneyLog = MySQLDao.load(customerMoneyLog,CustomerMoneyLogPO.class,conn);
            customerMoneyLog.setState(Config.STATE_UPDATE);
            MySQLDao.update(customerMoneyLog,conn);
            customerMoneyLog.setSid(MySQLDao.getMaxSid("crm_customermoneylog",conn));
            customerMoneyLog.setState(Config.STATE_CURRENT);
            customerMoneyLog.setOperateTime(TimeUtils.getNow());
            customerMoneyLog.setStatus(CustomerMoneyLogStatus.Successd);
            MySQLDao.insert(customerMoneyLog,conn);

        } catch (Exception e) {
            MyException.deal(e);
        }
    }
}
