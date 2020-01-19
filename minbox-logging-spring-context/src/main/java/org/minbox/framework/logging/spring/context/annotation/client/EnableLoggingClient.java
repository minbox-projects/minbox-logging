package org.minbox.framework.logging.spring.context.annotation.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable Logging Client Service
 * register logging client {@link LoggingClientBeanDefinitionRegistrar} beans
 * use {@link org.springframework.beans.factory.support.BeanDefinitionRegistry} register beans
 *
 * @author 恒宇少年
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LoggingClientBeanDefinitionRegistrar.class)
public @interface EnableLoggingClient {
}
