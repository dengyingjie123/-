package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatus;
import com.youngbook.service.customer.CustomerAuthenticationStatusService;
import com.youngbook.service.customer.CustomerProtectionQAService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class CustomerProtectionQAAction extends BaseAction {

    @Autowired
    CustomerAuthenticationStatusService customerAuthenticationStatusService;

    @Autowired
    CustomerProtectionQAService customerProtectionQAService;

    /**
     * 获取安全保护问题
     *
     * 说明：如果传入了 customerId，则返回该客户设置的安全密保问题和答案
     * 如果 customerId 没有传或为空，则返回系统定义的安全保护问题，供下拉菜单使用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月14日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getQA() throws Exception {

        // 获取请求对象、数据库连接
        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");

        // 如果客户 ID 参数不为空，那么查询该客户设置过的安全密保问题
        if(StringUtils.isEmpty(customerId) || customerId.length() != 32) {
            Pager pager = customerProtectionQAService.getQuestions(conn);
            this.getResult().setReturnValue(pager);
        }
        // 否则查询系统在 KV 里配置好的安全密保问题，供客户设置
        else {
            Pager pager = customerProtectionQAService.getQAByCustomerId(customerId, conn);
            this.getResult().setReturnValue(pager);
        }

        return SUCCESS;

    }

    /**
     * 保存客户的安全密保问题和答案
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月14日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String save() throws Exception {

        // 获取请求对象和连接
        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        // 获取客户 ID
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 获取客户选择的问题
        String q1 = HttpUtils.getParameter(request, "q1");
        String q2 = HttpUtils.getParameter(request, "q2");
        String q3 = HttpUtils.getParameter(request, "q3");
        // 获取客户的答案
        String a1 = HttpUtils.getParameter(request, "a1");
        String a2 = HttpUtils.getParameter(request, "a2");
        String a3 = HttpUtils.getParameter(request, "a3");

        // 校验参数
        if(StringUtils.isEmpty(customerId) || customerId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(StringUtils.isEmpty(a1) || StringUtils.isEmpty(a2) || StringUtils.isEmpty(a3) || StringUtils.isEmpty(q1) || StringUtils.isEmpty(q2) || StringUtils.isEmpty(q3)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 参数双边去空
        q1 = q1.trim();
        q2 = q2.trim();
        q3 = q3.trim();
        a1 = a1.trim();
        a2 = a2.trim();
        a3 = a3.trim();

        // 检查问题是否存在于系统中
        if(!customerProtectionQAService.isQuestionExist(q1, conn) || !customerProtectionQAService.isQuestionExist(q2, conn) || !customerProtectionQAService.isQuestionExist(q3, conn)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 保存安全密保问题与答案
        Integer count = customerProtectionQAService.saveQA(customerId, q1, a1, q2, a2, q3, a3, conn);
        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "您的数据保存失败，请稍候重试").throwException();
        }

        // 设置安全密保问题的认证状态
        customerAuthenticationStatusService.saveAuthenticationStatus(customerId, CustomerAuthenticationStatus.AUTH_TYPE_QA, conn);

        return SUCCESS;

    }

}
