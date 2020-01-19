package org.minbox.framework.logging.client.sample;

import org.minbox.framework.logging.client.notice.LoggingNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.springframework.stereotype.Component;

/**
 * 自定义日志通知
 * 当不上报日志到`Logging Admin`时，可使用{@link LoggingNotice}来进行本地处理日志
 * 上报日志与本地处理不冲突，可并存
 *
 * @author 恒宇少年
 */
@Component
public class CustomerLoggingNotice implements LoggingNotice {
    /**
     * 通知方法
     *
     * @param minBoxLog ApiBoot Log
     */
    @Override
    public void notice(MinBoxLog minBoxLog) {
        System.out.println(minBoxLog.getTraceId());
    }

    /**
     * 通知执行优先级
     * {@link #getOrder()}方法返回值值越小优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
