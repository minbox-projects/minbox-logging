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

package org.minbox.framework.logging.admin.ui;

import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.minbox.framework.logging.core.annotation.Endpoint;
import org.minbox.framework.logging.core.response.LoggingResponse;
import org.minbox.framework.logging.core.response.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * ApiBoot Logging Admin UI Endpoint
 * The ability to provide logging admin data to the public
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-26 14:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Endpoint
public class LoggingAdminUiEndpoint {
    /**
     * The bean name of {@link LoggingAdminUiEndpoint}
     */
    public static final String BEAN_NAME = "loggingAdminUiEndpoint";
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingAdminUiEndpoint.class);
    /**
     * LoggingAdmin FactoryBean{@link LoggingAdminFactoryBean}
     */
    private LoggingAdminFactoryBean adminFactoryBean;
    /**
     * {@link LoggingStorage} implement class instance
     */
    private LoggingStorage loggingStorage;

    public LoggingAdminUiEndpoint(LoggingAdminFactoryBean adminFactoryBean) {
        this.adminFactoryBean = adminFactoryBean;
        this.loggingStorage = adminFactoryBean.getLoggingStorage();
    }

    /**
     * Real-time query of link log information
     *
     * @param queryCount query log count
     * @return log list
     */
    @GetMapping(value = "/logs")
    @ResponseBody
    public List<LoggingResponse> logs(@RequestParam(value = "queryCount", defaultValue = "500") int queryCount) {
        try {
            List<LoggingResponse> loggingResponseList = loggingStorage.findTopList(queryCount);
            return loggingResponseList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Returns the list of log collectors
     * {@link ServiceResponse}
     *
     * @return ServiceResponse
     */
    @GetMapping(value = "/services")
    @ResponseBody
    public List<ServiceResponse> applications() {
        try {
            List<ServiceResponse> serviceResponseList = loggingStorage.findAllService();
            return serviceResponseList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * forward to index page
     *
     * @return index html name
     */
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }

    /**
     * forward to login page
     *
     * @return login html name
     */
    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public String login() {
        return "login";
    }

    /**
     * get spring security login user info
     * Determine whether to display user icons
     *
     * @param principal spring security user info
     * @return user info map
     */
    @ModelAttribute(value = "user", binding = false)
    public Map<String, Object> getUser(@Nullable Principal principal) {
        if (principal != null) {
            return singletonMap("name", principal.getName());
        }
        return emptyMap();
    }

    /**
     * @return
     */
    @GetMapping(path = "/sba-settings.js", produces = "application/javascript")
    public String sbaSettings() {
        return "sba-settings.js";
    }

    @ModelAttribute(value = "baseUrl", binding = false)
    public String getBaseUrl(UriComponentsBuilder uriBuilder) {
        UriComponents publicComponents = UriComponentsBuilder.newInstance().build();
        if (publicComponents.getScheme() != null) {
            uriBuilder.scheme(publicComponents.getScheme());
        }
        if (publicComponents.getHost() != null) {
            uriBuilder.host(publicComponents.getHost());
        }
        if (publicComponents.getPort() != -1) {
            uriBuilder.port(publicComponents.getPort());
        }
        if (publicComponents.getPath() != null) {
            uriBuilder.path(publicComponents.getPath());
        }
        return uriBuilder.path("/").toUriString();
    }

    @ModelAttribute(value = "uiSettings", binding = false)
    public LoggingAdminFactoryBean.AdminUiSetting getUiSettings() {
        return this.adminFactoryBean.getAdminUiSetting();
    }
}
