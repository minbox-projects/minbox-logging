package org.minbox.framework.logging.client.interceptor;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.span.LogSpanIdGenerator;
import org.minbox.framework.logging.client.span.support.DefaultLogSpanIdGenerator;
import org.minbox.framework.logging.client.tracer.LogTraceIdGenerator;
import org.minbox.framework.logging.client.tracer.support.DefaultLogTraceIdGenerator;
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
     * create new traceId {@link LogTraceIdGenerator}
     *
     * @return traceId
     * @see DefaultLogTraceIdGenerator
     */
    @Override
    public String createTraceId() {
        return factoryBean.getTraceGenerator().createTraceId();
    }

    /**
     * create new spanId {@link LogSpanIdGenerator}
     *
     * @return spanId
     * @see DefaultLogSpanIdGenerator
     */
    @Override
    public String createSpanId() {
        return factoryBean.getSpanGenerator().createSpanId();
    }
}
