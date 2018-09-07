package com.youngbook.service.cms;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.cms.ColumnBelongPO;
import com.youngbook.entity.po.cms.ColumnPO;
import com.youngbook.entity.po.cms.ColumnPermissionPO;
import com.youngbook.entity.vo.cms.ColumnVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("columnService")
public class ColumnService extends BaseService {

    //更新栏目
    public int insertOrUpdate(ColumnPO column, UserPO user, Connection conn, HttpServletRequest request) throws Exception {
        int count = 0;
        String positionIdsStr = request.getParameter("positionIds");
        String deptIds = request.getParameter("columnBelong.departmentId");
        if (column.getId().equals("")) {
            column.setId(IdUtils.getUUID32());
            if (positionIdsStr != null && !positionIdsStr.equals("")) {
                count = saveColumnPermission(column, positionIdsStr, user, conn);
            }
            if (deptIds != null && !deptIds.equals("")) {
                count = saveColumnBelong(column, deptIds, user, conn);
            }
            column.setSid(MySQLDao.getMaxSid("cms_column", conn));
            column.setState(Config.STATE_CURRENT);
            column.setOperatorId(user.getId());
            column.setOperateTime(TimeUtils.getNow());
            if (column.getParentId() == null || column.getParentId().equals("")) {
                column.setParentId("-1");
            }
            count = MySQLDao.insert(column, conn);
        }
        // 更新
        else {
            ColumnPO temp = new ColumnPO();
            temp.setSid(column.getSid());
            temp = MySQLDao.load(temp, ColumnPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                if (positionIdsStr != null && !positionIdsStr.equals("")) {
                    count = saveColumnPermission(column, positionIdsStr, user, conn);
                }
                column.setSid(MySQLDao.getMaxSid("cms_column", conn));
                column.setState(Config.STATE_CURRENT);
                column.setOperatorId(user.getId());
                column.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(column, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    //删除
    public int delete(ColumnPO column, UserPO user, Connection conn) throws Exception {
        int count = 0;
        column = MySQLDao.load(column, ColumnPO.class);
        column.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(column, conn);
        if (count == 1) {
            column.setSid(MySQLDao.getMaxSid("cms_column", conn));
            column.setState(Config.STATE_DELETE);
            column.setOperateTime(TimeUtils.getNow());
            column.setOperatorId(user.getId());
            count = MySQLDao.insert(column, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    public int saveColumnPermission(ColumnPO column, String positionIdsStr, UserPO user, Connection conn) throws Exception {
        int count = 0;
        String sql = "select * from CMS_ColumnPermission where state=0 and columnId='" + Database.encodeSQL(column.getId()) + "'";
        ColumnPermissionPO columnPermission = new ColumnPermissionPO();
        String[] positionIds = positionIdsStr.split(",");
        List<ColumnPermissionPO> list = MySQLDao.query(sql, ColumnPermissionPO.class, null);
        if (list != null && list.size() > 0) {
            for (ColumnPermissionPO cpPO : list) {
                cpPO.setState(Config.STATE_UPDATE);
                count = MySQLDao.update(cpPO, conn);
            }
        }
        for (String s : positionIds) {
            columnPermission.setSid(MySQLDao.getMaxSid("cms_columnpermission", conn));
            columnPermission.setId(IdUtils.getUUID32());
            columnPermission.setState(Config.STATE_CURRENT);
            columnPermission.setOperatorId(user.getId());
            columnPermission.setOperateTime(TimeUtils.getNow());
            columnPermission.setColumnId(column.getId());
            columnPermission.setPositionId(s);
            count += MySQLDao.insert(columnPermission, conn);
        }
        return count;
    }

    private int saveColumnBelong(ColumnPO column, String deptIdsStr, UserPO user, Connection conn) throws Exception {
        int count = 0;
        ColumnBelongPO columnBelong = new ColumnBelongPO();
        String[] deptIds = deptIdsStr.split(",");
        for (String str : deptIds) {
            columnBelong.setSid(MySQLDao.getMaxSid("cms_columnbelong", conn));
            columnBelong.setId(IdUtils.getUUID32());
            columnBelong.setState(Config.STATE_CURRENT);
            columnBelong.setOperatorId(user.getId());
            columnBelong.setOperateTime(TimeUtils.getNow());
            columnBelong.setColumnId(column.getId());
            columnBelong.setDepartmentId(str);
            count += MySQLDao.insert(columnBelong, conn);
        }
        return count;
    }

    public JSONArray getColumnTreeList(ColumnPO column, HttpServletRequest request, String departmentId) throws Exception {
        JSONArray array = new JSONArray();
        String sql = "select C.sid,c.id,c.state,c.OperatorId,c.OperateTime,c.ParentId,c.Name,c.Description\n" +
                "from cms_column C\n" +
                "      LEFT JOIN cms_columnbelong B on B.columnId=C.id\n" +
                "      LEFT JOIN system_department D on B.departmentId=D.ID\n" +
                "where C.state=0";
        if (departmentId != null && !departmentId.equals("")) {
            sql = sql + " and D.id like '%" + Database.encodeSQL(departmentId) + "%'";
        }
        List<ColumnPO> list = MySQLDao.query(sql, ColumnPO.class, null);
        Tree columnRoot = TreeOperator.createForest();
        for (int i = 0; i < list.size(); i++) {
            ColumnPO c = list.get(i);
            Tree leaf = new Tree(c.getId(), c.getName(), c.getParentId(), c);
            TreeOperator.add(columnRoot, leaf);
        }
        List<ColumnVO> listSummary = new ArrayList<ColumnVO>();
        ColumnVO columnVO = new ColumnVO();
        String id = request.getParameter("id");
        if (id != null && !id.equals("")) {
            column.setId(id);
            Tree columnTree = TreeOperator.find(columnRoot, column.getId());
            for (int i = 0; columnTree != null && columnTree.getChildren() != null && i < columnTree.getChildren().size(); i++) {
                Tree leaf = columnTree.getChildren().get(i);
                if (leaf.getChildren().size() > 0) {
                    columnVO.set_state("closed");
                }
                columnVO.setId(leaf.getId());
                columnVO.setName(leaf.getName());
                columnVO.setParentId(leaf.getParentId());
                ColumnPO po = (ColumnPO) leaf.getData();
                columnVO.setSid(po.getSid());
                columnVO.setDescription(po.getDescription());
                array.add(columnVO.toJsonObject4Treegrid());
            }
        } else {
            String strSql = sql + " and c.ParentId='-1'";
            List<ColumnPO> listP = MySQLDao.query(strSql, ColumnPO.class, null);
            if (listP != null && list.size() > 0) {
                for (ColumnPO cPO : listP) {
                    Tree leaf = TreeOperator.find(columnRoot, cPO.getId());
                    if (leaf.getChildren().size() > 0) {
                        columnVO.set_state("closed");
                    }
                    columnVO.setId(leaf.getId());
                    columnVO.setName(leaf.getName());
                    columnVO.setParentId(leaf.getParentId());
                    ColumnPO po = (ColumnPO) leaf.getData();
                    columnVO.setSid(po.getSid());
                    columnVO.setDescription(po.getDescription());
                    array.add(columnVO.toJsonObject4Treegrid());
                }
            }

        }
        return array;
    }

    public static String getSql(String departmentId) {
        String sql = "select C.id,C.sid,C.Name,C.Description,c.ParentId\n" +
                "from cms_column C\n" +
                "     LEFT JOIN cms_columnbelong B on B.columnId=C.id\n" +
                "\t\t LEFT JOIN system_department D on B.departmentId=D.ID\n" +
                "where C.state=0";
        if (departmentId != null && !departmentId.equals("")) {
            sql = sql + " and D.id like '%" + Database.encodeSQL(departmentId) + "%'";
        }
        return sql;
    }

    /**
     * 根据 ID 查询栏目
     * 用法：在 Action 中调用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-7-20
     *
     * @author 邓超
     * @return Pager
     * @throws Exception
     */
    public ColumnPO load4W(String id, Connection conn) throws Exception {

        ColumnPO po = null;
        String sql = "select * from cms_column c where c.state = 0 and c.id = '" + Database.encodeSQL(id) + "'";
        List<ColumnPO> columns = MySQLDao.query(sql, ColumnPO.class, new ArrayList<KVObject>(), conn);
        if(columns.size() == 1) {
            po = columns.get(0);
        }
        return po;
    }

    /**
     * 获取栏目列表
     * 用法：在 Action 中调用
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月10日
     */
   public  Pager getColumns(HttpServletRequest request) throws  Exception{
       ColumnVO column=new ColumnVO();
       StringBuffer sbSQL=new StringBuffer();
       sbSQL.append("select c.id,c.name,c.description from cms_column c where c.state = 0");
       Pager columns = Pager.search(sbSQL.toString(), null, column, null, request, null);
       return  columns;
   }

    public List<ColumnVO> loadColumn4W(Connection conn) throws Exception {

        StringBuffer sbSQL=new StringBuffer();
        sbSQL.append("select * from cms_column c where c.state = 0 ");
        List<KVObject>  paramters=new ArrayList<KVObject>();
        List<ColumnVO> column = MySQLDao.search(sbSQL.toString(), paramters, ColumnVO.class, null, conn);
            return column;
    }

    public Pager getColumn4W(HttpServletRequest request, List<KVObject> conditions, QueryType queryType) throws Exception {
        ColumnVO column=new ColumnVO();
        StringBuffer sbSQL=new StringBuffer();
        sbSQL.append("select c.id,c.name,c.description from cms_column c where c.state = 0 ");
        Pager columns = Pager.search(sbSQL.toString(), null, column, conditions, request, queryType);
        return columns;
    }

}