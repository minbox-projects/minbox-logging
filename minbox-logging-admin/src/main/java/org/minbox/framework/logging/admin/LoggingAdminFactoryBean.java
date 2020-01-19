package org.minbox.framework.logging.admin;

import org.minbox.framework.logging.admin.listener.ReportLogJsonFormatListener;
import org.minbox.framework.logging.admin.listener.ReportLogStorageListener;
import org.minbox.framework.logging.admin.storage.LoggingDataSourceStorage;
import org.minbox.framework.logging.admin.storage.LoggingDefaultStorage;
import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * MinBox Logging Admin FactoryBean
 * Provide the parameter configuration needed by the management side
 * <p>
 * Register the following beans to BeanFactory{@link org.springframework.beans.factory.BeanFactory}：
 * Register {@link LoggingDataSourceStorage} bean
 * Register {@link ReportLogStorageListener} bean
 * Register {@link ReportLogJsonFormatListener} bean
 * Register {@link org.minbox.framework.logging.admin.endpoint.LoggingEndpoint} bean
 *
 * @author 恒宇少年
 * @since 1.0.1
 */
public class LoggingAdminFactoryBean
        implements ApplicationContextAware {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingAdminFactoryBean.class);
    /**
     * Whether to display the logs reported on the console
     */
    private boolean showConsoleReportLog;
    /**
     * Does the console display the formatted log JSON
     */
    private boolean formatConsoleLogJson;
    /**
     * spring {@link ApplicationContext} application context
     */
    private ApplicationContext applicationContext;
    /**
     * {@link LoggingStorage}
     * default datasource support {@link LoggingDataSourceStorage}
     */
    private LoggingStorage loggingStorage;
    /**
     * {@link AdminUiSetting}
     * setting logging admin ui config
     */
    private AdminUiSetting adminUiSetting;

    /**
     * init default instance
     * {@link LoggingDefaultStorage}
     */
    public LoggingAdminFactoryBean() {
        this.loggingStorage = new LoggingDefaultStorage();
        this.adminUiSetting = new AdminUiSetting();
    }

    public boolean isShowConsoleReportLog() {
        return showConsoleReportLog;
    }

    public void setShowConsoleReportLog(boolean showConsoleReportLog) {
        this.showConsoleReportLog = showConsoleReportLog;
    }

    public boolean isFormatConsoleLogJson() {
        return formatConsoleLogJson;
    }

    public void setFormatConsoleLogJson(boolean formatConsoleLogJson) {
        this.formatConsoleLogJson = formatConsoleLogJson;
    }

    public LoggingStorage getLoggingStorage() {
        return loggingStorage;
    }

    public void setLoggingStorage(LoggingStorage loggingStorage) {
        this.loggingStorage = loggingStorage;
    }

    public AdminUiSetting getAdminUiSetting() {
        return adminUiSetting;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        logger.debug("ApplicationContext set complete.");
    }

    /**
     * Logging Admin Ui Setting
     */
    public static class AdminUiSetting {
        /**
         * page title
         */
        private String title = "MinBox Logging Admin";
        /**
         * ApiBoot white logo
         */
        private String brand = "<img src=\"assets/img/apiboot-white.png\">";
        /**
         * notification filter enable
         */
        private boolean notificationFilterEnabled;
        /**
         * remember me enabled
         */
        private boolean rememberMeEnabled;
        /**
         * page routes
         */
        private List<String> routes = asList(
                "/about/**",
                "/services/**",
                "/logs/**",
                "/wallboard/**"
        );

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public boolean isNotificationFilterEnabled() {
            return notificationFilterEnabled;
        }

        public void setNotificationFilterEnabled(boolean notificationFilterEnabled) {
            this.notificationFilterEnabled = notificationFilterEnabled;
        }

        public boolean isRememberMeEnabled() {
            return rememberMeEnabled;
        }

        public void setRememberMeEnabled(boolean rememberMeEnabled) {
            this.rememberMeEnabled = rememberMeEnabled;
        }

        public List<String> getRoutes() {
            return routes;
        }

        public void setRoutes(List<String> routes) {
            this.routes = routes;
        }
    }
}
