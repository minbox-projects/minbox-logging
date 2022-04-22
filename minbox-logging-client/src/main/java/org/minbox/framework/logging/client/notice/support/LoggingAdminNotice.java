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
     * the bean name of {@link LoggingAdminNotice}
     */
    public static final String BEAN_NAME = "loggingAdminNotice";
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

    /**
     * Injecting {@link LoggingFactoryBean} through constructor injection
     *
     * @param factoryBean {@link LoggingFactoryBean}
     */
    public LoggingAdminNotice(@Nullable LoggingFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    /**
     * if just report away，execute report logs to admin
     * if timing report away，cache logs to {@link LoggingCache} support，
     * wait for {@link org.minbox.framework.logging.client.admin.report.LoggingReportScheduled} execute report
     *
     * @param minBoxLog ApiBoot Log
     */
    @Override
    public void notice(MinBoxLog minBoxLog) {
        ReportAway reportAway = factoryBean.getReportAway();
        switch (reportAway) {
            case just:
                LoggingAdminReport loggingAdminReport = factoryBean.getLoggingAdminReport();
                loggingAdminReport.report(Arrays.asList(minBoxLog));
                break;
            case timing:
                factoryBean.getLoggingCache().cache(minBoxLog);
                logger.debug("Cache Request Logging Complete.");
                break;
            default:
                logger.warn("Unsupported reporting away.");
                break;
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
