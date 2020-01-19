package org.minbox.framework.logging.admin.sample;

import org.minbox.framework.logging.spring.context.annotation.admin.EnableLoggingAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 恒宇少年
 */
@SpringBootApplication
@EnableLoggingAdmin
public class LoggingAdminSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingAdminSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoggingAdminSampleApplication.class, args);
        logger.info("{}服务启动成功.", "Logging Admin Sample");
    }


}
