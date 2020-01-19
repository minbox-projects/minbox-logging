package org.minbox.framework.logging.client.interceptor;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019/9/15 5:01 下午
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
