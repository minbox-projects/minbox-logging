package org.minbox.framework.logging.admin.sample;

import org.minbox.framework.logging.admin.event.ReportLogEvent;
import org.minbox.framework.logging.core.LoggingClientNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义上报日志事件{@link ReportLogEvent}监听
 *
 * @author 恒宇少年
 */
@Component
public class CustomerReportEventListener implements SmartApplicationListener {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(CustomerReportEventListener.class);

    /**
     * 判断事件类型为{@link ReportLogEvent}
     *
     * @param eventType
     * @return
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ReportLogEvent.class == eventType;
    }

    /**
     * 自定义处理业务
     * Client一次可上报多条日志{@link MinBoxLog}信息
     *
     * @param event {@link ReportLogEvent}
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ReportLogEvent reportLogEvent = (ReportLogEvent) event;
        LoggingClientNotice loggingClientNotice = reportLogEvent.getLogClientNotice();

        // MinBoxLog 日志列表
        List<MinBoxLog> logs = loggingClientNotice.getLoggers();

        logger.debug("上报日志服务：{}，IP地址：{}，端口号：{}，日志列表：", loggingClientNotice.getClientServiceId(),
                loggingClientNotice.getClientServiceIp(),
                loggingClientNotice.getClientServicePort(),
                logs);
    }
}
