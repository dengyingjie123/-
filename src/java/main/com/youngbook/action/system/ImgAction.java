package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.service.system.FilesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;


public class ImgAction extends BaseAction {

    private InputStream inputStream = null;

    @Autowired
    FilesService filesService;

    public String getUploadImg() throws Exception {

        String imgPath = filesService.getRootPathOfUpload();

        String path = getRequest().getParameter("path");
        if (path==null) {
            MyException.newInstance( "路径不能为空").throwException();
        }
        try {
            File f = new File(imgPath,path);
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bi, "png", imOut);
            inputStream = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            throw new Exception("图片无法预览");
        }
        return SUCCESS;

    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
