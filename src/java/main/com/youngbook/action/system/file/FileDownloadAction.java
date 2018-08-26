package com.youngbook.action.system.file;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.system.FilesPO;
import com.youngbook.service.system.FilesService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by 周海鸿 on 2015/7/1.
 * 实现文件下载
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class FileDownloadAction extends BaseAction {

    private String filesId;
    private String moduleId;
    private String bizId;
    //文件的地址
    private String filePath;
    //文件名的成员变量，用于接受传递过来的文件名参数
    private String fileName;
    //文件流对象
    private InputStream fileInput;
    //下载默认名
    private String downloadName;

    @Autowired
    FilesService filesService;



    /**
     * 通过ServletContext的getResourceAsStream方法获取指定文件流
     *
     * 获取方式：
     * 1.
     *
     * @return
     * @throws Exception
     */
    public InputStream getFileInput() {
        try {

            FilesPO filesPO = null;

            if (!StringUtils.isEmpty(filesId)) {
                filesPO = filesService.loadById(filesId, getConnection());

                if (filesPO != null) {
                    filePath = filesPO.getPath();

                    // 拼接绝对路径
                    String path = filesService.getRootPathOfUpload() + filePath;
                    // 返回文件流
                    return new FileInputStream(new File(path));
                }
            }

            if (!StringUtils.isEmptyAny(moduleId, bizId)) {
                filesPO = filesService.loadByModuleBizId(moduleId, bizId, getConnection());

                if (filesPO != null) {
                    filePath = filesPO.getPath();

                    // 拼接绝对路径
                    String path = filesService.getRootPathOfUpload() + filePath;
                    // 返回文件流
                    return new FileInputStream(new File(path));
                }
            }

            if (!StringUtils.isEmpty(filePath)) {
                // 拼接绝对路径
                String path = filesService.getRootPathOfUpload() + filePath;
                // 返回文件流
                return new FileInputStream(new File(path));
            }

        }
        catch (Exception e) {
            try {
                MyException.deal(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public void setFileInput(InputStream fileInput) {
        this.fileInput = fileInput;
    }

    public String execute() {
        return SUCCESS;
    }

    ////通过ServletContext的getMimeType方法获取MIME类型
    public String getContentType() {
        String contentType = "";
        try {
            contentType = ServletActionContext.getServletContext().getMimeType(fileName);
        } catch (Exception e) {
            try {
                MyException.deal(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return contentType;
    }
    //下载文件名

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getFilesId() {
        return filesId;
    }

    public void setFilesId(String filesId) {
        this.filesId = filesId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }


}

