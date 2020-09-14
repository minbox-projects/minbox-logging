package org.minbox.framework.logging.client.sample;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.admin.discovery.support.LoggingAppointAdminDiscovery;
import org.minbox.framework.logging.client.global.GlobalLogging;
import org.minbox.framework.logging.client.global.support.GlobalLoggingMemoryStorage;
import org.minbox.framework.logging.client.interceptor.web.LoggingWebInterceptor;
import org.minbox.framework.logging.client.span.LogSpanIdGenerator;
import org.minbox.framework.logging.client.tracer.LogTraceIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Logging Client 配置
 *
 * @author 恒宇少年
 */
@Configuration
public class LoggingClientConfiguration implements WebMvcConfigurer {
    /**
     * Logging Client提供的日志拦截器 {@link LoggingWebInterceptor}
     */
    @Autowired
    private LoggingWebInterceptor loggingWebInterceptor;

    /**
     * 注册使用拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingWebInterceptor).addPathPatterns("/**");
    }

    /**
     * 初始化{@link LoggingFactoryBean}
     *
     * @return {@link LoggingFactoryBean}
     */
    @Bean
    public LoggingFactoryBean loggingFactoryBean() {
        LoggingFactoryBean factoryBean = new LoggingFactoryBean();
        // 设置自定义生成traceId
        factoryBean.setTraceGenerator(customerTraceGenerator());
        // 设置自定义生成spanId
        factoryBean.setSpanGenerator(customerSpanGenerator());
        // 设置在控制台输出日志
        factoryBean.setShowConsoleLog(true);
        // 格式化控制台输出的日志
        factoryBean.setFormatConsoleLog(true);
        factoryBean.setLoggingAdminDiscovery(
                new LoggingAppointAdminDiscovery(new String[]{"user:123456@localhost:9091"})
        );
        return factoryBean;
    }

    /**
     * 自定义生成traceId
     *
     * @return {@link LogTraceIdGenerator}
     */
    @Bean
    public LogTraceIdGenerator customerTraceGenerator() {
        return new CustomerTraceIdGenerator();
    }

    /**
     * 自定义生成spanId
     *
     * @return
     */
    @Bean
    public LogSpanIdGenerator customerSpanGenerator() {
        return new CustomerSpanIdGenerator();
    }

    @Bean
    public GlobalLogging globalLogging() {
        return new GlobalLoggingMemoryStorage();
    }
}
