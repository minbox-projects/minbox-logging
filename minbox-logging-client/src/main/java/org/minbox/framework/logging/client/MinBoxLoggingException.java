package org.minbox.framework.logging.client;

import lombok.NoArgsConstructor;

/**
 * Logging Exception
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-08-07 09:32
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@NoArgsConstructor
public class MinBoxLoggingException extends RuntimeException {
    /**
     * 构造函数初始化异常对象
     *
     * @param message 异常信息
     */
    public MinBoxLoggingException(String message) {
        super(message);
    }

    /**
     * 构造函数初始化异常对象
     *
     * @param message 异常消息
     * @param cause   异常堆栈信息
     */
    public MinBoxLoggingException(String message, Throwable cause) {
        super(message, cause);
    }
}
