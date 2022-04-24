package org.minbox.framework.logging.spring.util;


import org.minbox.framework.logging.admin.cleaner.ExpiredDataTimingCleaner;
import org.minbox.framework.logging.admin.endpoint.LoggingEndpoint;
import org.minbox.framework.logging.admin.listener.ReportLogJsonFormatListener;
import org.minbox.framework.logging.admin.listener.ReportLogStorageListener;
import org.minbox.framework.logging.admin.ui.LoggingAdminUiEndpoint;
import org.minbox.framework.logging.client.filter.LoggingBodyFilter;
import org.minbox.framework.logging.client.interceptor.web.LoggingWebInterceptor;
import org.minbox.framework.logging.client.notice.LoggingNoticeListener;
import org.minbox.framework.logging.client.notice.support.LoggingAdminNotice;
import org.minbox.framework.logging.client.notice.support.LoggingLocalNotice;
import org.minbox.framework.logging.core.annotation.Endpoint;
import org.minbox.framework.logging.core.mapping.LoggingRequestMappingHandlerMapping;
import org.minbox.framework.util.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * Logging Bean Utilities classes
 *
 * @author 恒宇少年
 * @since 1.0.1
 */
public class LoggingBeanUtils {
    /**
     * Register LoggingAdmin beans
     * {@link BeanUtils#registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry, String, Class, Object...)}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingAdminBeans(BeanDefinitionRegistry registry) {
        registerReportLogJsonFormatListener(registry);
        registerReportLogStorageListener(registry);
        registerLoggingEndpoint(registry);
        registerLoggingRequestMappingHandler(registry);
        registerExpiredDataTimingCleaner(registry);
    }

    /**
     * Register logging client beans
     * {@link BeanUtils#registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry, String, Class, Object...)}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingClientBeans(BeanDefinitionRegistry registry) {
        registerLoggingInterceptor(registry);
        registerLoggingBodyFilter(registry);
        registerLoggingNoticeListener(registry);
        registerLoggingLocalNotice(registry);
        registerLoggingAdminNotice(registry);
    }

    /**
     * Register LoggingAdminUi beans
     * {@link BeanUtils#registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry, String, Class, Object...)}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingAdminUiBeans(BeanDefinitionRegistry registry) {
        registerLoggingAdminUiEndpoint(registry);
    }

    /**
     * Register Format Report Logging in console listener {@link ReportLogJsonFormatListener}
     * bean name is use {@link ReportLogJsonFormatListener#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     * @see org.minbox.framework.logging.admin.event.ReportLogEvent
     */
    public static void registerReportLogJsonFormatListener(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, ReportLogJsonFormatListener.BEAN_NAME,
                ReportLogJsonFormatListener.class);
    }

    /**
     * Register storage logging{@link ReportLogStorageListener} listener
     * bean name is use {@link ReportLogStorageListener#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     * @see org.minbox.framework.logging.admin.event.ReportLogEvent
     */
    public static void registerReportLogStorageListener(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, ReportLogStorageListener.BEAN_NAME,
                ReportLogStorageListener.class);
    }

    /**
     * Register logging endpoint {@link LoggingEndpoint}
     * Register spring MVC endpoint{@link Endpoint}
     * bean name is use {@link LoggingEndpoint#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     * @see org.minbox.framework.logging.core.mapping.LoggingRequestMappingHandlerMapping
     */
    public static void registerLoggingEndpoint(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingEndpoint.BEAN_NAME, LoggingEndpoint.class);
    }

    /**
     * Register logging request mapping handler
     * Using the {@link LoggingRequestMappingHandlerMapping} method to determine whether @Endpoint
     * is configured on the class to load the request mapping method
     * bean name is use {@link LoggingRequestMappingHandlerMapping#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingRequestMappingHandler(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingRequestMappingHandlerMapping.BEAN_NAME,
                LoggingRequestMappingHandlerMapping.class);
    }

    /**
     * Register Expired data timing cleaner
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerExpiredDataTimingCleaner(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, ExpiredDataTimingCleaner.BEAN_NAME, ExpiredDataTimingCleaner.class);
    }

    /**
     * Register logging admin ui endpoint {@link org.minbox.framework.logging.admin.ui.LoggingAdminUiEndpoint}
     * bean name is use {@link LoggingAdminUiEndpoint#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingAdminUiEndpoint(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingAdminUiEndpoint.BEAN_NAME,
                LoggingAdminUiEndpoint.class);
    }

    /**
     * Register logging interceptor {@link LoggingWebInterceptor}
     * bean name is use {@link LoggingWebInterceptor#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingInterceptor(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingWebInterceptor.BEAN_NAME, LoggingWebInterceptor.class);
    }

    /**
     * Register logging body filter {@link LoggingBodyFilter}
     * bean name is use {@link LoggingBodyFilter#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingBodyFilter(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingBodyFilter.BEAN_NAME, LoggingBodyFilter.class);
    }

    /**
     * Register logging notice listener {@link LoggingNoticeListener}
     * bean name is use {@link LoggingNoticeListener#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingNoticeListener(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingNoticeListener.BEAN_NAME, LoggingNoticeListener.class);
    }

    /**
     * Register logging local notice {@link LoggingLocalNotice}
     * bean name is use {@link LoggingLocalNotice#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingLocalNotice(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingLocalNotice.BEAN_NAME, LoggingLocalNotice.class);
    }

    /**
     * Register logging admin notice {@link LoggingAdminNotice}
     * report request logging to admin
     * bean name is use {@link LoggingAdminNotice#BEAN_NAME}
     *
     * @param registry {@link BeanDefinitionRegistry}
     */
    public static void registerLoggingAdminNotice(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, LoggingAdminNotice.BEAN_NAME, LoggingAdminNotice.class);
    }
}
