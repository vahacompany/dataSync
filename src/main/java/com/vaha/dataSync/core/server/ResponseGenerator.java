package com.vaha.dataSync.core.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseGenerator {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private static final Set<String> EXIT_MESSAGES = new HashSet<String>();
    static {
        EXIT_MESSAGES.add("bye");
    }

    private String request;

    public ResponseGenerator(String request) {
    	logger.debug("ResponseGenerator--------------------");
        this.request = request;
    }

    public final String response() {
    	logger.debug("response()--------------------");
        String command = null;

        if (this.request.isEmpty()) {
            command = "명령을 입력해 주세요.\r\n";
        }
        else if (EXIT_MESSAGES.contains(this.request.toLowerCase())) {
            command = "좋은 하루 되세요!\r\n";
        }
        else {
            command = "입력하신 명령이 '" + request + "' 입니까?\r\n";
        }

        return command;
    }

    public boolean isClose() {
        return EXIT_MESSAGES.contains(this.request);
    }

    public final String makeHello() throws UnknownHostException {
    	logger.debug("makeHello--------------------");
    	
        StringBuilder builder = new StringBuilder();

        builder.append("환영합니다. ")
                .append(InetAddress.getLocalHost().getHostName())
                .append("에 접속하셨습니다!\r\n")
                .append("현재 시간은 ").append(new Date().toString())
                .append(" 입니다.\r\n");

        return builder.toString();
    }
}
