package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.DepartmentTypeStatus;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.system.DepartmentVO;
import com.youngbook.service.system.DepartmentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DepartmentAction extends BaseAction {
    private ReturnObject result;
    private DepartmentPO department;
    private UserPO user = null;


    @Autowired
    DepartmentService departmentService;

    public static void main(String [] args) {
        try {

            DepartmentAction action = new  DepartmentAction();
            TreeOperator.printForest(action.getDepartmentTree(), 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserDepartmentInfo() throws Exception {

        if (user == null || StringUtils.isEmpty(user.getId())) {
            MyException.newInstance("无法获得登录用户信息").throwException();
        }

        DepartmentService service = new DepartmentService();
        List<UserPositionInfoPO> list = service.getUserDepartmentInfo(user.getId(), getConnection());

        getResult().setReturnValue(list);

        return SUCCESS;
    }

     public Tree getDepartmentTree() throws Exception {
         DepartmentPO root = new DepartmentPO();

         List<DepartmentPO> list = MySQLDao.query(root, DepartmentPO.class, null, null);

         Tree tree = TreeOperator.createForest();

         for (int i = 0; list != null && i < list.size(); i++) {
             DepartmentPO temp = list.get(i);
             Tree leaf = new Tree(temp.getId(), temp.getName(), temp.getParentId(), temp);
             TreeOperator.add(tree, leaf);
         }

         return tree;
     }

    public String load() throws Exception {
        DepartmentPO department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);
        department = MySQLDao.load(department,DepartmentPO.class);
        result.setReturnValue(department.toJsonObject4Form());
        return SUCCESS;
    }
    public String insertOrUpdate() throws Exception {

        DepartmentPO department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);

        departmentService.insertOrUpdate(department, getConnection());

        return SUCCESS;
    }

    public String delete() throws Exception {

        department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);

        result = new ReturnObject();
        try {
            int count = departmentService.remove(department,getLoginUser().getId(),getConnection());
            if (count >= 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            } else {
                result.setMessage("删除失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String list() throws Exception {

        Connection conn = getConnection();
        result = new ReturnObject();
        department = new DepartmentPO();

        QueryType queryType = new QueryType(Database.NUMBER_EQUAL, Database.QUERY_EXACTLY);
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " orders "));

        List<DepartmentPO> departmentList = departmentService.search(department, DepartmentPO.class,conditions, queryType, conn);


        if (departmentList == null && departmentList.size() == 0) {
            MyException.newInstance("部门加载失败").throwException();
        }

        Tree root = TreeOperator.createForest();
        for(DepartmentPO departmentpo : departmentList){
            Tree tree = new Tree(departmentpo.getId(),departmentpo.getName(),departmentpo.getParentId(),departmentpo);
            TreeOperator.add(root,tree);
        }

        JSONObject json = TreeOperator.getJson4Tree(root);
        String jsonStr = json.getJSONArray("children").toString();

        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setReturnValue(jsonStr);

        return SUCCESS;
    }


    public String getDepartments4FortuneCenter() throws Exception {


        Connection conn = getConnection();
        result = new ReturnObject();
        department = new DepartmentPO();
        department.setTypeID("14511");
        String json = departmentService.getDepartments4FortuneCenter(department, conn);


        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setReturnValue(json);

        return SUCCESS;
    }


    /**
     * 获取状态JSON数组
     * @return
     */
    public String departmentTypeTree() throws Exception{
        DepartmentTypeStatus departmentTypeStatus = new DepartmentTypeStatus();
        JSONArray array = departmentTypeStatus.toJsonArray();
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    //获取公司与分公司的列表
    /*
    * 作者：周海鸿
    * 时间：2015-7-29
    * 内容：获取类型公司与分公司的列表*/
    public String getDepartmentTrees() throws  Exception{

        try {
           StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT");
            sbSQL.append("	id,");
            sbSQL.append("	icon,");
            sbSQL.append("	NAME as fromName,");
            sbSQL.append("	parentId,");
            sbSQL.append("	orders,");
            sbSQL.append("	typeID ,");
            sbSQL.append("	fromName as name");
            sbSQL.append(" FROM");
            sbSQL.append("	system_department");
            sbSQL.append(" WHERE");
            sbSQL.append("	typeID = "+DepartmentTypeStatus.TYPE_14506);
            sbSQL.append(" or typeID = "+DepartmentTypeStatus.TYPE_14509);
            sbSQL.append("  ORDER BY");
            sbSQL.append("	orders asc");
            List<DepartmentPO> departmentList = MySQLDao.query(sbSQL.toString(), DepartmentPO.class,null);
            if(departmentList.size() != 0){
                Tree root = TreeOperator.createForest();
                for(DepartmentPO departmentpo : departmentList){
                    Tree tree = new Tree(departmentpo.getId(),departmentpo.getName(),departmentpo.getParentId(),departmentpo);
                    TreeOperator.add(root,tree);
                }
                JSONObject json = TreeOperator.getJson4Tree(root);
                String jsonStr = json.getJSONArray("children").toString();
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue(jsonStr);
            }else{
                result.setReturnValue("{}");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("加载失败");
            result.setException(e);
        }
        return SUCCESS;
    }
    /*
    *作者：周海鸿
    * 时间：2015-7-29
    * 内容：获取公司部门ixnx
    * */
    public String getDepartmentTrees2() throws  Exception{

        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT");
            sbSQL.append("	id,");
            sbSQL.append("	icon,");
            sbSQL.append("	NAME,");
            sbSQL.append("	parentId,");
            sbSQL.append("	orders,");
            sbSQL.append("	typeID");
            sbSQL.append(" FROM");
            sbSQL.append("	system_department");
            sbSQL.append(" WHERE");
            sbSQL.append("	(typeID = "+DepartmentTypeStatus.TYPE_14510);
            sbSQL.append(" or typeID = "+DepartmentTypeStatus.TYPE_14511);

            sbSQL.append(") and parentId = '"+department.getParentId()+"'");
            sbSQL.append(" ORDER BY");
            sbSQL.append("	orders asc");
            List<DepartmentPO> departmentList = MySQLDao.query(sbSQL.toString(), DepartmentPO.class,null );
            if(departmentList.size() != 0){
                Tree root = TreeOperator.createForest();
                for(DepartmentPO departmentpo : departmentList){
                    Tree tree = new Tree(departmentpo.getId(),departmentpo.getName(),departmentpo.getParentId(),departmentpo);
                    TreeOperator.add(root,tree);
                }
                JSONObject json = TreeOperator.getJson4Tree(root);
                String jsonStr = json.getJSONArray("children").toString();
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue(jsonStr);
            }else{
                result.setReturnValue("{}");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("加载失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String listDepartmentsForSearch() throws Exception {
        String sql = "SELECT id,name text\n" +
                "from system_department\n" +
                "where typeID='14511'";
        List<DepartmentVO> list = MySQLDao.query(sql,DepartmentVO.class,null);
        JSONArray array = new JSONArray();
        for(DepartmentVO d:list){
            array.add(d.toJsonObject4Form());
        }
        result.setReturnValue(array);
        return SUCCESS;
    }

    public DepartmentPO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentPO department) {
        this.department = department;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }
}
