package org.minbox.framework.logging.spring.context.annotation.admin;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable Logging Admin Service
 * register logging admin {@link LoggingAdminBeanDefinitionRegistrar} beans
 * use {@link org.springframework.beans.factory.support.BeanDefinitionRegistry} register beans
 *
 * @author 恒宇少年
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LoggingAdminBeanDefinitionRegistrar.class)
public @interface EnableLoggingAdmin {
}
