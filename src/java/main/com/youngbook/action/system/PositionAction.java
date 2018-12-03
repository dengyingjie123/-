package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.PositionUserPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.service.system.PositionService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jayden
 * Date: 14-4-25
 * Time: 上午9:41
 * To change this template use File | Settings | File Templates.
 */
public class PositionAction extends BaseAction {
    private ReturnObject result;
    private PositionPO position;
    private PositionUserPO positionUser = new PositionUserPO();

    @Autowired
    PositionService positionService;


    /**
     * @description
     * 根据id添加或修改岗位
     * @author 胡超怡
     *
     * @date 2018/12/3 10:27
     * @return java.lang.String
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        /**
         * 根据id添加或修改岗位
         */
        PositionPO position = HttpUtils.getInstanceFromRequest(getRequest(), "position", PositionPO.class);
        positionService.insertOrUpdate(position, getConnection());

        return SUCCESS;
    }


    /**
     * @description
     * 根据positionId获取相应的岗位数据和加载出修改窗口
     * @author 胡超怡
     *
     * @date 2018/12/3 10:28
     * @return java.lang.String
     * @throws Exception
     */
    public String load() throws Exception {

        PositionPO position = HttpUtils.getInstanceFromRequest(getRequest(), "position", PositionPO.class);
        position = MySQLDao.load(position, PositionPO.class, getConnection());
        getResult().setReturnValue(position.toJsonObject4Form());

        return SUCCESS;
    }

    public String load4Form() {
        result = new ReturnObject();
        try {
            position = MySQLDao.load(position, PositionPO.class);
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(position.toJsonObject4Form());
        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }


    /**
     * @description
     * 加载出所有state=0和departmentId的相应的岗位数据
     * @author 胡超怡
     *
     * @date 2018/12/3 10:32
     * @return java.lang.String
     * @throws Exception
     */
    public String list() throws Exception {

        /**
         * 查询出岗位集合
         */
        String departmentId = getHttpRequestParameter("position.DepartmentId");
        List<PositionPO> positionList =positionService.searchByDepartment(departmentId, getConnection());




        /**
         * @description
         * 处理岗位集合并赋值给tree并转换为json响应给浏览器
         * @author 胡超怡
         *
         * @date 2018/12/3 10:34
         * @return java.lang.String
         * @throws Exception
         */
        if (positionList != null && positionList.size() != 0) {
            Tree menuRoot = TreeOperator.createForest();

            for (PositionPO po : positionList) {
                Tree tree = new Tree(po.getId(), po.getName(), po.getDepartmentId(), po);
                TreeOperator.add(menuRoot, tree);
            }

            JSONArray array = TreeOperator.getJson4Tree(menuRoot).getJSONArray("children");
            String json = "";
            if (array != null) {
                json = array.toString();
            }

            getResult().setReturnValue(json);
        }

        return SUCCESS;
    }

    public String listAllStaff() throws Exception {
        result = new ReturnObject();

        Connection conn = getConnection();

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT t.*, d.name as 'fromName' FROM ( ");
        sbSQL.append("SELECT id, `name`, parentId, 'department' AS 'typeID', 0 AS 'isUser' FROM system_department ");
        sbSQL.append("UNION ALL SELECT id, `name`, DepartmentId parentId, 'position' AS 'typeID', 0 AS 'isUser' FROM system_position ");
        sbSQL.append("UNION ALL SELECT u.id, u. NAME, positionId parentId, 'user' AS 'typeID', 1 AS 'isUser' ");
        sbSQL.append("FROM system_positionuser pu, system_user u WHERE 1 = 1 AND u.state = 0 AND u.id = pu.userId ");
        sbSQL.append(") t, system_department d WHERE t.isUser = 1 OR t.parentId = d.id GROUP BY t.id, t.name, t.parentId ");


        List<DepartmentPO> departmentList = MySQLDao.search(sbSQL.toString(), null, DepartmentPO.class, null, conn);
        Tree menuRoot = TreeOperator.createForest();
        if (departmentList.size() != 0) {
            for (DepartmentPO departmentpo : departmentList) {
                Tree tree = new Tree(departmentpo.getId(), departmentpo.getName(), departmentpo.getParentId(), departmentpo);
                TreeOperator.add(menuRoot, tree);
            }
        }


        JSONArray array = TreeOperator.getJson4Tree(menuRoot).getJSONArray("children");
        String json = "";
        if (array != null) {
            json = array.toString();
        }

        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setMessage("操作成功");
        result.setReturnValue(json);

        return SUCCESS;
    }

    public String listRecipient() {
        result = new ReturnObject();
        try {
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

            List<PositionPO> positionList = MySQLDao.query(position, PositionPO.class, null, queryType);
            if (positionList.size() != 0) {
                Tree menuRoot = TreeOperator.createForest();

                for (PositionPO po : positionList) {
                    String sql = "select user.sid, user.id, user.state, user.operatorId, user.operateTime, user.name from system_user user, system_positionuser pu where user.state=0 and pu.userid = user.id";
                    if (positionUser != null) {
                        sql += " and pu.positionId='" + Database.encodeSQL(po.getId()) + "'";
                    }
                    result = new ReturnObject();

                    UserPO userPO = new UserPO();
                    List<UserPO> userList = MySQLDao.search(sql, null, UserPO.class, null);

                    Tree tree = new Tree(po.getId(), po.getName(), po.getDepartmentId(), po);
                    TreeOperator.add(menuRoot, tree);
                    for (UserPO uPO : userList) {
                        Tree utree = new Tree(uPO.getId(), uPO.getName(), po.getId(), uPO);
                        TreeOperator.add(menuRoot, utree);
                    }
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

    public String list4Tree() {
        result = new ReturnObject();
        try {
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

            List<PositionPO> positionList = MySQLDao.query(position, PositionPO.class, null, queryType);
            if (positionList.size() != 0) {
                Tree menuRoot = TreeOperator.createForest();

                for (PositionPO po : positionList) {

                    Tree tree = new Tree(po.getId(), po.getName(), po.getDepartmentId(), po);

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
     * @description
     * 根据positionId删除（级联删除，附属的user和permission也会被删除）
     * @author 胡超怡
     *
     * @date 2018/12/3 10:35
     * @return java.lang.String
     * @throws Exception
     */
    public String delete() throws Exception{

        PositionPO position = HttpUtils.getInstanceFromRequest(getRequest(), "position", PositionPO.class);
        int count = positionService.remove(position, getLoginUser().getId(), getConnection());

        if (count != 1) {
            throw new Exception("操作失败");
        }

        return SUCCESS;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public PositionPO getPosition() {
        return position;
    }

    public void setPosition(PositionPO position) {
        this.position = position;
    }
}
