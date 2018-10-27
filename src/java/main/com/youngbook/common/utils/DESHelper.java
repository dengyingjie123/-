package com.youngbook.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import java.security.Key;
import java.security.spec.KeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by leevits on 10/23/2018.
 */
public class DESHelper {

    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    public static final String DESede_ECB_PKCS5Padding = "DESede/ECB/PKCS5Padding";

    public static final String DEFAULT_ENCRYPTION_KEY	= "This is a fairly long phrase used to encrypt";

    private KeySpec keySpec;
    private SecretKeyFactory keyFactory;
    private Cipher cipher;

    private static final String	UNICODE_FORMAT	= "UTF8";


    public DESHelper( String encryptionScheme ) throws Exception
    {
        this( encryptionScheme, DEFAULT_ENCRYPTION_KEY );
    }

    public DESHelper( String encryptionScheme, String encryptionKey )
            throws Exception
    {

        if ( encryptionKey == null )
            throw new IllegalArgumentException( "encryption key was null" );
        if ( encryptionKey.trim().length() < 24 )
            throw new IllegalArgumentException(
                    "encryption key was less than 24 characters" );

        try
        {
            byte[] keyAsBytes = encryptionKey.getBytes( UNICODE_FORMAT );

            if ( encryptionScheme.equals( DESEDE_ENCRYPTION_SCHEME) )
            {
                keySpec = new DESedeKeySpec( keyAsBytes );
            }
            else
            {
                throw new IllegalArgumentException( "Encryption scheme not supported: "
                        + encryptionScheme );
            }

            keyFactory = SecretKeyFactory.getInstance( encryptionScheme );
            cipher = Cipher.getInstance( encryptionScheme );

        }
        catch (Exception e) {
            throw e;
        }

    }

    public String encrypt( String unencryptedString ) throws Exception
    {
        if ( unencryptedString == null || unencryptedString.trim().length() == 0 )
            throw new IllegalArgumentException(
                    "unencrypted string was null or empty" );

        try
        {
            SecretKey key = keyFactory.generateSecret( keySpec );
            cipher.init( Cipher.ENCRYPT_MODE, key );
            byte[] cleartext = unencryptedString.getBytes( UNICODE_FORMAT );
            byte[] ciphertext = cipher.doFinal( cleartext );

            BASE64Encoder base64encoder = new BASE64Encoder();
            return base64encoder.encode( ciphertext );
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public String decrypt( String encryptedString ) throws Exception
    {
        if ( encryptedString == null || encryptedString.trim().length() <= 0 )
            throw new IllegalArgumentException( "encrypted string was null or empty" );

        try
        {
            SecretKey key = keyFactory.generateSecret( keySpec );
            cipher.init( Cipher.DECRYPT_MODE, key );
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] cleartext = base64decoder.decodeBuffer( encryptedString );
            byte[] ciphertext = cipher.doFinal( cleartext );

            return bytes2String( ciphertext );
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    private static String bytes2String( byte[] bytes )
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            stringBuffer.append( (char) bytes[i] );
        }
        return stringBuffer.toString();
    }


    public static void main(String args[]) throws Exception{

        DESHelper encryption = new DESHelper(DESEDE_ENCRYPTION_SCHEME);

        String target = "Java Honk";
        String encrypted = encryption.encrypt(target);
        String decrypted = encryption.decrypt(encrypted);

        System.out.println("String To Encrypt: " + target);
        System.out.println("Encrypted String:" + encrypted);
        System.out.println("Decrypted String:" + decrypted);
    }
}
