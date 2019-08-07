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

package org.minbox.framework.logging.client.notice;

import com.alibaba.fastjson.JSON;
import org.minbox.framework.logging.client.notice.away.LoggingStorageNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * ApiBoot Logging Console Notice Listener
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-16 15:05
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingNoticeListener implements SmartApplicationListener {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingNoticeListener.class);
    /**
     * ApiBoot Log Storage Notice
     */
    private LoggingStorageNotice loggingStorageNotice;
    /**
     * format console log json
     */
    private boolean formatConsoleLogJson;
    /**
     * show console log
     */
    private boolean showConsoleLog;

    public LoggingNoticeListener(LoggingStorageNotice loggingStorageNotice, boolean formatConsoleLogJson, boolean showConsoleLog) {
        this.loggingStorageNotice = loggingStorageNotice;
        this.formatConsoleLogJson = formatConsoleLogJson;
        this.showConsoleLog = showConsoleLog;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == LoggingNoticeEvent.class;
    }

    @Override
    @Async
    public void onApplicationEvent(ApplicationEvent event) {
        LoggingNoticeEvent loggingNoticeEvent = (LoggingNoticeEvent) event;
        MinBoxLog minBoxLog = loggingNoticeEvent.getLog();
        if (showConsoleLog) {
            logger.info("Request Uri：{}， Logging：\n{}", minBoxLog.getRequestUri(), formatConsoleLogJson ? JsonUtil.beautifyJson(minBoxLog) : JSON.toJSONString(minBoxLog));
        }

        // notice logging object instance
        loggingStorageNotice.notice(minBoxLog);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
