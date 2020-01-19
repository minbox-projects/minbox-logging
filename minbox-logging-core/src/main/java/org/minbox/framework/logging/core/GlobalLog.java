package org.minbox.framework.logging.core;

import lombok.Data;

import java.io.Serializable;

/**
 * Global log data entity
 *
 * @author 恒宇少年
 */
@Data
public class GlobalLog implements Serializable {
    /**
     * Global log level
     * {@link GlobalLogLevel}
     */
    private GlobalLogLevel level;
    /**
     * all level's log content
     */
    private String content;
    /**
     * Error stack information collected in error level logs
     */
    private String exceptionStack;
    /**
     * caller class name
     * {@link StackTraceElement#getClassName()}
     */
    private String callerClass;
    /**
     * caller method name
     * {@link StackTraceElement#getMethodName()}
     */
    private String callerMethod;
    /**
     * caller code line number
     * {@link StackTraceElement#getLineNumber()}
     */
    private int callerCodeLineNumber;
    /**
     * the global log create time
     * default is current time millis
     */
    private Long createTime = System.currentTimeMillis();
}
