package org.minbox.framework.logging.admin.sample;

import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.storage.LoggingDataSourceStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019/9/14 8:31 下午
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
public class LoggingAdminConfiguration {

    /**
     * 当bean容器内存在{@link DataSource}对象实例时创建{@link LoggingAdminFactoryBean}示例
     *
     * @param dataSource {@link DataSource}
     * @return {@link LoggingAdminFactoryBean}
     */
    @Bean
    public LoggingAdminFactoryBean dataSourceLoggingAdminFactoryBean(DataSource dataSource) {
        LoggingAdminFactoryBean adminFactoryBean = new LoggingAdminFactoryBean();
        adminFactoryBean.setShowConsoleReportLog(true);
        adminFactoryBean.setFormatConsoleLogJson(true);
        adminFactoryBean.setLoggingStorage(new LoggingDataSourceStorage(dataSource));
        return adminFactoryBean;
    }
}
