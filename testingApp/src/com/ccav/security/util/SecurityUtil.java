package com.ccav.security.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil{

	String decryptValueOne=null;
	String decryptValueTwo=null;
	String keyvalueOne=null;
	String keyvalueTwo=null;
	SecretKey generatedkey=null;
//	String logFile="security_util.log";
	public	SecurityUtil(){
	
		try{
//			CCAVUtil.printLog(logFile, "---------------------Reading card enc key");
			generatedkey=readKey(new File("/opt/ccavenue_keys/ccavenue/keygen.txt"));
//			CCAVUtil.printLog(logFile, "---------------------Reading other keys");
			decryptValueOne=decryptKeyValue(generatedkey,"keyvalueone.txt");
			decryptValueTwo=decryptKeyValue(generatedkey,"keyvaluetwo.txt");
		}
		catch(NoSuchAlgorithmException exc){	
//			CCAVUtil.printLog(logFile, "  NoSuchAlgorithmException in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
		}catch(InvalidKeyException exc){
//			CCAVUtil.printLog(logFile, "  InvalidKeyException in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
			//exc.printStackTrace(CCAVUtil.getPrintWriter());
		}catch(IllegalBlockSizeException exc){
//			CCAVUtil.printLog(logFile, "  IllegalBlockSizeException in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
			//exc.printStackTrace(CCAVUtil.getPrintWriter());
		}catch(NoSuchPaddingException exc){	
//			CCAVUtil.printLog(logFile, "  NoSuchPaddingException in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
			//exc.printStackTrace(CCAVUtil.getPrintWriter());
		}catch(BadPaddingException exc){
//			CCAVUtil.printLog(logFile, "  BadPaddingException in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
			//exc.printStackTrace(CCAVUtil.getPrintWriter());
		}catch(InvalidKeySpecException exc){
//			CCAVUtil.printLog(logFile, "  InvalidKeySpecException in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
			//exc.printStackTrace(CCAVUtil.getPrintWriter());
		}catch(Exception exc){
//			CCAVUtil.printLog(logFile, "  Exception finally in com.org.ccavenue.security : "+exc);
			exc.printStackTrace();
			//exc.printStackTrace(CCAVUtil.getPrintWriter());
		}
	}
	
	/*
		function use to encrypt card number taking number as a string parameter.
	*/
	public  String encrypt(String text)throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException{
	try{
//		CCAVUtil.printLog(logFile, "---------------------Inside encrypt()");

		Class providerClass = Class.forName ( "org.bouncycastle.jce.provider.BouncyCastleProvider" );
		Security.addProvider ( (Provider)providerClass.newInstance() );
		//Algorithm objeto 3DES Cipher 
		 Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); // Triple-DES encryption
		 //Installing SunJCE provider.
		// Modo encriptar
		cipher.init(Cipher.ENCRYPT_MODE, this.privatekeygenerator());
		byte[] bytesToEncrypt = text.getBytes("UTF8");
		byte[] base64encrypted = Base64.encode ( cipher.doFinal ( bytesToEncrypt ) );
		String Ciphertext = byte2hex(base64encrypted);
		//String Ciphertext = new String(base64encrypted, "US-ASCII");
		return Ciphertext;
	  }catch (Exception e){
		  throw new java.lang.RuntimeException(e);
	  }
	}

	public String encryptCardNo(String cardNo) throws Exception {
		String tempCardNo = "".concat(String.valueOf(String.valueOf(cardNo)));
		int len = tempCardNo.length();
		String part1 = "";
		String part2 = "";
		String part3 = "";
		if(len < 12)
			throw new Exception("Card number should have length more than 12 digits.");
		try
		{
			part1 = tempCardNo.substring(0, 6);
			part3 = tempCardNo.substring(len - 4);
			part2 = tempCardNo.substring(6, len - 4);
			part1 = encrypt(part1);
			part2 = encrypt(part2);
			part3 = encrypt(part3);
		}
		catch(Exception e)
		{
			throw e;
		}
		return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(part1)))).append("-").append(part2).append("-").append(part3)));
	}
	/*
		function use to decrypt card number taking number as a string parameter.
	*/
	public  String decrypt(String cipherText)throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException{
		try{
			// Algorithm objeto 3DES Cipher
			Class providerClass = Class.forName ( "org.bouncycastle.jce.provider.BouncyCastleProvider" );
			Security.addProvider ( (Provider)providerClass.newInstance() );
			//Algorithm objeto 3DES Cipher 
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); // Triple-DES encryption
			//Installing SunJCE provider.
			// Modo encriptar
			cipher.init(Cipher.DECRYPT_MODE, this.privatekeygenerator());
			byte[] encryptedBytes = hex2byte(cipherText);
			byte[] base64rawdata = Base64.decode ( encryptedBytes );
			//  Desencriptamos           
			byte[] decryptedBytes = cipher.doFinal(base64rawdata);
			String plainText = new String(decryptedBytes, "UTF8");
			return plainText;
		}
		catch (Exception e){
			throw new java.lang.RuntimeException(e);
		}
	}

	public String decryptCardNo(String encryptedCardNo) throws Exception{
		if(encryptedCardNo.indexOf("-") < 0)
            throw new Exception("Card number format is invalid.");
        String parts[] = encryptedCardNo.split("-");
		return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(decrypt(parts[0]))))).append(decrypt(parts[1])).append(decrypt(parts[2]))));
    }

	/*
		function use to generate encrypted secretkey with TripleDES algorithm.
	*/
	private SecretKey privatekeygenerator() throws Exception {
		String keyAsString = decryptValueOne + decryptValueTwo + decryptValueOne;
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec    keySpec = new DESedeKeySpec(keyAsString.getBytes());
		return keyFactory.generateSecret(keySpec);
	}
	/*
		function use to read the  encrypted key generated by passwordController.
	*/
	public SecretKey readKey(File f) throws IOException,InvalidKeyException,NoSuchAlgorithmException,InvalidKeySpecException,
	   InvalidKeyException{
			// Read the raw bytes from the keyfile
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			byte[] rawkey = new byte[(int) f.length()];
			in.readFully(rawkey);
			in.close();
			// Convert the raw bytes to a secret key like this
			DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyfactory.generateSecret(keyspec);
			return key;
		  }

	/*
		function use to decrypt the privatekey encrypted by passwordController.
	*/
	public String decryptKeyValue(SecretKey key,String fileName) throws NoSuchAlgorithmException, InvalidKeyException, IOException,
      IllegalBlockSizeException, NoSuchPaddingException,BadPaddingException {
		// Create and initialize the decryption engine
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, key);
		InputStream in = new FileInputStream(new File("/opt/ccavenue_keys/ccavenue/"+fileName));
		byte[] buffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
		  cipher.update(buffer, 0, bytesRead);
		}		   
		cipher.doFinal();
		String plainText = new String(buffer);
		return plainText;
		//end
	}
	
	/*
		function use to read the  encrypted privatekey from the generated by passwordController .
	*/
	public String readKeyValue(File f) throws IOException{
		// Read the raw bytes from the keyfile
		DataInputStream in = new DataInputStream(new FileInputStream(f));
		byte[] rawkey = new byte[(int) f.length()];
		in.readFully(rawkey);
		in.close();
		String s=new String(rawkey);
		return s;
	}

	private static SecurityUtil instance = null;

	public static SecurityUtil getInstance()
    {
        if(instance == null)
            instance = new SecurityUtil();
        return instance;
    }

	public static String byte2hex(byte[] b) {
	
		String hs="";
		String stmp="";
		for (int n=0;n<b.length;n++){
			stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length()==1){
				hs=hs+"0"+stmp;
			}else{ hs=hs+stmp;}
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(String hex) throws IllegalArgumentException{
		if(hex.length() % 2 != 0){
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for(int i = 0, j = 0, l = hex.length(); i < l; i++, j++){
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}
}