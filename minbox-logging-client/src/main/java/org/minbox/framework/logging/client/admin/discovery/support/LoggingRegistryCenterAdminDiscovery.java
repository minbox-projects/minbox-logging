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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * ApiBoot Logging Registry Center Admin Discovery
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 15:45
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingRegistryCenterAdminDiscovery extends LoggingAbstractAdminDiscovery {
    /**
     * admin service id
     */
    private String serviceId;
    /**
     * admin spring security username
     */
    private String username;
    /**
     * admin spring security user password
     */
    private String password;
    /**
     * Ribbon Load Balance Client
     */
    private LoadBalancerClient loadBalancerClient;

    public LoggingRegistryCenterAdminDiscovery(String serviceId, LoadBalancerClient loadBalancerClient) {
        this.serviceId = serviceId;
        this.loadBalancerClient = loadBalancerClient;
    }

    /**
     * lookup logging admin service url
     *
     * @return Service Instance URL
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public String lookup() throws MinBoxLoggingException {
        Assert.notNull(serviceId, "ApiBoot Logging Admin ServiceID is null.");
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        if (ObjectUtils.isEmpty(serviceInstance)) {
            throw new MinBoxLoggingException("There Is No Online ApiBoot Logging Admin Service.");
        }
        return serviceInstance.getUri().toString();
    }

    /**
     * Setting basic auth username
     *
     * @param username {{@link #username}}
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setting basic auth password
     *
     * @param password {@link #password}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get basic auth base64 string
     *
     * @return basic auth base64 string
     * @throws MinBoxLoggingException ApiBoot Exception
     */
    @Override
    public String getBasicAuth() throws MinBoxLoggingException {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
            return null;
        }
        return getBasicBase64(String.format("%s:%s", username, password));
    }
}
