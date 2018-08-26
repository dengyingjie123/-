package com.youngbook.action.iceland;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.iceland.CallCommentPO;
import com.youngbook.entity.iceland.CommonSentencePO;
import com.youngbook.entity.iceland.CustomerOrderReviewPO;
import com.youngbook.entity.iceland.CustomerVO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerFeedbackPO;
import com.youngbook.service.customer.CustomerFeedbackService;
import com.youngbook.service.iceland.IcelandService;
import com.youngbook.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 3/7/2017.
 */
public class IcelandAction extends BaseAction {

    @Autowired
    IcelandService icelandService;

    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Autowired
    UserService userService;


    public String login() throws Exception {

        String mobile = getHttpRequestParameter("mobile");
        String password = getHttpRequestParameter("password");

        UserPO userPO = userService.login(mobile, password, getConnection());

        if (userPO == null) {
            MyException.newInstance("登录失败", "mobile="+mobile+"&password=" + password).throwException();
        }

        getResult().setReturnValue(userPO);

        return SUCCESS;
    }

    public String saveNewCallCommentStart() throws Exception {

        String comment = getHttpRequestParameter("comment");
        String customerId = getHttpRequestParameter("customerId");
        String userId = getHttpRequestParameter("userId");
        String bizId = getHttpRequestParameter("bizId");

        CallCommentPO callCommentPO = new CallCommentPO();
        callCommentPO.setComment(comment);
        callCommentPO.setCustomerId(customerId);
        callCommentPO.setUserId(userId);
        callCommentPO.setBizId(bizId);
        callCommentPO.setStartTime(TimeUtils.getNow());
        callCommentPO.setStopTime(null);

        MySQLDao.insertOrUpdate(callCommentPO, getConnection());

        getResult().setReturnValue(callCommentPO);

        return SUCCESS;
    }

    public String saveNewCallCommentStop() throws Exception {

        String callCommentId = getHttpRequestParameter("callCommentId");

        CallCommentPO callCommentPO = icelandService.loadCallCommentPO(callCommentId, getConnection());

        if (callCommentPO != null) {
            callCommentPO.setStopTime(TimeUtils.getNow());

            getResult().setReturnValue(callCommentPO);
        }

        return SUCCESS;
    }

    public String getListCallCommentPO() throws Exception {

        String callCommentId = getHttpRequestParameter("callCommentId");
        String comment = getHttpRequestParameter("comment");
        String bizId = getHttpRequestParameter("bizId");

        List<CallCommentPO> list = icelandService.getListCallCommentPO(callCommentId, comment, bizId, getConnection());

        getResult().setReturnValue(list);

        return SUCCESS;
    }

    public String saveNewCallComment() throws Exception {

        String comment = getHttpRequestParameter("comment");
        String customerId = getHttpRequestParameter("customerId");
        String userId = getHttpRequestParameter("userId");
        String bizId = getHttpRequestParameter("bizId");
        Connection conn = getConnection();

        icelandService.saveNewCallComment(comment, bizId, customerId, userId, conn);

        getResult().setReturnValue("1");

        return  SUCCESS;
    }

    public String saveCallComment() throws Exception {

        String callCommentId = getHttpRequestParameter("callCommentId");
        String comment = getHttpRequestParameter("comment");
        CallCommentPO callCommentPO = icelandService.loadCallCommentPO(callCommentId, getConnection());

        if (callCommentPO != null) {
            callCommentPO.setComment(comment);

            MySQLDao.insertOrUpdate(callCommentPO, getConnection());

            getResult().setReturnValue(callCommentPO);
        }

        return  SUCCESS;
    }


    public String getListCommonSentencePO() throws Exception {

        String salesmanId = getHttpRequestParameter("salesmanId");

        List<CommonSentencePO> list = icelandService.getListCommonSentencePO(null, salesmanId, getConnection());

        getResult().setReturnValue(list);

        return SUCCESS;
    }


    public String commonSentenceSaveDelete() throws Exception {

        String commonSentenceId = getHttpRequestParameter("commonSentenceId");

        CommonSentencePO commonSentencePO = icelandService.loadCommonSentencePO(commonSentenceId, getConnection());

        if (commonSentencePO != null) {
            MySQLDao.remove(commonSentencePO, getConnection());
        }

        return SUCCESS;
    }


    public String commonSentenceSave() throws Exception {

        String salesmanId = getHttpRequestParameter("salesmanId");
        String sentence = getHttpRequestParameter("sentence");

        CommonSentencePO commonSentencePO = new CommonSentencePO();
        commonSentencePO.setSalesmanId(salesmanId);
        commonSentencePO.setSentence(sentence);

        MySQLDao.insertOrUpdate(commonSentencePO, getConnection());

        getResult().setReturnValue(commonSentencePO);

        return SUCCESS;
    }

    public String getListCustomerVO() throws Exception {

        String salesmanId = getHttpRequestParameter("salesmanId");

        List<CustomerVO> listCustomerVO = icelandService.getListCustomerVO(salesmanId, getConnection());

        if (listCustomerVO != null) {

            for (int i = 0; i < listCustomerVO.size(); i++) {
                CustomerVO customerVO = listCustomerVO.get(i);
                String mobileMasked = StringUtils.maskMobile(customerVO.getMobile());
                customerVO.setMobileMasked(mobileMasked);

                if (customerVO.getCommentCount() > 0) {
                    customerVO.setStatusName("已拨打");
                }
            }

            getResult().setReturnValue(listCustomerVO);
        }

        return SUCCESS;
    }

    public String getListCustomerFeedback() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        List<CustomerFeedbackPO> listCustomerFeedback = customerFeedbackService.listCustomerFeedback(customerId, null, null, null, getConnection());
        getResult().setReturnValue(listCustomerFeedback);

        return  SUCCESS;
    }

    public String getListCustomerOrderReview() throws Exception {

        List<CustomerOrderReviewPO> customerOrderReviewPOs = icelandService.getListCustomerOrderReview(getConnection());

        getResult().setReturnValue(customerOrderReviewPOs);

        return SUCCESS;
    }
}
