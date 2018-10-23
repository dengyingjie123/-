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


    public byte[] decrypt(PrivateKey privateKey, byte[] inputData) throws Exception {

//        PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        // Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PRIVATE_KEY, privateKey);

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

        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks");
        helper.setJksPrivateAlias("allinpay_private");
        helper.setJksPrivatePassword("111111");


        byte[] abcs = helper.encrypt(helper.getPublicKey(), "abc".getBytes());

        String encryptText = new String(abcs);
        System.out.println(Base64Utils.encode(encryptText));

        byte[] decrypt = helper.decrypt(helper.getPrivateKey(), abcs);
        System.out.println(new String(decrypt));

        helper.testAllinpay();
    }


    public void testAllinpay() throws Exception {

//        RSAHelper helper = new RSAHelper();
//        helper.setJksPublicPath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\public.jks");
//        helper.setJksPublicAlias("allinpay_public");
//        helper.setJksPublicPassword("111111");
//
//        helper.setJksPrivatePath("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks");
//        helper.setJksPrivateAlias("allinpay_private");
//        helper.setJksPrivatePassword("111111");

        String message = "repMsg=PFNUU1BhY2thZ2U+PEVuY3J5cHRlZFRleHQ+c21GZEtBd1Q1OXNjMHhpTi9vcUpEK05sYm1SWndJN3ZCUWJjU3lxcXZkTDFMZ09iekpNZUQ5NFQyOGlFRVM5SGI5N1VrYklnd1lTb1NUb2o3TjN3MjJ2dDJTcHZXbnlFdzA2Tm0yUlN3NG9OOVU3SEMyZjVaYXlnL3VtVlhOSTFjYzJGdUFSbTU1Tnp5Y1BadjdwQWFtOVI3WmZNdDl4RGNUd3VpNFBadXBOUUM0bk9UOXorTlVUdCtkYTBFM3duSDlMcDNPNUdILytXczltTHVxK3ZuUzQ0THVqcnltNkorQlRxUkY2S2dsZ1lTbzQwNFloamk4dHZTY3RtRkZhNlVFc2NkYWJ0R0FuWEMwaHNtU1pQOHo2WHZtOVdzUFNzT21QQ3hCMTd6eW85ak8rdGZrRXlKWUhCWFZQcDBoM1JCSjdCdS80Q2ZqQ3Q4RzRjTERwN3pnTThZTHhGQjcvNkhQVWhqZldOZnE4a2pNazJESlJuNkZiSStUZnl1UFNycjVOczhhZi9qQjF5NXR4V0FEZm9nQ3g0cUx4S1F1dWZrOUtodHU5Sm5BVUsyV3d6NzY2SEp6NkxYMGo4YkR1Y1UveVRvdXZkbDJjeFpsQzFQVmxsaytmbDdPc2cxdWZaNFp4QUZxeWt6eEsrZWd0R0FYQzdmNUhGMmdZeTdya1ZMSUpZQ0xYT0llZjlYb3MzdGltemI5THZiY0QvVnFZUUZHMjBJdHIzd0Z4VGpVTGhMMUUwdmV2SzJGOHdMVFhwZStMK25OclJ3Ui9xSzZCNXNWL01uWEw5Tmx2bEpqazBqc1YzMlJ3Z0M1U09FVndweC9PY3EwZW13bHZYbE1XTG4zNFZiQzQrYm4rZFlRYnA4UHM5RWZLUWNxaGhzMUJMYTZSZWJTdTJvWjdlZXFveFBUQnhHK09vNEpacUIyZVdudC9rSC8yeGFSTVd2TE1BeHcxOSttekRFSDFwQVAvK2tLY2pscUxhbGVrUVp1NkVhWXpqdUR4NmczUEE1WVlVbG9kdmlrZm16TzYrMjVzNVlUOW5XTjN4Wi82NS9tWEloRmZmRlpPeCtnRFl3M3NTYjVtOWd3Y1ovQklkOGJPRmhwdEhZYUhaWC81Q3Uzc1draTZleEZvQkpyV1ZmaFFHSmxXb3ZxbDNDT1hLNzBWR0pUQWRqckZDRThSNjlRZ0xTOURjRTNNbjFwcW81YW8vL0tGVHR6aVdKcEh4dXpNZS9KbDJ0L0dvaGFOZnFYUXF0ZzQ5UnBYRE1PRmhDVnlEbnplaGxzZkEyajhpbFNOZHhpMEF4NFFyd1JNR05GTXU4QlJoZVJYd1F6SXlkd09JbWNIakxGcVdidmQ2SHJsMmZTbzJqc1dHS3l0MUgxcFpMcUZMQXNVOHpwU3ZzMkNzYURSbVJVK0dzN3o5T2QxT0prS1drclJONVVhbWkwYWE4dmxjN2lhdjN1eUQxNS9OYVhMRExKVnRpUkRDNko5TEM2ck5DMDYyM0JUM05EL3hBaTVyRUVzbXFnUFVzWUtJc2ZIbkVsU0xtSUorbGJCbEQyY3FGSTd1eUhSUGtVYTF2NzIrRmFCV3lRNnFCMEx6QUI5ODFvQmtxZG9rUHBBaXhKQThhU0FWaUNjY1BmUkFOTGR5NkxLWWZpdlozN3lZTFJkTlVNb3Z2c3NBdnRIU1plcVNCRDJwcHpVOFpBTkI2R3lXOXdNU201L0o5VzFqbFkwMzY3aSsxdG5FTU1ka01xQ3hTUmszUWx1aHlnb2hORzNHeDl1UFhuRzJUNkF5S1VLUjc5V0JQNjNSejEwK01YL2pDbzhyMGpvcHpPRFM0Z1BCZDdjRkpIdEdWa1NHSmNVbzF2RFU3Mmd2dTdkWVZ3Z3VWN1dta2VNRXdvVENSUzFuc0pFTjJHZzBDTHdxUC9zRkErWmcrUkR1bVpLU3hKek9GOXlCdkYrb2hZNjVOaEh2RDgxc1M3M2dvVS9VQkhWeHJ4QlUwQW5XWFk0SlZEM05xbldSUERYdzFPOW9MN3UzV01za3o3d3dxUGdJY3ZucTFSMWV4YTNKd3FmeEhPZWZiaTZsMGc0L0FDQys8L0VuY3J5cHRlZFRleHQ+PEtleUluZm8+PFJlY2VpdmVyWDUwOUNlcnRTTj45MDg1MzM3NjYzNDc2NjI3OTM2OTQxNjA3MDc0Mjk0NDcxNzA0PC9SZWNlaXZlclg1MDlDZXJ0U04+PEVuY3J5cHRlZEtleT5GQnpFMlpWZzV1QnRJdGJRYnh2MVNUTE91Mnh4VncycU1tTXhlZG9oTHFrcDdUaUVNTzVFRit0SHNIbVFPUVpKTUQ0VEpWMW9FbnJ2YVllcHlxTlBGQzlrYnlINUtSYmF2amIxUUNHOURxWmhCbGwzWkJIOGNpUzhEQ29YaXJyaFZuay9ZUWtueEJBWUFKSkkxeStDRjJjbVRFaWJ5bjdnZTBETzhRWnRSZE09PC9FbmNyeXB0ZWRLZXk+PC9LZXlJbmZvPjwvU1RTUGFja2FnZT4=";
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
