package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.info.LegalAgreementPO;
import com.youngbook.entity.po.production.*;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.po.sale.PaymentPlanType;
import com.youngbook.entity.po.sale.PaymentPlanUnit;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.Sale.ProductionCommissionVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.production.ProductionCustomerVO;
import com.youngbook.entity.vo.production.ProductionSaleStatisticsVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.entity.vo.web.AdImageVO;
import com.youngbook.entity.wvo.production.ProductionWVO;
import com.youngbook.service.customer.*;
import com.youngbook.service.info.LegalAgreementService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionInfoService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.production.ProductionCompositionService;
import com.youngbook.service.sale.PaymentPlanService;
import com.youngbook.service.sale.ProductionCommissionService;
import com.youngbook.service.web.AdImageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionAction extends BaseAction {

    @Autowired
    PaymentPlanService paymentPlanService;

    @Autowired
    CustomerCertificateService customerCertificateService;

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    ProductionInfoService productionInfoService;

    @Autowired
    ProductionService productionService;

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerAccountService customerAccountService;

    @Autowired
    ProductionCompositionService productionCompositionService;

    @Autowired
    AdImageService adImageService;

    @Autowired
    LegalAgreementService legalAgreementService;

    @Autowired
    CustomerMoneyService customerMoneyService;

    @Autowired
    ProductionCommissionService productionCommissionService;

    InputStream is = null;

    private ProductionPO production = new ProductionPO();
    private ProductionVO productionVO = new ProductionVO();
    private ProductionWVO productionWVO = new ProductionWVO();
    private PaymentPlanVO paymentPlanVO = new PaymentPlanVO();

    private ProductionCustomerVO productionCustomer = new ProductionCustomerVO();
    private ProductionInfoPO productionInfo = new ProductionInfoPO();
    private LegalAgreementPO legalAgreement = new LegalAgreementPO();



    public String queryProductions4Combotree() throws Exception {

        String productionName = HttpUtils.getParameter(getRequest(), "productionName");
        String productionNo = HttpUtils.getParameter(getRequest(), "productId");

        if (!StringUtils.isEmpty(productionName) && !StringUtils.isEmpty(productionNo)) {

            List<ProductionPO> list = productionService.listProductionPOByProductionNameOrProductionNO(productionName, productionNo, getConnection());

            JSONArray array = new JSONArray();

            for (ProductionPO production : list) {


                JSONObject json = new JSONObject();

                json.put("text", production.getName() + "【"+production.getProductionNo()+"】");
                json.put("id", production.getId());

                array.add(json);
            }

            getResult().setReturnValue(array);
            getResult().setMessage("操作成功");
            getResult().setCode(ReturnObject.CODE_SUCCESS);
        }


        return SUCCESS;
    }


    public String getProductionSaleStatistics() throws Exception {

        production = HttpUtils.getInstanceFromRequest(getRequest(), "production", ProductionPO.class);

        if (StringUtils.isEmpty(production.getId())) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        ProductionSaleStatisticsVO statistics = productionService.getProductionSaleStatistics(production.getId(), getConnection());

        getResult().setReturnValue(statistics.toJsonObject());

        return SUCCESS;
    }

    /**
     * 产品列表接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月6日
     *
     * @return
     * @throws Exception
     */
    public String getProductions() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");

        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);

        // 客户种类默认是 0，包括没登录
        String customerType = "0";

        // 手动获取 Token
        String token = request.getParameter("token");
        if(!StringUtils.isEmpty(token)) {

            try {
                TokenPO tokenPO = Config.checkToken(request, conn);
                if (tokenPO != null) {
                    CustomerPersonalPO personalPO = customerPersonalService.getByToken(tokenPO, conn);
                    // 获取客户种类
                    customerType = personalPO.getCustomerTypeId();
                }
            } catch (Exception e) {
                // TODO 此处不能抛出异常，因为产品列表处如果 Token 错误，当做未登录处理，以展示不用的结果
            }
        }

        Pager pager = productionService.getProductions(customerType, currentPageInt, showRowCountInt, conn);
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }


    /**
     * 销售 APP 产品列表接口
     * url
     * http://localhost:8080/core/api/production/Productioncomposition_getCompositions?incomeType=0&&currentPage=1&&showRowCount=20
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月16日
     *
     * @return
     * @throws Exception
     */
//    @Security(needToken = true)
    public String getProductionsSalesAPI() throws Exception {

        // 获得数据库连接
        Connection conn = this.getConnection();
        // 获得请求对象
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");


        // 转换参数为 int 类型
        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);

        // 产品列表结果
        Pager pager = productionService.getProductions(null, currentPageInt, showRowCountInt, conn);
        List list = pager.getData();
        for (Object o: list) {
            if (o == null) {
                continue;
            }
            ProductionVO vo = (ProductionVO) o;
            double value = productionCompositionService.getMaxCommission(vo.getId(), conn);

            BigDecimal b = new BigDecimal(value);
            value = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            vo.setMaxCommissionRate(value);
        }
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;

    }

    /**
     * 获取产品接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月6日
     *
     * @return
     * @throws Exception
     */
    public String loadProduction() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();
        // 获取数据库连接对象
        Connection conn = getConnection();

        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");
        // 校验参数合法性
        if(StringUtils.isEmpty(productionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(productionId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不合法").throwException();
        }

        // 客户种类默认是 0，包括没登录
        String customerType = "0";

        // 手动获取 Token
        String token = request.getParameter("token");
        if(!StringUtils.isEmpty(token)) {
            TokenPO tokenPO = Config.checkToken(request, conn);

            if (tokenPO != null) {
                CustomerPersonalPO personalPO = customerPersonalService.getByToken(tokenPO, conn);
                // 获取客户种类
                customerType = personalPO.getCustomerTypeId();
            }
        }

        Pager pager = productionService.loadProduction(productionId, customerType, conn);
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，同时获取产品的详情、订单信息、兑付信息和理财资讯
     * 前提是网站的 Customer 已经登录，这里将跳到产品详情页面productionCashDetail.jsp以供使用
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：方斌杰
     * 内容：创建代码
     * 时间：2015-7-26
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 方斌杰
     * <p/>
     * 修改人：姚章鹏
     * 时间：2015年7月31日16:06:49
     * 内容：修改期限按照天数修改为年月日
     */
    @Security(needWebLogin = true)
    public String showDetailCash() throws Exception {
        productionWVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionWVO", ProductionWVO.class);
        Connection conn = getConnection();

        // 查看订单详情不区分是否 VIP 客户，客户列表存在即允许查看订单详情
        String customerType = "1";

        //订单编号
        String orderId = getRequest().getParameter("orderId");
        // 产品详情信息
        ProductionWVO pWVO = productionService.getProductionDetail(productionWVO, customerType, orderId, conn);

        if(pWVO != null){
            getRequest().setAttribute("orderId", orderId);
            getRequest().setAttribute("productionWVO", pWVO);
        }

        return SUCCESS;
    }

    /**
     *
     * 订单详情接口
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015-12-10
     *
     */
    @Security(needToken = true)
    public String paymentPlanOrderDetail() throws Exception{

        Connection conn = getConnection();

        String orderId = getRequest().getParameter("orderId");
        String customerId = getRequest().getParameter("customerId");
        String productionId = getRequest().getParameter("productionId");

        //订单编号
        if(StringUtils.isEmpty(orderId)||StringUtils.isEmpty(customerId)||StringUtils.isEmpty(productionId)){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        //通过订单编号获取订单信息
        OrderPO order = orderService.getOrderById(orderId, conn);
        if(order == null){
            MyException.newInstance(ReturnObjectCode.ORDER_GET_DETIAL_ERROR, "订单数据获取失败").throwException();
        }

        // 产品详情信息
        ProductionPO production = productionService.loadProductionById(order.getProductionId(), conn);
        if(production == null){
            MyException.newInstance(ReturnObjectCode.ORDER_GET_DETIAL_ERROR, "获取失败").throwException();
        }

        //获取客户信息
        CustomerPersonalPO personalPO = customerPersonalService.loadByCustomerPersonalId(customerId, conn);
        if(personalPO == null){
            MyException.newInstance(ReturnObjectCode.ORDER_GET_DETIAL_ERROR, "获取失败").throwException();
        }

        //组装 JSON
        JSONObject returnValue = new JSONObject();
        JSONObject pJson = new JSONObject();
        pJson.put("orderNum",order.getOrderNum());  //订单编号
        pJson.put("referralCode", order.getReferralCode()); // 推荐人
        pJson.put("productionName",production.getWebsiteDisplayName()); //产品名称
        pJson.put("interestType", production.getInterestType());  // 付息方式说明

        String createTime = TimeUtils.format(order.getCreateTime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD);
        pJson.put("createTime",createTime); //下单时间
        pJson.put("money", order.getMoney()); //购买金额

        // 如果是固定收益，才有起息日
        if(!StringUtils.isEmpty(production.getValueDate())) {
            String valueDate = "";
            if (!StringUtils.isEmpty(order.getValueDate())) {
                valueDate = TimeUtils.format(order.getValueDate(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD);
            }
            pJson.put("valueDate", valueDate); //起息日
        }

        pJson.put("status", order.getStatus()); //订单状态
        pJson.put("statusStr", OrderStatus.getStatusName(order.getStatus())); //订单状态
        //计算出每一个订单的投资期限,并且传给微厚币
        returnValue.put("productionDetail",pJson);

        JSONObject cJson = new JSONObject();
        cJson.put("name",personalPO.getName());

        // 如果实名认证了，并成功支付订单，获取身份证（投资人）信息
        CustomerCertificatePO certificatePO = customerCertificateService.loadByCustomerId(customerId, "98", conn);
        if(certificatePO != null) {
            String number = AesEncrypt.decrypt(certificatePO.getNumber());
            number = StringUtils.replaceToStars(number, 4, 4);
            cJson.put("number", number);
        }


        returnValue.put("customerInfo",cJson);

        List<PaymentPlanPO> paymentPlans = paymentPlanService.getPaymentPlansByOrderId(orderId, conn);
        JSONArray paymentPlanJO = new JSONArray();// 兑付计划JO

        for (int i = 0; paymentPlans != null && i < paymentPlans.size(); i++) {
            PaymentPlanPO paymentPlan = paymentPlans.get(i);
            JSONObject item = new JSONObject();
            String paymentTime = "";
            if (!StringUtils.isEmpty(paymentPlan.getPaymentTime())) {
                paymentTime = TimeUtils.format(paymentPlan.getPaymentTime(),TimeUtils.Format_YYYY_MM_DD_HH_M_S,TimeUtils.Format_YYYY_MM_DD);
            }
            //兑付日期
            item.put("paymentTime",paymentTime);
            //应兑付总金额
            item.put("totalPaymentMoney", NumberUtils.format(paymentPlan.getTotalPaymentMoney(), 2));
            //应兑付本金总金额
            item.put("totalPaymentPrincipalMoney", NumberUtils.format(paymentPlan.getTotalPaymentPrincipalMoney(), 2));
            //应兑付收益总金额
            item.put("totalProfitMoney", NumberUtils.format(paymentPlan.getTotalProfitMoney(), 2));
            double totalPaymentPrincipalAndProfitMoney = paymentPlan.getTotalProfitMoney() + paymentPlan.getTotalPaymentPrincipalMoney();
            //应兑付本金和收益总金额
            item.put("totalPaymentPrincipalAndProfitMoney", NumberUtils.format(totalPaymentPrincipalAndProfitMoney, 2));
            item.put("statusName", PaymentPlanStatus.getStatusName(paymentPlan.getStatus()));
            paymentPlanJO.add(i,item);
        }

        returnValue.put("paymentPlanInItems",paymentPlanJO);
        getResult().setMessage("获取成功");
        getResult().setReturnValue(returnValue);

        return SUCCESS;
    }

    /**
     * 作者;周海鸿
     * 时间：2015-8-4
     * 内容：根据开始时间跟结束时间获取投资期限
     * * @param StartTime 开始时间
     *
     * @param stopTime 结束时间
     * @return
     * @throws Exception
     */
    public static String getdataStr(String StartTime, String stopTime) throws Exception {
        int date = TimeUtils.getDayDifference(StartTime, stopTime, "yyyy-MM-dd");
        //判断有日期有几年
        int ye = (date / 365);
        //除去年剩下的月数
        int mo = (date % 365 / 30);
        //判断天数
        int day = (date % 365 % 30);
        StringBuffer daystr = new StringBuffer();
        if (ye > 0) {
            daystr.append(ye + "年 ");
        }
        if (mo > 0) {
            daystr.append(mo + "个月");
        }
        if (day > 0) {
            daystr.append(day + "天");
        }
        return daystr.toString();
    }

    /**
     * 作者：方斌杰
     * 内容：创建代码
     * 时间：2015-7-26
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 方斌杰
     * <p/>
     * 姚章鹏
     * 内容：在service.produFindOrderCustomerId4Web 中添加sql语句，让每个订单都有指定的兑付信息
     * 添加内容： sbSQL.append("AND o.id = '" + Database.encodeSQLReplace(orderId) + "' ");
     */
    public String list4WebOrderQueryCash() throws Exception {
        String customerId = getRequest().getSession().getAttribute("loginUserId").toString();

        //获取订单编号
        String orderId = getRequest().getParameter("orderId");

        Pager pager = productionService.produFindOrderCustomerId4Web(productionWVO.getId(), customerId, orderId);

        // 对数据后期处理
        List<IJsonable> datas = pager.getData();
        for (IJsonable data : datas) {

            int status = ((OrderVO) data).getStatus();

            // 订单状态
            switch (status) {
                case 0:
                    ((OrderVO) data).setStatusName("未支付");
                    break;
                case 1:
                    ((OrderVO) data).setStatusName("已销售");
                    break;
                case 2:
                    ((OrderVO) data).setStatusName("申请退款");
                    break;
                case 3:
                    ((OrderVO) data).setStatusName("已退款");
                    break;
                case 4:
                    ((OrderVO) data).setStatusName("已作废");
                    break;
                case 5:
                    ((OrderVO) data).setStatusName("已兑付");
                    break;
            }
            // 客户类型：个人客户、机构客户
            if (((OrderVO) data).getCustomerAttribute().equals("0")) {
                ((OrderVO) data).setCustomerAttribute("个人客户");
            } else if (((OrderVO) data).getCustomerAttribute().equals("1")) {
                ((OrderVO) data).setCustomerAttribute("机构客户");
            }
        }

        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取兑付计划列表数据
     *
     * @return 返回
     * @throws Exception 修改人：姚章鹏
     *                   时间：2015年9月10日
     *                   内容：保留兑付本金总金额和兑付收益总金额后两位
     */
    @Security(needWebLogin = true)
    public String paymentplanOrder() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        String customerId = getRequest().getSession().getAttribute("loginUserId").toString();
        String orderId = request.getParameter("orderId");
        Pager pagerOrder = productionService.produFindOrderCustomerId4Web(productionWVO.getId(), customerId, orderId);
        if (pagerOrder.getData().size() > 0) {
            OrderVO or = (OrderVO) pagerOrder.getData().get(0);
        }

        //根据条件查询数据
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, PaymentPlanVO.class);
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "sid,currentInstallment " + Database.ORDERBY_DESC));
        //将数据封装起来 到一个分页的类里面
        if (!StringUtils.isEmpty(orderId)) {
            Pager pager = paymentPlanService.paymentplanOrderList(paymentPlanVO, conditions, orderId);
            //将数据以JSON的方式返回到脚本哪里
            getResult().setReturnValue(pager.toJsonObject());
        } else {
            getResult().setReturnValue(new Pager());
        }
        return SUCCESS;
    }


    /**
     * 作者：刘雪冬
     * 内容：创建代码
     * 时间：2015-7-9
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 刘雪冬
     */
    public String list4WebOrderQuery() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = productionService.produFindOrder4Web(productionWVO, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取符合产品构成的投资金额的产品（投资计划）
     * 前提是网站的 Customer 已经登录，产品构成有多条，取符合的一条
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     * <p/>
     * 修改人：张舜清
     * 时间：7/9/2015
     * 内容：计算出期限返回web页面
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     * <p/>
     * * 修改人：姚章鹏
     * 时间：2015年7月31日15:51:20
     * 内容：修改sql语句。添加P.WebsiteDisplayName,查出网站显示的名称
     * <p/>
     * * 修改人：姚章鹏
     * 时间：2015年8月24日17:03:52
     * 内容： 添加用户法律协议的查询协议
     */
    @Security(needWebLogin = true)
    public String goPay4W() throws Exception {
        // 执行操作
        String customerId = getRequest().getSession().getAttribute("loginUserId").toString();
        //获取数据库连接

        Connection conn = getConnection();

        //判断用户是否有进行实名验证与设置交易密码

        //用户编号不能为null

        if (!customerPersonalService.isCertificate(customerId, conn)) {
            // 没有进行实名验证 跳转到账户用户管理 进行用户认证
            getRequest().setAttribute("mark", "");
            return "customerError";
        }

        if (!customerPersonalService.isTransactionPassword(customerId, conn)) {
            // 没有设置交易密码 跳转到账户用户管理 进交易密码认证
            getRequest().setAttribute("mark", "password");
            return "customerErrorPassword";
        }

        Integer cardCount = customerAccountService.getBankCardCount(customerId, conn);
        if (CustomerAccountStatus.NO_CARD == cardCount || CustomerAccountStatus.UNKNOWN == cardCount) {
            return "customerBankCardError";
        }

        String investMeneyStr = getRequest().getParameter("investMeney");
        if (StringUtils.isEmpty(investMeneyStr)) {
            //请输入投资金额
            getResult().setReturnValue("2");
            return SUCCESS;
        }

        String productionWVOId = getRequest().getParameter("productionWVOId");
        if (StringUtils.isEmpty(productionWVOId)) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "未找到对应的产品信息").throwException();
        }


        //查询法律协议
        LegalAgreementPO legalAgreement = legalAgreementService.getAgreementById(Config.getSystemVariable("web.investment.lawId"), conn);
        getRequest().setAttribute("legalAgreement", legalAgreement);


        //获取客户资金
        CustomerMoneyPO customerMoney = customerMoneyService.getCustomerMoney(customerId, getConnection());
        //判断
        if (customerMoney == null) {
            //没有找到用户资金记录
            getResult().setMessage("没有找到用户资金记录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }


        double investMeney = Double.valueOf(investMeneyStr);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     p.id,");
        sbSQL.append("     p.websiteDisplayName,");
        sbSQL.append("     p.`Name`,");
        sbSQL.append("     p.ValueDate,");
        sbSQL.append("     p.InterestDate,");
        sbSQL.append("     p.interestType,");
        sbSQL.append("     p.InvestTerm,");
        sbSQL.append("     p.InvestTermView,");
        sbSQL.append("     pc.ExpectedYield,");
        sbSQL.append("     pc.id compositionId,");
        sbSQL.append("     pc.SizeStart");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_production p,");
        sbSQL.append("     crm_productioncomposition pc");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND p.state = 0");
        sbSQL.append(" AND pc.state = 0");
        sbSQL.append(" and p.id=pc.ProductionId");
        sbSQL.append(" and p.id='" + Database.encodeSQL(productionWVOId) + "'");
        sbSQL.append(" and pc.SizeStart <= " + investMeney);
        sbSQL.append(" and pc.SizeStop >= " + investMeney);
        sbSQL.append(" AND p.StopTime >= '"+TimeUtils.getNowDate()+"'");
        sbSQL.append(" AND p.StartTime <= '"+TimeUtils.getNowDate()+"'");


        List<ProductionWVO> list = MySQLDao.query(sbSQL.toString(), ProductionWVO.class, null);

        if (list == null || list.size() != 1) {
            String message = Config.getLanguageVariable("w.pay.production.stopOrMoneyError");
            MyException.newInstance(message).throwException();
        }


        // 获得客户基本信息
        CustomerPersonalPO customer = customerPersonalService.loadByCustomerPersonalId(customerId, conn);

        // 获得客户证件信息
        String customerCertificateName = Config.getSystemVariable("web.customer.certificate.idcard.kv.v");
        CustomerCertificatePO customerCertificate = customerCertificateService.loadByCustomerId(customerId, customerCertificateName, conn);
        // 解密证件号
        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificate.getNumber());
        customerCertificate.setNumber(customerCertificateNumber);


        if (list.size() == 1) {
            productionWVO = list.get(0);
            getRequest().setAttribute("productionWVO", productionWVO);
            getRequest().setAttribute("investMoney", investMeney);
            getRequest().setAttribute("customer", customer);
            getRequest().setAttribute("customerCertificate", customerCertificate);

            return SUCCESS;
        }

        return SUCCESS;
    }

    /**
     * 添加或修改
     * <p/>
     * <p/>
     * 修改：leevits
     * 时间：2015年7月27日 22:06:04
     * 内容：
     * 封装短信发送方法
     * 修改业务逻辑
     *
     * @return
     * @throws Exception
     */
    @com.youngbook.annotation.Permission(require = "产品管理-产品分期管理-新增")
    public String insertOrUpdate() throws Exception {

        production = HttpUtils.getInstanceFromRequest(getRequest(), "production", ProductionPO.class);
        Connection conn = getConnection();




        ProductionPO po = productionService.insertOrUpdate(production, getLoginUser().getId(), conn);




        return SUCCESS;
    }

    /**
     * @description 修改产品，当产品分期为草稿状态(status == 0)才允许修改
     *
     * @author 苟熙霖
     *
     * @date 2018/12/3 9:15
     * @param
     * @return com.youngbook.entity.po.production.ProductionPO
     * @throws Exception
     */
    public String editProduction() throws Exception {

        production = HttpUtils.getInstanceFromRequest(getRequest(), "production", ProductionPO.class);
        Connection conn = getConnection();


        /**
         * 状态为草稿(status == 0) 的产品才能进行修改操作
         */
        int status = production.getStatus();
        if(status != 0){
            MyException.newInstance("当前状态产品分期无法修改，请改为草稿后执行修改操作");
        }




        ProductionPO po = productionService.insertOrUpdate(production, getLoginUser().getId(), conn);




        return SUCCESS;
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @com.youngbook.annotation.Permission(require = "产品管理-产品分期管理-删除")
    public String del() throws Exception {

        production = HttpUtils.getInstanceFromRequest(getRequest(), "production", ProductionPO.class);

        int sta = productionService.del(production, getLoginUser(), getConnection());
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        json.element("sta", sta);
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        productionService.delete(production, productionInfo, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 读取
     *
     * @return
     * @throws Exception
     */
    @com.youngbook.annotation.Permission(require = "产品管理-产品分期管理-查看")
    public String load() throws Exception {

        String productionId = getHttpRequestParameter("production.id");

        production.setId(productionId);
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production, ProductionPO.class);

        if (production == null) {
            getResult().setCode(ReturnObject.CODE_EXCEPTION);
            getResult().setMessage("获取产品失败");
        }
        else {
            getResult().setReturnValue(production.toJsonObject4Form());
        }


        return SUCCESS;
    }

    /**
     * 列出数据
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        productionVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionVO", ProductionVO.class);

        Pager pager = Pager.getInstance(getRequest());
        pager = productionService.getProductions(productionVO, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String listProductionVO4modern() throws Exception {
        List<ProductionVO> listProductionVOs = productionService.getListProductionVO(null, getConnection());
        getResult().setReturnValue(listProductionVOs);

        return SUCCESS;
    }

    /**
     * 根据产品类型构建产品清单，用于移动端
     * @return
     * @throws Exception
     */
    public String listProductionVO4modernGroupByIncomeType() throws Exception {
        List<ProductionVO> listProductionVOs = productionService.getListProductionVO(null, getConnection());

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_kv where groupname='Project_Type' ORDER BY Orders");
        List<KVPO> projectTypes = MySQLDao.search(dbSQL, KVPO.class, getConnection());

        JSONObject json = new JSONObject();


        for (int i = 0; projectTypes != null && i < projectTypes.size(); i++) {

            KVPO type = projectTypes.get(i);

            List<ProductionVO> productionVOs = new ArrayList<ProductionVO>();

            for (int j = 0; listProductionVOs != null && j < listProductionVOs.size(); j++) {
                ProductionVO productionVO = listProductionVOs.get(j);

                if (productionVO.getDisplayType() != null && productionVO.getDisplayType().equals(ProductionDisplayType.FinanceCircle)) {
                    if (productionVO.getProjectType().equals(type.getK())) {
                        productionVOs.add(productionVO);
                    }
                }
            }

            json.put("productions" + (i + 1), productionVOs);
            json.put("type" + (i + 1), type);

        }

        getResult().setReturnValue(json);

        return SUCCESS;
    }


    /**
     * 获取客户
     *
     * @return
     * @throws Exception
     */
    public String listCustomer() throws Exception {
        String productId = getRequest().getParameter("productId");

        if (StringUtils.isEmpty(productId)) {
            MyException.newInstance("传入产品编号为空").throwException();
        }

        Pager pager = Pager.getInstance(getRequest());
        pager = productionService.listCustomer(productId, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());


        for (IJsonable data : pager.getData()) {
            ProductionCustomerVO productionCustomer = (ProductionCustomerVO)data;
            int status = productionCustomer.getOrderStatus();
            String statusName = OrderStatus.getStatusName(status);
            productionCustomer.setOrderStatusName(statusName);
        }

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，通过 id 查找 production
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String getProductById4Web() throws Exception {
        String id = getRequest().getParameter("p");
        String money = getRequest().getParameter("m");
        Pager pager = productionService.getProductById4Web(id, money, productionWVO, getRequest());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String getProductionById() throws Exception {

        String productionId = HttpUtils.getParameter(getRequest(), "productionId");

        ProductionWVO production = productionService.getProductionWVOById(productionId, getConnection());

        getResult().setReturnValue(production);

        return SUCCESS;
    }

    public String getProductionWVOById() throws Exception {

        String productionId = HttpUtils.getParameter(getRequest(), "productionId");

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        List<ProductionWVO>  listProductionWVO = productionService.getListProductionWVOById(productionId, getConnection());
        ProductionWVO productionWVO = new ProductionWVO();

        StringBuffer sbExpectedYield = new StringBuffer();

        double sizeStart = Double.MAX_VALUE;

        for (int i = 0; listProductionWVO != null && i < listProductionWVO.size(); i++) {
            productionWVO = listProductionWVO.get(i);
            sbExpectedYield.append(productionWVO.getExpectedYield()).append("%，");

            if (productionWVO.getSizeStart() < sizeStart) {
                sizeStart = productionWVO.getSizeStart();
            }

        }

        productionWVO.setSizeStart(sizeStart);

        sbExpectedYield = StringUtils.removeLastLetters(sbExpectedYield, "，");
        if (sbExpectedYield.toString().equals("0.0%")) {
            sbExpectedYield = new StringBuffer("浮动");
        }

        productionWVO.setExpectedYieldDescription(sbExpectedYield.toString());


        List<ProductionCommissionVO> productionCommissionVOs = productionCommissionService.getProductionCommissionVOByProctionId(productionId, getConnection());


        /**
         * 获得产品描述文章
         */
        List<ArticlePO> productionArticles = productionService.getListProductionArticle(productionId, getConnection());

        JSONObject json = new JSONObject();
        json.put("productionCommissionVOs", productionCommissionVOs);
        json.put("production", productionWVO);
        json.put("productionArticles", productionArticles);

        getResult().setReturnValue(json);

        return SUCCESS;
    }

    ;

    /**
     * 交易平台请求的 Action，获取产品的详情
     * 前提是网站的 Customer 已经登录，在这里对查询出来的时间进行后期处理
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String getProductDetail4Web() throws Exception {

        productionWVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionWVO", ProductionWVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        String id = getRequest().getParameter("id");
        Pager pager = productionService.getProductDetail4Web(id, productionWVO, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，同时获取产品的详情和理财资讯
     * 前提是网站的 Customer 已经登录，这里将跳到产品详情页面以供使用
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     * <p/>
     * 修改人：姚章鹏
     * 内容：计算期限
     * 时间：2015年7月31日15:44:59
     */
    @Security(needWebLogin = true)
    public String showDetail4W() throws Exception {

        productionWVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionWVO", ProductionWVO.class);

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();

        //获取数据库连接
        Connection conn = getConnection();

        // 客户种类默认是 0，包括没登录
        String customerType = "0";

        Object loginObject = session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        if (loginObject != null) {
            CustomerPersonalPO personalPO = (CustomerPersonalPO) loginObject;
            // 获取客户种类
            customerType = personalPO.getCustomerTypeId();
        }

        // 产品详情信息
        List<ProductionWVO> list = productionService.list4Website(productionWVO, customerType, conn);

        if (list == null ||  list.size() == 0) {
            MyException.newInstance("产品数据获取失败").throwException();
        }

        if (list.size() > 0) {   //修改为>0 目前数据存在重复数据 HOPEWEALTH-491
            productionWVO = (ProductionWVO) list.get(0);
            //当前时间
            String now = TimeUtils.getNow("yyyy-mm-dd");
            long remainSeconds = TimeUtils.getSecondDifference(TimeUtils.getNowDate(), productionWVO.getStopTime(), TimeUtils.Format_YYYY_MM_DD);

            productionWVO.setStopTimeDay("已结束");
            if (remainSeconds > 0) {
                productionWVO.setStopTimeDay((remainSeconds / TimeUtils.getSecondsOfOneDay()) + "天");
            }
            ProductionInfoPO productionInfo = productionInfoService.loadByProductionId(productionWVO.getId(), conn);
            getRequest().setAttribute("productionInfo", productionInfo);
            getRequest().setAttribute("productionWVO", productionWVO);
        }

        //创建图片service

        //产品支付页面
        List<AdImageVO> ProductionDetailImg = adImageService.getADImg(AdImageService.ProdutionDetailCoded, AdImageService.Yes, conn);
        //计算结束时间与当前系统时间的差值

        getRequest().setAttribute("ProductionDetailImg", ProductionDetailImg);
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，列出投资列表
     * 前提是网站的 Customer 已经登录，这里对期限进行后期处理
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String list4W() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = getRequest();
        String projectNames = getRequest().getParameter("ProjectNames");
        if (projectNames == null) {
            projectNames = "";
        }

        // 客户种类默认是 0，包括没登录
        String customerType = "0";

        // 手动获取 Token
        String token = request.getParameter("token");
        if(!StringUtils.isEmpty(token)) {
            TokenPO tokenPO = Config.checkToken(request, conn);

            if (tokenPO != null) {
                CustomerPersonalPO personalPO = customerPersonalService.getByToken(tokenPO, conn);
                // 获取客户种类
                customerType = personalPO.getCustomerTypeId();
            }
        }

        List<ProductionWVO> list = productionService.list4Website(productionWVO, customerType, conn);
        for (ProductionWVO data :list) {
            String valueDate = ((ProductionWVO) data).getValueDate();
            String interestDate = ((ProductionWVO) data).getInterestDate();
            ((ProductionWVO) data).setTimeLimit(TimeUtils.getMonthDifference(valueDate, interestDate, "yyyy-MM-dd") + "个月");
        }
        request.setAttribute("pager", list);
        return SUCCESS;
    }


    /**
     * 创建：张舜清
     * 时间：2015年8月31日10:37:16
     * 内容：第一步验证了是否登录状态，第二步验证订单参数是否正常，最后查询出对应产品和构成还有法律协议
     *
     * @return
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String goToPay() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = this.getConnection();

        String orderId = request.getParameter("orderId");

        if (StringUtils.isEmpty(orderId)) {
            MyException.newInstance("订单参数异常").throwException();
        }


        OrderPO orderPO = new OrderPO();
        orderPO.setId(orderId);
        orderPO.setState(Config.STATE_CURRENT);
        orderPO.setStatus(OrderStatus.Appointment);
        orderPO = MySQLDao.load(orderPO, OrderPO.class, conn);
        if (orderPO == null) {
            MyException.newInstance("订单获取异常").throwException();
        }


        // 判断产品募集期是否结束，投资金额是否满足要求
        boolean checkIsProduction4Invest = productionService.checkIsOk(orderPO.getProductionId(),  orderPO.getMoney());
        if (!checkIsProduction4Invest) {
            String message = Config.getLanguageVariable("w.pay.production.stopOrMoneyError");
            MyException.newInstance(message).throwException();
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     p.id productId,");
        sbSQL.append("     p.websiteDisplayName,");
        sbSQL.append("     p.`Name`,");
        sbSQL.append("     p.ValueDate,");
        sbSQL.append("     p.InterestDate,");
        sbSQL.append("     p.interestType,");
        sbSQL.append("     p.InvestTerm,");
        sbSQL.append("     p.InvestTermView,");
        sbSQL.append("     pc.ExpectedYield,");
        sbSQL.append("     pc.id compositionId,");
        sbSQL.append("     pc.SizeStart");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_production p,");
        sbSQL.append("     crm_productioncomposition pc");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND p.state = 0");
        sbSQL.append(" AND pc.state = 0");
        sbSQL.append(" and p.id=pc.ProductionId");
        sbSQL.append(" and p.id=?");
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, orderPO.getProductionId(), parameters);
        List<ProductionWVO> list = MySQLDao.search(sbSQL.toString(), parameters, ProductionWVO.class, null, conn);

        getRequest().setAttribute("productionWVO", list.get(0));
        getRequest().setAttribute("investMoney", orderPO.getMoney());
        //返回订单
        getRequest().setAttribute("order", orderPO);
        //查询法律协议
        LegalAgreementService agreementService = new LegalAgreementService();
        LegalAgreementPO legalAgreement = agreementService.getAgreementById(Config.getSystemVariable("web.investment.lawId"), conn);
        getRequest().setAttribute("legalAgreement", legalAgreement);

        // 查询客户编号
        CustomerPersonalPO customer = customerPersonalService.loadByCustomerPersonalId(orderPO.getCustomerId(), conn);
        if (customer == null) {
            MyException.newInstance("无法获得订单所属客户").throwException();
        }
        getRequest().setAttribute("customer", customer);


        // 获得客户证件信息
        String customerCertificateName = Config.getSystemVariable("web.customer.certificate.idcard.kv.v");
        CustomerCertificatePO customerCertificate = customerCertificateService.loadByCustomerId(orderPO.getCustomerId(), customerCertificateName, conn);
        // 解密证件号
        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificate.getNumber());
        customerCertificate.setNumber(customerCertificateNumber);
        getRequest().setAttribute("customerCertificate", customerCertificate);

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，支持多条件查询地获取产品列表
     * 前提是网站的 Customer 已经登录，条件包括期限、起投金额等，并对响应的期限进行后期处理
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     * <p/>
     * *修改人：姚章鹏
     * 内容：计算期限
     * 时间：2015年7月31日15:44:59
     */

    public String list4WebMuitiQuery() throws Exception {

        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();

        productionWVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionWVO", ProductionWVO.class);

        // 客户种类默认是 0，包括没登录
        String customerType = "0";

        Object loginObject = session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        if (loginObject != null) {
            CustomerPersonalPO personalPO = (CustomerPersonalPO) loginObject;
            // 获取客户种类
            customerType = personalPO.getCustomerTypeId();
        }

        Pager pager = productionService.list4WebMuitiQuery(productionWVO, customerType, conditions, request, conn);

        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取 ProductId
     *
     * @return
     * @throws Exception
     */
    public String getProductId() throws Exception {
        String productionId = getRequest().getParameter("id");
        production.setId(productionId);
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production, ProductionPO.class);
        getResult().setReturnValue(production.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 周海鸿
     * 2015-8-31
     * 根据产品编号获取产品验证信息
     *
     * @return
     * @throws Exception
     */
    public String getProdectYW() throws Exception {

        production = HttpUtils.getInstanceFromRequest(getRequest(), "production", ProductionPO.class);

        //判断id是否为null
        if (StringUtils.isEmpty(production.getId())) {
            MyException.deal(new Exception("获取产品审批数据失败！"));
        }

        //获取验证信息
        ProductionVO productionVO = productionService.getProdectYW(production.getId(), getLoginUser(), getConnection());

        //返回产品
        getResult().setReturnValue(productionVO);
        return SUCCESS;
    }

    /**
     * 状态树形节点
     *
     * @return
     */
    public String getStatusTree() {
        getResult().setReturnValue(ProductionStatus.getStrutsJSONArray());
        return SUCCESS;
    }

    /**
     * 付息类型状态
     *
     * @return
     */
    public String interestTypeTree() {
        getResult().setReturnValue(PaymentPlanType.getStrutsJSONArray());
        return SUCCESS;
    }

    /**
     * 付息单位
     *
     * @return
     */
    public String interestUnitTree() {
        getResult().setReturnValue(PaymentPlanUnit.getStrutsJSONArray());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年10月15日16:36:10
     * 内容：查询出对应法律协议
     * todo 不知道为什么IDE生成的get和set方法无法生成get方法，如果还是尝试不行就通过request拿字段值
     *
     * @return
     */
    public String getLegalAgreement1() throws Exception {
        return SUCCESS;
    }


    /**
     * Created by Jevons on 2015/10/27.
     * 1.将处理好的的word模板，另存为xml格式的文档。并最后添加参数：  ${export}(结束标记)
     * 2.将xml文档修改完成后，保存并修改后缀名为“*.ftl”;
     * 3.替换ftl文件中预先设置好的变更${XXX}
     * 4.编写方法实现赋值和处理流
     */
    public String getDemoFinancePlanContract4W() throws Exception {

        HttpServletResponse response = getResponse();
        HttpServletRequest request = getRequest();

        try {
            Map dataMap = new HashMap();
            if (getData(request, dataMap)) {
                File previewFile = new File(request.getSession().getServletContext().getRealPath(WordUtil.PREVIEW_DOC));
                InputStream is = new FileInputStream(previewFile);
                response.reset();
                response.setContentType("application/vnd.ms-word;charset=UTF-8");
                response.addHeader("Content-Disposition", "attachment; filename=\"" + WordUtil.PREVIEW_DOC + "\"");
                byte[] b = new byte[1024];
                int len;
                while ((len = is.read(b)) > 0) {
                    response.getOutputStream().write(b, 0, len);
                }
                is.close();
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建人：方斌杰
     * 时间：2015年10月27日14:54:10
     * 内容：填充word模板需要替换的数据
     *
     * @return
     * @throws Exception
     */
    public boolean getData(HttpServletRequest request, Map dataMap) throws Exception {

        Connection conn = getConnection();

        String productId = getRequest().getParameter("productId");
        String orderId = getRequest().getParameter("orderId");

        List<ProductionAgreementPO> listPro = new ArrayList<ProductionAgreementPO>();

        List<PaymentPlanPO> listPay = new ArrayList<PaymentPlanPO>();

        if (!StringUtils.isEmpty(orderId)) {
            listPro = productionService.getProductionAgreement(orderId, productId, conn);

            if (listPro.size() != 0) {
                ProductionAgreementPO pro = listPro.get(0);

                //甲方（受让方）
                dataMap.put("user_name", pro.getCustomer_name());
                //证件号码
                dataMap.put("ID_number", AesEncrypt.decrypt(pro.getId_number()));
                //产品名称
                dataMap.put("production_name", pro.getProduction_name());
                //所属项目
                dataMap.put("project_Name", "定期债券类");
                //客户名称
                dataMap.put("customer_name", pro.getCustomer_name());
                //客户属性
                dataMap.put("customer_Attribute", pro.getCustomer_Attribute());
                //订单号
                dataMap.put("order_Num", pro.getOrder_Num());
                //投资金额
                dataMap.put("invest_Money", pro.getInvest_Money());
                //投资时间
                dataMap.put("invest_Date", pro.getInvest_Date());
                //起息日
                dataMap.put("value_Date", pro.getValue_Date());
                //预期年化收益
                dataMap.put("expectedYiel", pro.getExpectedYiel());
            }

            // 打印兑付计划
//            if (listPay.size() != 0) {
//                PaymentPlanPO pay = listPay.get(0);
//
//                //兑付类型
//                dataMap.put("paymentPlan_type", pay.getType());
//                //总期数
//                dataMap.put("total_Installment", pay.getTotalInstallment());
//                //总利息
//                dataMap.put("Total_Interest", pay.getTotalPaymentMoney());
//                //总收益
//                dataMap.put("Total_revenue", pay.getTotalProfitMoney());
//
//                dataMap.put("paymentTime", pay.getPaymentTime());
//
//            }
        }

        WordUtil.toPreview(request, WordUtil.WORD_TEMPLATE, dataMap);

        return true;
    }

    /**
     * 创建人：姚章鹏
     * 1.将获取的协议数据放入处理好的pdf表格中，在生成pdf格式协议。
     * 2.编写方法实现赋值和处理流
     */
    public String getDemoFinancePlanContractByPDF4W() throws Exception {

        HttpServletResponse response = getResponse();
        HttpServletRequest request = getRequest();
        try {
            if (getDataByPDF(request)) {
                File previewFile = new File(request.getSession().getServletContext().getRealPath(WordUtil.PREVIEW_PDF));
                is = new FileInputStream(previewFile);
                response.reset();
                response.setContentType("application/vnd.ms-word;charset=UTF-8");
                // String fileName = URLEncoder.encode(WordUtil.PREVIEW_PDF, "UTF-8");
                String fileName = Config.getSystemConfig("w.pay.agreement.filename");
                fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");


                response.addHeader("Content-Disposition", "attachment; filename="+fileName+ "");
                byte[] b = new byte[1024];
                int len;
                while ((len = is.read(b)) > 0) {
                    response.getOutputStream().write(b, 0, len);
                }
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

   /**
    * 创建人：姚章鹏
    * 时间：2015年11月4日16:15:22
    * 内容：根据获取的产品编号和订单编号查出数据，并转换成pdf
    *
    * @return
     * @throws Exception
    */
    public boolean getDataByPDF(HttpServletRequest request) throws Exception {

        Connection conn = getConnection();
        try {
            String productId = getRequest().getParameter("productId");
            String orderId = getRequest().getParameter("orderId");

            List<ProductionAgreementPO> listPro = new ArrayList<ProductionAgreementPO>();

            List<PaymentPlanPO> paymentPlans = new ArrayList<PaymentPlanPO>();

            ProductionAgreementPO pro = null;
            if(!StringUtils.isEmpty(orderId)) {
                listPro = productionService.getProductionAgreement(orderId,productId, conn);
                paymentPlans = productionService.getPaymentPlan(orderId,productId, conn);

                if(listPro.size()!=0) {
                    pro = listPro.get(0);
                    pro.setNumber(AesEncrypt.decrypt(pro.getNumber()));
                    String date = pro.getCreateTime();
                    final String year = date.substring(0, 4);
                    final String month = date.substring(5, 7);
                    final String day = date.substring(8, 10);
                    pro.setDateTime(year+"年"+month+"月"+day+"日");
                }
            }
            //获取模板路径
            String TempPath = request.getSession().getServletContext().getRealPath("") +WordUtil.PREVIEW_PDF;
            String tempTxTModePath = request.getSession().getServletContext().getRealPath(WordUtil.TEMPLATE_PATH+WordUtil.PREVIEW_PDFMODE);
            //公达的印章图片
            String ImgModePath = request.getSession().getServletContext().getRealPath(WordUtil.TEMP_IMGMODE);
            String hopewcoreImgPath = request.getSession().getServletContext().getRealPath(WordUtil.TEMP_HOPECOREMODE);

            WordUtil.toPDF_Mode(pro, paymentPlans,TempPath,tempTxTModePath,ImgModePath,hopewcoreImgPath);
        }
        catch (Exception e) {
            throw e;
        }
        return true;
    }
    /**
     * 投资记录
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月9日
     *
     */
    public String getInvestmentLog() throws Exception {

        // 获取请求
        HttpServletRequest request = ServletActionContext.getRequest();

        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");

        // 校验参数合法性
        if(StringUtils.isEmpty(productionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        //创建kv对象
        List<KVObject> conditions = new ArrayList<KVObject>();

        Pager pager = productionService.getOrderByProductionId(productionId, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 用法：productionId是产品编号，根据optionId取产品介绍内容， 0代表取产品介绍1，1代表取产品介绍2，以此推类。
     * 获取产品介绍
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月11日
     *
     */
    public String  getProductionArticle() throws  Exception{
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");
        String optionId = HttpUtils.getParameter(request, "optionId");
        // 校验参数合法性
        if(StringUtils.isEmpty(productionId)||StringUtils.isEmpty(optionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        //校验optionId
        int id=0;
        try {
            id = Integer.parseInt(optionId);
        }catch (Exception ex){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, " 参数不正确").throwException();
        }
        //执行service
        JSONObject jsonObject = productionInfoService.getProductionArticle(productionId, id, conn);
        if(jsonObject==null||jsonObject.size()==0){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, " 参数不正确").throwException();
        }
        getResult().setReturnValue(jsonObject);
        getResult().setCode(100);
        getResult().setMessage("操作成功");
        return SUCCESS;
    }




    public ProductionPO getProduction() {
        return production;
    }

    public void setProduction(ProductionPO production) {
        this.production = production;
    }

    public ProductionVO getProductionVO() {
        return productionVO;
    }

    public void setProductionVO(ProductionVO productionVO) {
        this.productionVO = productionVO;
    }

    public ProductionCustomerVO getProductionCustomer() {
        return productionCustomer;
    }

    public void setProductionCustomer(ProductionCustomerVO productionCustomer) {
        this.productionCustomer = productionCustomer;
    }

    public ProductionInfoPO getProductionInfo() {
        return productionInfo;
    }

    public void setProductionInfo(ProductionInfoPO productionInfo) {
        this.productionInfo = productionInfo;
    }

    public ProductionWVO getProductionWVO() {
        return productionWVO;
    }

    public void setProductionWVO(ProductionWVO productionWVO) {
        this.productionWVO = productionWVO;
    }


    public PaymentPlanVO getPaymentPlanVO() {
        return paymentPlanVO;
    }

    public void setPaymentPlanVO(PaymentPlanVO paymentPlanVO) {
        this.paymentPlanVO = paymentPlanVO;
    }

    public PaymentPlanService getPaymentPlanService() {
        return paymentPlanService;
    }

    public void setPaymentPlanService(PaymentPlanService paymentPlanService) {
        this.paymentPlanService = paymentPlanService;
    }

    public LegalAgreementPO getLegalAgreement() {
        return legalAgreement;
    }

    public void setLegalAgreement(LegalAgreementPO legalAgreement) {
        this.legalAgreement = legalAgreement;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
