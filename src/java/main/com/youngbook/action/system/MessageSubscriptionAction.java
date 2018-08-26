package com.youngbook.action.system;

/**
 * Created by Jepson on 2015/6/25.
 */
import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.MessageSubscriptionPO;
import com.youngbook.entity.vo.system.MessageSubscriptionVO;
import com.youngbook.service.system.MessageSubscriptionService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个MessageSubscriptionAction类，继承BaseAction累
 *
 * @author Codemaker
 */

public class MessageSubscriptionAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private MessageSubscriptionPO messageSubscription = new MessageSubscriptionPO();
    private MessageSubscriptionVO messageSubscriptionVO = new MessageSubscriptionVO();
    private MessageSubscriptionService service = new MessageSubscriptionService();

    private String sort = new String();//需要排序的列名
    private String order = new String();



    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select sy.*,kv.V as emailName,kv2.v as smsName,kv3.v as todoListName,kv4.v as messageTypeName from system_MessageSubscription  sy,system_kv kv, system_kv kv2, system_kv kv3,system_kv kv4");
        sbSQL.append(" where 1=1 AND kv.groupName = 'Is_Avaliable' AND kv.k = sy.isEmail ");
        sbSQL.append(" AND kv2.k = sy.isSms  AND kv2.groupName = 'Is_Avaliable'");
        sbSQL.append(" AND kv3.k = sy.isTodoList  AND kv3.groupName = 'Is_Avaliable'");
        sbSQL.append(" AND kv4.k = sy.messageTypeId  AND kv4.groupName = 'System_MessageType' and state=0");

        HttpServletRequest request = getRequest();

        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, messageSubscriptionVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, messageSubscriptionVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), messageSubscriptionVO, conditions, request, queryType);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */

    @Permission(require = "系统管理-消息订阅-新增")
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(messageSubscription, getLoginUser(), getConnection());
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
    @Permission(require = "系统管理-消息订阅-修改")
    public String load() throws Exception {

        messageSubscription = service.loadMessageSubscriptionPO(messageSubscription.getId());

        getResult().setReturnValue(messageSubscription.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-消息订阅-删除")
    public String delete() throws Exception {

        service.delete(messageSubscription, getLoginUser(), getConnection());

        return SUCCESS;
    }
    public MessageSubscriptionPO getMessageSubscription() {
        return messageSubscription;
    }

    public void setMessageSubscription(MessageSubscriptionPO messageSubscription) {
        this.messageSubscription = messageSubscription;
    }

    public MessageSubscriptionVO getMessageSubscriptionVO() {
        return messageSubscriptionVO;
    }

    public void setMessageSubscriptionVO(MessageSubscriptionVO messageSubscriptionVO) {
        this.messageSubscriptionVO = messageSubscriptionVO;
    }

    public MessageSubscriptionService getService() {
        return service;
    }

    public void setService(MessageSubscriptionService service) {
        this.service = service;
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
