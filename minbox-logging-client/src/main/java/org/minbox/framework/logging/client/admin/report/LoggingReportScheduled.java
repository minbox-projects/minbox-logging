/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.logging.client.admin.report;

import org.minbox.framework.logging.client.LoggingFactoryBean;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ApiBoot Logging Report Scheduled
 *
 * @author 恒宇少年
 */
public class LoggingReportScheduled {
    /**
     * the bean name of {@link LoggingReportScheduled}
     */
    public static final String BEAN_NAME = "loggingReportScheduled";
    /**
     * Scheduled Executor Service
     */
    private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);

    /**
     * Scheduled Report
     *
     * @param factoryBean logging factory bean
     */
    public LoggingReportScheduled(LoggingFactoryBean factoryBean) {
        // scheduled report request logs
        executorService.scheduleAtFixedRate(() -> factoryBean.getLoggingAdminReport().report(),
                factoryBean.getReportInitialDelaySecond(), factoryBean.getReportIntervalSecond(), TimeUnit.SECONDS);
    }
}
