package org.minbox.framework.logging.client.interceptor;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LoggingInterceptor} basic support
 *
 * @author 恒宇少年
 */
public class LoggingAbstractInterceptor implements LoggingInterceptor {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingAbstractInterceptor.class);
    /**
     * logging factory bean
     * {@link LoggingFactoryBean}
     */
    private LoggingFactoryBean factoryBean;

    public LoggingAbstractInterceptor(LoggingFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    public LoggingFactoryBean getFactoryBean() {
        return factoryBean;
    }

    /**
     * create new traceId {@link org.minbox.framework.logging.client.tracer.LoggingTraceGenerator}
     *
     * @return traceId
     * @see org.minbox.framework.logging.client.tracer.support.LoggingDefaultTraceGenerator
     */
    @Override
    public String createTraceId() {
        return factoryBean.getTraceGenerator().createTraceId();
    }

    /**
     * create new spanId {@link org.minbox.framework.logging.client.span.LoggingSpanGenerator}
     *
     * @return spanId
     * @see org.minbox.framework.logging.client.span.support.LoggingDefaultSpanGenerator
     */
    @Override
    public String createSpanId() {
        return factoryBean.getSpanGenerator().createSpanId();
    }
}
