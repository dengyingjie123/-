package com.youngbook.service.customer;

import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.InfoRsp;
import com.aipg.common.XSUtil;
import com.aipg.singleacctvalid.ValidR;
import com.aipg.singleacctvalid.ValidRet;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.SessionConfig;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.bank.TranxServiceImpl;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerAuthenticationStatusDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.production.OrderDetailPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.customer.CustomerScoreVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component("customerScoreService")
public class CustomerScoreService extends BaseService {

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    @Autowired
    IOrderDao orderDao;

    public List<CustomerScorePO> getListCustomerScorePO(String customerId, Integer type, Connection conn) throws Exception {

        StringUtils.checkIsEmpty(customerId, "客户编号");

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("B1BC1806")
                .addParameter4All("customerId", customerId)
                .addParameter4All("type", type)
                .initSQL();

        List<CustomerScorePO> search = MySQLDao.search(dbSQL, CustomerScorePO.class, conn);

        return search;
    }


    public CustomerScoreVO getCustomerScoreVO(String customerId, String userId,  Connection conn) throws Exception {

        List<CustomerScorePO> listCustomerScorePO = getListCustomerScorePO(customerId, null, conn);

        /**
         * 对于没有积分记录的用户，首先初始化其积分
         */
        if (listCustomerScorePO == null || listCustomerScorePO.size() == 0) {
            listCustomerScorePO = initCustomerScorePO(customerId, userId, conn);
        }

        int totalScore = 0;
        int availableScore = 0;
        int usedScore = 0;

        for (int i = 0; listCustomerScorePO != null && i < listCustomerScorePO.size(); i++) {
            CustomerScorePO customerScorePO = listCustomerScorePO.get(i);

            if (customerScorePO.getType() == CustomerScoreType.Add) {
                totalScore += customerScorePO.getScore();
            }
            else {
                usedScore += customerScorePO.getScore();
            }
        }


        // score level
        availableScore = totalScore - usedScore;
        int customerScoreLevel = getCustomerScoreLevel(availableScore);


        CustomerScoreVO customerScoreVO = new CustomerScoreVO();
        customerScoreVO.setCustomerId(customerId);
        customerScoreVO.setTotalScore(totalScore);
        customerScoreVO.setAvailableScore(availableScore);
        customerScoreVO.setUsedScore(usedScore);
        customerScoreVO.setScoreLevel(customerScoreLevel);


        return customerScoreVO;
    }



        public List<CustomerScorePO> initCustomerScorePO(String customerId, String userId, Connection conn) throws Exception {


        OrderVO orderVO = new OrderVO();
        orderVO.setCustomerId(customerId);

        List<OrderVO> listOrderVO = orderDao.getListOrderVO(orderVO, null, conn);

        List<CustomerScorePO> listCustomerScorePO = new ArrayList<>();

        for (int i = 0; listOrderVO != null && i < listOrderVO.size(); i++) {
            orderVO = listOrderVO.get(i);

            int score = getCustomerScoreByMoney(orderVO.getMoney());

            CustomerScorePO customerScorePO = saveCustomerScorePO(customerId, score, orderVO.getId(), "购买金融产品【" + orderVO.getProductionName() + "】，金额【" + orderVO.getMoney() + "】，日期【" + orderVO.getPayTime() + "】", CustomerScoreType.Add, userId, conn);

            listCustomerScorePO.add(customerScorePO);

        }


        return listCustomerScorePO;
    }

//    public CustomerScorePO loadCustomerScorePO(String customerId, Connection conn) throws Exception {
//
//        PaymentPlanVO paymentPlanVO = paymentPlanDao.getCustomerPaymentPlanInfo4ph(customerId, conn);
//
//        CustomerScorePO customerScorePO = getCustomerScorePOByMoney(paymentPlanVO.getTotalPaymentPrincipalMoney());
//
//        customerScorePO.setCustomerId(customerId);
//
//        return customerScorePO;
//    }

    public CustomerScorePO loadCustomerScorePOById(String customerScoreId, Connection conn) throws Exception {

        CustomerScorePO customerScorePO = new CustomerScorePO();
        customerScorePO.setId(customerScoreId);
        customerScorePO.setState(Config.STATE_CURRENT);

        customerScorePO = MySQLDao.load(customerScorePO, CustomerScorePO.class, conn);

        return customerScorePO;
    }

    public CustomerScorePO saveCustomerScorePO(String customerId, int score, String bizId, String comment, int type, String userId, Connection conn) throws Exception {

        CustomerScorePO customerScorePO = new CustomerScorePO();

        customerScorePO.setOperatorId(userId);
        customerScorePO.setCustomerId(customerId);
        customerScorePO.setScore(score);
        customerScorePO.setBizId(bizId);
        customerScorePO.setComment(comment);
        customerScorePO.setType(type);

        MySQLDao.insertOrUpdate(customerScorePO, userId, conn);

        return customerScorePO;
    }

    public int getCustomerScoreLevel(int score) {


        int level = 1;

        if (score < 1000000) {
            level = 1;
        }
        else if (score >= 1000000 && score < 5000000) {
            level = 2;
        }
        else if (score >= 5000000 && score < 10000000) {
            level = 3;
        }
        else if (score >= 10000000 && score < 20000000) {
            level = 4;
        }
        else if (score >= 20000000 && score < 50000000) {
            level = 5;
        }
        else {
            level = 5;
        }

        return level;
    }

    public CustomerScorePO useScore(String customerId, int score, String bizId, String comment, String userId, Connection conn) throws Exception {

        CustomerScoreVO customerScoreVO = getCustomerScoreVO(customerId, userId, conn);

        if (customerScoreVO.getAvailableScore() < score) {
            MyException.newInstance("积分不足", "customerId=" + customerId).throwException();
        }

        CustomerScorePO customerScorePO = saveCustomerScorePO(customerId, score,  bizId, comment, CustomerScoreType.Use, userId, conn);

        return customerScorePO;
    }


    public CustomerScorePO addScore(String customerId, int score, String bizId, String comment, String userId, Connection conn) throws Exception {

        CustomerScorePO customerScorePO = saveCustomerScorePO(customerId, score,  bizId, comment, CustomerScoreType.Add, userId, conn);

        return customerScorePO;
    }


    public int getCustomerScoreByMoney(double money) throws Exception {


        double rate = Config.getSystemConfigDouble("customer.score.rate");

        int score = (int) (money * rate);

        return score;
    }

    public CustomerScorePO getCustomerScorePOByMoney(double money) throws Exception {


        double rate = Config.getSystemConfigDouble("customer.score.rate");

        int score = (int) (money * rate);

        int level = getCustomerScoreLevel(score);


        CustomerScorePO customerScorePO = new CustomerScorePO();

        customerScorePO.setScore(score);

        return customerScorePO;
    }


}

