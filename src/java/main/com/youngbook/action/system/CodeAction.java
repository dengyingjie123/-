package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.CodePO;
import com.youngbook.entity.vo.system.CodeVO;
import com.youngbook.service.system.CodeService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2015/4/22.
 * 系统管理
 */
public class CodeAction extends BaseAction {
    /**
     * 系统管理PO。VO service 类
     */
    private CodeVO codeVO = new CodeVO();
    private CodePO code = new CodePO();
    private CodeService service = new CodeService();

    /**
     * 添加更新类
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-验证码-新增")
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(code, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取数据
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-验证码-修改")
    public String load() throws Exception {

        code = service.loadCodePO(code.getId());

        getResult().setReturnValue(code.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-验证码-删除")
    public String delete() throws Exception {

        service.delete(code, getLoginUser(), getConnection());

        return SUCCESS;
    }

    /**
     * 获取数据列表类
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request,CodeVO.class);
        Pager pager  =service.list(codeVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public CodeVO getCodeVO() {
        return codeVO;
    }
    public void setCodeVO(CodeVO codeVO) {
        this.codeVO = codeVO;
    }

    public CodePO getCode() {
        return code;
    }

    public void setCode(CodePO code) {
        this.code = code;
    }

    public CodeService getService() {
        return service;
    }

    public void setService(CodeService service) {
        this.service = service;
    }
}
