package com.youngbook.action.oa.email;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.email.FromEmailPO;
import com.youngbook.service.oa.email.FromEmailService;
import com.youngbook.service.system.LogService;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FromEmailAction extends BaseAction {

    private FromEmailPO fromEmail = new FromEmailPO();
    private FromEmailService service = new FromEmailService();
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        conditions = MySQLDao.getQueryDatetimeParameters(request, fromEmail.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, fromEmail.getClass(), conditions);
        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取数据列表
        Pager pager = service.list (fromEmail,conditions,request);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        //添加更新数据
        int count = service.insertOrUpdate(fromEmail, getLoginUser(), getConnection());

        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数据
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        //根据条件获取书
        fromEmail = service.loadFromEmailPO(fromEmail.getId());

        getResult().setReturnValue(fromEmail.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除数据
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        //删除数据
        service.delete(fromEmail, getLoginUser(), getConnection());

        return SUCCESS;
    }

    /**
     * 更新邮件列表
     * @return
     */
    public String refresh() throws Exception {
        //更新邮件列表
        service.refresh(fromEmail, getLoginUser(), getConnection());

        return SUCCESS;
    }

    /**
     * 添加邮件
     * @return
     * @throws Exception
     */
    public String FromToEmail() throws Exception {
        list = service.loadEmailForUser(getLoginUser());
        int count = service.FromToEmail(fromEmail, list.get(0).get("Password").toString(), getLoginUser(), getConnection());
        if (count != 1) {
            new Exception("邮件发送失败");
        }
        return SUCCESS;
    }

    public String loadEmailAccount() throws Exception {
        list = service.loadEmailForUser(getLoginUser());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsonArray.add(list.get(i));
        }
        // 判断邮箱和用户有没有存在关系表
        if (list.size() == 0) {
            getResult().setCode(6855);
            getResult().setMessage("当前用户没有系统邮箱，请与管理员联系！");
        }
        getResult().setReturnValue(jsonArray);
        return SUCCESS;
    }

    public String downloadAll() throws Exception {
        LogService.debug("Enter downloadAll Action", this.getClass());
        service.downloadAll();
        return SUCCESS;
    }

    public String uploadAttachment() throws Exception {
        String attachmentPath = getRequest().getParameter("attachmentPath");
        service.sendEmailAttachment(fromEmail, attachmentPath);
        return SUCCESS;
    }

    public FromEmailPO getFromEmail() {
        return fromEmail;
    }
    public void setFromEmail(FromEmailPO fromEmail) {
        this.fromEmail = fromEmail;
    }

    public FromEmailService getService() {
        return service;
    }
    public void setService(FromEmailService service) {
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