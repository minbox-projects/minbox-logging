package org.minbox.framework.logging.client.global;

import org.minbox.framework.logging.core.GlobalLog;
import org.minbox.framework.logging.core.GlobalLogLevel;
import org.springframework.util.ObjectUtils;

/**
 * GlobalLogging abstract implementation
 * Provide public methods required for log acquisition
 *
 * @author 恒宇少年
 */
public abstract class AbstractGlobalLogging implements GlobalLogging {
    /**
     * Get the StackTrace of the initial calling method
     * caller class name {@link StackTraceElement#getClassName()}
     * caller method name {@link StackTraceElement#getMethodName()}
     * caller code line number {@link StackTraceElement#getLineNumber()}
     * <p>
     * Why is the index of the Get StackTraceElement 5?
     * info、debug、error level log method invocation process：
     * 0. {@link #getCallMethodStackTrace()}
     * 1. {@link #getCallerClassName()} or {@link #getCallerMethodName()} or {@link #getCallerCodeLineNumber()}
     * 2. {@link #instanceGlobalLog()}
     * 3. {@link #buildGlobalLog(GlobalLogLevel, String)}
     * 4. {@link GlobalLogging#info(String)}
     * 5. Real business call method
     * </p>
     *
     * @return {@link StackTraceElement}
     */
    private StackTraceElement getCallMethodStackTrace() {
        StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
        return stackTraceElements[5];
    }

    /**
     * get caller class name
     *
     * @return {@link StackTraceElement#getClassName()}
     */
    protected String getCallerClassName() {
        return getCallMethodStackTrace().getClassName();
    }

    /**
     * get caller method name
     *
     * @return {@link StackTraceElement#getMethodName()}
     */
    protected String getCallerMethodName() {
        return getCallMethodStackTrace().getMethodName();
    }

    /**
     * get caller code line number
     *
     * @return {@link StackTraceElement#getLineNumber()}
     */
    protected int getCallerCodeLineNumber() {
        return getCallMethodStackTrace().getLineNumber();
    }

    /**
     * create the {@link GlobalLog} object instance
     * initialization set call information
     *
     * @return {@link GlobalLog}
     */
    protected GlobalLog instanceGlobalLog() {
        GlobalLog globalLog = new GlobalLog();
        globalLog.setCallerClass(getCallerClassName());
        globalLog.setCallerMethod(getCallerMethodName());
        globalLog.setCallerCodeLineNumber(getCallerCodeLineNumber());
        return globalLog;
    }

    /**
     * Build Global Log Instance
     *
     * @param level   {@link GlobalLogLevel}
     * @param content {@link GlobalLog#getContent()}
     * @return {@link GlobalLog}
     */
    protected GlobalLog buildGlobalLog(GlobalLogLevel level, String content) {
        GlobalLog globalLog = instanceGlobalLog();
        globalLog.setLevel(level);
        globalLog.setContent(content);
        return globalLog;
    }

    /**
     * Replace placeholders for log content
     *
     * @param format    Unformatted log content
     * @param arguments List of parameters corresponding to log content
     * @return formatted log
     */
    protected String replacePlaceholder(String format, Object[] arguments) {
        if (!ObjectUtils.isEmpty(arguments)) {
            for (int i = 0; i < arguments.length; i++) {
                format = format.replaceFirst("\\{\\}", String.valueOf(arguments[i]));
            }
        }
        return format;
    }
}
