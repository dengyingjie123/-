package com.youngbook.action.cms;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.cms.ColumnPO;
import com.youngbook.entity.po.cms.ColumnTemp;
import com.youngbook.entity.vo.cms.ColumnVO;
import com.youngbook.service.cms.ColumnService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColumnAction extends BaseAction {

    private ColumnService service = new ColumnService();
    private ColumnPO column = new ColumnPO();
    private ColumnVO columnVO = new ColumnVO();
    private ReturnObject result;

    /**
     * 查询出所有文章栏目的数据
     * 通过查询 CMS_Column 表，把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/cms/Column_list.action，如未成功，请检查 struts 配置
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
        HttpServletRequest request = ServletActionContext.getRequest();
        String departmentId = getRequest().getParameter("departmentId");
        String sql = "select DISTINCT  C.name from cms_column C LEFT JOIN cms_columnbelong B on B.columnId=C.id LEFT JOIN system_department D on B.departmentId=D.ID where C.state=0";
        if (departmentId != null && !departmentId.equals("")) {
            sql = sql + " and D.id like '%" + Database.encodeSQL(departmentId) + "%'";
        }
        List<Map<String, Object>> columns = MySQLDao.query(sql);
        JSONArray array = new JSONArray();
        for (Map<String, Object> map : columns) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                JSONObject json = new JSONObject();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                json.element(key, value);
                array.add(json);
            }
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    public String getTree() throws Exception {
        column = new ColumnPO();
        column.setState(0);
        QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);
        List<ColumnPO> columnList = MySQLDao.query(column, ColumnPO.class, null, queryType);

        if (columnList == null || columnList.size() == 0) {
            MyException.newInstance("请先创建栏目").throwException();
        }

        Tree root = TreeOperator.createForest();
        for (ColumnPO columnpo : columnList) {
            Tree tree = new Tree(columnpo.getId(), columnpo.getName(), columnpo.getParentId(), columnpo);
            TreeOperator.add(root, tree);
        }
        JSONObject json = TreeOperator.getJson4Tree(root);
        String jsonStr = json.getJSONArray("children").toString();
        getResult().setReturnValue(jsonStr);

        return SUCCESS;
    }

    public String getTreelist() {
        result = new ReturnObject();
        try {
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);
            String departmentId = getRequest().getParameter("departmentId");
            String sql = "select C.id,C.name text,c.ParentId from cms_column C LEFT JOIN cms_columnbelong B on B.columnId=C.id LEFT JOIN system_department D on B.departmentId=D.ID where C.state=0";
            if (departmentId != null && !departmentId.equals("")) {
                sql = sql + " and D.id like '%" + Database.encodeSQL(departmentId) + "%'";
            }
            List<ColumnTemp> columnList = MySQLDao.query(sql, ColumnTemp.class, null);
            if (columnList.size() != 0) {
                Tree menuRoot = TreeOperator.createForest();
                for (ColumnTemp po : columnList) {
                    Tree tree = new Tree(po.getId(), po.getText(), po.getParentId(), po);
                    TreeOperator.add(menuRoot, tree);
                }
                JSONArray array = TreeOperator.getJson4Tree(menuRoot).getJSONArray("children");
                String json = "";
                if (array != null) {
                    json = array.toString();
                }
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setMessage("操作成功");
                result.setReturnValue(json);
            } else {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    /**
     * 新增或修改
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/cms/Column_load.action，如未成功，请检查 struts 配置
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
        int count = 1;
        HttpServletRequest request = getRequest();
        count = service.insertOrUpdate(column, getLoginUser(), getConnection(), request);
        return SUCCESS;
    }


    public String delete() throws Exception {
        service.delete(column, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String load() throws Exception {
        column.setState(Config.STATE_CURRENT);
        column = MySQLDao.load(column, ColumnPO.class);
        getResult().setReturnValue(column.toJsonObject4Form());
        return SUCCESS;
    }

    public String getColumnTreeList() throws Exception {
        HttpServletRequest request = getRequest();
        String departmentId = request.getParameter("departmentId");
        JSONArray json = service.getColumnTreeList(column, request, departmentId);
        getResult().setReturnValue(json);
        return SUCCESS;
    }

    /**
     * 根据 ID 查询栏目
     * 用法：/core/w/cms/loadColumn 进行访问
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-7-20
     *
     * @author 邓超
     * @return Pager
     * @throws Exception
     */
    public String load4W() throws Exception {
        HttpServletRequest request = getRequest();
        String columnId = request.getParameter("id");
        if(columnId == null || "".equals(columnId)) {
            getResult().setReturnValue("ID 参数缺失");
            return "info";
        }
        ColumnPO po = service.load4W(columnId, getConnection());
        getResult().setReturnValue(po.toJsonObject());
        return SUCCESS;
    }


    /**
     * 获取栏目接口
     * 获取栏目列表
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月9日
     */
    public String getColumnList() throws Exception{
        // 获取请求
        HttpServletRequest request = this.getRequest();
        //执行查询
        Pager columns = service.getColumns(request);
        getResult().setReturnValue(columns.toJsonObject());
        return  SUCCESS;
    }

    /**
     * 获取栏目数据
     * @return
     * @throws Exception
     */
    public String loadColumnList() throws  Exception{
        HttpServletRequest request = getRequest();
        //获取数据库连接
        Connection conn = getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //获取栏目
        Pager columns = service.getColumn4W(request, conditions, queryType);
        if(columns!=null&&columns.getData().size()>0){
            getResult().setReturnValue(columns.toJsonObject());
        }
        return SUCCESS;
    }

    public ColumnPO getColumn() {
        return column;
    }
    public void setColumn(ColumnPO column) {
        this.column = column;
    }

    public ColumnVO getColumnVO() {
        return columnVO;
    }
    public void setColumnVO(ColumnVO columnVO) {
        this.columnVO = columnVO;
    }

    public ReturnObject getResult() {
        return result;
    }
    public void setResult(ReturnObject result) {
        this.result = result;
    }

}
