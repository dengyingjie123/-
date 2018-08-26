package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.MessageDetailPO;
import com.youngbook.entity.po.system.MessagePO;
import com.youngbook.entity.vo.system.MessageVO;
import com.youngbook.service.system.MessageDetailService;
import com.youngbook.service.system.MessageService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fugy on 2016/3/11.
 */
public class MessageDetailAction extends BaseAction {
    private MessageDetailPO msgDPO = new MessageDetailPO();
    private MessageDetailService service = new MessageDetailService();

    public String load() throws Exception {
        msgDPO.setState(Config.STATE_CURRENT);
        msgDPO = MySQLDao.load(msgDPO, MessageDetailPO.class);
        getResult().setReturnValue(msgDPO.toJsonObject4Form());
        return SUCCESS;
    }

    public String insertOrUpdate() throws Exception {
        service.insertOrUpdate(msgDPO, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String delete() throws Exception {
        service.delete(msgDPO, getLoginUser(), getConnection());
        return SUCCESS;
    }
}
