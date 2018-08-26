package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.FilesPO;
import com.youngbook.entity.vo.system.FilesVO;
import com.youngbook.service.system.FilesService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2015/4/3.
 */
public class FilesAction extends BaseAction {
    private FilesPO files = new FilesPO();
    private FilesVO filesVO = new FilesVO();

    @Autowired
    FilesService filesService;

    public String loadByModuleBizId() throws Exception {
        String moduleId = getHttpRequestParameter("moduleId");
        String bizId = getHttpRequestParameter("bizId");
        FilesPO filesPO = filesService.loadByModuleBizId(moduleId, bizId, getConnection());

        if (filesPO != null) {
            getResult().setReturnValue(filesPO);
        }

        return SUCCESS;
    }

    //获取数据方法
    public String list() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();

        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, FilesVO.class);
        Pager pager = filesService.list(filesVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    //加载数据方法
    public String load() throws Exception {

        files.setState(Config.STATE_CURRENT);
        files = MySQLDao.load(files, FilesPO.class);
        getResult().setReturnValue(files.toJsonObject4Form());

        return SUCCESS;
    }

    //添加和修改方法
    public String insertOrUpdate() throws Exception {
        MySQLDao.insertOrUpdate(files, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }

    //删除方法
    public String delete() throws Exception {
        filesService.delete(files, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 获取对应的文件的总数。
     * @return
     * @throws Exception
     */
    public String getBizIdCounts() throws Exception{

        String moduleId = getHttpRequestParameter("moduleId");
        String bizId = getHttpRequestParameter("bizId");


        String errorMessage = "您没有操作权限";
        if (!StringUtils.isEmpty(moduleId)) {
            if (moduleId.equals("18833") && !hasPermission("订单管理_订单附件_操作")) {
                // MyException.newInstance(exceptionMessage, "userId=" + getLoginUser().getId() + "&userName=" + getLoginUser().getName()).throwException();

                filesVO.setErrorMessage(errorMessage);
                getResult().setReturnValue(filesVO.toJsonObject4Form());
                return SUCCESS;
            }

            if (moduleId.equals("18834") && !hasPermission("产品管理_产品附件_操作")) {
                // MyException.newInstance(exceptionMessage, "userId=" + getLoginUser().getId() + "&userName=" + getLoginUser().getName()).throwException();
                filesVO.setErrorMessage(errorMessage);
                getResult().setReturnValue(filesVO.toJsonObject4Form());
                return SUCCESS;
            }
        }


        List<FilesPO> list = filesService.getListFiles(bizId, moduleId, getConnection());

        filesVO.setBizIds(list.size());

        getResult().setReturnValue(filesVO.toJsonObject4Form());
        return SUCCESS;
    }

    public FilesPO getFiles() {
        return files;
    }

    public void setFiles(FilesPO files) {
        this.files = files;
    }


    public FilesVO getFilesVO() {
        return filesVO;
    }

    public void setFilesVO(FilesVO filesVO) {
        this.filesVO = filesVO;
    }
}
