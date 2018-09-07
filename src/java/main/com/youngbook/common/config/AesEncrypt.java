package com.youngbook.common.config;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.youngbook.common.Database;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密、解密
 * Created by Jevons on 2015/8/24.
 * @author 方斌杰
 */
public class AesEncrypt {

    /**
     * 根据参数生成KEY
     */
    public static Key getKey() {
        Key key = null;
        String encryptKey = "";
        try {
            //取得配置文件language.xml中的加密节点
            encryptKey = Config.getSystemConfig("w.index.manageMoney");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断传入加密Key是否为空
        if ("".equals(encryptKey)) {
            // 生成密匙 8位以上编码
            encryptKey = "12345678";
        }

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key  = keyFactory.generateSecret(new DESKeySpec(encryptKey.getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException("Error initializing AesEncrypt class. Cause: " + e);
        }
        return key;
    }

    /**
     * 加密String明文输入,String密文输出
     */
    public static String encrypt(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing AesEncrypt class. Cause: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public static String decrypt(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        }
        catch (Exception e) {
            e.printStackTrace();
            return StringUtils.isEmpty(strMi) ? "" : strMi + "（*）";
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private static byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(),SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing AesEncrypt class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private static byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, getKey(),SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing AesEncrypt class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    public static void main(String args[]) throws Exception {

//        String str1 = "0nUN5XH3J8i4Fzbf3uH/NjbEAKqB3wcp";
        //String str1 = "wkHEjTCgOflS48TtMdwyZn3Wp0VLe62z";
        String str1 = "BI4PkMPnmukntx4RlnUqHUxFXuiQehTL";

        // DES加密
        String str2 = AesEncrypt.encrypt(str1);

        // DES解密
        String deStr = AesEncrypt.decrypt(str1);

        System.out.println("明文:" + str1);
        System.out.println("加密:" + str2);
        System.out.println("解密:" + deStr);
        System.out.println("解密1:" + AesEncrypt.encrypt("532502198310130022"));

//
//        Connection conn = Config.getConnection ();
//
//        StringBuffer sbSQL = new StringBuffer();
//        sbSQL.append(" SELECT");
//        sbSQL.append("     a.Number,");
//        sbSQL.append("     c.Name,");
//        sbSQL.append("     c.personalNumber,");
//        sbSQL.append("     c.Mobile");
//        sbSQL.append(" FROM");
//        sbSQL.append("     crm_customerpersonal c,");
//        sbSQL.append("     crm_customeraccount a");
//        sbSQL.append(" WHERE");
//        sbSQL.append("     1 = 1");
//        sbSQL.append(" AND c.state = 0");
//        sbSQL.append(" and a.state=0");
//        sbSQL.append(" and c.id=a.CustomerId");
//
//        try {
//            List<Map<String, Object>> accounts = MySQLDao.query(sbSQL.toString(),conn);
//            CustomerCertificatePO cc = null;
//            for (int i = 0; i < accounts.size(); i++) {
//                Map<String, Object> account = accounts.get(i);
//
//                StringBuffer out = new StringBuffer();
//                out.append(account.get("personalNumber")).append(" ");
//                out.append(account.get("Name")).append(" ");
//                out.append(account.get("Mobile")).append(" ");
//                out.append(AesEncrypt.decrypt(account.get("Number").toString()));
//                System.out.println(out);
//            }
//
//            //修改到数据库
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            Database.close(conn);
//        }
//
////
//        String sqlCA = "select * from crm_customeraccount ";
//        try {
//            List<CustomerAccountPO> calist = MySQLDao.query(sqlCA, CustomerAccountPO.class, null);
//            List<CustomerAccountPO> calistTemp = new ArrayList<CustomerAccountPO>();
//            CustomerAccountPO ca = null;
//            for (int i = 0; i <= calist.size(); i++) {
//                ca = calist.get(i);
//                if (null != ca && !StringUtils.isEmpty(ca.getNumber())) {
//                    ca.setNumber(AesEncrypt.encrypt(ca.getNumber()));
//                }
//                //修改到数据库
//                //int count = MySQLDao.update(ca, conn);
//                //calistTemp.add(ca);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
