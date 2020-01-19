package org.minbox.framework.logging.client.sample;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.tracer.LoggingTraceGenerator;

import java.util.UUID;

/**
 * 自定义链路编号（TraceID）{@link LoggingTraceGenerator}
 *
 * @author 恒宇少年
 */
public class CustomerTraceIdGenerator implements LoggingTraceGenerator {
    @Override
    public String createTraceId() throws MinBoxLoggingException {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
