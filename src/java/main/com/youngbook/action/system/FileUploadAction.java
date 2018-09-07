package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.FilesPO;
import com.youngbook.service.system.FilesService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/4/3.
 */
public class FileUploadAction extends BaseAction {
    private File upload;
    private String uploadFileName;
    private String uploadContentType;
    private FilesPO files = new FilesPO();
    //将数据添加到系统文件中

    @Autowired
    FilesService filesService;


    public String uploadBase64() throws Exception {

        String fileName = getHttpRequestParameter("fileName");
        String base64String = getHttpRequestParameter("base64String");
        String moduleId = getHttpRequestParameter("moduleId");
        String bizId = getHttpRequestParameter("bizId");

        if (StringUtils.isEmptyAny(fileName, base64String, moduleId, bizId)) {
            MyException.newInstance("传入参数不完整").throwException();
        }


        if (StringUtils.isEmpty(fileName)) {
            fileName = "file.data";
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH");
        String time = sdf.format(date);

        // 绝对路径
        String absolutePath = filesService.getRootPathOfUpload() + "/" + time;
        // 相对路径
        String relativePath = "/" + time;

        File file = new File(absolutePath);
        String fileExtensionName = com.youngbook.common.utils.FileUtils.getFileExtensionName(fileName);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(absolutePath + "/" + IdUtils.getUUID32() + "." + fileExtensionName);


        com.youngbook.common.utils.FileUtils.decodeBase64ToFile(file, base64String);


        FilesPO fp = setFiles(fileName, file.getName(), relativePath, fileExtensionName, file.length());
        fp.setBizId(bizId);
        fp.setModuleId(moduleId);
        int count = MySQLDao.insertOrUpdate(fp, "0000", getConnection());
        if (count != 1) {
            MyException.newInstance("上传文件失败").throwException();
        }

        return SUCCESS;
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

    /**
     * 上传文件，根据yyyy/MM/dd/HH和UUID生成一个相对路径，与KV表中的文件存放路径拼接成绝对路径来存放文件
     *
     * @return
     * @throws Exception
     */
    public String upload() throws Exception {
        //用来保存文件原本的名称；
        String fileName = uploadFileName;

        String name = getHttpRequestParameter("name");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH");
        String time = sdf.format(date);

        // 绝对路径
        String absolutePath = filesService.getRootPathOfUpload() + "/" + time;
        // 相对路径
        String relativePath = "/" + time;

        File file = new File(absolutePath);
        String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        setUploadFileName(IdUtils.getUUID32() + "." + suffix);//将文件名设为时间+UUID的格式，如15/04/03/uuid.xxx
        if (!file.exists()) {
            file.mkdir();
        }
        FileUtils.copyFile(upload, new File(file, uploadFileName));

        FilesPO fp = setFiles(name, uploadFileName, relativePath, suffix, upload.length());
        int count = MySQLDao.insertOrUpdate(fp, getLoginUser().getId(), getConnection());

        if (count != 1) {
            MyException.newInstance("上传文件失败").throwException();
        }

        return SUCCESS;
    }


    public String uploadImg() throws Exception {
        //用来保存文件原本的名称；
        String fileName = uploadFileName;
        PrintWriter out = getResponse().getWriter();

        //ckeditor 默认自带的
        String callback = ServletActionContext.getRequest().getParameter("CKEditorFuncNum");

        //检测文件后缀
        String expandedName = "";
        if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
            //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
            expandedName = ".jpg";
        } else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
            //IE6上传的png图片的headimageContentType是"image/x-png"
            expandedName = ".png";
        } else if (uploadContentType.equals("image/gif")) {
            expandedName = ".gif";
        } else if (uploadContentType.equals("image/bmp")) {
            expandedName = ".bmp";
        } else {
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
            out.println("</script>");
            return null;
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH");
        String time = sdf.format(date);

//       String  filePath= "/webapp/"+time;
//        String absolutePath = ServletActionContext.getServletContext().getRealPath(filePath);
        String absolutePath = filesService.getRootPathOfUpload() + "/" + time;
        String relativePath = "/" + time;


        File file = new File(absolutePath);
        String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        setUploadFileName(IdUtils.getUUID32() + "." + suffix);//将文件名设为时间+UUID的格式，如15/04/03/uuid.xxx
        if (!file.exists()) {
            file.mkdir();
        }
        FileUtils.copyFile(upload, new File(file, uploadFileName));

        FilesPO fp = setFiles(fileName, uploadFileName, relativePath, suffix, upload.length());
        int count = MySQLDao.insertOrUpdate(fp, getLoginUser().getId(), getConnection());
        if (count != 1) {
            throw new Exception("上传文件失败");
        }
        //提供给ckeditor预览的url
//        String contextPath = getRequest().getContextPath()+filePath;

        String contextPath = "/core/system/getUploadImg.action?path=" + time + "/" + uploadFileName;
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + contextPath + "','')");
        out.println("</script>");
        out.close();
        return SUCCESS;
    }

    public FilesPO setFiles(String fileName, String storedName, String path, String extensionName, long fileSize) throws UnsupportedEncodingException {
        //将斜杠转义
        path = StringUtils.updatePath(path);

        //设置文件名
        files.setFileName(fileName);
        //设置后缀名
        files.setExtensionName("." + extensionName);
        //设置文件大小
        files.setSize((int) fileSize);
        //设置保存路径
        files.setPath(path + storedName);
        //设置创建时间
        files.setCreateTime(TimeUtils.getNow());
        //设置验证码
        files.setCheckCode(IdUtils.getUUID32().substring(0, 9).toUpperCase());

        return files;
    }


    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public FilesPO getFiles() {
        return files;
    }

    public void setFiles(FilesPO files) {
        this.files = files;
    }

    public FilesService getFilesService() {
        return filesService;
    }

    public void setFilesService(FilesService filesService) {
        this.filesService = filesService;
    }
}
