package com.youngbook.common.utils;

import javax.crypto.Cipher;
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
