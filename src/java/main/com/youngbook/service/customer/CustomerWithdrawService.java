package com.youngbook.service.customer;

import com.aipg.transquery.QTDetail;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentDetailPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentStatus;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.vo.customer.CustomerWithdrawVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.allinpay.AllinpayBatchPaymentDetailService;
import com.youngbook.service.system.LogService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class CustomerWithdrawService extends BaseService {

    /**
     * 新增和更新
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2015-8-28
     *
     * @throws Exception
     * @author 邓超
     * @throws Exception
     */
    public int insertOrUpdate(CustomerWithdrawPO customerWithdraw, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerWithdraw.getId().equals("")) {
            customerWithdraw.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdraw", conn));
            customerWithdraw.setId(IdUtils.getUUID32());
            customerWithdraw.setState(Config.STATE_CURRENT);
            customerWithdraw.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerWithdraw.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerWithdraw, conn);
        }
        // 更新
        else {
            CustomerWithdrawPO temp = new CustomerWithdrawPO();
            temp.setSid(customerWithdraw.getSid());
            temp = MySQLDao.load(temp, CustomerWithdrawPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerWithdraw.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdraw", conn));
                customerWithdraw.setState(Config.STATE_CURRENT);
                customerWithdraw.setOperatorId(user.getId());
                customerWithdraw.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerWithdraw, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    // 通联的新增批量代付明细
    public int insertAllinpaybatchPaymentDetail(AllinpayBatchPaymentDetailPO allinpayBatchPaymentDetail, Connection conn) throws Exception {

        int count = 0;
        // 新增
        if (allinpayBatchPaymentDetail.getId().equals("")) {
            allinpayBatchPaymentDetail.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail", conn));
            allinpayBatchPaymentDetail.setId(IdUtils.getUUID32());
            allinpayBatchPaymentDetail.setState(Config.STATE_CURRENT);
            allinpayBatchPaymentDetail.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            allinpayBatchPaymentDetail.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(allinpayBatchPaymentDetail, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 网站：新增客户提现记录
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2015-8-28
     *
     * @throws Exception
     * @author 邓超

     * @throws Exception
     */
    public String  insertCustomerWithdraw4W(CustomerWithdrawPO customerWithdraw, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        String bizId=IdUtils.getUUID32();
        customerWithdraw.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdraw", conn));
        customerWithdraw.setId(bizId);
        customerWithdraw.setState(Config.STATE_CURRENT);
        customerWithdraw.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customerWithdraw.setOperateTime(TimeUtils.getNow());
        customerWithdraw.setState(Config4Status.STATUS_CUSTOMER_DEPOSIT_UNFINISH); //未提现
        customerWithdraw.setStatus(String.valueOf(Config4Status.STATUS_CUSTOMER_DEPOSIT_UNFINISH));//未提现
        count = MySQLDao.insert(customerWithdraw, conn);
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return bizId;
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年8月22日17:16:35
     * 内容：处理提现业务
     * @param list
     * @param conn
     * @throws Exception
     */
    public void executionWithdrawType(List<AllinpayBatchPaymentDetailPO> list, QTDetail detail, Connection conn) throws Exception {
        String bizId = list.get(0).getBizId();

        //根据业务id查出提现记录
        List<CustomerWithdrawPO> customerWithdrawList = checkCustomerWithDrawById(bizId);

        if (customerWithdrawList.size() == 1) {
            String customerId = customerWithdrawList.get(0).getCustomerId(); // 取出客户 id
            CustomerMoneyPO money = getCustomerMoneyById(customerId, conn); // 根据客户 id 查出客户资金

            AllinpayBatchPaymentDetailService detailService = new AllinpayBatchPaymentDetailService();
            AllinpayBatchPaymentDetailPO temp = detailService.getCustomerAccountById(bizId, conn); //业务 id 查出客户提现金额

            LogService.info(temp);

            if (money.getAvailableMoney() >= temp.getAmount()) { // 客户资金足额提现
                // 处理提现
                changeCustomerStatus(detail, money, temp, customerId, bizId, list.get(0).getParentid(), conn);
            }
        }
    }


    /***
     * 修改人：姚章鹏
     * 时间：2015年8月18日10:44:51
     * 内容：根据客户id获取客户资金
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerMoneyPO  getCustomerMoneyById(String customerId,Connection conn) throws Exception {
        //更改客户资金
        CustomerMoneyPO money = new CustomerMoneyPO();
        money.setCustomerId(customerId);
        money.setState(Config.STATE_CURRENT);
        money = MySQLDao.load(money, CustomerMoneyPO.class, conn);
        return money;
    }

    /**
     * 修改人：姚章鹏
     * 时间：2015年8月18日10:47:18
     * 内容：判断业务id和提现表中的id匹配后，
     * 更改客户资金，提现表d状态，客户资金日志，批量请求数据的状态
     *
     * @param money
     * @param temp
     * @param customerId
     * @param bizId
     * @param parentId
     * @param conn
     * @throws Exception
     */
    public void changeCustomerStatus(QTDetail detail, CustomerMoneyPO money,AllinpayBatchPaymentDetailPO temp,String customerId,String bizId, String parentId,Connection conn) throws Exception {

        //业务id和提现表中的id匹配
        try {

            //更改明细表中的业务类型 改为1 取现成功
            int tempCount=0;
            temp.setState(Config.STATE_UPDATE);
            tempCount = MySQLDao.update(temp, conn);

            if (tempCount == 1) {
                temp.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail"));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                temp.setOperateTime(TimeUtils.getNow());

                if("0000".equals(detail.getRET_CODE())) {

                    temp.setStatus(AllinpayBatchPaymentStatus.SUCCESS);

                    //更改提现表中的状态id 改为1 取现成功
                    int withdrawCount = 0;
                    CustomerWithdrawPO withdraw = new CustomerWithdrawPO();
                    withdraw.setId(bizId);
                    withdraw.setState(Config.STATE_CURRENT);
                    withdraw = MySQLDao.load(withdraw, CustomerWithdrawPO.class, conn);
                    withdraw.setState(Config.STATE_UPDATE);
                    withdrawCount = MySQLDao.update(withdraw, conn);
                    if (withdrawCount == 1) {
                        withdraw.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdraw"));
                        withdraw.setState(Config.STATE_CURRENT);
                        withdraw.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                        withdraw.setOperateTime(TimeUtils.getNow());
                        withdraw.setStatus(String.valueOf(AllinpayBatchPaymentStatus.SUCCESS));
                        MySQLDao.insert(withdraw, conn);
                    }

                    //更改客户资金
                    int moneyCount=0;
                    money.setState(Config.STATE_UPDATE);
                    moneyCount = MySQLDao.update(money, conn);
                    if (moneyCount == 1) {
                        money.setSid(MySQLDao.getMaxSid("CRM_CustomerMoney"));
                        money.setState(Config.STATE_CURRENT);
                        money.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                        money.setOperateTime(TimeUtils.getNow());
                        money.setAvailableMoney(money.getAvailableMoney() -withdraw.getMoney()  );
                        MySQLDao.insert(money, conn);
                    }

                    //更改客户资金日志
                    CustomerMoneyLogPO moneyLog = new CustomerMoneyLogPO();
                    int logCount = 0;
                    moneyLog.setBizId(bizId);
                    moneyLog = MySQLDao.load(moneyLog, CustomerMoneyLogPO.class, conn);
                    moneyLog.setState(Config.STATE_UPDATE);
                    logCount = MySQLDao.update(moneyLog);

                    if (logCount == 1) {
                        moneyLog.setSid(MySQLDao.getMaxSid("CRM_CustomerMoneyLog", conn));
                        moneyLog.setState(Config.STATE_CURRENT);
                        moneyLog.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                        moneyLog.setOperateTime(TimeUtils.getNow());
                        moneyLog.setStatus(CustomerMoneyLogStatus.Success);//提现成功
                        logCount = MySQLDao.insert(moneyLog, conn);
                    }

                }
                else {
                    temp.setStatus(AllinpayBatchPaymentStatus.FAILED);
                    temp.setRevised(Config4Status.STATUS_ALLINPAY_REVISED_FAIL);     // 设为未修正状态，需要通联重新扫描
                }

                tempCount = MySQLDao.insert(temp, conn);
            }

        }catch(Exception ex){
            MyException.deal(ex);
            //添加客户提现失败日志
            customerMoneyLogStatus(customerId,conn);
        }
    }


    /**
     * 修改人：姚章鹏
     * 时间：2015年8月9日22:21:26
     * 内容：根据业务id更改明细表类型
     * @param bizId
     * @return
     * @throws Exception
     */
   public List<CustomerWithdrawPO>  checkCustomerWithDrawById(String bizId) throws Exception {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT ");
       sql.append(" * ");
       sql.append(" FROM ");
       sql.append(" crm_customerwithdraw ");
       sql.append(" WHERE ");
       sql.append(" 1 = 1 ");
       sql.append(" AND state = " + Config.STATE_CURRENT + " ");
       sql.append(" AND id ='"+Database.encodeSQL(bizId)+"'" );
       List<CustomerWithdrawPO> list = MySQLDao.query(sql.toString(), CustomerWithdrawPO.class, new ArrayList<KVObject>());
           return list;
   }

    /**
     * 提现失败
     * @param loginUserId
     * @param conn
     * @throws Exception
     */
    public void customerMoneyLogStatus(String loginUserId,Connection conn) throws Exception {
        CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();
        customerMoneyLog.setType(CustomerMoneyLogType.WithdrawOrPayment);
        customerMoneyLog.setContent("更改客户资金失败");
        customerMoneyLog.setStatus(CustomerMoneyLogStatus.Failed);//提现失败
        customerMoneyLog.setCustomerId(loginUserId);
        MySQLDao.insertOrUpdate(customerMoneyLog, conn);
    }












    // 获取数据集合
    public Pager list(CustomerWithdrawVO customerWithdrawVO, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //多表sql查询语句
        String sql = "SELECT cw.*,u.`name` operatorName,cp.`Name` customerName "+
                " FROM crm_customerwithdraw cw,crm_customerpersonal cp,system_user u " +
                " WHERE 1 = 1 AND cw.state = 0 AND cp.state = 0 AND u.state = 0 AND cp.id = cw.CustomerId AND cw.operatorId = u.Id";
        //AND u.Id = cp.OperatorId  删除条件
        Pager pager = Pager.query(sql, customerWithdrawVO, conditions, request, queryType);

        return pager;
    }

    //删除的方法
    public int delete(CustomerWithdrawPO customerWithdraw, UserPO user, Connection conn) throws Exception {
        int count = 0;

        customerWithdraw = MySQLDao.load(customerWithdraw, CustomerWithdrawPO.class);
        customerWithdraw.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerWithdraw, conn);
        if (count == 1) {
            customerWithdraw.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdraw", conn));
            customerWithdraw.setState(Config.STATE_DELETE);
            customerWithdraw.setOperateTime(TimeUtils.getNow());
            customerWithdraw.setOperatorId(user.getId());
            count = MySQLDao.insert(customerWithdraw, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }
}
