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
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * Data Cleaner Configuration Parameters
     */
    private CleanerSetting cleanerSetting;

    /**
     * init default instance
     * {@link LoggingDefaultStorage}
     */
    public LoggingAdminFactoryBean() {
        this.loggingStorage = new LoggingDefaultStorage();
        this.adminUiSetting = new AdminUiSetting();
        this.cleanerSetting = new CleanerSetting();
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

    public CleanerSetting getCleanerSetting() {
        return cleanerSetting;
    }

    public void setCleanerSetting(CleanerSetting cleanerSetting) {
        this.cleanerSetting = cleanerSetting;
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

    public static class CleanerSetting {
        /**
         * Whether to enable outdated data cleaner
         * <p>
         * enabled by default
         */
        private boolean enableExpiredDataCleaner = true;
        /**
         * Data cleaner execution interval
         */
        private int cleanerExecuteInterval = 1;
        /**
         * Data cleaner execution interval time unit
         */
        private TimeUnit cleanerExecuteIntervalUnit = TimeUnit.HOURS;
        /**
         * The value of the cleaning time from the last deadline.
         * When data cleaning is performed, the specific cleaning time will be calculated according to this value and {@link #cleanerTimeUnitSinceLastCleanup}.
         */
        private int cleanerTimeSinceLastCleanup = 30;
        /**
         * The time unit at which the cleaner needs to delete the data
         */
        private TimeUnit cleanerTimeUnitSinceLastCleanup = TimeUnit.DAYS;


        public boolean isEnableExpiredDataCleaner() {
            return enableExpiredDataCleaner;
        }

        public void setEnableExpiredDataCleaner(boolean enableExpiredDataCleaner) {
            this.enableExpiredDataCleaner = enableExpiredDataCleaner;
        }

        public int getCleanerExecuteInterval() {
            return cleanerExecuteInterval;
        }

        public void setCleanerExecuteInterval(int cleanerExecuteInterval) {
            if (cleanerExecuteInterval > 0) {
                this.cleanerExecuteInterval = cleanerExecuteInterval;
            }
        }

        public TimeUnit getCleanerExecuteIntervalUnit() {
            return cleanerExecuteIntervalUnit;
        }

        public void setCleanerExecuteIntervalUnit(TimeUnit cleanerExecuteIntervalUnit) {
            if (!ObjectUtils.isEmpty(cleanerExecuteIntervalUnit)) {
                this.cleanerExecuteIntervalUnit = cleanerExecuteIntervalUnit;
            }
        }

        public int getCleanerTimeSinceLastCleanup() {
            return cleanerTimeSinceLastCleanup;
        }

        public void setCleanerTimeSinceLastCleanup(int cleanerTimeSinceLastCleanup) {
            if(cleanerTimeSinceLastCleanup > 0) {
                this.cleanerTimeSinceLastCleanup = cleanerTimeSinceLastCleanup;
            }
        }

        public TimeUnit getCleanerTimeUnitSinceLastCleanup() {
            return cleanerTimeUnitSinceLastCleanup;
        }

        public void setCleanerTimeUnitSinceLastCleanup(TimeUnit cleanerTimeUnitSinceLastCleanup) {
            if(!ObjectUtils.isEmpty(cleanerTimeUnitSinceLastCleanup)) {
                this.cleanerTimeUnitSinceLastCleanup = cleanerTimeUnitSinceLastCleanup;
            }
        }
    }
}
