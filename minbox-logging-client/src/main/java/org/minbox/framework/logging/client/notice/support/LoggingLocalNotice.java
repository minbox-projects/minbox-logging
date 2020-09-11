package org.minbox.framework.logging.client.notice.support;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.notice.LoggingNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local console log notification
 *
 * @author 恒宇少年
 */
public class LoggingLocalNotice implements LoggingNotice {
    /**
     * the bean name of {@link LoggingLocalNotice}
     */
    public static final String BEAN_NAME = "loggingLocalNotice";
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingLocalNotice.class);
    /**
     * Logging factory bean {@link LoggingFactoryBean}
     */
    private LoggingFactoryBean loggingFactoryBean;

    public LoggingLocalNotice(LoggingFactoryBean loggingFactoryBean) {
        this.loggingFactoryBean = loggingFactoryBean;
    }

    /**
     * Output formatted log information according to configuration in console
     * {@link LoggingNotice}
     *
     * @param minBoxLog ApiBoot Log
     */
    @Override
    public void notice(MinBoxLog minBoxLog) {
        if (loggingFactoryBean.isShowConsoleLog()) {
            logger.info("Request Uri：{}， Logging：\n{}", minBoxLog.getRequestUri(),
                    loggingFactoryBean.isFormatConsoleLog() ? JsonUtils.beautifyJson(minBoxLog) : JsonUtils.toJsonString(minBoxLog));
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
