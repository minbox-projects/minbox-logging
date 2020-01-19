package org.minbox.framework.logging.client.admin.discovery.lb;

import org.minbox.framework.logging.client.MinBoxLoggingException;

/**
 * Load balance strategy
 *
 * @author 恒宇少年
 */
public interface LoadBalanceStrategy {
    /**
     * lookup Load-balanced addresses
     *
     * @param adminAddress logging admin address array
     * @return Load-balanced addresses
     * @throws MinBoxLoggingException MinBox Logging Exception
     */
    String lookup(String[] adminAddress) throws MinBoxLoggingException;
}
