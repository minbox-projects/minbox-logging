/*
 *  Copyright 2022. [恒宇少年 - 于起宇]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.minbox.framework.logging.admin.cleaner;

import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Expired data timing cleaner
 * <p>
 * Periodically clean up according to the configured parameters, and specify the historical data before the time node
 *
 * @author 恒宇少年
 */
public class ExpiredDataTimingCleaner implements InitializingBean {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ExpiredDataTimingCleaner.class);
    /**
     * The name of this bean in ioc
     */
    public static final String BEAN_NAME = "expiredDataTimingCleaner";
    private ScheduledExecutorService executorService;
    private LoggingAdminFactoryBean.CleanerSetting cleanerSetting;
    private LoggingStorage loggingStorage;

    public ExpiredDataTimingCleaner(LoggingAdminFactoryBean factoryBean) {
        this.cleanerSetting = factoryBean.getCleanerSetting();
        this.loggingStorage = factoryBean.getLoggingStorage();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (cleanerSetting.isEnableExpiredDataCleaner()) {
            this.executorService = new ScheduledThreadPoolExecutor(1);
            this.executorService.scheduleAtFixedRate(() -> {
                try {
                    long effectiveDeadlineSeconds =
                            cleanerSetting.getCleanerTimeUnitSinceLastCleanup().toSeconds(cleanerSetting.getCleanerTimeSinceLastCleanup());
                    // The effective deadline for cleaning data, the data before this time will be cleaned up
                    LocalDateTime effectiveDeadlineTime = LocalDateTime.now().plusSeconds(-effectiveDeadlineSeconds);
                    long deletedGlobalLogCount = loggingStorage.cleanupExpiredGlobalLogs(effectiveDeadlineTime);
                    long deletedRequestLogCount = loggingStorage.cleanupExpiredRequestLogs(effectiveDeadlineTime);
                    logger.info("Expired data cleanup is complete, until the valid time node: {}, " +
                                    "cleanup global log count: {}, cleanup request log count: {}.", effectiveDeadlineTime,
                            deletedGlobalLogCount, deletedRequestLogCount);
                } catch (Exception e) {
                    logger.error("An exception was encountered when cleaning expired data.", e);
                }
            }, 0, cleanerSetting.getCleanerExecuteInterval(), cleanerSetting.getCleanerExecuteIntervalUnit());
            logger.info("Expired data timing cleaner started successfully.");
        }
    }
}
