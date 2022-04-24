package org.minbox.framework.logging.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Global log data entity
 *
 * @author 恒宇少年
 */
@Data
public class GlobalLog implements Serializable {
    /**
     * The request log id
     */
    private String requestLogId;
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
    /**
     * The request logs create time
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime = LocalDateTime.now();
}
