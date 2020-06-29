package com.vaha.dataSync.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AESManagerTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	AESManager aesManager = null;

	@BeforeEach
	void setUp() throws Exception {
		aesManager = new AESManager();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testEncAES() throws Exception {
		logger.debug("testEncAES() 실행  >>>>> " + aesManager.encAES("암/복호화"));
		
        assertEquals("YpiuIa6UzwlG+WPZP595hA==", aesManager.encAES("암/복호화"));
	}

	@Test
	void testDecAES() throws Exception {
		logger.debug("testDecAES() 실행  >>>>> " + aesManager.decAES("YpiuIa6UzwlG+WPZP595hA=="));
		
        assertEquals("암/복호화", aesManager.decAES("YpiuIa6UzwlG+WPZP595hA=="));
	}

}
