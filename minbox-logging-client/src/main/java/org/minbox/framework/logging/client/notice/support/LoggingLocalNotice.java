package org.minbox.framework.logging.client.notice.support;

import com.alibaba.fastjson.JSON;
import org.minbox.framework.logging.client.notice.LoggingNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local console log notification
 *
 * @author 恒宇少年
 */
public class LoggingLocalNotice implements LoggingNotice {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingLocalNotice.class);
    /**
     * Whether to output logs in the console
     */
    private boolean showConsoleLog;
    /**
     * Whether to format log information when output
     */
    private boolean formatConsoleLogJson;

    /**
     * Output formatted log information according to configuration in console
     * {@link LoggingNotice}
     *
     * @param minBoxLog ApiBoot Log
     */
    @Override
    public void notice(MinBoxLog minBoxLog) {
        if (showConsoleLog) {
            logger.info("Request Uri：{}， Logging：\n{}", minBoxLog.getRequestUri(), formatConsoleLogJson ? JsonUtil.beautifyJson(minBoxLog) : JSON.toJSONString(minBoxLog));
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public void setShowConsoleLog(boolean showConsoleLog) {
        this.showConsoleLog = showConsoleLog;
    }

    public void setFormatConsoleLogJson(boolean formatConsoleLogJson) {
        this.formatConsoleLogJson = formatConsoleLogJson;
    }
}
