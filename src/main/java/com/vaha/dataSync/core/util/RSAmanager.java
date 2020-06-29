package com.vaha.dataSync.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAmanager {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PublicKey serverMakeKey() {
		logger.debug("Server Start");
		
		// 서버에서 공개키, 개인키 생성
		PublicKey publicKey = null;
		PrivateKey privateKey = null;

		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512, secureRandom);

			KeyPair keyPair = keyPairGenerator.genKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();

			KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPublicKeySpec = keyFactory1.getKeySpec(publicKey, RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory1.getKeySpec(privateKey, RSAPrivateKeySpec.class);
			
			logger.debug("Public  key modulus : " + rsaPublicKeySpec.getModulus());
			logger.debug("Public  key exponent: " + rsaPublicKeySpec.getPublicExponent());
			logger.debug("Private key modulus : " + rsaPrivateKeySpec.getModulus());
			logger.debug("Private key exponent: " + rsaPrivateKeySpec.getPrivateExponent());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		byte[] bpublicKey = publicKey.getEncoded();
		String spublicKey = Base64.encodeBase64String(bpublicKey);

		byte[] bprivateKey = privateKey.getEncoded();
		String sprivateKey = Base64.encodeBase64String(bprivateKey);

		try {
			BufferedWriter bw1 = new BufferedWriter(new FileWriter("PublicKey.txt"));
			bw1.write(spublicKey);
			bw1.newLine();
			bw1.close();
			BufferedWriter bw2 = new BufferedWriter(new FileWriter("PrivateKey.txt"));
			bw2.write(sprivateKey);
			bw2.newLine();
			bw2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return publicKey;
	}
	
	public PrivateKey clientUseKey() {
		PrivateKey privateKey = null;
		
		// 클라이언트에서 개인키로 조회
		logger.debug("Client Start");
		
		String sPrivateKey = null;
		BufferedReader brPrivateKey = null;
		
		try {
			brPrivateKey = new BufferedReader(new FileReader("PrivateKey.txt"));
			sPrivateKey = brPrivateKey.readLine(); // First Line Read
			logger.debug("Private Key Read");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (brPrivateKey != null)
					brPrivateKey.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		byte[] bPrivateKey = Base64.decodeBase64(sPrivateKey.getBytes());

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bPrivateKey);
			privateKey = keyFactory.generatePrivate(privateKeySpec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return privateKey;
	}
}
