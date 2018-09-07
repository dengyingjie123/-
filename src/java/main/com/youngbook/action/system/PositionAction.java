package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.PositionUserPO;
import com.youngbook.entity.po.UserPO;
import net.sf.json.JSONArray;
import org.omg.PortableServer.POA;

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

    public String insertOrUpdate() throws Exception {

        PositionPO position = HttpUtils.getInstanceFromRequest(getRequest(), "position", PositionPO.class);

        if (position.getId().equals("")) {
            position.setId(IdUtils.getUUID32());
            MySQLDao.insert(position);
        } else {
            MySQLDao.update(position);
        }
        return SUCCESS;
    }

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

    public String list() throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

        String departmentId = getHttpRequestParameter("position.DepartmentId");


        PositionPO position = new PositionPO();
        position.setDepartmentId(departmentId);

        List<PositionPO> positionList = MySQLDao.query(position, PositionPO.class, null, queryType, getConnection());

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

    public String delete() {
        result = new ReturnObject();
        try {
            int count = MySQLDao.deletePhysically(position);
            if (count != 1) { //删除失败
                result.setMessage("删除失成功");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            } else {//删除成功
                result.setMessage("操作失败");
                result.setCode(ReturnObject.CODE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
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
