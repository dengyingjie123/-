package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.MessageDetailPO;
import com.youngbook.entity.po.system.MessagePO;
import com.youngbook.entity.vo.system.MessageVO;
import com.youngbook.service.system.MessageDetailService;
import com.youngbook.service.system.MessageService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by fugy on 2016/3/11.
 */
public class MessageAction extends BaseAction {

    private MessagePO message = new MessagePO();
    private MessageVO messageVO = new MessageVO();
    private MessageService service = new MessageService();
    MessageDetailService msgDS = new MessageDetailService();

    public String list() throws Exception {

        Connection conn = getConnection();

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        Pager pager = service.list(messageVO, getLoginUser(), conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String listReceived() throws Exception {

        Connection conn = getConnection();

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, MessageVO.class);
        Pager pager = service.listReceived(messageVO, getLoginUser(), conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String listPublished() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        Pager pager = service.listPublished(messageVO, getLoginUser(), conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String listDraft() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        Pager pager = service.listDraft(messageVO, getLoginUser(), conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }



    public String load() throws Exception {
            message.setState(Config.STATE_CURRENT);
            message = service.load(message, getLoginUser(), getConnection());
            getResult().setReturnValue(message.toJsonObject4Form());
            return SUCCESS;
    }



    public String loadEdit() throws Exception {
        message.setState(Config.STATE_CURRENT);
        message = service.loadEdit(message, getLoginUser(), getConnection());
        getResult().setReturnValue(message.toJsonObject4Form());
        return SUCCESS;
    }


    public String insertOrUpdate() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        service.sendOrSaveDraft(message, getLoginUser(), getConnection());
        return SUCCESS;
    }


    public String save() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        service.save(message, getLoginUser(), getConnection());
        return SUCCESS;
    }


    public String delete() throws Exception {
        service.delete(message, getLoginUser(), getConnection());
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
}
