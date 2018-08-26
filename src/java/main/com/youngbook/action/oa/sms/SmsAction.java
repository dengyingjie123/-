package com.youngbook.action.oa.sms;

/**
 * Created by Jepson on 2015/7/3.
 */

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.vo.system.SmsVO;
import com.youngbook.service.oa.sms.SmsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个 SmsAction 类，继承 BaseAction 类
 *
 * @author Codemaker
 */

public class SmsAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private SmsPO sms = new SmsPO();
    private SmsVO smsVO = new SmsVO();
    private SmsService service = new SmsService();

    private String sort = new String();//需要排序的列名
    private String order = new String();


    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        HttpServletRequest request = getRequest();
        String type = request.getParameter("type");
        String loginId = request.getParameter("loginId");
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select os.*,kv.V as typeName from system_Sms os,system_kv kv ");
        sbSQL.append(" where 1 = 1 ");
        sbSQL.append(" AND state = 0  AND kv.groupName = 'System_SmsType' AND kv.k = os.type");
        //发送类型
        if (type.equals("SendType")) {
            // 构造SQL语句
            sbSQL.append(" AND SenderId='" + Database.encodeSQL(loginId) + "'");
        }
        //接收类型
        if (type.equals("ReceiveType")) {
            sbSQL.append(" AND ReceiverId='" + Database.encodeSQL(loginId) + "'");
        }

        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, smsVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, smsVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), smsVO, conditions, request, queryType);
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
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(sms, getLoginUser(), getConnection());
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

        sms = service.loadSmsPO(sms.getId());

        getResult().setReturnValue(sms.toJsonObject4Form());

        return SUCCESS;
    }


    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        service.delete(sms, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public SmsPO getSms() {
        return sms;
    }

    public void setSms(SmsPO sms) {
        this.sms = sms;
    }

    public SmsService getService() {
        return service;
    }

    public void setService(SmsService service) {
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

    public SmsVO getSmsVO() {
        return smsVO;
    }

    public void setSmsVO(SmsVO smsVO) {
        this.smsVO = smsVO;
    }


}