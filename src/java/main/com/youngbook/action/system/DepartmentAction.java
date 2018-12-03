package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
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


    /**
     * @description
     * 根据departmentId获取相应的部门数据和加载出修改窗口
     * @author 胡超怡
     *
     * @date 2018/12/3 9:49
     * @return java.lang.String
     * @throws Exception
     */
    public String load() throws Exception {


        /**
         * 获取departmentId，并根据id查询出所有数据并响应回浏览器
         */
        DepartmentPO department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);
        department = MySQLDao.load(department,DepartmentPO.class);
        result.setReturnValue(department.toJsonObject4Form());

        return SUCCESS;
    }


    /**
     * @description
     * 添加或修改部门
     * @author 胡超怡
     *
     * @date 2018/12/3 9:59
     * @return java.lang.String
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {


        /**
         * 根据部门id来修改或添加部门
         */
        DepartmentPO department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);
        departmentService.insertOrUpdate(department, getConnection());

        return SUCCESS;
    }


    /**
     * @description
     * 根据部门id来删除（把state变为2，级联删除，附属的position和position对应的user和permission也会被删除）
     * @author 胡超怡
     *
     * @date 2018/12/3 10:01
     * @return java.lang.String
     * @throws Exception
     */
    public String delete() throws Exception {

        /**
         * 根据部门id删除
         */
        DepartmentPO department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);
        int count = departmentService.remove(department,getLoginUser().getId(),getConnection());

        if (count != 1) {
            throw new Exception("操作失败");
        }
        return SUCCESS;
    }


    /**
     * @description
     * 读取所有的state=0的部门列表
     * @author 胡超怡
     *
     * @date 2018/12/3 10:21
     * @return java.lang.String
     * @throws Exception
     */
    public String list() throws Exception {

        /**
         * 获取部门id和创建
         */
        result = new ReturnObject();
        DepartmentPO department = HttpUtils.getInstanceFromRequest(getRequest(), "department", DepartmentPO.class);


        /**
         * 根据department获取state=0的数据
         */
        List<DepartmentPO> departmentList = departmentService.searchByStateCondition(department,getConnection());


        /**
         * 判断部门集合的是否为空，为空则抛出异常
         */
        if (departmentList == null && departmentList.size() == 0) {
            MyException.newInstance("部门加载失败").throwException();
        }




        /**
         * 创建树并赋值departmentpo
         */
        Tree root = TreeOperator.createForest();
        for(DepartmentPO departmentpo : departmentList){
            Tree tree = new Tree(departmentpo.getId(),departmentpo.getName(),departmentpo.getParentId(),departmentpo);
            TreeOperator.add(root,tree);
        }


        /**
         * 处理树并转换为json响应会浏览器
         */
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
