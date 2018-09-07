package com.youngbook.action.dehesales;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.cms.IArticleDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerCatalog;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.cms.ArticleVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.service.customer.CustomerPersonalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 7/29/2018.
 */
public class DeheSalesAction extends BaseAction {

    @Autowired
    IArticleDao articleDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    CustomerPersonalService customerPersonalService;

    public String loadPage_deheSales_home_list() throws Exception {

        String columnId = getHttpRequestParameter("columnId");

        ArticleVO articleVO = new ArticleVO();
        articleVO.setColumnId(columnId);

        List<ArticleVO> listArticleVO = articleDao.getListArticleVO(articleVO, getConnection());

        getRequest().setAttribute("listArticleVO", listArticleVO);

        return "home_list";
    }


    public String loadPage_deheSales_customer_list() throws Exception {

        UserPO loginUser = getLoginUser();

        List<CustomerPersonalVO> listCustomerPersonalVO = customerPersonalDao.listCustomerPersonalVO(loginUser.getId(), null, null, getConnection());

        getRequest().setAttribute("listCustomerPersonalVO", listCustomerPersonalVO);

        return "customer_list";
    }

    public String loadPage_deheSales_customer_detail() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        CustomerPersonalVO customerPersonalVO = customerPersonalDao.loadCustomerVOByCustomerPersonalId(customerId, getConnection());

        getRequest().setAttribute("customerPersonalVO", customerPersonalVO);

        return "customer_detail";
    }


    public String loadPage_deheSales_customer_save() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, getConnection());

        getRequest().setAttribute("customerPersonalPO", customerPersonalPO);

        return "customer_save";
    }


    public String customerPersonal_update() throws Exception {

        CustomerPersonalPO personal = HttpUtils.getInstanceFromRequest(getRequest(), "personal", CustomerPersonalPO.class);

        Connection conn = getConnection();

        // 客户号不能删除
        personal.setPersonalNumber(null);
        personal.setLoginName(null);
        personal.setState(Config.STATE_CURRENT);
        personal.setCustomerCatalogId(null);


        if (!StringUtils.isEmpty(personal.getId())) {
            CustomerPersonalPO tempCustomer = customerPersonalDao.loadByCustomerPersonalId(personal.getId(), conn);

            if (tempCustomer == null) {
                MyException.newInstance("数据异常", "customerId=" + personal.getId()).throwException();
            }

        }
        else {
            personal.setCustomerCatalogId(CustomerCatalog.Template);
        }



        // 保存基本信息
//        customerPersonalService.insertOrUpdate(personal, getLoginUser().getId(), conn);
        customerPersonalDao.updateModern(personal, getLoginUser().getReferralCode(), conn);
        return SUCCESS;
    }

    public String customerPersonal_remove() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        customerPersonalService.removeByCustomerPersonalId(customerId, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }




    public String customerPersonal_register() throws Exception {
        Connection conn = getConnection();

        CustomerPersonalPO personal = HttpUtils.getInstanceFromRequest(getRequest(), CustomerPersonalPO.class);
        personal.setCustomerCatalogId(CustomerCatalog.Template);

        String referralCode = getLoginUser().getReferralCode();
        if (StringUtils.isEmpty(referralCode)) {
            referralCode = getLoginUser().getReferralCode();
        }

        personal = customerPersonalService.registerCustomer(personal, referralCode, conn);


        getResult().setReturnValue(personal);

        return SUCCESS;
    }


    public String loadPage_deheSales_bank_save() throws Exception {

        return "bank_save";
    }

}
