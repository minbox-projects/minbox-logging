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

package org.minbox.framework.logging.admin.listener;

import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.endpoint.LoggingEndpoint;
import org.minbox.framework.logging.admin.event.ReportLogEvent;
import org.minbox.framework.logging.core.LoggingClientNotice;
import org.minbox.framework.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * Report Log Json Format Listener
 *
 * @author 恒宇少年
 * @see ReportLogEvent
 */
public class ReportLogJsonFormatListener implements SmartApplicationListener {
    /**
     * The bean name of {@link ReportLogJsonFormatListener}
     */
    public static final String BEAN_NAME = "reportLogJsonFormatListener";
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ReportLogJsonFormatListener.class);

    private LoggingAdminFactoryBean loggingAdminFactoryBean;

    public ReportLogJsonFormatListener(LoggingAdminFactoryBean loggingAdminFactoryBean) {
        this.loggingAdminFactoryBean = loggingAdminFactoryBean;
    }

    /**
     * Report logs on console output
     * Format Update Log
     *
     * @param event ReportLogEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            ReportLogEvent reportLogEvent = (ReportLogEvent) event;
            if (loggingAdminFactoryBean.isShowConsoleReportLog()) {
                LoggingClientNotice notice = reportLogEvent.getLogClientNotice();
                String serviceInfo = String.format("%s -> %s", notice.getClientServiceId(), notice.getClientServiceIp());
                logger.info("Receiving Service: 【{}】, Request Log Report，Logging Content：{}", serviceInfo,
                        loggingAdminFactoryBean.isFormatConsoleLogJson() ? JsonUtils.beautifyJson(notice.getLoggers()) :
                                JsonUtils.toJsonString(notice.getLoggers()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == LoggingEndpoint.class;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ReportLogEvent.class;
    }

    /**
     * first execute
     *
     * @return execute order
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
