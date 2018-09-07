package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.MessageDetailPO;
import com.youngbook.entity.po.system.MessagePO;
import com.youngbook.entity.vo.system.MessageVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import org.springframework.jca.cci.CannotGetCciConnectionException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fugy on 2016/3/11.
 */
public class MessageService extends BaseService {

    public Pager list(MessageVO message, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        StringBuffer SQL = new StringBuffer();
        String uid = user.getId();

        SQL.append("SELECT a.id, a.sid, CASE a.isTop WHEN '3946' Then '置顶' ELSE '否' END AS isTop, ");
        SQL.append("CASE a.level WHEN '32084' Then '一般' WHEN '32083' Then '紧急' END AS level, ");
        SQL.append("a.publishedTime, a.title, c.name AS 'senderName', d.name AS 'senderDepartmentName', ");
        SQL.append("a.orders, CASE b.isRead WHEN 0 THEN '未读' WHEN 1 THEN '已读' END AS isRead ");
        SQL.append("FROM system_systemmessage a, system_systemmessagedetail b, ");
        SQL.append("system_user c, system_department d, system_positionuser e, system_position f ");
        SQL.append("WHERE ((a.operatorId = '" + uid + "' OR a.sendRange = '32079') ");
        SQL.append("OR (b.recipientId = '" + uid + "') AND a.id = b.messageId) ");
        SQL.append("AND c.Id = a.operatorId AND e.userId = a.operatorId AND e.states = 1 ");
        SQL.append("AND e.positionId= f.Id AND d.id = f.DepartmentId ");
        SQL.append("AND a.state = 0 AND b.state = 0 AND c.state = 0 ");
        SQL.append("GROUP BY a.isTop, a.orders, a.publishedTime, a.id ");

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.isTop " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.orders " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.publishedTime " + Database.ORDERBY_DESC));
        Pager pager = Pager.query(SQL.toString(), message, conditions, request, queryType, conn);
        return pager;
    }

    public Pager listDraft(MessageVO message, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        StringBuffer SQL = new StringBuffer();
        String uid = user.getId();

        SQL.append("SELECT a.id, a.sid, CASE a.isTop WHEN '3946' Then '置顶' ELSE '否' END AS isTop, ");
        SQL.append("CASE a.level WHEN '32084' Then '一般' WHEN '32083' Then '紧急' END AS level, ");
        SQL.append("a.publishedTime, a.title, c.name AS 'senderName', d.name AS 'senderDepartmentName', ");
        SQL.append("a.orders FROM system_systemmessage a, system_systemmessagedetail b, ");
        SQL.append("system_user c, system_department d, system_positionuser e, system_position f ");
        SQL.append("WHERE (a.operatorId = '" + uid + "' AND a.status = '32076') ");
        SQL.append("AND c.Id = a.operatorId AND e.userId = a.operatorId AND e.states = 1 ");
        SQL.append("AND e.positionId= f.Id AND d.id = f.DepartmentId ");
        SQL.append("AND a.state = 0 AND b.state = 0 AND c.state = 0 ");
        SQL.append("GROUP BY a.isTop, a.orders, a.publishedTime, a.id ");

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.isTop " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.orders " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.publishedTime " + Database.ORDERBY_DESC));
        Pager pager = Pager.query(SQL.toString(), message, conditions, request, queryType,conn);
        return pager;
    }

    public Pager listPublished(MessageVO message, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        StringBuffer SQL = new StringBuffer();
        String uid = user.getId();

        SQL.append("SELECT a.id, a.sid, CASE a.isTop WHEN '3946' Then '置顶' ELSE '否' END AS isTop, ");
        SQL.append("CASE a.level WHEN '32084' Then '一般' WHEN '32083' Then '紧急' END AS level, ");
        SQL.append("a.publishedTime, a.title, c.name AS 'senderName', d.name AS 'senderDepartmentName', ");
        SQL.append("a.orders FROM system_systemmessage a, system_systemmessagedetail b, ");
        SQL.append("system_user c, system_department d, system_positionuser e, system_position f ");
        SQL.append("WHERE (a.operatorId = '" + uid + "' AND a.status = '32077') ");
        SQL.append("AND c.Id = a.operatorId AND e.userId = a.operatorId AND e.states = 1 ");
        SQL.append("AND e.positionId= f.Id AND d.id = f.DepartmentId ");
        SQL.append("AND a.state = 0 AND b.state = 0 AND c.state = 0 ");
        SQL.append("GROUP BY a.isTop, a.orders, a.publishedTime, a.id ");

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.isTop " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.orders " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.publishedTime " + Database.ORDERBY_DESC));
        Pager pager = Pager.query(SQL.toString(), message, conditions, request, queryType, conn);
        return pager;
    }

    public Pager listReceived(MessageVO message, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {


        StringBuffer SQL = new StringBuffer();
        String uid = user.getId();
        String cStr = "";



        String sql = "SELECT a.id FROM system_systemmessage a, system_systemmessagedetail b " +
                "WHERE a.sendRange = '32079' AND b.recipientId = '" + uid + "' " +
                "AND a.id = b.messageId ";

        List<MessagePO> list = MySQLDao.query(sql, MessagePO.class, null, conn);
        if (list == null || list.size() == 0) {
            cStr = "''";
        } else {
            StringBuilder sb = new StringBuilder();
            for (MessagePO mp : list) {
                sb.append(", ");
                sb.append("'" + mp.getId() + "'");
            }
            cStr = sb.toString().substring(2);
        }


        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        SQL.append("select id, sid, isTop, level, publishedTime, title, senderName, senderDepartmentName, orders, isRead FROM ( ");
        SQL.append("(SELECT a.id, a.sid, CASE a.isTop WHEN '3946' Then '置顶' ELSE '否' END AS isTop, ");
        SQL.append("CASE a.level WHEN '32084' Then '一般' WHEN '32083' Then '紧急' END AS level, ");
        SQL.append("a.publishedTime, a.title, c.name AS 'senderName', d.name AS 'senderDepartmentName', ");
        SQL.append("a.orders, '未读' AS isRead ");
        SQL.append("FROM system_systemmessage a, ");
        SQL.append("system_user c, system_department d, system_positionuser e, system_position f WHERE a.status = '32077' AND ");
        SQL.append("a.sendRange = '32079' AND a.id NOT IN (" + cStr + ") ");
        SQL.append("AND c.Id = a.operatorId AND e.userId = a.operatorId AND e.states = 1 ");
        SQL.append("AND e.positionId= f.Id AND d.id = f.DepartmentId ");
        SQL.append("AND a.state = 0 AND c.state = 0) ");
        SQL.append("UNION ");
        SQL.append("(SELECT a.id, a.sid, CASE a.isTop WHEN '3946' Then '置顶' ELSE '否' END AS isTop, ");
        SQL.append("CASE a.level WHEN '32084' Then '一般' WHEN '32083' Then '紧急' END AS level, ");
        SQL.append("a.publishedTime, a.title, c.name AS 'senderName', d.name AS 'senderDepartmentName', ");
        SQL.append("a.orders, CASE b.isRead WHEN 1 THEN '已读' ELSE '未读' END AS isRead ");
        SQL.append("FROM system_systemmessage a, system_systemmessagedetail b, ");
        SQL.append("system_user c, system_department d, system_positionuser e, system_position f WHERE a.status = '32077' AND ");
        SQL.append("b.recipientId = '" + uid + "' AND a.id = b.messageId ");
        SQL.append("AND c.Id = a.operatorId AND e.userId = a.operatorId AND e.states = 1 ");
        SQL.append("AND e.positionId= f.Id AND d.id = f.DepartmentId ");
        SQL.append("AND a.state = 0 AND b.state = 0 AND c.state = 0)) AS u ");
        SQL.append("GROUP BY isRead, id, isTop, orders, publishedTime ");
        SQL.append("ORDER BY isRead DESC, isTop DESC, orders ASC, publishedTime DESC ");

        Pager pager = Pager.query(SQL.toString(), message, conditions, request, queryType, conn);
        return pager;
    }


    public int send2Users(MessagePO message, UserPO user, Connection conn) throws Exception {
        Map<String, String> map = JSONDao.toMap(message.getStaffIds().substring(1));
        String[] mids;
        for (String ix : map.keySet()) {
            JSONArray arrJS = JSONArray.fromObject(map.get(ix));
            mids = (String[]) JSONArray.toArray(arrJS, String.class);
            for (String id : mids) {
                MessageDetailPO msgDetail = new MessageDetailPO();
                msgDetail.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
                msgDetail.setId(IdUtils.getUUID32());
                msgDetail.setState(Config.STATE_CURRENT);
                msgDetail.setOperatorId(user.getId());
                msgDetail.setOperateTime(TimeUtils.getNow());
                msgDetail.setRecipientId(id);
                msgDetail.setMessageId(message.getId());
                msgDetail.setType(message.getType());
                msgDetail.setIsRead(0);
                MySQLDao.insert(msgDetail, conn);
            }
        }
        return 1;
    }

    public void send2Departments(MessagePO message, UserPO user, Connection conn) throws Exception {
        Map<String, String> map = JSONDao.toMap(message.getDepartmentIds().substring(1));
        String[] mids;
        for (String ix : map.keySet()) {
            JSONArray arrJS = JSONArray.fromObject(map.get(ix));
            mids = (String[]) JSONArray.toArray(arrJS, String.class);
            for (String id : mids) {
                String sql = "SELECT Id, Name, DepartmentId, DepartmentName FROM `system_position` where DepartmentId = '" + id + "'";
                List<PositionPO> positionList = MySQLDao.search(sql, null, PositionPO.class, null, conn);

                if (positionList != null && positionList.size() != 0) for (PositionPO po : positionList) {
                    sql = "select user.sid, user.id, user.state, user.operatorId, user.operateTime, user.name " +
                            "from system_user user, system_positionuser pu where user.state=0 and pu.userid = user.id" +
                            " and pu.positionId='" + Database.encodeSQL(po.getId()) + "'";

                    List<UserPO> userList = MySQLDao.search(sql, null, UserPO.class, null, conn);

                    for (UserPO uPO : userList) {
                        sql = "SELECT id FROM `system_systemmessagedetail` WHERE recipientId = '" + uPO.getId() + "' AND messageId = '" + message.getId() + "'";
                        List<DepartmentPO> dpoList = MySQLDao.search(sql, null, DepartmentPO.class, null, conn);
                        if (dpoList == null || dpoList.size() == 0) {
                            MessageDetailPO msgDetail = new MessageDetailPO();
                            msgDetail.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
                            msgDetail.setId(IdUtils.getUUID32());
                            msgDetail.setState(Config.STATE_CURRENT);
                            msgDetail.setOperatorId(user.getId());
                            msgDetail.setOperateTime(TimeUtils.getNow());
                            msgDetail.setRecipientId(uPO.getId());
                            msgDetail.setMessageId(message.getId());
                            msgDetail.setType(message.getType());
                            msgDetail.setIsRead(0);
                            MySQLDao.insert(msgDetail, conn);
                        }
                    }
                }
            }
        }
    }

    public void saveDraft(MessagePO message, UserPO user, Connection conn) throws Exception {
        MySQLDao.insertOrUpdate(message, user.getId(), conn);
    }

    public void send2All(MessagePO message, UserPO user, Connection conn) throws Exception {
        if (!message.getStaffIds().equals("")) {
            send2Users(message, user, conn);
        }

        if (!message.getDepartmentIds().equals("{}") && message.getSendRange().equals("32080")) {
            send2Departments(message, user, conn);
        }
    }

    public int sendOrSaveDraft(MessagePO message, UserPO user, Connection conn) throws Exception {

        saveDraft(message, user, conn);

        if (!message.getStatus().equals("32076")) {
            send2All(message, user, conn);
        }

        return 1;
    }

    public int insertOrUpdate(MessagePO message, String json, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (message.getId().equals("")) {
            String messageId = IdUtils.getUUID32();

            message.setSid(MySQLDao.getMaxSid("system_systemmessage", conn));
            message.setId(messageId);
            message.setState(Config.STATE_CURRENT);
            message.setOperatorId(user.getId());
            message.setOperateTime(TimeUtils.getNow());

            if (message.getPublishedTime().equals("")) {
                message.setPublishedTime(TimeUtils.getNow());
            }
            count = MySQLDao.insert(message, conn);

            //入库详细表
            if (!json.equals("")) {
                Map<String, String> map = JSONDao.toMap(json);
                String[] ids;
                for (String ix : map.keySet()) {
                    JSONArray arrJS = JSONArray.fromObject(map.get(ix));
                    ids = (String[]) JSONArray.toArray(arrJS, String.class);
                    for (String id : ids) {
                        MessageDetailPO msgDetail = new MessageDetailPO();
                        msgDetail.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
                        msgDetail.setId(IdUtils.getUUID32());
                        msgDetail.setState(Config.STATE_CURRENT);
                        msgDetail.setOperatorId(user.getId());
                        msgDetail.setOperateTime(TimeUtils.getNow());
                        msgDetail.setRecipientId(id);
                        msgDetail.setMessageId(messageId);
                        msgDetail.setType(message.getType());
                        msgDetail.setIsRead(0);

                        count = MySQLDao.insert(msgDetail, conn);
                    }
                }
            }
        }
        // 更新
        else {
            MessagePO mpo = new MessagePO();
            mpo = MySQLDao.load(mpo, MessagePO.class);
            mpo.setSid(message.getSid());
            mpo.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(mpo, conn);
            if (count == 1) {
                message.setSid(MySQLDao.getMaxSid("system_systemmessage", conn));
                message.setState(Config.STATE_CURRENT);
                message.setOperatorId(user.getId());
                message.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(message, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    public int save(MessagePO message, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (message.getId().equals("")) {
            String messageId = IdUtils.getUUID32();

            message.setSid(MySQLDao.getMaxSid("system_systemmessage", conn));
            message.setId(messageId);
            message.setState(Config.STATE_CURRENT);
            message.setOperatorId(user.getId());
            message.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(message, conn);
        }
        // 更新
        else {
            MessagePO mpo = new MessagePO();
            mpo = MySQLDao.load(mpo, MessagePO.class);
            mpo.setSid(message.getSid());
            mpo.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(mpo, conn);
            if (count == 1) {
                message.setSid(MySQLDao.getMaxSid("system_systemmessage", conn));
                message.setState(Config.STATE_CURRENT);
                message.setOperatorId(user.getId());
                message.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(message, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    public MessagePO loadEdit(MessagePO message, UserPO user, Connection conn) throws Exception {
        MessagePO m = MySQLDao.load(message, MessagePO.class, conn);
        return m;
    }

    public MessagePO load(MessagePO message, UserPO user, Connection conn) throws Exception {
        MessagePO m = MySQLDao.load(message, MessagePO.class);

        MessageDetailPO msgD = new MessageDetailPO();
        msgD.setMessageId(m.getId());
        msgD.setRecipientId(user.getId());
        MessageDetailPO md = MySQLDao.load(msgD, MessageDetailPO.class);
        if (null == md) {
            int count = 0;
            if (m.getSendRange().equals("32079")) {
                MessageDetailPO msgDetail = new MessageDetailPO();
                msgDetail.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
                msgDetail.setId(IdUtils.getUUID32());
                msgDetail.setState(Config.STATE_CURRENT);
                msgDetail.setOperatorId(user.getId());
                msgDetail.setOperateTime(TimeUtils.getNow());
                msgDetail.setRecipientId(user.getId());
                msgDetail.setMessageId(m.getId());
                msgDetail.setType(m.getType());
                msgDetail.setIsRead(1);
                msgDetail.setReadTime(TimeUtils.getNow());
                count = MySQLDao.insert(msgDetail, conn);
            }
        } else if (md.getIsRead() == 0) {
            md.setOperatorId(user.getId());
            md.setIsRead(1);
            md.setReadTime(TimeUtils.getNow());
            MySQLDao.insertOrUpdate(md, user.getId(), conn);
        }
        return m;
    }

    public int delete(MessagePO message, UserPO user, Connection conn) throws Exception {
        int count = 0;
        message = MySQLDao.load(message, MessagePO.class);
        message.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(message, conn);
        if (count == 1) {
            message.setSid(MySQLDao.getMaxSid("system_systemmessage", conn));
            message.setState(Config.STATE_DELETE);
            message.setOperateTime(TimeUtils.getNow());
            message.setOperatorId(user.getId());
            count = MySQLDao.insert(message, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
}












