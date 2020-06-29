package com.vaha.dataSync.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import com.vaha.dataSync.core.server.DataSyncServer;

@SpringBootApplication
@ComponentScan({ "com.vaha.dataSync.core.server", "com.vaha.dataSync.app" })
public class DataSyncApplication {
	Logger logger = LoggerFactory.getLogger(DataSyncApplication.class);

	@Autowired
	private DataSyncServer dataSyncServer;

	public static void main(String[] args) {
		SpringApplication.run(DataSyncApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		if (logger.isInfoEnabled()) {
			logger.info("DataSyncServer Srart !!");
		}
		dataSyncServer.start();
	}

	@EventListener(ContextClosedEvent.class)
	public void shutdown() {
		dataSyncServer.shutdown();
		
		if (logger.isInfoEnabled()) {
			logger.info("DataSyncServer shutDwon !!");
		}
	}
}
