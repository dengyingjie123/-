package com.youngbook.common.utils;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;


public class RSAUtils {

    private static final String file_pfx = "C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.pfx";
    private static final String jks_pfx = "C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.jks";
    private static final String jks_public = "C:\\Users\\leevits\\Desktop\\ttt\\kepler_public.jks";

    private static final String alias_private = "kepler_private";
    private static final String password_private = "12345678";

    private static final String alias_public = "kepler_public";
    private static final String password_public = "12345678";

    private static final String ALGORITHM = "RSA";

    public static byte[] encrypt(byte[] publicKey, byte[] inputData)
            throws Exception {

        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PUBLIC_KEY, key);

        byte[] encryptedBytes = cipher.doFinal(inputData);

        return encryptedBytes;
    }

    public static byte[] encrypt(String inputString) throws Exception {
        return encrypt(RSAUtils.getPublicKey().getEncoded(), inputString.getBytes());
    }


    public static byte[] encrypt(byte[] publicKey, String inputString) throws Exception {
        return encrypt(publicKey, inputString.getBytes());
    }

    public static byte[] decrypt(byte[] privateKey, String inputString) throws Exception {
        return decrypt(privateKey, inputString.getBytes());
    }

    public static byte[] decrypt(String inputString) throws Exception {
        return decrypt(RSAUtils.getPrivateKey().getEncoded(), inputString.getBytes());
    }

    public static byte[] decrypt(byte[] inputData) throws Exception {
        return decrypt(RSAUtils.getPrivateKey().getEncoded(), inputData);
    }

    public static byte[] decrypt(byte[] privateKey, byte[] inputData) throws Exception {

        PrivateKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PRIVATE_KEY, key);

        byte[] decryptedBytes = cipher.doFinal(inputData);

        return decryptedBytes;
    }

    public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) {
        try {
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }

            if (key instanceof PublicKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, null);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // 512 is keysize
        keyGen.initialize(512, random);

        KeyPair generateKeyPair = keyGen.generateKeyPair();
        return generateKeyPair;
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

        byte[] abcs = RSAUtils.encrypt("abc");
        System.out.println(abcs);

        byte[] decrypt = RSAUtils.decrypt(abcs);
        System.out.println(new String(decrypt));
    }


    public static PrivateKey getPrivateKey() throws Exception {

        FileInputStream fis = new FileInputStream(jks_pfx);

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fis, password_private.toCharArray());

            Key key = keyStore.getKey(alias_private, password_private.toCharArray());

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


    public static PublicKey getPublicKey() throws Exception {

        FileInputStream fis = new FileInputStream(jks_public);

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fis, password_public.toCharArray());

            Certificate certificate = keyStore.getCertificate(alias_public);

            return certificate.getPublicKey();

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            fis.close();
        }

    }


    public static void test () throws Exception {


        // KeyStore keyStore = KeyStore.getInstance("PKCS12");


        String alias = "kepler_private";
        String password = "12345678";
        FileInputStream fis = new FileInputStream(jks_pfx);
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


        KeyPair keyPair = getKeyPair(keyStore, alias, password.toCharArray());


        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        byte[] encryptedData = encrypt(publicKey,
                "hi this is Visruth 22222".getBytes());

        byte[] decryptedData = decrypt(privateKey, encryptedData);

        System.out.println(new String(decryptedData));

    }


    public static void testPublic() throws Exception {



        String alias = "kepler_public";
        String password = "12345678";
        FileInputStream fis = new FileInputStream(jks_public);
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
}
