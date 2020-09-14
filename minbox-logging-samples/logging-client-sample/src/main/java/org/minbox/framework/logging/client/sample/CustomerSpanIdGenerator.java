package org.minbox.framework.logging.client.sample;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.span.LogSpanIdGenerator;

/**
 * 自定义单元编号（SpanID）{@link LogSpanIdGenerator}
 *
 * @author 恒宇少年
 */
public class CustomerSpanIdGenerator implements LogSpanIdGenerator {
    @Override
    public String createSpanId() throws MinBoxLoggingException {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return String.format("%s-%s", "span", currentTime);
    }
}
