package com.youngbook.common.utils;

import com.youngbook.common.config.XmlHelper;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;


public class RSAHelper {

    private String pfxFilePath = "C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.pfx";
    /**
     * C:\Users\leevits\Desktop\ttt\p-sha1.jks
     * C:\Users\leevits\Desktop\ttt\allinpayCircle\private.jks
     */
    private String jksPrivatePath = "C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks";
    private String jksPublicPath = "C:\\Users\\leevits\\Desktop\\ttt\\kepler_public.jks";

    /**
     * kepler_private
     * allinpay_private
     */
    private String jksPrivateAlias = "allinpay_private";

    /**
     * 12345678
     *
     */
    private String jksPrivatePassword = "111111";

    private String jksPublicAlias = "kepler_public";
    private String jksPublicPassword = "12345678";

    /**
     * RSA
     * RSA/ECB/PKCS1Padding
     */
    private String ALGORITHM = "RSA/ECB/PKCS1Padding";

    public byte[] encrypt(PublicKey publicKey, byte[] inputData)
            throws Exception {

//        PublicKey key = KeyFactory.getInstance(ALGORITHM)
//                .generatePublic(new X509EncodedKeySpec(publicKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PUBLIC_KEY, publicKey);

        byte[] encryptedBytes = cipher.doFinal(inputData);

        return encryptedBytes;
    }

    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public byte[] decrypt(PrivateKey privateKey, byte[] inputData) throws Exception {

//        PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        // Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedBytes = cipher.doFinal(inputData);

        return decryptedBytes;
    }

    public PrivateKey getPrivateKey(byte[] key) throws Exception {

        PrivateKey privateKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(key));

        return privateKey;
    }



    public static void main(String[] args) throws Exception {

//        KeyPair generateKeyPair = generateKeyPair();
//
//        byte[] publicKey = generateKeyPair.getPublic().getEncoded();
//        byte[] privateKey = generateKeyPair.getPrivate().getEncoded();
//
//        byte[] encryptedData = encrypt(publicKey,
//                "hi this is Visruth here".getBytes());
//
//        byte[] decryptedData = decrypt(privateKey, encryptedData);
//
//        System.out.println(new String(decryptedData));
//
//
//        // test();
//        testPublic();

        RSAHelper helper = new RSAHelper();
        helper.setJksPublicPath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\public.jks");
        helper.setJksPublicAlias("allinpay_public");
        helper.setJksPublicPassword("111111");

//        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks");
//        helper.setJksPrivateAlias("allinpay_private");
//        helper.setJksPrivatePassword("111111");


        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.jks");
        helper.setJksPrivateAlias("kepler_private");
        helper.setJksPrivatePassword("12345678");

        helper.testNormal();
//        helper.testEncryptAllinpay();
    }

    public void testNormal() throws Exception {

        RSAHelper helper = new RSAHelper();
//        helper.setJksPublicPath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\public.jks");
//        helper.setJksPublicAlias("allinpay_public");
//        helper.setJksPublicPassword("111111");
//
//        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks");
//        helper.setJksPrivateAlias("allinpay_private");
//        helper.setJksPrivatePassword("111111");


        helper.setJksPublicPath("C:\\Users\\leevits\\Desktop\\ttt\\kepler_public.jks");
        helper.setJksPublicAlias("kepler_public");
        helper.setJksPublicPassword("12345678");

        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.jks");
        helper.setJksPrivateAlias("kepler_private");
        helper.setJksPrivatePassword("12345678");


        byte[] abcs = helper.encrypt(helper.getPublicKey(), "abc".getBytes());

        String encryptText = new String(abcs) + "3";
        System.out.println(Base64Utils.encode(encryptText));

        BASE64Decoder base64Decoder = new BASE64Decoder();

        byte[] decrypt = helper.decrypt(helper.getPrivateKey(), base64Decoder.decodeBuffer(encryptText));
        System.out.println(new String(decrypt));
    }


    public void testEncryptAllinpay() throws Exception {

//        RSAHelper helper = new RSAHelper();
//        helper.setJksPublicPath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\public.jks");
//        helper.setJksPublicAlias("allinpay_public");
//        helper.setJksPublicPassword("111111");
//
//        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks");
//        helper.setJksPrivateAlias("allinpay_private");
//        helper.setJksPrivatePassword("111111");

        String message = "repMsg=PFNUU1BhY2thZ2U+PEVuY3J5cHRlZFRleHQ+Z2ZuR29GbXRqT2ZOTGd6SENOcitGYzExUk5CQWdVcXNDM2gzMGE1NmZPRWlsajZWS284VTErOVprWDVzOFJtZEJBN250YXc2aW1XMg0KZnVsOWo5UktsNkpnSXh5U0ZzNEE4RmF4cWg0WHUxN1k5WlhFN1ZlaVlYam5UWkhqa3dTVUZHYTIvSkhUSjJIaDNTNFRZZG9keWxkaA0KeXdwQ0FEWVZ3RzJ2bTE1alYvM25aQlY3cHhXazVDQmY4cVFLdzFLbDhTaEZ5eEMxem94TmJjTXpadXFQZzVuMVRnUGtQTitIdElKKw0KdVFnZnNtVno1QnVYVjQ0Q1M2R2JuK0dqNURFaDRWY0MyeUF0QklUSXlCTmJkamdzcmVVaEdrS3pYays1Tzd6S1dsVlZya2hHaGZ1VA0KWGczdFduL1BkMnJHeURwTlZnblM1Nzl1TTVSMThIOUUzeW1BS3Q4VnA5Y2RIN3Ywc1lxUHpOTWxDVG15akJBclI5RHZnYkkxazF5Ng0KNzhzS21GS3pXcjJMWmpHVEV4bm5oenhMTDg5MmwxcXNEYnlkdkxlWFlqOWNwY2lsb3NuMHZjSjZPV1NxaW9nRk5BWDN3RW1TQ1RSZA0KaEw2Z2Q4VXNmMUprL1llZzRpWXp5NzVnS1pHelpibVo1MkE5eXU1a1VKbzg0OFNsQndkeXpBd2ZTcW1mYzUrWjhDMmd5ZCszK0l6QQ0KZFYxVWdUMVNVbDVVRjNpL1p0WGE2ZXVZc0ZCV0xibGZyNjZTMGdVOC9xNlBqRFBUdGlFRWxsV29YY2w3dFh4NFZWb3RjVXZMUUhGLw0KSmt0eFVub2pnczFlRldXekJpUk4wTzZoWU9mY1U3eDR1RVR1dVRjbTdIYllpZ0Z2a2tBOStweWNVeXQvRFNEZWF2TG0veVBQNTU0Sw0KczBFRzN6dlYrLzZHMzBDZDgwRkJwSW9UbUdBdFkxcERCaG5yTkF4NDFlNFJ6bGlkbWRpd0NjYzl3WUpCSTRIUnVHbXRlZHlUYXEvcw0Ka1ZRbzU2N2NqaERVMnViaEFNK3h3TjR1cDFmWjlWRXJlREx5MitYQ2ZjeVB4cFFmNzVxT0lOeGNJUk9iZW16K3lWU0l3U1VmcVZ6ZA0KOElORzZoMzVFci9CdG84Nk8vcUFoL3V2YVY2UWxobVowa1BMaE9wSUcwQmgzU0lnSzVWTzRuYjY4SWxoVjVOVWt3Vk1XWDBHOXFqaQ0KTk5DcjYybVV5SWlkalRhREg1dkk1OGRnMjU3d3VVOUdKaE9rWTdGMW1oZEVQUTFGQzkxQ0p5WTZlbnRTMXh1cEduaVpEYUhXYW5aeA0KN3Vpay9HSW51c2d6YXFSdDRJNWlmV2RjYTdiemdRV0hhUml2VG5Hb0N4dmJGMW0wQTJJOFE5RHUwNU4zMFducE1vRDZhVkRrSDN6OQ0KS0FVd2hndkRSUVIwWnhjaEc2U1hnMVVKamdEbjcxR2ZTdnA2d1VwNGdMeDhqbng4QnU4MHhZV2xiS2F3Y2dLY3IzRGd4T0ZNS1Uydg0KU3k3N014eXFEQktxbG5XTUsrbXFIazVBaHQxSFFSNkVvUkQ5cnE2Tk9nNjVTMnNMbjBoRlZXSm40MnZhL2R0aHRVc3R2SmFRaUo0TQ0KL2hMK1Y3ZHBMSlVkdlJPU01hM0l1UmVZakVLVSt5b05FZ0k4bCs5MTBuWGN0c1MrSjJnNjJUdzc0V1ptaU9xeWFUaVBHdVUwTWMwPTwvRW5jcnlwdGVkVGV4dD48S2V5SW5mbz48UmVjZWl2ZXJYNTA5Q2VydFNOPjIxODIwNzM2NzQwMzEyMTA2MDc4MzE3MDcxNTQ2MjE5MDY4MTQ3PC9SZWNlaXZlclg1MDlDZXJ0U04+PEVuY3J5cHRlZEtleT5CQzlVWkZNSk9reXpJN1hXOHViRkt0dHNzUUI5SXZoZmVBSElHTjFtR2d5OFphMUkrUlZmYWRacU93UnIvV1orOXpibkEybXRoTGNBDQp4akFIU3F5cjhvbzl6RDdnUWRSZVZVS1luOEZoSDlnWHhBc29za2t3SnRqVmEvdGJOcnZwaFJubCtIOUZuU243am92a1Jxbko0TnBMDQpLVGRRQU8yejhySURvb1JndUtnPTwvRW5jcnlwdGVkS2V5PjwvS2V5SW5mbz48L1NUU1BhY2thZ2U+";
        message = message.substring(7);

        String encryptedXmlWithBase64 = Base64Utils.decode(message);

        System.out.println(encryptedXmlWithBase64);

        XmlHelper xmlHelper = new XmlHelper(encryptedXmlWithBase64);

        String encryptedDESKeyWithBase64 = xmlHelper.getValue("/STSPackage/KeyInfo/EncryptedKey");

//        String encryptedDESKey = Base64Utils.decode(encryptedDESKeyWithBase64);
//
//        System.out.println(encryptedDESKey);

        // byte[] desKey = decrypt(getPrivateKey(), encryptedDESKey.getBytes());
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] desKey = decrypt(getPrivateKey(), decoder.decodeBuffer(encryptedDESKeyWithBase64));

        SecretKey key = new SecretKeySpec(desKey, "DESede");

        String encryptedDESMessageWithBase64 = xmlHelper.getValue("/STSPackage/EncryptedText");

        String encryptedDESMessage = Base64Utils.decode(encryptedDESMessageWithBase64);

        Cipher tCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        tCipher.init(Cipher.DECRYPT_MODE, key);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] tPlainText = tCipher.doFinal(base64Decoder.decodeBuffer(encryptedDESMessageWithBase64));
        tCipher = null;
        //modify by sam end
        String m = new String(tPlainText, "UTF-8");

        System.out.println(m);

    }


    public String decode(String message) throws Exception {

        message = message.substring(7);

        String encryptedXmlWithBase64 = Base64Utils.decode(message);

        System.out.println(encryptedXmlWithBase64);

        XmlHelper xmlHelper = new XmlHelper(encryptedXmlWithBase64);

        String encryptedDESKeyWithBase64 = xmlHelper.getValue("/STSPackage/KeyInfo/EncryptedKey");

        String encryptedDESKey = Base64Utils.decode(encryptedDESKeyWithBase64);

        System.out.println(encryptedDESKey);

        // byte[] desKey = decrypt(getPrivateKey(), encryptedDESKey.getBytes());
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] desKey = decrypt(getPrivateKey(), decoder.decodeBuffer(encryptedDESKeyWithBase64));

        SecretKey key = new SecretKeySpec(desKey, "DESede");

        String encryptedDESMessageWithBase64 = xmlHelper.getValue("/STSPackage/EncryptedText");

        String encryptedDESMessage = Base64Utils.decode(encryptedDESMessageWithBase64);

        Cipher tCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        tCipher.init(Cipher.DECRYPT_MODE, key);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] tPlainText = tCipher.doFinal(base64Decoder.decodeBuffer(encryptedDESMessageWithBase64));
        tCipher = null;
        //modify by sam end
        String m = new String(tPlainText, "UTF-8");

        System.out.println(m);

        return m;
    }


    public PrivateKey getPrivateKey() throws Exception {

        FileInputStream fis = new FileInputStream(jksPrivatePath);

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fis, jksPrivatePassword.toCharArray());

            Key key = keyStore.getKey(jksPrivateAlias, jksPrivatePassword.toCharArray());

            if (key instanceof PrivateKey) {

                return (PrivateKey) key;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            fis.close();
        }

        return null;

    }


    public PublicKey getPublicKey() throws Exception {

        FileInputStream fis = new FileInputStream(jksPublicPath);

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fis, jksPublicPassword.toCharArray());

            Certificate certificate = keyStore.getCertificate(jksPublicAlias);

            return certificate.getPublicKey();

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            fis.close();
        }

    }


    public void test () throws Exception {


        // KeyStore keyStore = KeyStore.getInstance("PKCS12");


        String alias = "kepler_private";
        String password = "12345678";
        FileInputStream fis = new FileInputStream(jksPrivatePath);
// Instantiate BouncyCastle KeyStore implementation
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(fis, password.toCharArray());
        fis.close();

        Key key = keyStore.getKey(alias, password.toCharArray());

        System.out.println(key);

        Enumeration aliases = keyStore.aliases();
        while(aliases.hasMoreElements()) {
            System.out.println(aliases.nextElement());
        }

    }


    public void testPublic() throws Exception {



        String alias = "kepler_public";
        String password = "12345678";
        FileInputStream fis = new FileInputStream(jksPublicPath);
// Instantiate BouncyCastle KeyStore implementation
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(fis, password.toCharArray());
        fis.close();

        Key key = keyStore.getKey(alias, password.toCharArray());

        Certificate certificate = keyStore.getCertificate(alias);


        Enumeration aliases = keyStore.aliases();
        while(aliases.hasMoreElements()) {
            System.out.println(aliases.nextElement());
        }

//        System.out.println(key);
//
//
//        byte[] publicKey = key.getEncoded();
//
//        byte[] encryptedData = encrypt(publicKey,
//                "hi this is Visruth 22222".getBytes());
//
//        System.out.println(new String(encryptedData));

    }

    public String getPfxFilePath() {
        return pfxFilePath;
    }

    public void setPfxFilePath(String pfxFilePath) {
        this.pfxFilePath = pfxFilePath;
    }

    public String getJksPrivatePath() {
        return jksPrivatePath;
    }

    public void setJksPrivatePath(String jksPrivatePath) {
        this.jksPrivatePath = jksPrivatePath;
    }

    public String getJksPublicPath() {
        return jksPublicPath;
    }

    public void setJksPublicPath(String jksPublicPath) {
        this.jksPublicPath = jksPublicPath;
    }

    public String getJksPrivateAlias() {
        return jksPrivateAlias;
    }

    public void setJksPrivateAlias(String jksPrivateAlias) {
        this.jksPrivateAlias = jksPrivateAlias;
    }

    public String getJksPrivatePassword() {
        return jksPrivatePassword;
    }

    public void setJksPrivatePassword(String jksPrivatePassword) {
        this.jksPrivatePassword = jksPrivatePassword;
    }

    public String getJksPublicAlias() {
        return jksPublicAlias;
    }

    public void setJksPublicAlias(String jksPublicAlias) {
        this.jksPublicAlias = jksPublicAlias;
    }

    public String getJksPublicPassword() {
        return jksPublicPassword;
    }

    public void setJksPublicPassword(String jksPublicPassword) {
        this.jksPublicPassword = jksPublicPassword;
    }
}
