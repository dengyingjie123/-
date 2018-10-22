package com.youngbook.common.utils;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;


public class CryptographyUtils {

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

    public static byte[] decrypt(byte[] privateKey, byte[] inputData)
            throws Exception {

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

        KeyPair generateKeyPair = generateKeyPair();

        byte[] publicKey = generateKeyPair.getPublic().getEncoded();
        byte[] privateKey = generateKeyPair.getPrivate().getEncoded();

        byte[] encryptedData = encrypt(publicKey,
                "hi this is Visruth here".getBytes());

        byte[] decryptedData = decrypt(privateKey, encryptedData);

        System.out.println(new String(decryptedData));


        // test();
        testPublic();

    }




    public static void test () throws Exception {

        String file_pfx = "C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.pfx";
        // KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String jks_pfx = "C:\\Users\\leevits\\Desktop\\ttt\\p-sha1.jks";

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

        String jks_public = "C:\\Users\\leevits\\Desktop\\ttt\\kepler_public.jks";

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
