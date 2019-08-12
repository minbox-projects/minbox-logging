package org.minbox.framework.logging.client.notice.support;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.admin.report.LoggingAdminReport;
import org.minbox.framework.logging.client.cache.LoggingCache;
import org.minbox.framework.logging.client.notice.LoggingNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.logging.core.ReportAway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.Arrays;

/**
 * Logging admin notification
 *
 * @author 恒宇少年
 */
public class LoggingAdminNotice implements LoggingNotice {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingAdminNotice.class);

    /**
     * logging factory bean
     * {@link LoggingFactoryBean}
     * {@link LoggingCache}
     * {@link LoggingAdminReport}
     */
    @Nullable
    private LoggingFactoryBean factoryBean;

    public LoggingAdminNotice(@Nullable LoggingFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    @Override
    public void notice(MinBoxLog minBoxLog) {
        factoryBean.getLoggingCache().cache(minBoxLog);
        logger.debug("Cache Request Logging Complete.");
        // if just report away，execute report logs to admin
        ReportAway reportAway = factoryBean.getReportAway();
        switch (reportAway) {
            case just:
                LoggingAdminReport loggingAdminReport = factoryBean.getLoggingAdminReport();
                loggingAdminReport.report(Arrays.asList(minBoxLog));
                break;
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }
}
