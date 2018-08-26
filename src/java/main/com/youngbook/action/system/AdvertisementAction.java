package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.config.Config;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;

public class AdvertisementAction extends BaseAction {

    //@Security(needToken = true)
    public String getLocalDiskAdvs() throws Exception {

        // 获取广告图片所在的位置
        String classLocation = AdvertisementAction.class.getResource("AdvertisementAction.class").toString();
        String rootDirectory = classLocation.substring(0, classLocation.indexOf("WEB-INF"));
        String advFolder = rootDirectory + Config.getSystemVariable("mobi.adv.dir");
        advFolder = advFolder.substring(6, advFolder.length());

        // JSON 数组
        JSONArray array = new JSONArray();

        // 读取文件夹下的文件
        File folder = new File(advFolder);
        if(folder.isDirectory()) {
            String[] files = folder.list();
            for(String file : files) {
                JSONObject object = new JSONObject();
                String advPath = "/core/" + Config.getSystemVariable("mobi.adv.dir") + file;
                object.put("path", advPath);
                array.add(object);
            }
        }

        getResult().setReturnValue(array);

        return SUCCESS;
    }

}
