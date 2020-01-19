package org.minbox.framework.logging.client.sample;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.span.LoggingSpanGenerator;

/**
 * 自定义单元编号（SpanID）{@link LoggingSpanGenerator}
 *
 * @author 恒宇少年
 */
public class CustomerSpanIdGenerator implements LoggingSpanGenerator {
    @Override
    public String createSpanId() throws MinBoxLoggingException {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return String.format("%s-%s", "span", currentTime);
    }
}
