package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerMoneyLogPO;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.vo.customer.CustomerMoneyLogVO;
import com.youngbook.service.customer.CustomerMoneyLogService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2015/4/28.
 */
public class CustomerMoneyLogAction extends BaseAction{

    private CustomerMoneyLogVO customerMoneyLogVO = new CustomerMoneyLogVO();
    private CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    @Autowired
    CustomerMoneyLogService customerMoneyLogService;

    /**
     * 查询出的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerMoneyLog_list.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String list() throws Exception {

        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" SELECT");
        sbSQL.append("     cc.*, cp.`Name` CustomerName");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customermoneylog cc,");
        sbSQL.append("     crm_customerpersonal cp");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND cc.state = 0");
        sbSQL.append(" AND cp.state = 0");
        sbSQL.append(" AND cc.CustomerId = cp.id");
        sbSQL.append(" ORDER BY");
        sbSQL.append("     sid DESC");

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerMoneyLogVO.class);
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), null, customerMoneyLogVO,conditions,request,queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 新增或修改数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerMoneyLog_insertOrUpdate.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = MySQLDao.insertOrUpdate(customerMoneyLog, getLoginUser().getId(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerMoneyLog_load.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String load() throws Exception {
        customerMoneyLog = customerMoneyLogService.loadCustomerMoneyLogPO(customerMoneyLog.getId());
        getResult().setReturnValue(customerMoneyLog.toJsonObject4Form());
        return SUCCESS;
    }


    /**
     * 创建人：姚章鹏
     * 时间：2015年9月22日10:21:10
     * 内容：加载客户资金日志数据
     * @return
     * @throws Exception
     */
    public String loadCustomerLogData() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuffer sbSQL = new StringBuffer();
        String productionId = request.getParameter("productionId");
        sbSQL.append(" SELECT ");
        sbSQL.append("  cc.*, ccp.`Name` CustomerName ");
        sbSQL.append(" FROM ");
        sbSQL.append("  crm_customermoneylog cc LEFT JOIN   crm_order O on  cc.CustomerId=o.CustomerId and o.state=0 ");
        sbSQL.append("  LEFT JOIN crm_customerpersonal ccp ON ccp.Id = cc.CustomerId  AND ccp.state = 0 ");
        sbSQL.append("  LEFT JOIN  crm_production cp  on  cp.Id=o.ProductionId and cp.state=0 ");
        sbSQL.append("  WHERE ");
        sbSQL.append("  1 = 1 ");
        sbSQL.append("   AND cc.state = 0 ");
        sbSQL.append("   and cp.Id='"+Database.encodeSQL(productionId)+"' ");
        sbSQL.append("   group by ");
        sbSQL.append("  cc.sid ");
        sbSQL.append("  ORDER BY ");
        sbSQL.append("  sid DESC ");
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerMoneyLogVO.class);
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(),customerMoneyLogVO,conditions,request,queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerMoneyLog_delete.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String delete() throws Exception {
        customerMoneyLogService.delete(customerMoneyLog, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 获取类型JSON数组
     * @return
     */
    public String TypeTree() throws Exception{
        getResult().setReturnValue(customerMoneyLogService.getTypeTree());
        return SUCCESS;
    }

    /**
     * 获取状态JSON数组
     * @return
     */
    public String StatusTree() throws Exception{
        getResult().setReturnValue(customerMoneyLogService.getStatusTree());
        return SUCCESS;
    }

    /**
     * 通过登录的客户 ID 查询关联的资金日志
     * @return
     * @throws Exception
     */
    public String list4Web() throws Exception {
        HttpSession session = getRequest().getSession();
        // todo codereview 曾权 业务有关的代码抽取，写在CustomerMoneyLogService中，用于公共调用。 query换成search
        String customerId = session.getAttribute("loginUserId").toString();

        if(!StringUtils.isEmpty(customerId)) {

            Pager customerMoneyLogs = customerMoneyLogService.getCustomerMoneyLogs(customerId);
            if(customerMoneyLogs!=null){
                getResult().setReturnValue(customerMoneyLogs.toJsonObject());
            }

        }
        return SUCCESS;
    }

    /**
     * 获取客户的资金日志
     *
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015-12-8
     *
     * @return String
     * @throws Exception
     */
    @Security(needToken = true)
    public String getCustomerMoneyLogs() throws Exception {

        // 获取 Token
        TokenPO token = this.getToken();
        if(token == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_NOT_CORRECT, "请检查参数是否正确！").throwException();
        }

        // 获取客户 ID
        String customerId = token.getBizId();
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "系统没有找到您的账户").throwException();
        }

        Pager customerMoneyLogs = customerMoneyLogService.getCustomerMoneyLogs(customerId);

        if(customerMoneyLogs != null){
            getResult().setCode(100);
            getResult().setMessage("获取成功");
            getResult().setReturnValue(customerMoneyLogs.toJsonObject());
        }

        return SUCCESS;
    }

    public String loadCustomerLog() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuffer sbSQL = new StringBuffer();
        String customerId = request.getParameter("customerId");
        sbSQL.append("select cc.*,cp.`Name` CustomerName from  crm_customermoneylog cc,crm_customerpersonal cp  where 1=1 AND cc.state=0 AND cp.state = 0 AND cc.CustomerId = cp.id and CustomerId='"+Database.encodeSQL(customerId)+"'  ORDER BY sid DESC");
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerMoneyLogVO.class);
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(),customerMoneyLogVO,conditions,request,queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    public CustomerMoneyLogVO getCustomerMoneyLogVO() {
        return customerMoneyLogVO;
    }
    public void setCustomerMoneyLogVO(CustomerMoneyLogVO customerMoneyLogVO) {this.customerMoneyLogVO = customerMoneyLogVO;}

    public CustomerMoneyLogPO getCustomerMoneyLog() {
        return customerMoneyLog;
    }
    public void setCustomerMoneyLog(CustomerMoneyLogPO customerMoneyLog) {
        this.customerMoneyLog = customerMoneyLog;
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
}
