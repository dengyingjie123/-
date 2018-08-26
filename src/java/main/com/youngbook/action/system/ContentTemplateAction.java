package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.entity.po.system.ContentTemplatePO;
import com.youngbook.entity.vo.system.ContentTemplateVO;
import com.youngbook.service.system.ContentTemplateService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/4/23.
 */
public class ContentTemplateAction extends BaseAction{
    private ContentTemplateVO contentTemplateVO = new ContentTemplateVO();
    private ContentTemplatePO contentTemplate = new ContentTemplatePO();
    private ContentTemplateService service = new ContentTemplateService();
    public String list() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select st.*,kv.V as typeName from system_contenttemplate st,system_kv kv where st.State=0 AND kv.groupName = 'ContentTemplate' AND kv.k = st.Type");
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(),contentTemplateVO,conditions,request,queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }
    @Permission(require = "内容管理-内容模板-新增")
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(contentTemplate, getLoginUser(), getConnection());
        if (count != 1) {            getResult().setMessage("操作失败");

        }
        return SUCCESS;
    }
    @Permission(require = "内容管理-内容模板-修改")
    public String load() throws Exception {

        contentTemplate = service.loadSmsTemplatePO(contentTemplate.getId());

        getResult().setReturnValue(contentTemplate.toJsonObject4Form());

        return SUCCESS;
    }
    @Permission(require = "内容管理-内容模板-删除")
    public String delete() throws Exception {

        service.delete(contentTemplate, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public ContentTemplateVO getContentTemplateVO() {
        return contentTemplateVO;
    }

    public void setContentTemplateVO(ContentTemplateVO contentTemplateVO) {
        this.contentTemplateVO = contentTemplateVO;
    }

    public ContentTemplatePO getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(ContentTemplatePO contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public ContentTemplateService getService() {
        return service;
    }

    public void setService(ContentTemplateService service) {
        this.service = service;
    }
}
