package com.youngbook.service.web;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.web.AdImagePO;
import com.youngbook.entity.vo.web.AdImageVO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("adImageService")
public class AdImageService extends BaseService {


    //增加图片常量
    public static final String indexCenterCode="3722";//主页中间大图
    public static final String indexRightCode="3723";//主页右边图片
    public static final String inverstmentPlanDetailCoded="16243";//投资计划
    public static final String ProdutionDetailCoded="16244";//产品支付页面

    public static final String Yes = "3946"; //启用
    public int insertOrUpdate(AdImagePO adImage, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (adImage.getId().equals("")) {
            adImage.setSid(MySQLDao.getMaxSid("Web_AdImage", conn));
            adImage.setId(IdUtils.getUUID32());
            adImage.setState(Config.STATE_CURRENT);
            adImage.setOperatorId(user.getId());
            adImage.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(adImage, conn);
        }
        // 更新
        else {
            AdImagePO temp = new AdImagePO();
            temp.setSid(adImage.getSid());
            temp = MySQLDao.load(temp, AdImagePO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                adImage.setSid(MySQLDao.getMaxSid("Web_AdImage", conn));
                adImage.setState(Config.STATE_CURRENT);
                adImage.setOperatorId(user.getId());
                adImage.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(adImage, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    // 编写Service的load
    public AdImagePO loadAdImagePO(String id) throws Exception{
        AdImagePO po = new AdImagePO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, AdImagePO.class);

        return po;
    }
    // 编写Service的delete
    public int delete(AdImagePO adImage, UserPO user, Connection conn) throws Exception {
        int count = 0;

        adImage.setState(Config.STATE_CURRENT);
        adImage = MySQLDao.load(adImage, AdImagePO.class);
        adImage.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(adImage, conn);
        if (count == 1) {
            adImage.setSid(MySQLDao.getMaxSid("Web_AdImage", conn));
            adImage.setState(Config.STATE_DELETE);
            adImage.setOperateTime(TimeUtils.getNow());
            adImage.setOperatorId(user.getId());
            count = MySQLDao.insert(adImage, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }



    /*
    * 修改人：周海鸿
    * 时间：2015-7-13
    * 内容：获取首页轮播图片*/


    /**
     * 根据kv管理的两个kv键获取对应的图片信息
     * @param kv 归类的键值
     * @param kv2 是否启用的键值
     * @return
     * @throws Exception
     */
    public  List<AdImageVO> getADImg(String kv,String kv2, Connection conn) throws Exception {
        //获取数据库链接

        //判断数据库链接是否为null
        if(null == conn){
            throw new Exception("数据库链接失败");
        }

        try {
            //组装SQL语句
            StringBuffer sbSQL  = new StringBuffer();
            sbSQL.append("  SELECT ");
            sbSQL.append("  	wa.*,kv.k, kv.V AS catalogType, ");
            sbSQL.append("  kv2.k k, ");
            sbSQL.append("  	kv2.V AS IsAvaliableType ");
            sbSQL.append("  FROM ");
            sbSQL.append("  	web_adimage wa, ");
            sbSQL.append("  	system_kv kv, ");
            sbSQL.append("  	system_kv kv2 ");
            sbSQL.append("  WHERE ");
            sbSQL.append("  1=1 ");
            sbSQL.append("  and wa.state = 0 ");
            sbSQL.append("  AND kv.groupName = 'Web_AdImageCatalog' ");
            sbSQL.append("  AND kv.k = wa.CatalogId ");
            sbSQL.append("  AND kv2.k = wa.IsAvaliable ");
            sbSQL.append("  AND kv2.groupName = 'Is_Avaliable' ");
            sbSQL.append("  AND KV.K='"+kv+"' ");
            sbSQL.append("  AND kv2.K='"+kv2+"' ");
            sbSQL.append("   ORDER BY wa.Orders desc ");

            //查询数据库获取对应列表数据
            List list = MySQLDao.query(sbSQL.toString(),AdImageVO.class,null,conn);


            return list;
        }
        catch (Exception e) {
            throw e;
        }
    }
}