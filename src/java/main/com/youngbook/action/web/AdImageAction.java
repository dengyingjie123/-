package com.youngbook.action.web;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.web.AdImagePO;
import com.youngbook.entity.vo.oa.task.TaskVO;
import com.youngbook.entity.vo.web.AdImageVO;
import com.youngbook.service.web.AdImageService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/4/22.
 */
public class AdImageAction extends BaseAction {
    private AdImageVO adImageVO = new AdImageVO();
    private AdImagePO adImage = new AdImagePO();

    @Autowired
    AdImageService adImageService;

    public String list() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT wa.*,kv.V as catalogType, kv2.V as IsAvaliableType from web_adimage wa,system_kv kv, system_kv kv2  WHERE wa.state = 0 " +
                "AND kv.groupName = 'Web_AdImageCatalog' " +
                "AND kv.k = wa.CatalogId AND kv2.k = wa.IsAvaliable  AND kv2.groupName = 'Is_Avaliable' ");
        HttpServletRequest request = ServletActionContext.getRequest();
        //根据时间获取时间查询对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, AdImageVO.class);
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(), adImageVO, conditions, request, queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    //添加和删除
    @Permission(require = "内容管理-图片广告-新增")
    public String insertOrUpdate() throws Exception {
        int count = adImageService.insertOrUpdate(adImage, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    // Action的delete
    @Permission(require = "内容管理-图片广告-删除")
    public String delete() throws Exception {

        adImageService.delete(adImage, getLoginUser(), getConnection());

        return SUCCESS;
    }


    // 编写Action里的load
    @Permission(require = "内容管理-图片广告-修改")
    public String load() throws Exception {

        adImage = adImageService.loadAdImagePO(adImage.getId());

        getResult().setReturnValue(adImage.toJsonObject4Form());

        return SUCCESS;
    }


    public AdImageVO getAdImageVO() {
        return adImageVO;
    }

    public void setAdImageVO(AdImageVO adImageVO) {
        this.adImageVO = adImageVO;
    }

    public AdImagePO getAdImage() {
        return adImage;
    }

    public void setAdImage(AdImagePO adImage) {
        this.adImage = adImage;
    }

}
