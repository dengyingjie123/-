package com.youngbook.action.web;

import com.youngbook.action.BaseAction;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.sale.InvestmentPlanPO;
import com.youngbook.entity.vo.web.AdImageVO;
import com.youngbook.entity.wvo.production.ProductionWVO;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.web.AdImageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-5-2
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 * 修改人：姚章鹏
 * 时间2015年7月13日17:35:48
 * 内容：修改sql语句，实现根据栏目id查询公告，新功能，理财资讯
 * <p/>
 * 修个人：姚章鹏
 * <p/>
 * 为首页的投资期限，格式化为年月日
 */
public class IndexAction extends BaseAction {
    private ProductionWVO productionWVO = new ProductionWVO();
    @Autowired
    ProductionService productionService;

    @Autowired
    AdImageService adImageService;

    // 投资计划
    private InvestmentPlanPO investmentPlanPO = new InvestmentPlanPO();


    /**
     * 显示首页
     * @return
     * @throws Exception
     */
    public String showIndex() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        // 客户种类默认是 0，包括没登录
        String customerType = "0";

        Object loginObject = session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        if (loginObject != null) {
            CustomerPersonalPO personalPO = (CustomerPersonalPO) loginObject;
            // 获取客户种类
            customerType = personalPO.getCustomerTypeId();
        }

        List<ProductionWVO> list  = productionService.list4Website(productionWVO, customerType, conn);

        for (ProductionWVO data :list) {
            ProductionWVO productVO = data;
            String nowTime = TimeUtils.getNowDate();
            String stopTime = productVO.getStopTime();
            long remainSeconds = TimeUtils.getSecondDifference(nowTime, stopTime, TimeUtils.Format_YYYY_MM_DD);
            productVO.setRemainSeconds(remainSeconds);
        }
        request.setAttribute("productionList", list);


        /*
        *修改：周海鸿
        * 时间：2015-7-21
        * 内容：添加获取图片
        * */
        //创建图片service

        // 获取主页大图广告
        List<AdImageVO> indexCenterImg = adImageService.getADImg(AdImageService.indexCenterCode, AdImageService.Yes, conn);

        // 获取左边广告图
        List<AdImageVO> indexRightImg = adImageService.getADImg(AdImageService.indexRightCode, AdImageService.Yes, conn);

        //将数据放到请求作用域
        request.setAttribute("centerImg", indexCenterImg);
        request.setAttribute("rightImg", indexRightImg);

        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月18日14:32:11
     * 内容：根据前台查询条件传过来的值进行查询
     *
     * @return
     * @throws Exception
     */
    public String queryList() throws Exception {
        // 初始化请求
        HttpServletRequest request = getRequest();
        // 初始化数据库连接
        Connection conn = getConnection();
        // 获取最小金额
        String minMoney = request.getParameter("minMoney");
        // 获取最大金额
        String maxMoney = request.getParameter("maxMoney");
        // 获取最小天数
        String minExpiringDay = request.getParameter("minExpiringDay");
        // 获取最大天数
        String maxExpiringDay = request.getParameter("maxExpiringDay");
        // 获取最小年化率
        String minExpectedYield = request.getParameter("minExpectedYield");
        // 获取最大年化率
        String maxExpectedYield = request.getParameter("maxExpectedYield");

        // 调用service查询出产品集合
        List<ProductionWVO> ProductionWVOList = productionService.getProductionList4Index(minMoney, maxMoney, minExpiringDay, maxExpiringDay, minExpectedYield, maxExpectedYield, conn);
        // 如果返回的集合的size为0
        if (ProductionWVOList.size() == 0) {
            // 返回一个0标识给前台
            getResult().setReturnValue("0");
            return SUCCESS;
        }
        // 否则把数据封装成json返回给前台
        else {
            getResult().setReturnValue(ProductionWVOList);
        }
        return SUCCESS;
    }


    public ProductionWVO getProductionWVO() {
        return productionWVO;
    }

    public void setProductionWVO(ProductionWVO productionWVO) {
        this.productionWVO = productionWVO;
    }

    public ProductionService getProductionService() {
        return productionService;
    }

    public InvestmentPlanPO getInvestmentPlanPO() {
        return investmentPlanPO;
    }

    public void setInvestmentPlanPO(InvestmentPlanPO investmentPlanPO) {
        this.investmentPlanPO = investmentPlanPO;
    }

    public void setProductionService(ProductionService productionService) {
        this.productionService = productionService;
    }
}
