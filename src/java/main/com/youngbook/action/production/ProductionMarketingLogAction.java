package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.service.production.ProductionMarketingLogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class ProductionMarketingLogAction extends BaseAction {



    @Autowired
    ProductionMarketingLogService productionMarketingLogService;

    /**
     * 厚币销客 APP 产品营销接口
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String marketing() throws Exception {

        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");
        String salemanOuterId = HttpUtils.getParameter(request, "salemanOuterId");

        // 校验参数
        if(StringUtils.isEmpty(productionId) || StringUtils.isEmpty(salemanOuterId) || productionId.length() != 32 || salemanOuterId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 进行营销
        Integer count = productionMarketingLogService.marketing(productionId, salemanOuterId, conn);

        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "操作失败").throwException();
        }

        return SUCCESS;

    }

    /**
     * 厚币销客 APP 获取产品营销统计接口
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String countMarketingTimes() throws Exception {

        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");

        // 校验参数
        if(StringUtils.isEmpty(productionId) || productionId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 获取统计
        Integer count = productionMarketingLogService.countMarketingTimes(productionId);

        // 构造 JSON
        JSONObject json = new JSONObject();
        json.put("productionId", productionId);
        json.put("count", count);

        getResult().setReturnValue(json);

        return SUCCESS;

    }

}
