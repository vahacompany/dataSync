package com.vaha.dataSync.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RSAmanagerTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	RSAmanager rsaManager = null;
	Cipher cipher = null;
	String sPlain1 = "Welcome to RSA";
	String sPlain2 = null;

	@BeforeEach
	void setUp() throws Exception {
		rsaManager = new RSAmanager();
		cipher = Cipher.getInstance("RSA");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testServerMakeKey() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		PublicKey publicKey = rsaManager.serverMakeKey();		
	
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bCipher1 = cipher.doFinal(sPlain1.getBytes());
		logger.debug("PublicKey === " + Base64.encodeBase64String(bCipher1));
	}

	@Test
	void testClientUseKey() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		PublicKey publicKey = rsaManager.serverMakeKey();
		
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bCipher1 = cipher.doFinal(sPlain1.getBytes());
		String sCipherBase64 = Base64.encodeBase64String(bCipher1);
		System.out.println("sCipherBase64 : " + sCipherBase64); // 암호화 전 문자

		PrivateKey privateKey = rsaManager.clientUseKey();

		byte[] bCipher2 = Base64.decodeBase64(sCipherBase64.getBytes());
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bPlain2 = cipher.doFinal(bCipher2);
		sPlain2 = new String(bPlain2);
		logger.debug(sPlain1+"-"+sPlain2);
		
		assertEquals(sPlain1,sPlain2);

	}
	
	@Test
	void testMakeKey() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
	
		PublicKey publicKey = rsaManager.serverMakeKey();
		PrivateKey privateKey = rsaManager.clientUseKey();

		String sPlain1 = "Welcome to RSA";
		String sPlain2 = null;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bCipher1 = cipher.doFinal(sPlain1.getBytes());
			String sCipherBase64 = Base64.encodeBase64String(bCipher1);
			logger.debug("sCipherBase64 : " + sCipherBase64); // 암호화 전 문자

			byte[] bCipher2 = Base64.decodeBase64(sCipherBase64.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bPlain2 = cipher.doFinal(bCipher2);
			sPlain2 = new String(bPlain2);

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		assertEquals(sPlain1,sPlain2);

	}

}
