package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.vo.system.UserVO;
import com.youngbook.service.customer.SalemanOuterService;
import com.youngbook.service.system.TokenService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class SalemanOuterAction extends BaseAction {

    @Autowired
    SalemanOuterService salemanOuterService;


    private UserPO userPO=new UserPO();
    private SalemanOuterService service = new SalemanOuterService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 修改手机号码
     *
     * 作者：付高杨
     * 内容：创建代码
     * 时间：2016年3月31日
     *
     * @throws Exception
     * @return String
     */
    @Security(needMobileCode = true, needToken = true)
    public String updateMobileNumberAPI() throws Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String mobile = HttpUtils.getParameter(request, "mobile");
        String salemanId = HttpUtils.getParameter(request, "salemanId");
        // 获取老的手机号
        String oldMobile = HttpUtils.getParameter(request, "oldMobile");
        // 获取老的手机动态码
        String oldMobileCode = HttpUtils.getParameter(request, "oldMobileCode");
        // 获取token
        String token = HttpUtils.getParameter(request, "token");


        // 校验参数合法性
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(salemanId) || StringUtils.isEmpty(oldMobile) || StringUtils.isEmpty(oldMobileCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 校验新手机号码是否已经被注册
        UserPO po = salemanOuterService.loadSalemanByMobile(mobile, conn);
        if(po != null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_IS_REGISTERED, "当前手机号已被注册，请填写另一个手机号").throwException();
        }

        // 校验原手机和原动态码（以防绕过验证）
        Boolean isOldCodeSuccess = Config.checkMobileCode(oldMobile, oldMobileCode, conn);
        if(!isOldCodeSuccess) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_MOBILECODE_NOT_CORRECT, "原手机动态码校验失败").throwException();
        }

        // 修改手机号码（新手机动态码在拦截器里已经验证）
        int count = salemanOuterService.updateMobile(mobile, salemanId, conn);
        if(count!=1){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_MOBILE_NUMBER_OPERATION_ERROR, "操作失败").throwException();
        }

        TokenService tokenService = new TokenService();
        TokenPO tokenPO = new TokenPO();
        tokenPO.setToken(token);
        tokenPO.setState(Config.STATE_CURRENT);
        tokenService.cancelToken(tokenPO, conn);

        // 返回数据
        getResult().setCode(100);
        getResult().setMessage("操作成功");
        return SUCCESS;
    }

    /**
     * 找回对外销售人员的登录密码
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月1日
     *
     * @return
     * @throws Exception
     */
    @Security(needMobileCode = true)
    public String findLoginPwdAPI() throws Exception {

        String mobile = HttpUtils.getParameter(getRequest(), "mobile");
        String password = HttpUtils.getParameter(getRequest(), "password");
        String rePassword = HttpUtils.getParameter(getRequest(), "rePassword");

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不完整").throwException();
        }

        if(password.length() != 32 || rePassword.length()!=32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将密码加密后进行传输").throwException();
        }

        if(!password.equals(rePassword)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PASSWORDS_NOT_SAME, "两次密码不一致").throwException();
        }

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 检测对外销售人员是否存在
        UserPO outerPO = salemanOuterService.loadSalemanByMobile(mobile, conn);
        if (outerPO == null) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_OUTER_NOT_EXISTENT, "销售人员不存在").throwException();
        }

        Boolean isChangePassword = salemanOuterService.updatePassword(outerPO, password, conn);

        if(!isChangePassword){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_TRANDING_PASSWORD_OPERATION_ERROR, "操作失败").throwException();
        }

        getResult().setCode(100);
        getResult().setMessage("操作成功");

        return SUCCESS;

    }

    /**
     * 获取对外销售人员返佣统计接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月31日
     *
     * @return
     */
    @Security(needToken = true)
    public String getCommissionCountAPI() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取参数
        String salemanId = HttpUtils.getParameter(request, "salemanId");

        if(StringUtils.isEmpty(salemanId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        UserVO vo = salemanOuterService.getCommissionCount(salemanId, conn);
        if(vo == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        getResult().setReturnValue(vo);

        return SUCCESS;

    }

    /**
     * 获取对外销售人员的账户信息
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月24日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String loadMyInformationAPI() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取参数
        String salemanId = HttpUtils.getParameter(request, "salemanId");

        // 校验参数
        if (StringUtils.isEmpty(salemanId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if (salemanId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "参数不合规").throwException();
        }

        UserVO userVO = salemanOuterService.loadMyInformation(salemanId, conn);

        getResult().setReturnValue(userVO);

        return SUCCESS;

    }

    /**
     * JIRA: HOPEWEALTH-1327
     * 修改销客登录密码接口
     * 付高杨 16/03/28
     *
     * @return String
     * @throws Exception
     */
    @Security(needToken = true)
    public String updatePasswordAPI() throws Exception {
        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取参数
        String salemanId = HttpUtils.getParameter(request, "salemanId");
        String oldPwd = HttpUtils.getParameter(request, "oldPwd");
        String newPwd = HttpUtils.getParameter(request, "newPwd");
        String newPwdRepetition = HttpUtils.getParameter(request, "newPwdRepetition");
        String token = HttpUtils.getParameter(request, "token");

        // 校验参数
        if (StringUtils.isEmpty(salemanId) || StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(newPwdRepetition) || StringUtils.isEmpty(token)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if (salemanId.length() != 32 || oldPwd.length() != 32 || newPwd.length() != 32 || newPwdRepetition.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将敏感字段加密传输").throwException();
        }
        if (!newPwd.equals(newPwdRepetition)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PASSWORDS_NOT_SAME, "两次密码不相同").throwException();
        }

        // 校验旧密码是否正确
        UserPO outerPO = salemanOuterService.loadSalemanById(salemanId, conn);
        if (outerPO == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_FAIL, "旧密码不正确").throwException();
        }

        Boolean changedPwd = salemanOuterService.updatePassword(outerPO, newPwd, conn);
        if(!changedPwd) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_OUTER_UPDATE_PWD_FAILED, "密码修改失败").throwException();
        }

        //注销登陆
        TokenService tokenService = new TokenService();
        TokenPO tokenPO = new TokenPO();
        tokenPO.setToken(token);
        tokenPO.setState(Config.STATE_CURRENT);
        tokenPO = MySQLDao.load(tokenPO,TokenPO.class,conn);
        tokenService.cancelToken(tokenPO, conn);

        return SUCCESS;
    }

    /**
     * JIRA: HOPEWEALTH-1313
     * 对外销售人员的 APP 我的佣金列表接口
     * 付高杨 16/03/28
     *
     * @return String
     * @throws Exception
     */
    @Security(needToken = true)
    public String listCommissionsAPI() throws Exception {

        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        // 获取参数
        String salemanId = HttpUtils.getParameter(request, "salemanId");//获取推荐码
        String status = HttpUtils.getParameter(request, "status"); //获取状态

        // 校验参数
        if (StringUtils.isEmpty(salemanId) || StringUtils.isEmpty(status)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        int statusInt = Integer.parseInt(HttpUtils.getParameter(request, "status"));//获取状态

        String statusCheck = OrderStatus.getStatusName(statusInt);
        if (statusCheck.equals("未知状态")) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        OrderPO orderPO = new OrderPO();
        orderPO.setSalemanId(salemanId);
        orderPO.setStatus(statusInt);

        Pager pager = salemanOuterService.listCommissions(orderPO, request, conn);
        this.getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }



    /**
     * 销售 APP 手机注册接口
     *
     * @return String
     */
    public String mobileRegisterAPI() throws Exception {

        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        // 获取参数
        String username = HttpUtils.getParameter(request, "usr");
        String password = HttpUtils.getParameter(request, "pwd");
        // String mobileCode = HttpUtils.getParameter(request, "mobileCode");

        // 校验参数
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)/* || StringUtils.isEmpty(mobileCode)*/) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if (password.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将敏感字段加密传输").throwException();
        }

        // 校验手机动态码
//        CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
//        CustomerAuthenticationCodePO authenticationCodePO = statusService.viladateMobileCode(mobileCode, username, conn);
//        if (authenticationCodePO == null) {
//            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "手机动态码错误").throwException();
//        }

        // 检测手机号码是否已经被占用
        Boolean isRegistered = salemanOuterService.isRegistered(username, conn);
        if (isRegistered) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_IS_REGISTERED, "手机号码已被占用").throwException();
        }

        // 注册外部销售人员
        Integer count = salemanOuterService.register(username, password, conn);
        if (count != 1) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_IS_REGISTERED, "注册失败").throwException();
        }

        return SUCCESS;

    }



    public SalemanOuterService getService() {
        return service;
    }

    public void setService(SalemanOuterService service) {
        this.service = service;
    }

    // 测试API地址
    // 检验登录
    // http://localhost:8080/core/api/customer/SalemanOuter_mobileLoginAPI?usr=test01&pwd=e10adc3949ba59abbe56e057f20f883e
    // 检验注册
    // http://localhost:8080/core/api/customer/SalemanOuter_mobileRegisterAPI?usr=18007550150&pwd=21232f297a57a5a743894a0e4a801fc3
    // 用户
    // test01
    // 123456
    public static void main(String[] args) throws Exception {
        String username = "test01";
        String password = "e10adc3949ba59abbe56e057f20f883e";//123456

        Connection conn = Config.getConnection();

        SalemanOuterService service = new SalemanOuterService();
        service.register(username, password, conn);
    }


    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        return SUCCESS;
    }

    /**
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(userPO, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        userPO = service.loadSalemanOuterPO(userPO.getId());

        getResult().setReturnValue(userPO.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        service.delete(userPO, getLoginUser(), getConnection());

        return SUCCESS;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public SalemanOuterService getSalemanOuterService() {
        return salemanOuterService;
    }

    public void setSalemanOuterService(SalemanOuterService salemanOuterService) {
        this.salemanOuterService = salemanOuterService;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }
}
