package org.minbox.framework.logging.client;

import org.minbox.framework.logging.client.admin.discovery.LoggingAdminDiscovery;
import org.minbox.framework.logging.client.admin.report.LoggingAdminReport;
import org.minbox.framework.logging.client.admin.report.support.LoggingAdminReportSupport;
import org.minbox.framework.logging.client.cache.LoggingCache;
import org.minbox.framework.logging.client.cache.support.LoggingMemoryCache;
import org.minbox.framework.logging.client.http.rest.LoggingRestTemplateInterceptor;
import org.minbox.framework.logging.client.span.LogSpanIdGenerator;
import org.minbox.framework.logging.client.span.support.DefaultLogSpanIdGenerator;
import org.minbox.framework.logging.client.span.support.MinBoxSequenceLogSpanIdGenerator;
import org.minbox.framework.logging.client.tracer.LogTraceIdGenerator;
import org.minbox.framework.logging.client.tracer.support.DefaultLogTraceIdGenerator;
import org.minbox.framework.logging.client.tracer.support.MinBoxSequenceLogTraceIdGenerator;
import org.minbox.framework.logging.core.ReportAway;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MinBox Logging Factory Bean
 * Classes and configurations needed to encapsulate the implementation of log components
 *
 * @author 恒宇少年
 */
public class LoggingFactoryBean implements EnvironmentAware, InitializingBean, ApplicationContextAware {
    /**
     * applicationContext config environment
     * Affected by "spring.profiles.active" config properties
     * {@link EnvironmentAware}
     */
    @Nullable
    private Environment environment;

    /**
     * application context
     * {@link ApplicationContextAware}
     */
    private ApplicationContext applicationContext;
    /**
     * logging tracer generator away
     * default is uuid
     * Create a unique number for each request link
     * {@link DefaultLogTraceIdGenerator}
     */
    private LogTraceIdGenerator traceGenerator;

    /**
     * logging span id generator away
     * default is uuid
     * Unique number for each request unit created
     * {@link LogSpanIdGenerator}
     */
    private LogSpanIdGenerator spanGenerator;
    /**
     * Logging Cache
     * {@link org.minbox.framework.logging.client.cache.support.LoggingMemoryCache}
     */
    private LoggingCache loggingCache;
    /**
     * Logging Admin Report Away
     * default just report to admin
     * {@link ReportAway}
     */
    private ReportAway reportAway = ReportAway.just;
    /**
     * report to logging admin
     * {@link org.minbox.framework.logging.client.admin.report.support.LoggingAdminReportSupport}
     */
    private LoggingAdminReport loggingAdminReport;
    /**
     * logging admin discovery instance
     * {@link org.minbox.framework.logging.client.admin.discovery.support.LoggingAbstractAdminDiscovery}
     * {@link org.minbox.framework.logging.client.admin.discovery.support.LoggingAppointAdminDiscovery}
     * {@link org.minbox.framework.logging.client.admin.discovery.support.LoggingRegistryCenterAdminDiscovery}
     */
    private LoggingAdminDiscovery loggingAdminDiscovery;
    /**
     * Rest Template
     */
    private RestTemplate restTemplate;
    /**
     * Number of logs reported at a time
     */
    private Integer numberOfRequestLog = 10;

    /**
     * report to admin initial delay second
     */
    private int reportInitialDelaySecond = 5;
    /**
     * report to admin interval second
     */
    private int reportIntervalSecond = 5;
    /**
     * Ignore path array
     */
    private List<String> ignorePaths = new ArrayList() {{
        add("/error");
    }};
    /**
     * Ignore the {@link HttpStatus} of not logging
     * <p>
     * Ignore 404 by default
     */
    private List<HttpStatus> ignoreHttpStatus = new ArrayList() {
        {
            add(HttpStatus.NOT_FOUND);
        }
    };
    /**
     * Service ID
     * Affected by "spring.application.name" config properties
     */
    private String serviceId;
    /**
     * Service Address
     */
    private String serviceAddress;

    /**
     * Service Port
     */
    private Integer servicePort;
    /**
     * show log in console
     */
    private boolean showConsoleLog;
    /**
     * format log in console
     */
    private boolean formatConsoleLog;

    /**
     * Examples of classes required for initialization of constructors
     * {@link DefaultLogTraceIdGenerator}
     * {@link DefaultLogSpanIdGenerator}
     * {@link LoggingMemoryCache}
     */
    public LoggingFactoryBean() {
        this.traceGenerator = new MinBoxSequenceLogTraceIdGenerator();
        this.spanGenerator = new MinBoxSequenceLogSpanIdGenerator();
        this.loggingCache = new LoggingMemoryCache();
    }

    /**
     * after properties set handler
     * Initialize service parameter configuration
     * {@link RestTemplate}
     * {@link LoggingAdminReportSupport}
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.serviceId = environment.getProperty("spring.application.name");
        Assert.notNull(this.serviceId, "Please add the 【spring.application.name】 configuration in the application.yml or application.properties");
        String serverPort = environment.getProperty("server.port");
        Assert.notNull(serverPort, "Please add the 【server.port】 configuration in the application.yml or application.properties");
        this.servicePort = Integer.valueOf(serverPort);
        InetAddress inetAddress = InetAddress.getLocalHost();
        this.serviceAddress = inetAddress.getHostAddress();
        this.restTemplate = new RestTemplate();
        this.restTemplate.setInterceptors(Arrays.asList(new LoggingRestTemplateInterceptor()));
        this.loggingAdminReport = new LoggingAdminReportSupport(this);
    }

    @Override
    public void setEnvironment(Environment environment) {
        Assert.notNull(environment, "Environment must not be null");
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Assert.notNull(applicationContext, "ApplicationContext must not be null");
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public LogTraceIdGenerator getTraceGenerator() {
        return traceGenerator;
    }

    public void setTraceGenerator(LogTraceIdGenerator traceGenerator) {
        this.traceGenerator = traceGenerator;
    }

    public LogSpanIdGenerator getSpanGenerator() {
        return spanGenerator;
    }

    public void setSpanGenerator(LogSpanIdGenerator spanGenerator) {
        this.spanGenerator = spanGenerator;
    }

    public List<String> getIgnorePaths() {
        return ignorePaths;
    }

    public void setIgnorePaths(String[] ignorePaths) {
        if (!ObjectUtils.isEmpty(ignorePaths)) {
            this.ignorePaths.addAll(Arrays.asList(ignorePaths));
        }
    }

    public LoggingCache getLoggingCache() {
        return loggingCache;
    }

    public ReportAway getReportAway() {
        return reportAway;
    }

    public LoggingAdminReport getLoggingAdminReport() {
        return loggingAdminReport;
    }

    public LoggingAdminDiscovery getLoggingAdminDiscovery() {
        return loggingAdminDiscovery;
    }

    public Integer getNumberOfRequestLog() {
        return numberOfRequestLog;
    }

    @Nullable
    public Environment getEnvironment() {
        return environment;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public Integer getServicePort() {
        return servicePort;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setLoggingAdminDiscovery(LoggingAdminDiscovery loggingAdminDiscovery) {
        this.loggingAdminDiscovery = loggingAdminDiscovery;
    }

    public void setLoggingCache(LoggingCache loggingCache) {
        this.loggingCache = loggingCache;
    }

    public void setReportAway(ReportAway reportAway) {
        this.reportAway = reportAway;
    }

    public void setNumberOfRequestLog(Integer numberOfRequestLog) {
        this.numberOfRequestLog = numberOfRequestLog;
    }

    public int getReportInitialDelaySecond() {
        return reportInitialDelaySecond;
    }

    public void setReportInitialDelaySecond(int reportInitialDelaySecond) {
        this.reportInitialDelaySecond = reportInitialDelaySecond;
    }

    public int getReportIntervalSecond() {
        return reportIntervalSecond;
    }

    public void setReportIntervalSecond(int reportIntervalSecond) {
        this.reportIntervalSecond = reportIntervalSecond;
    }

    public boolean isShowConsoleLog() {
        return showConsoleLog;
    }

    public void setShowConsoleLog(boolean showConsoleLog) {
        this.showConsoleLog = showConsoleLog;
    }

    public boolean isFormatConsoleLog() {
        return formatConsoleLog;
    }

    public void setFormatConsoleLog(boolean formatConsoleLog) {
        this.formatConsoleLog = formatConsoleLog;
    }

    public void setIgnoreHttpStatus(List<HttpStatus> ignoreHttpStatus) {
        this.ignoreHttpStatus = ignoreHttpStatus;
    }

    public List<HttpStatus> getIgnoreHttpStatus() {
        return ignoreHttpStatus;
    }
}
