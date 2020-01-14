package org.minbox.framework.logging.client.interceptor;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019/9/15 10:43 下午
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
