package org.minbox.framework.logging.client.interceptor;

/**
 * MinBox logging interceptor
 *
 * @author 恒宇少年
 */
public interface LoggingInterceptor {
    /**
     * create new traceId
     * {@link org.minbox.framework.logging.client.tracer.LoggingTraceGenerator}
     *
     * @return traceId
     */
    String createTraceId();

    /**
     * create new spanId
     * {@link org.minbox.framework.logging.client.span.LoggingSpanGenerator}
     *
     * @return spanId
     */
    String createSpanId();
}
