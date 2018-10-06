package com.youngbook.common;

import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.entity.vo.BaseVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Lee
 * Date: 14-4-1
 */
public class Pager implements IJsonable {
    private int total = 0;
    private List<IJsonable> data = new ArrayList();

    private int currentPage = 0;
    private int showRowCount = 0;

    private String _state = "open";

    private List<KVObjects> footer = new ArrayList<KVObjects>();

    public Pager() {
    }



    public static Pager  getInstance(List<?> list) {

        List<IJsonable> listIJsonable = new ArrayList<IJsonable>();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);

                if (o instanceof IJsonable) {
                    IJsonable data = (IJsonable) o;
                    listIJsonable.add(data);
                }
            }
        }


        Pager pager = new Pager();

        pager.setTotal(listIJsonable.size());
        pager.setData(listIJsonable);


        return pager;
    }

    public static Pager getInstance(HttpServletRequest request) {
        Pager pager = new Pager();
        String page = "";
        String rows = "";
        try {
            if(request.getParameter("req") == null){
                page = request.getParameter("page")!=null?request.getParameter("page"):"1";
                rows = request.getParameter("rows")!=null?request.getParameter("rows"):"15";
            }else{
                String reqStr = request.getParameter("req");
                JSONObject reqParam  = JSONObject.fromObject(reqStr);
                if(reqParam.has("currentPage")){
                    page = reqParam.getString("currentPage");

                }
                if(reqParam.has("pageSize")){
                    rows = reqParam.getString("pageSize");
                }
            }

            pager.setCurrentPage(Integer.valueOf(page));
            pager.setShowRowCount(Integer.valueOf(rows));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return pager;
    }



    public static Pager search(String sql, List<KVObject> parameters, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount, QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();

        try {
            return  MySQLDao.search(sql, parameters, object, conditions, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;
    }

    public static Pager search(DatabaseSQL dbSQL, IJsonable object, int currentPage, int showRowCount, Connection conn) throws Exception {

        return  MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), object, null, currentPage, showRowCount, null, conn);
    }

    public static Pager search(String sql, List<KVObject> parameters, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount, QueryType queryType, Connection conn) throws Exception {

        return  MySQLDao.search(sql, parameters, object, conditions, currentPage, showRowCount, queryType, conn);
    }

    public static Pager search(IJsonable object,List<KVObject> conditions, HttpServletRequest request, QueryType queryType) throws Exception {
        Pager pager = Pager.getInstance(request);
        return MySQLDao.search(object,conditions,pager.currentPage, pager.showRowCount, queryType);
    }

    /**
     * 实现VO的分页查询
     * @param sql 组织VO视图的SQL语句
     * @param object vo对象，可根据其中属性进行查询
     * @param conditions 其他条件，例如排序和自定义条件
     * @param request 用于获取分页数据
     * @param queryType 表示查询类型，模糊或是精确等
     * @return 分页好的Pager
     * @throws Exception
     */
    @Deprecated
    public static Pager query(String sql, IJsonable object,List<KVObject> conditions, HttpServletRequest request, QueryType queryType) throws Exception {

        Pager pager = Pager.getInstance(request);
        return MySQLDao.query(sql,object,conditions,pager.currentPage, pager.showRowCount, queryType);
    }

    public static Pager search(String sql, List<KVObject> parameters,  IJsonable object,List<KVObject> conditions, HttpServletRequest request, QueryType queryType) throws Exception {

        Pager pager = Pager.getInstance(request);
        return MySQLDao.search(sql, parameters, object, conditions, pager.currentPage, pager.showRowCount, queryType);
    }

    public static Pager search(String sql, List<KVObject> parameters,  IJsonable object,List<KVObject> conditions, HttpServletRequest request, QueryType queryType, Connection conn) throws Exception {

        Pager pager = Pager.getInstance(request);
        return MySQLDao.search(sql, parameters, object, conditions, pager.currentPage, pager.showRowCount, queryType, conn);
    }

    public static Pager query(String sql, IJsonable object,List<KVObject> conditions, int currentPage, int showRowCount, QueryType queryType) throws Exception {

        return MySQLDao.query(sql,object,conditions,currentPage, showRowCount, queryType);
    }

    public static Pager query(String sql, IJsonable object,List<KVObject> conditions, HttpServletRequest request, QueryType queryType, Connection conn) throws Exception {

        Pager pager = Pager.getInstance(request);
        // todo 升级查询
        // return MySQLDao.query(sql,object,conditions,pager.currentPage, pager.showRowCount, queryType, conn);

        return MySQLDao.search(sql, null, object, conditions, pager.currentPage, pager.showRowCount, queryType, conn);
    }

    public static Pager query(IJsonable object,HttpServletRequest request,List<KVObject>  conditions, QueryType queryType) throws Exception {
        Pager pager = Pager.getInstance(request);
        return MySQLDao.query(object,conditions,pager.currentPage, pager.showRowCount, queryType);
    }

    public static Pager search(String sql, List<KVObject> parameters, Class clazz, HttpServletRequest request, Connection conn) throws Exception {
        Pager pager = Pager.getInstance(request);
        // return MySQLDao.query(sql, clazz,pager.currentPage, pager.showRowCount);

        return MySQLDao.search(sql, parameters, clazz, pager.currentPage, pager.showRowCount, conn);
    }

    public static Pager search(String sql, List<KVObject> parameters, Class clazz, int currentPage, int showRowCount, Connection conn) throws Exception {

        return MySQLDao.search(sql, parameters, clazz, currentPage, showRowCount, conn);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<IJsonable> getData() {
        return data;
    }

    public void setData(List<IJsonable> data) {
        this.data = data;
    }

    public int getShowRowCount() {
        return showRowCount;
    }

    public void setShowRowCount(int showRowCount) {
        this.showRowCount = showRowCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    @Override
    public JSONObject toJsonObject4Treegrid() {

        JSONObject json = this.toJsonObject();

        json.element("state", this.get_state());

        return json;
    }


    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.element("total", total);
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            IJsonable temp = data.get(i);
            array.add(temp.toJsonObject());
        }
        json.put("rows", array);

        /**
         * 构造footer
         */
        if (footer != null && footer.size() > 0) {
            JSONArray footerJSONArray = new JSONArray();
            for (int i = 0; i < footer.size(); i++) {
                KVObjects footerDatas = footer.get(i);

                JSONObject jsonObject = new JSONObject();
                for (int j = 0; j < footerDatas.size(); j++) {

                    KVObject footerData = footerDatas.get(j);
                    jsonObject.put(footerData.getKey(), footerData.getValue());
                }
                footerJSONArray.add(jsonObject);
            }
            json.put("footer", footerJSONArray);
        }

        return json;
    }

    public JSONObject toJsonCustomerCertificatePO() {
        JSONObject json = new JSONObject();
        json.element("total", total);
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            CustomerCertificatePO cc = (CustomerCertificatePO)data.get(i);
            if(!StringUtils.isEmpty(cc.getNumber()))
            {
                //调用aes解密
                cc.setNumber(AesEncrypt.decrypt(cc.getNumber()));
            }
            IJsonable temp = (IJsonable)cc;
            array.add(temp.toJsonObject());
        }
        json.put("rows", array);
        return json;
    }


    public JSONObject toJsonCustomerAccountPO() {
        JSONObject json = new JSONObject();
        json.element("total", total);
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            CustomerAccountPO ca = (CustomerAccountPO)data.get(i);
            if(!StringUtils.isEmpty(ca.getNumber())) {
                //调用aes解密
                ca.setNumber(AesEncrypt.decrypt(ca.getNumber()));
            }
            IJsonable temp = (IJsonable)ca;
            array.add(temp.toJsonObject());
        }
        json.put("rows", array);
        return json;
    }

    @Override
    public JSONObject toJsonObject4Form() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONObject toJsonObject4Form(String prefix) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            IJsonable temp = data.get(i);
            array.add(temp.toJsonObject4Tree());
        }
        json.put("rows", array);
        return json;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public List<KVObjects> getFooter() {
        return footer;
    }

    public void setFooter(List<KVObjects> footer) {
        this.footer = footer;
    }
}
