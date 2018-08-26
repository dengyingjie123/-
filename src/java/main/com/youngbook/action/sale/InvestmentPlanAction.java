package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.sale.InvestmentParticipantPO;
import com.youngbook.entity.po.sale.InvestmentPlanPO;
import com.youngbook.entity.vo.Sale.InvestmentPlanVO;
import com.youngbook.entity.vo.web.AdImageVO;
import com.youngbook.entity.wvo.sale.InvestmentPlanWVO;
import com.youngbook.service.customer.CustomerMoneyService;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.sale.InvestmentPlanService;
import com.youngbook.service.system.CaptchaService;
import com.youngbook.service.web.AdImageService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple to Introduction
 *
 * @author leevits
 */
public class InvestmentPlanAction extends BaseAction {

    private InvestmentPlanPO investmentPlan = new InvestmentPlanPO();
    InvestmentParticipantPO participant = new InvestmentParticipantPO();
    private InvestmentPlanVO investmentPlanVO = new InvestmentPlanVO();
    private InvestmentPlanWVO investmentPlanWVO = new InvestmentPlanWVO();
    private InvestmentPlanService service = new InvestmentPlanService();


    //用户服务类
    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    AdImageService adImageService;

    //获取数据方法
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, InvestmentPlanVO.class);
        Pager pager = service.list(investmentPlanVO, conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，分页读取所有的投资计划
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
     *
     * 修改人：姚章鹏
     * 时间：2015年7月29日10:03:00
     * 内容：根据时间，给期限 计算出 天数，月
     */
    public String list4Website() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, InvestmentPlanVO.class);
        Pager pager = service.list4Website(investmentPlanWVO, conditions, request);
        /*// 后期处理期限
        for (IJsonable data : pager.getData()) {
            int date = ((InvestmentPlanWVO) data).getInvestTimeMax();
            // 根据天数进行显示判断
            if (date < 30) {
                ((InvestmentPlanWVO) data).setTimeLimit(date + "天");
            }
            if (date >= 365){
                int tempDate =date/365;
                ((InvestmentPlanWVO) data).setTimeLimit(tempDate + "年");
            }
            if (date < 365 && date >= 30){
                int tempDate = date/30;
                ((InvestmentPlanWVO) data).setTimeLimit(tempDate + "个月");
            }
        }*/
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    //加载数据方法
    public String load() throws Exception {
        investmentPlan.setState(Config.STATE_CURRENT);
        investmentPlan = MySQLDao.load(investmentPlan, InvestmentPlanPO.class);
        getResult().setReturnValue(investmentPlan.toJsonObject4Form());
        return SUCCESS;
    }

    public String insertOrUpdate() throws Exception {
        service.insertOrUpdate(investmentPlan, getLoginUser(), getConnection());
        return SUCCESS;
    }

    //删除方法
    public String delete() throws Exception {
        service.delete(investmentPlan, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，根据 Customer 的 ID，查询投资计划
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
    public String loadByCustomerId4Web() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUserId") != null) {
            List<KVObject> conditions = new ArrayList<KVObject>();
            String customerId = session.getAttribute("loginUserId").toString();
            Pager pager = service.loadByCustomerId4Web(investmentPlanWVO, conditions, customerId);
          /*  for (IJsonable data : pager.getData()) {
                int date = ((InvestmentPlanWVO) data).getInvestTimeMax();
                // 根据天数进行显示判断
                if (date < 30) {
                    ((InvestmentPlanWVO) data).setTimeLimit(date + "天");
                }
                if (date >= 365){
                    int tempDate =date/365;
                    ((InvestmentPlanWVO) data).setTimeLimit(tempDate + "年");
                }
                if (date < 365 && date >= 30){
                    int tempDate = date/30;
                    ((InvestmentPlanWVO) data).setTimeLimit(tempDate + "个月");
                }
            }*/
            getResult().setReturnValue(pager.toJsonObject());
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，根据 investmentPlanWVO 的 ID，查询参与记录
     * 用法：在 status-w-sale 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：刘雪冬
     * 内容：创建代码
     * 时间：2015-7-10
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 刘雪冬
     */
    public String investmentQuery4Web() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = service.investmentParticipantList4Web(investmentPlanWVO, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取投资计划的详情和理财资讯
     * 前提是网站的 Customer 已经登录，查询完毕后跳入投资计划详情的页面
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     * <p/>
     * 修改：邓超
     * 内容：新增起息日的获取
     * 时间：2015-6-13
     *
     * @return 适用于交易平台的 json
     * @throws Exception 修改：姚章鹏
     *                   内容：修改sql语句，实现根据栏目id查询公告，新功能，理财资讯
     * @author 邓超
     *
     * 修改人：姚章鹏
     * 内容：计算投资期限，格式年月日
     */
    public String showDetail4W() throws Exception {

        //获取数据库连接

        Connection conn = getConnection ();

        //判断用户是否有进行实名验证与设置交易密码
        Object customerId = getRequest().getSession().getAttribute("loginUserId");

        //用户编号不能为null

        if(customerId  !=  null) {
            //判断当前用户是否有经过实名验证与设置交易密码
            if ( !customerPersonalService.isTransactionPassword (customerId.toString (), conn) ) {
                //没有进行实名验证 跳转到账户用户管理 进行用户认证
                getRequest ().setAttribute ("mark", "password");
                return "customerError";
            }
            if ( !customerPersonalService.isCertificate (customerId.toString (), conn) ) {
                //没有设置交易密码 跳转到账户用户管理 进交易密码认证
                getRequest ().setAttribute ("mark", "");
                return "customerError";
            }
        }

        // 投资计划详情
        InvestmentPlanWVO wvo = service.showDetail4W(investmentPlanWVO,conn);
        if (wvo != null) {

            wvo.setPlanStartTime(wvo.getPlanStartTime().substring(0, 10));
            getRequest().setAttribute("investmentPlanWVO", wvo);
        }
        // 理财资讯
        StringBuffer sbArticleInfo = new StringBuffer();
        sbArticleInfo.append("select * from cms_article where state=0 and ColumnId = (select V from system_kv where K='16226')  LIMIT 5");
        List<ArticlePO> listArticleInfo = MySQLDao.query(sbArticleInfo.toString(), ArticlePO.class, null,conn);
        getRequest().setAttribute("listArticleInfo", listArticleInfo);

 /*
        *修改：周海鸿
        * 时间：2015-7-21
        * 内容：添加获取图片
        * */
        //创建图片service

        //获取投资计划详情告图
        List<AdImageVO> inverstmentPlanDetailImg = adImageService.getADImg(AdImageService.inverstmentPlanDetailCoded, AdImageService.Yes, getConnection());

        getRequest().setAttribute("inverstmentPlanDetailImg", inverstmentPlanDetailImg);

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，参与投资计划
     * 前提是网站的 Customer 已经登录，同时对客户的金额进行检测
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception 修改人：姚章鹏
     *                   时间：2015年7月13日17:39:44
     *                   内容：添加交易密码的验证
     * @author 邓超
     */
    public String joinPlan4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();

        ReturnObject returnObject = new ReturnObject();
        double availableMoney = 0d;
        String payMoneyType = request.getParameter("payMoneyType");
        double joinMoney = 0d;

        String joinMoneyStr = request.getParameter("joinMoney");

        //前台传入密码已经MD5加密
        String businessPwd = request.getParameter("businessPwd");
        //后台拿到密码再次根据规则加密比较
        businessPwd = StringUtils.md5(businessPwd + Config.MD5String);

        String captcha = request.getParameter("captcha");
        String u = request.getParameter("u");
        String investmentId = request.getParameter("investmentId");
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");

        try {
            Connection conn = this.getConnection();

            // 检测验证码是否正确
            CaptchaService captchaService = new CaptchaService();
            if (!captchaService.validateCode(captcha, u, conn)) {
                getResult().setReturnValue("5");
                return SUCCESS;
            }

            // 检测交易密码是否输入
            if (businessPwd == null || businessPwd.equals("")) {
                getResult().setReturnValue("6");
                return SUCCESS;
            }

            // 检测是否登录
            if (session.getAttribute(Config.SESSION_LOGINUSER_STRING) == null) {
                getResult().setMessage("未登录");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 获取客户资金
            CustomerMoneyService moneyService = new CustomerMoneyService();
            CustomerMoneyPO customerMoney = moneyService.getCustomerMoney(loginUser.getId(), conn);

            // 检测是否含有客户资金记录
            if (customerMoney == null) {
                getResult().setMessage("没有找到用户资金记录");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
            if (StringUtils.isEmpty(investmentId)) {
                getResult().setMessage("没有投资计划编号");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
            participant.setInvestmentId(investmentId);
            // 获取可用金额
            availableMoney = customerMoney.getAvailableMoney();
            if (StringUtils.isEmpty(payMoneyType) || payMoneyType.equals("0")) {
                getResult().setMessage("请选择参与方式");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 如果没有选择参与方式返回提示
            if (payMoneyType.equals("0")) {
                getResult().setReturnValue("10");
                return SUCCESS;
            }

            // 如果全部余额参与，即可用金额为参与金额
            if (payMoneyType.equals("1")) {
                if (availableMoney < 1) {
                    getResult().setReturnValue("0");
                }
                participant.setJoinMoney(availableMoney);
            }
            // 如果是确定金额
            if (payMoneyType.equals("2")) {
                // 检测交易金额是否输入
                if (StringUtils.isEmpty(joinMoneyStr)) {
                    getResult().setReturnValue("7");
                    return SUCCESS;
                }
                // 检测可用余额
                if (availableMoney < Double.valueOf(joinMoneyStr)) {
                    getResult().setReturnValue("0");
                    return SUCCESS;
                }
                joinMoney = Double.parseDouble(joinMoneyStr);
                participant.setJoinMoney(joinMoney);
            }

            // 查询用户的交易密码
            CustomerPersonalPO po = customerPersonalService.loadByCustomerPersonalId(loginUser.getId(), conn);
            if (po == null || "".equals(po.getTransactionPassword())) {
                // 前往设置交易密码
                getResult().setReturnValue("1");
                return SUCCESS;
            }

            // 查出来的交易密码与输入的交易密码匹配
            if (!po.getTransactionPassword().equals(businessPwd)) {
                getResult().setReturnValue("2");
                return SUCCESS;
            }

            // 参与计划
            participant.setCustomerId(loginUser.getId());
            this.service.investmentParticipantSave(participant, conn, customerMoney);
            return SUCCESS;
        } catch (Exception e) {
            returnObject.setMessage(e.getMessage());
            getRequest().setAttribute("returnObject", returnObject);
            return "info";
        }
    }

    public InvestmentPlanPO getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(InvestmentPlanPO investmentPlan) {
        this.investmentPlan = investmentPlan;
    }

    public InvestmentParticipantPO getParticipant() {
        return participant;
    }

    public void setParticipant(InvestmentParticipantPO participant) {
        this.participant = participant;
    }

    public InvestmentPlanVO getInvestmentPlanVO() {
        return investmentPlanVO;
    }

    public void setInvestmentPlanVO(InvestmentPlanVO investmentPlanVO) {
        this.investmentPlanVO = investmentPlanVO;
    }

    public InvestmentPlanService getService() {
        return service;
    }

    public void setService(InvestmentPlanService service) {
        this.service = service;
    }

    public InvestmentPlanWVO getInvestmentPlanWVO() {
        return investmentPlanWVO;
    }

    public void setInvestmentPlanWVO(InvestmentPlanWVO investmentPlanWVO) {
        this.investmentPlanWVO = investmentPlanWVO;
    }
}
