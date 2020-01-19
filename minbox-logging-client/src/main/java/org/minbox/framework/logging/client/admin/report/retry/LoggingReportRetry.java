package org.minbox.framework.logging.client.admin.report.retry;

import org.minbox.framework.logging.core.MinBoxLog;

import java.util.List;

/**
 * Request loggers retry interface definition
 * use to log recycling, log retry, file caching
 *
 * @author 恒宇少年
 */
public interface LoggingReportRetry {
    /**
     * Add {@link MinBoxLog} to retry log collection
     *
     * @param minBoxLog {@link MinBoxLog}
     */
    void addToRetryCollection(MinBoxLog minBoxLog);

    /**
     * Add {@link MinBoxLog} list to retry log collection
     *
     * @param minBoxLogs {@link MinBoxLog}
     */
    void addToRetryCollection(List<MinBoxLog> minBoxLogs);

    /**
     * Get all {@link MinBoxLog} from retry collection
     *
     * @return {@link MinBoxLog}
     */
    List<MinBoxLog> getAllRetryLogs();
}
