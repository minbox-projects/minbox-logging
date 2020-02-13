/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.logging.client.admin.discovery.support;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.admin.discovery.lb.LoadBalanceNode;
import org.minbox.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy;
import org.minbox.framework.logging.client.admin.discovery.lb.support.SmoothWeightedRoundRobinStrategy;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * ApiBoot Logging Appoint Admin Discovery
 * Support multiple admins
 *
 * @author 恒宇少年
 */
public class LoggingAppointAdminDiscovery extends LoggingAbstractAdminDiscovery {
    /**
     * basic user split
     */
    private static final String BASIC_SPLIT = "@";
    /**
     * http prefix
     */
    private static final String HTTP_PREFIX = "http://";
    /**
     * ApiBoot Logging Admin Server Address
     */
    private String[] adminServerAddress;
    /**
     * Choice logging admin load balancer{@link LoadBalanceStrategy}
     */
    private LoadBalanceStrategy loadBalanceStrategy;
    /**
     * ApiBoot Logging Admin Server Address
     * To Current Thread
     */
    private final ThreadLocal<String> CURRENT_SERVER_ADDRESS = new ThreadLocal();

    /**
     * Set default load balance strategy {@link SmoothWeightedRoundRobinStrategy}
     *
     * @param adminServerAddress logging admin serviceAddress
     */
    public LoggingAppointAdminDiscovery(String[] adminServerAddress) {
        this.adminServerAddress = adminServerAddress;
        this.loadBalanceStrategy = new SmoothWeightedRoundRobinStrategy();
    }

    /**
     * load balance lookup admin server address {@link LoadBalanceNode#getAddress()}
     * get load-balanced admin address {@link LoadBalanceStrategy#lookup(String[])}
     * exclude basic auth info {@link #extractBasicAuth(String)} and append {@link #HTTP_PREFIX}
     *
     * @return admin server address
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public String lookup() throws MinBoxLoggingException {
        Assert.notNull(adminServerAddress, "ApiBoot Logging Admin Server Address Is Null.");
        String address = loadBalanceStrategy.lookup(adminServerAddress);
        CURRENT_SERVER_ADDRESS.set(address);
        address = extractBasicAuth(address);
        return String.format("%s%s", HTTP_PREFIX, address);
    }

    /**
     * get basic auth
     *
     * @return basic auth base64
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public String getBasicAuth() throws MinBoxLoggingException {
        String serverAddress = CURRENT_SERVER_ADDRESS.get();
        if (serverAddress.indexOf(BASIC_SPLIT) > 0) {
            String basicInfo = serverAddress.substring(0, serverAddress.indexOf(BASIC_SPLIT));
            CURRENT_SERVER_ADDRESS.remove();
            if (!ObjectUtils.isEmpty(basicInfo)) {
                return getBasicBase64(basicInfo);
            }
        }
        return null;
    }

    /**
     * Set load balance strategy
     *
     * @param loadBalanceStrategy {@link LoadBalanceStrategy}
     * @see org.minbox.framework.logging.client.admin.discovery.lb.support.DefaultLoadBalanceStrategy
     * @see org.minbox.framework.logging.client.admin.discovery.lb.support.RandomWeightedStrategy
     * @see SmoothWeightedRoundRobinStrategy
     */
    public void setLoadBalanceStrategy(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    /**
     * Extract basic auth from logging admin server address
     *
     * @param adminServerAddress {@link LoadBalanceStrategy#lookup(String[])}
     * @return admin server address
     */
    protected String extractBasicAuth(String adminServerAddress) {
        if (adminServerAddress.indexOf(BASIC_SPLIT) > 0) {
            adminServerAddress = adminServerAddress.substring(adminServerAddress.indexOf(BASIC_SPLIT) + 1);
        }
        return adminServerAddress;
    }
}
