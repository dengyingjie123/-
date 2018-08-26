package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.service.system.KVService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class KVAction extends BaseAction {

    @Autowired
    KVService kvService;

    private KVPO kv = new KVPO();


    public String reloadSystemConfig() throws Exception {

        Config.reloadSystemConfig();

        Config.reloadSystemSQL();

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    /**
     * 新增或修改
     *
     * 修改：邓超
     * 内容：创建注释
     * 时间：2015-12-02
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        KVPO kv = HttpUtils.getInstanceFromRequest(getRequest(), "kv", KVPO.class);

        kvService.insertOrUpdate(kv, getConnection());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * 修改：邓超
     * 内容：创建注释
     * 时间：2015-12-02
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        KVPO kv = HttpUtils.getInstanceFromRequest(getRequest(), "kv", KVPO.class);
        int count = MySQLDao.remove(kv, getLoginUser().getId());
        if (count != 1) {
            throw new Exception("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询并组装成树
     *
     * 修改：邓超
     * 内容：创建注释
     * 时间：2015-12-02
     *
     * @return
     * @throws Exception
     */
    public String listAll4Tree() throws Exception {

        KVPO kv = HttpUtils.getInstanceFromRequest(getRequest(), "kv", KVPO.class);

        Connection conn = getConnection();

        QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

        HttpServletRequest request = ServletActionContext.getRequest();
        String strOrderBy = request.getParameter("OrderBy");
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, strOrderBy));

        List<KVPO> list = MySQLDao.search(kv,KVPO.class,conditions,queryType,conn);
        JSONObject json = JSONDao.get4Tree(list);
        getResult().setReturnValue(json.toString());

        return SUCCESS;
    }

    /**
     * 加载数据
     *
     * 修改：邓超
     * 内容：创建注释
     * 时间：2015-12-02
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        KVPO kv = HttpUtils.getInstanceFromRequest(getRequest(), "kv", KVPO.class);
        kv.setState(Config.STATE_CURRENT);
        kv = MySQLDao.load(kv, KVPO.class);
        getResult().setReturnValue(kv.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 查询所有数据
     *
     * 修改：邓超
     * 内容：创建注释
     * 时间：2015-12-02
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        KVPO kv = HttpUtils.getInstanceFromRequest(getRequest(), "kv", KVPO.class);

        kv.setState(Config.STATE_CURRENT);
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY,"GroupName "+Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY,"Orders "+Database.ORDERBY_ASC));
        Pager pager = Pager.search(kv,conditions,request,queryType);
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    public KVPO getKv() {
        return kv;
    }

    public void setKv(KVPO kv) {
        this.kv = kv;
    }
}
