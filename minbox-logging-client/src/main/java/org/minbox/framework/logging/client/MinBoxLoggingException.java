package org.minbox.framework.logging.client;

import lombok.NoArgsConstructor;

/**
 * Custom runtime log exception
 *
 * @author 恒宇少年
 */
@NoArgsConstructor
public class MinBoxLoggingException extends RuntimeException {
    /**
     * Constructor initializes exception object
     *
     * @param message Exception message
     */
    public MinBoxLoggingException(String message) {
        super(message);
    }

    /**
     * Constructor initializes exception object
     *
     * @param message Exception message
     * @param cause   {@link Throwable} stack information
     */
    public MinBoxLoggingException(String message, Throwable cause) {
        super(message, cause);
    }
}
