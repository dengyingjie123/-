package com.youngbook.service.oa.message;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.finance.MoneyLogPO;
import com.youngbook.entity.po.oa.message.MessagePO;
import com.youngbook.entity.vo.oa.message.MessageVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2014/12/3.
 */
public class MessageService extends BaseService {

    public Pager list(MessageVO messageVO , List<KVObject> conditions) throws Exception{

        HttpServletRequest request = ServletActionContext.getRequest();


        QueryType queryType = new QueryType(Database.QUERY_FUZZY , Database.NUMBER_EQUAL);

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY , "FromTime desc"));

        StringBuffer sb_sql = new StringBuffer();

        String sql = "select om.sid sid , om.id id , om.state state ,om.OperateTime OperateTime , om.OperatorId OperatorId , om.`Subject` Subject,om.Content Content\n" +
                " , om.SenderId SenderId, om.FromTime FromTime,om.ReceiverId ReceiverId,om.ReceiveTime ReceiveTime , kv_IsRead.V IsRead , kv_Priority.V Priority,\n" +
                " kv_Type.V Type from oa_message om , system_kv kv_IsRead , system_kv kv_Priority , system_kv kv_Type where om.IsRead=kv_IsRead.K \n" +
                "and om.Priority = kv_Priority.K and om.Type=kv_Type.K and om.state=0";

        sb_sql.append(sql);


        Pager pager = Pager.query(sb_sql.toString() , messageVO , conditions , request, queryType);
        return pager;

    }


    public int insertOrUpdate(MessagePO message , UserPO user , Connection conn) throws Exception{
       System.out.println("messageAction = "+message.getId()+user+conn);
        int count = 0;
        //新增，先用id是否为空判断是新增
        if(message.getId().equals("")){
            message.setSid(MySQLDao.getMaxSid("oa_message" , conn));
            message.setId(IdUtils.getUUID32());
            message.setState(Config.STATE_CURRENT);
            message.setOperatorId(user.getId());
            message.setOperateTime(TimeUtils.getNow());
            message.setIsRead(805);
            System.out.println("MessageToString="+message.toString());
            count = MySQLDao.insert(message , conn);
        }

        if( count != 1){
            throw new Exception("数据库异常");
        }
        return count;
    }

    public int delete(MessagePO message, UserPO user, Connection conn) throws Exception {
        int count = 0;

        message = MySQLDao.load(message, MessagePO.class);

        message.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(message, conn);

        if (count == 1) {
            message.setSid(MySQLDao.getMaxSid("oa_message", conn));
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

    public int mark(MessagePO message ,UserPO user, Connection conn) throws Exception{
        int count =0;



        message = MySQLDao.load(message, MessagePO.class);

        message.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(message , conn);
        if(count==1){
            message.setIsRead(806);
            message.setOperateTime(TimeUtils.getNow());
            message.setOperatorId(user.getId());
            message.setState(Config.STATE_CURRENT);
            count=MySQLDao.update(message , conn);
        }
        else if(count!=1){
            throw new Exception("数据库异常");
        }
    return count;
    }

}


