package com.youngbook.common.utils;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerAuthenticationCodePO;
import org.patchca.background.BackgroundFactory;
import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.ArrayList;

public class VarificationUtils {

    /**
     * 验证手机动态码
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月29日
     *
     * @return
     * @throws Exception
     */
    public static CustomerAuthenticationCodePO viladateMobileCode(String code, String mobile) throws Exception {

        Connection conn = Config.getConnection();

        try {
            CustomerAuthenticationCodePO po = null;
            // 组装 SQL、查询
            String sql = "select * from crm_customerauthenticationcode code where code.state = 0 and code.code = ? and code.expiredTime > '" + TimeUtils.getNow() + "' and code.mobile = ? and code.authenticationTime is null";
            java.util.List<KVObject> parameters = new ArrayList<KVObject>();
            parameters = Database.addQueryKVObject(1, code, parameters);
            parameters = Database.addQueryKVObject(2, mobile, parameters);
            java.util.List<CustomerAuthenticationCodePO> list = MySQLDao.search(sql,parameters, CustomerAuthenticationCodePO.class, new ArrayList<KVObject>(), conn);

            if (list != null && list.size() == 1) {
                po = list.get(0);
                po.setAuthenticationTime(TimeUtils.getNow());
                MySQLDao.insertOrUpdate(po, conn);
            }

            return po;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }




    }

    /**
     * 生成一张验证码图片
     * 前提是网站的 Customer 已经登录，返回 CustomerAuthenticationStatus 表的认证信息，里面包含了很多认证状态
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @param minLength             随机数最小个数
     * @param maxLength             随机数最大个数 Integer
     * @param fontColor             字体颜色 Color
     * @param backgroundColor      背景颜色 Color
     * @param fontFamilies         字体集 ArrayList<String>
     * @param filter                过滤器 Integer，0：粗线更跨文字干扰，1：油画特效干扰，2：毛玻璃特效干扰
     * @return Captcha 对象
     * @throws Exception
     */
    public static Captcha varificationCodeGenerator(Integer minLength, Integer maxLength, Color fontColor, Color backgroundColor, ArrayList<String> fontFamilies, Integer filter) throws Exception {
        Captcha captcha = null;
        String code = new String();
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        /*
         * 配置字体集
         */
        ArrayList<String> fonts = new ArrayList<String>();
        fonts.add("Microsoft Sans Serif");
        cs.setFontFactory(new RandomFontFactory(fontFamilies != null && fontFamilies.size() > 0 ? fontFamilies : fonts));
        /*
         * 配置字体颜色
         */
        cs.setColorFactory(new SingleColorFactory(fontColor != null ? fontColor : new Color(25, 60, 170)));
        /*
         * 配置背景颜色
         */
        Color color = new Color(255,255,255);
        BackgroundFactory cbf = new SingleColorBackgroundFactory(backgroundColor != null ? backgroundColor : color);
        cs.setBackgroundFactory(cbf);
        /*
         * 配置随机数特性
         */
        RandomWordFactory rwf = new RandomWordFactory();
        rwf.setCharacters("1234567890");
        rwf.setMinLength(minLength != null && minLength > 0 ? minLength : 5);
        rwf.setMaxLength(maxLength != null && maxLength > 0 ? maxLength : 5);
        cs.setWordFactory(rwf);
        /*
         * 配置干扰
         * CurvesRippleFilterFactory	：粗线更跨文字干扰
         * DiffuseRippleFilterFactory	：油画特效干扰
         * MarbleRippleFilterFactory	：毛玻璃特效干扰
         */
        if(filter != null && filter == 0) {
            cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
        } else if (filter != null && filter == 1) {
            cs.setFilterFactory(new DiffuseRippleFilterFactory());
        } else {
            cs.setFilterFactory(new MarbleRippleFilterFactory());
        }
        /*
         * 输出
         */
        captcha = cs.getCaptcha();
        code = captcha.getChallenge();
        return  captcha;
    }

    /**
     * 将 BufferedImage 转换成 ByteArrayInputStream
     * @param captcha
     * @return
     */
    public static ByteArrayInputStream convertImageToStream(Captcha captcha) throws Exception {
        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", os);
            byte[] bts = os.toByteArray();
            inputStream = new ByteArrayInputStream(bts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

}