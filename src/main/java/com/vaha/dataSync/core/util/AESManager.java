package com.vaha.dataSync.core.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESManager {

	// 암호화
	public String encAES(String str) throws Exception {
	    Key keySpec = getAESKey();
	    
	    String iv = "0987654321654321";
	    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
	    byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	    String enStr = new String(Base64.encodeBase64(encrypted));
	    
	    return enStr;
	}

	// 복호화
	public String decAES(String enStr) throws Exception {
	    Key keySpec = getAESKey();
	    
	    String iv = "0987654321654321";
	    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
	    byte[] byteStr = Base64.decodeBase64(enStr.getBytes("UTF-8"));
	    String decStr = new String(c.doFinal(byteStr), "UTF-8");

	    return decStr;
	}
	
	private Key getAESKey() throws Exception {
	    String iv;
	    Key keySpec;

	    String key = "1234567890123456";
	    iv = key.substring(0, 16);
	    
	    byte[] keyBytes = new byte[16];
	    byte[] b = iv.getBytes("UTF-8");

	    int len = b.length;
	    if (len > keyBytes.length) {
	       len = keyBytes.length;
	    }

	    System.arraycopy(b, 0, keyBytes, 0, len);
	    keySpec = new SecretKeySpec(keyBytes, "AES");
	    
	    return keySpec;
	}
}