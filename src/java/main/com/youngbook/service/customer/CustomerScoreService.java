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

    public CustomerScorePO loadCustomerScorePO(String customerId, Connection conn) throws Exception {

        PaymentPlanVO paymentPlanVO = paymentPlanDao.getCustomerPaymentPlanInfo4ph(customerId, conn);

        CustomerScorePO customerScorePO = getCustomerScorePOByMoney(paymentPlanVO.getTotalPaymentPrincipalMoney());

        customerScorePO.setCustomerId(customerId);

        return customerScorePO;
    }

    public CustomerScorePO getCustomerScorePOByMoney(double money) throws Exception {

        double rate = Config.getSystemConfigDouble("customer.score.rate");

        int score = (int) (money * rate);

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

        CustomerScorePO customerScorePO = new CustomerScorePO();

        customerScorePO.setScore(score);
        customerScorePO.setScoreLevel(level);

        return customerScorePO;
    }


}

