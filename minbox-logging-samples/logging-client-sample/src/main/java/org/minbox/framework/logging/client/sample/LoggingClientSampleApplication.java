package org.minbox.framework.logging.client.sample;

import org.minbox.framework.logging.spring.context.annotation.client.EnableLoggingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Logging Client 示例
 *
 * @author 恒宇少年
 */
@SpringBootApplication
@EnableLoggingClient
public class LoggingClientSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingClientSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoggingClientSampleApplication.class, args);
        logger.info("{}服务启动成功.", "Logging Client Sample");
    }
}
