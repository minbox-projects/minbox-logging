package org.minbox.framework.logging.client.admin.report.retry;

import org.minbox.framework.logging.core.MinBoxLog;
import org.springframework.beans.factory.DisposableBean;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link LoggingReportRetry} Support
 *
 * @author 恒宇少年
 */
public class LoggingReportRetrySupport implements LoggingReportRetry, DisposableBean {
    /**
     * the bean name of {@link LoggingReportRetrySupport}
     */
    public static final String BEAN_NAME = "loggingReportRetry";
    /**
     * Log waiting for retry report
     */
    private static final LinkedList<MinBoxLog> LOGS = new LinkedList<>();

    @Override
    public void addToRetryCollection(MinBoxLog minBoxLog) {
        LOGS.add(minBoxLog);
    }

    @Override
    public void addToRetryCollection(List<MinBoxLog> minBoxLogs) {
        LOGS.addAll(minBoxLogs);
    }

    @Override
    public List<MinBoxLog> getAllRetryLogs() {
        return LOGS;
    }

    @Override
    public void destroy() throws Exception {

    }
}
