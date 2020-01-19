package org.minbox.framework.logging.client.global;

/**
 * Global log collection interface
 * Provide debug, info, and error log collection
 *
 * @author 恒宇少年
 */
public interface GlobalLogging {
    /**
     * Collect Debug Level Logs
     *
     * @param msg log content
     */
    void debug(String msg);

    /**
     * Collect Debug Level Logs
     *
     * @param format    Unformatted log content
     * @param arguments List of parameters corresponding to log content
     */
    void debug(String format, Object... arguments);

    /**
     * Collect Info Level Logs
     *
     * @param msg log content
     */
    void info(String msg);

    /**
     * Collect Info Level Logs
     *
     * @param format    Unformatted log content
     * @param arguments List of parameters corresponding to log content
     */
    void info(String format, Object... arguments);

    /**
     * Collect Error Level Logs
     *
     * @param msg log content
     */
    void error(String msg);

    /**
     * Collect Error Level Logs
     *
     * @param msg       log content
     * @param throwable Exception object instance
     */
    void error(String msg, Throwable throwable);

    /**
     * Collect Error Level Logs
     *
     * @param format    Unformatted log content
     * @param arguments List of parameters corresponding to log content
     */
    void error(String format, Object... arguments);
}
