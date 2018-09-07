package com.youngbook.action.info;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.info.LegalAgreementPO;
import com.youngbook.entity.vo.info.LegalAgreementVO;
import com.youngbook.service.info.LegalAgreementService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by 邓超
 * Date 2015/4/1
 */
public class LegalAgreementAction extends BaseAction {

    private LegalAgreementVO legalAgreementVO = new LegalAgreementVO();
    private LegalAgreementPO legalAgreement = new LegalAgreementPO();
    private LegalAgreementService service = new LegalAgreementService();
    // 验证码流
    ByteArrayInputStream inputStream;

    /**
     * 查询
     * @return String
     * @throws Exception
     */
    public String list() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuffer sb = new StringBuffer();
        //使用StringBuffer组装sql语句
        sb.append("select distinct info.*, user.name username from info_legalagreement info, system_user user where info.state = 0 and user.id = info.operatorId");
        //将键值对（KV）封装为KVObject作为附加查询条件
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, LegalAgreementVO.class);
        //String类型用FUZZY查询，数字类型用EXACTLY查询
        QueryType queryType = new QueryType(Database.QUERY_FUZZY , Database.QUERY_EXACTLY);
        Pager pager = Pager.query(sb.toString() , legalAgreementVO , conditions, request , queryType);
        //将返回值处理为系统中指定的返回类型 ReturnObject
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 增加或修改
     * @return String
     * @throws Exception
     */
    @Permission(require = "内容管理-法律协议-新增")
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(legalAgreement, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 加载PO
     * @return String
     * @throws Exception
     */
    @Permission(require = "内容管理-法律协议-修改")
    public String load() throws Exception{
        // 从浏览器传过来的ID
        String id = legalAgreement.getId();
        LegalAgreementPO po = new LegalAgreementPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        getResult().setReturnValue(MySQLDao.load(po, LegalAgreementPO.class).toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除
     * @return String
     * @throws Exception
     */
    @Permission(require = "内容管理-法律协议-删除")
    public String delete() throws Exception {
        service.delete(legalAgreement, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 网站：查询
     * @return String
     * @throws Exception
     */
    public String list4Web() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuffer sb = new StringBuffer();
        //使用StringBuffer组装sql语句
        sb.append("select info.* from info_legalagreement info where info.state = 0");
        //将键值对（KV）封装为KVObject作为附加查询条件
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, LegalAgreementVO.class);
        //String类型用FUZZY查询，数字类型用EXACTLY查询
        QueryType queryType = new QueryType(Database.QUERY_FUZZY , Database.QUERY_EXACTLY);
        Pager pager = Pager.query(sb.toString() , legalAgreementVO , conditions, request , queryType);
        //将返回值处理为系统中指定的返回类型 ReturnObject
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public LegalAgreementVO getLegalAgreementVO() {return legalAgreementVO;}
    public void setLegalAgreementVO(LegalAgreementVO legalAgreementVO) {this.legalAgreementVO = legalAgreementVO;}

    public LegalAgreementPO getLegalAgreement() {return legalAgreement;}
    public void setLegalAgreement(LegalAgreementPO legalAgreement) {this.legalAgreement = legalAgreement;}

    public LegalAgreementService getService() {return service;}
    public void setService(LegalAgreementService service) {this.service = service;}

    public ByteArrayInputStream getInputStream() {return inputStream;}
    public void setInputStream(ByteArrayInputStream inputStream) {this.inputStream = inputStream;}

}
