package org.minbox.framework.logging.client.interceptor;

import org.minbox.framework.logging.client.span.LogSpanIdGenerator;
import org.minbox.framework.logging.client.tracer.LogTraceIdGenerator;

/**
 * MinBox logging interceptor
 *
 * @author 恒宇少年
 */
public interface LoggingInterceptor {
    /**
     * create new traceId
     * {@link LogTraceIdGenerator}
     *
     * @return traceId
     */
    String createTraceId();

    /**
     * create new spanId
     * {@link LogSpanIdGenerator}
     *
     * @return spanId
     */
    String createSpanId();
}
