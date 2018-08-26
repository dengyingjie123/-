package com.youngbook.action.oa.finance;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.message.MessagePO;
import com.youngbook.entity.vo.oa.message.MessageVO;
import com.youngbook.service.oa.message.MessageService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MessageAction extends BaseAction {
    private MessagePO message = new MessagePO();
    private MessageVO messageVO = new MessageVO();
    private UserPO user = new UserPO();

    private MessageService service = new MessageService();

    public String insertOrUpdate() throws Exception {
        service.insertOrUpdate(message, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String delete() throws Exception {
        service.delete(message, getLoginUser(), getConnection());
        return SUCCESS;
    }


    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, MessageVO.class);
        Pager pager = service.list(messageVO, conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String mark() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        service.mark(message, user, getConnection());
        return SUCCESS;
    }


    public String load() throws Exception {
        message.setState(Config.STATE_CURRENT);
        message = MySQLDao.load(message, MessagePO.class);
        getResult().setReturnValue(message.toJsonObject4Form());
        return SUCCESS;
    }

    public MessagePO getMessage() {
        return message;
    }
    public void setMessage(MessagePO message) {
        this.message = message;
    }

    public MessageVO getMessageVO() {
        return messageVO;
    }
    public void setMessageVO(MessageVO messageVO) {
        this.messageVO = messageVO;
    }

    public MessageService getService() {
        return service;
    }
    public void setService(MessageService service) {
        this.service = service;
    }

}
