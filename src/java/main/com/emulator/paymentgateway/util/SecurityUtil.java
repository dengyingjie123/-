package com.emulator.paymentgateway.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.youngbook.common.config.Config;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.emulator.fund.util.SignAndUnSign;

public class SecurityUtil 
{

	public static final int SYMMETRIC_KEY_LEN = 168;
	
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA"; //
	 
	public static final String KEY_GEN_ALGORITHM   = "RSA"; //
	  
	private static final String SIGNMODE = "PKCS12";//

	
	private static final String FORMAT = "X.509";

    private static HashMap aipPriKey = new HashMap();

	
	//add by sam begin 20100826
	static{
	  Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	};
	//add by sam end
	
    public SecurityUtil(){}


    public static String getPrivateKeyPassword() throws Exception {

    	return Config.getSystemConfig("allinpay_circle_private_password");
	}


    public static String getSignMsg(String sign)
    {
        if (null == sign || sign.trim().length() < 1)
        {
            return null;
        }
        sign = sign.replaceAll("\n", "");
        sign = sign.replaceAll("\r", "");
        sign = sign.trim();
        String signMsg = null;
        try
        {
        	FileInputStream priStream = new FileInputStream(getPrivateKeyPath());
        	
        	KeyStore ks = KeyStore.getInstance(SIGNMODE);  
    		ks.load(priStream, getPrivateKeyPassword().toCharArray());
    		priStream.close();  
    		
    		Enumeration enumas = ks.aliases();  
    	    String keyAlias = null;  
    	    if (enumas.hasMoreElements())
    	    {
    	          keyAlias = (String)enumas.nextElement();   
            }
    	    PrivateKey priKey = (PrivateKey) ks.getKey(keyAlias, getPrivateKeyPassword().toCharArray());  //�õ�˽Կ
    	    
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM,"BC");  
	        signature.initSign(priKey);
	        signature.update(sign.getBytes("UTF-8"));
	        byte[] signData = signature.sign();  
	        BASE64Encoder base64 = new BASE64Encoder();
	        
	        signMsg = base64.encode(signData);
        }
        catch (NoSuchAlgorithmException e) 
	    {
	          throw new RuntimeException("ǩ��ʧ�ܣ��Ҳ���RSA�㷨����Կ��������", e);
	    } 
	    catch (InvalidKeyException e) 
	    {
	          throw new RuntimeException("ǩ��ʧ�ܣ�˽Կ��Ч", e);
	    } 
	    catch (SignatureException e) 
	    {
	          throw new RuntimeException("ǩ��ʧ�ܣ�signatureִ��ǩ��ʧ��", e);
	    }    
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return signMsg;
    }
    
    /**
     * ��ǩ����
     */
    public static boolean verifySign(String signMsg,String respMsg)throws Exception
    {
        if (null == respMsg || respMsg.trim().length() < 1)
        {
            throw new Exception(
                    "value of param respMsg is :" + respMsg);
        }
        
        Boolean verify = verify(getPublicKeyPath(), signMsg , respMsg);
        return verify;
    }
    
    public static void main(String[] agre) throws Exception
    {
    	String str = "2014012099000007|20140121|9010|0|1000|2014012021000007|20140120|2014012099000018|20140121|9010|0|10|2014012021000018|20140120|";
    	
    	System.out.println(str);
    	
    	boolean flag = verifySign("lYPv7xU/DQsgjZWBoUPDLa2RDvG6stAde89d/waRDCbopPesva7zr6XE2lCbpYyspe/2vpb6Rx0k4KE4PrM0YezUZ2YagdUsJywl0M5vPeKcgWLjbPbCuQq5T0O05CSzrrQv/QszPJBPUXJ1oKDXPSlkbl0H+Ims8gEUgnrTXAs=",str);
    	
    	System.out.println(flag);
    }
    
    
	/**

     * ��֤ǩ��
     * 
     * @param certPath
     *            ��Կ·��
     * @param src
     *            Դ����
     * @param mac
     *            ǩ������
     * @return ��ǩ���
     */

     private static boolean verify(String certPath, String src, String mac) {

        boolean tResult = false;

        try 
        {
        	System.out.println("mac ==="+mac);
        	BASE64Decoder base64 = new BASE64Decoder();
            byte[] aPlainData = base64.decodeBuffer(src);

            InputStream inStream = new FileInputStream(certPath);
            CertificateFactory tCertFactory = CertificateFactory.getInstance("X.509");
            Certificate tCertificate = tCertFactory.generateCertificate(inStream);
            Signature tSign = Signature.getInstance(SIGNATURE_ALGORITHM);
            tSign.initVerify(tCertificate);
            tSign.update(mac.getBytes("UTF-8"));
            tResult = tSign.verify(aPlainData);

        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        return tResult;

     }



	/**
	 * �����Գ���Կ
	 * 
	 * @return Key
	 * @throws NoSuchAlgorithmException
	 */
	public static Key generateSymmetricKey() throws NoSuchAlgorithmException 
	{
		String algorithm = "DESede";

		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(SYMMETRIC_KEY_LEN);
		Key key = keyGenerator.generateKey();
		keyGenerator = null;
		//modify by sam end
		return key;
	}
	
	/**
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] encryptSymmetry(String plainText, Key key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalStateException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException 
	{
		Cipher tCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		tCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] tCipherText = tCipher.doFinal(plainText.getBytes("UTF-8"));
		tCipher = null;
		//modify by sam end
		return tCipherText;
	}
	
	/**
	 * ���ܶԳ���Կ(�ý����߹�Կ����)
	 * 
	 * @param key
	 * @param receiverPubKey
	 * @return symmetric key
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IllegalStateException
	 */
	public static byte[] encryptSymmetricKey(Key key, PublicKey receiverPubKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalStateException,
			IllegalBlockSizeException, BadPaddingException 
	{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, receiverPubKey);

		// Get the bytes of the key
		byte[] keyBytes = key.getEncoded();

		// Perform the actual encryption on those bytes
		byte[] cipherText = cipher.doFinal(keyBytes);
		cipher = null;
		//modify by sam end
		return cipherText;
	}
	
	
	public static String getRealPath()
	{
//		String url = SignAndUnSign.class.getResource("/").getFile();
//		return  url.substring(0, url.indexOf("WEB-INF"));
		return "";
	}
	
	public static X509Certificate getCer()
	{ 
		X509Certificate cert = null;
		try
		{
	    	FileInputStream inStream = new FileInputStream(getRealPath() + getPublicKeyPath());
			
			
		    //��Կ
		    CertificateFactory cf = CertificateFactory.getInstance(FORMAT);    
		    cert = (X509Certificate) cf.generateCertificate(inStream);  
	    } 
		catch (Exception e) 
		{
	        throw new RuntimeException("��ȡ��Կʱ��������[" + e.getMessage() + "]");
	    }
		
		return  cert;
	}


	public static String getPrivateKeyPath() throws Exception {
		return Config.getSystemConfig("allinpay_circle_private_key");
	}

	public static String getPublicKeyPath() throws Exception {
		return Config.getSystemConfig("allinpay_circle_public_key");
	}
	
	public static PrivateKey getPrivateKey() throws Exception
	{ 
		FileInputStream priStream = new FileInputStream(getRealPath()+getPrivateKeyPath());
		KeyStore ks = KeyStore.getInstance(SIGNMODE);  
		ks.load(priStream, getPrivateKeyPassword().toCharArray());
		priStream.close();  
		
		Enumeration enumas = ks.aliases();  
	    String keyAlias = null;  
	    if (enumas.hasMoreElements())
	    {  
	          keyAlias = (String)enumas.nextElement();   
        }  
	    PrivateKey priKey = (PrivateKey) ks.getKey(keyAlias, getPrivateKeyPassword().toCharArray());
	    return priKey;
	}
	
	/**
	 * �Գƽ���
	 * 
	 * @param cipherText
	 * @param key
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public static String decryptSymmetry(byte[] cipherText, Key key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalStateException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		//modify by sam 20100826 begin
		//Cipher tCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding",new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher tCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		tCipher.init(Cipher.DECRYPT_MODE, key);
		byte[] tPlainText = tCipher.doFinal(cipherText);
		tCipher = null;
		//modify by sam end
		return new String(tPlainText, "UTF-8");
	}
	
	/**
	 * ���ܶԳ���Կ��������˽Կ���ܣ�
	 * 
	 * @param encryptedKey
	 * @param receiverPriKey
	 * @return symmetric key
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static Key decryptSymmetricKey(byte[] encryptedKey,
			PrivateKey receiverPriKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalStateException,
			IllegalBlockSizeException, BadPaddingException 
	{
		long b = System.currentTimeMillis();
		String algorithm = "DESede";

		// initialize the RSA cipher to decrypt mode
		//modify by sam 20100826 begin 
		//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, receiverPriKey);

		// Perform the decryption
		byte[] decryptedKeyBytes = cipher.doFinal(encryptedKey);

		// Create a new key from the decrypted bytes using SecretKeySpec
		SecretKey key = new SecretKeySpec(decryptedKeyBytes, algorithm);
		cipher = null;
		
		//modify by sam end
		return key;
	}

}
