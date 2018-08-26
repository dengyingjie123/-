package com.youngbook.action.sale.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Pager;
import com.youngbook.service.sale.report.DailySalesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailySalesAction extends BaseAction {

    // 每日销售额服务
    @Autowired
    DailySalesService dailySalesService;

    // 简易时间格式化者
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取数据
     *
     * 1、如果开始时间或结束时间为空，都默认使用今天
     * 2、通过请求对象，返回 JSON 到前台
     *
     * 作者：邓超
     * 内容：创建方法
     * 时间：2015-8-30
     *
     * @return String
     * @throws Exception
     */
    public String list() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if(beginDate == null || "".equals(beginDate)) {
            beginDate = this.formatter.format(new Date());
        }
        if(endDate == null || "".equals(endDate)) {
            endDate = this.formatter.format(new Date());
        }

        // 获取数据
        Pager pager = dailySalesService.list(beginDate + " 00:00:00", endDate + " 23:59:59", request);

        // 返回数据
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;

    }

}
