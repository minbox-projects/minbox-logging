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

package org.minbox.framework.logging.client.admin.report.support;

import com.alibaba.fastjson.JSON;
import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.admin.report.LoggingAdminReport;
import org.minbox.framework.logging.client.cache.LoggingCache;
import org.minbox.framework.logging.core.LoggingClientNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.logging.core.ReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiBoot Logging Admin Report Support
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-21 14:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingAdminReportSupport implements LoggingAdminReport, DisposableBean {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingAdminReportSupport.class);

    /**
     * Report Request Logging Uri
     */
    private static final String REPORT_LOG_URI = "/logging/report";
    /**
     * Content-Type HEADER NAME
     */
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    /**
     * Authorization HEADER NAME
     */
    private static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * logging factory bean
     */
    private LoggingFactoryBean factoryBean;

    public LoggingAdminReportSupport(LoggingFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    /**
     * Report Logs Interval
     *
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public void report() throws MinBoxLoggingException {
        List<MinBoxLog> logs = new ArrayList<>();
        LoggingCache loggingCache = factoryBean.getLoggingCache();
        Integer numberOfRequestLog = factoryBean.getNumberOfRequestLog();
        try {
            // get log from cache
            logs = loggingCache.getLogs(numberOfRequestLog);
            // execute report
            report(logs);
        }
        // Recycle the request log when an exception is encountered
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (!ObjectUtils.isEmpty(logs)) {
                logs.stream().forEach(log -> loggingCache.cache(log));
            }
        }
    }

    /**
     * Report Logs To Admin
     * get number logs from cache
     * lookup a available api-boot-logging-admin service url
     * report request logs ro admin service
     * if admin use spring security, set restTemplate header basic auth info
     *
     * @param logs Request Logs
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public void report(List<MinBoxLog> logs) throws MinBoxLoggingException {
        if (ObjectUtils.isEmpty(logs)) {
            return;
        }
        // ApiBoot Logging Admin Server Url
        String adminServiceUrl = getAfterFormatAdminUrl();
        // client notice entity
        LoggingClientNotice clientNotice = new LoggingClientNotice();
        clientNotice.getLoggers().addAll(logs);
        clientNotice.setClientServiceId(factoryBean.getServiceId());
        clientNotice.setClientServiceIp(factoryBean.getServiceAddress());
        clientNotice.setClientServicePort(factoryBean.getServicePort());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        String basicAuth = factoryBean.getLoggingAdminDiscovery().getBasicAuth();
        if (!ObjectUtils.isEmpty(basicAuth)) {
            headers.add(HEADER_AUTHORIZATION, basicAuth);
        }
        HttpEntity<String> httpEntity = new HttpEntity(JSON.toJSONString(clientNotice), headers);

        ResponseEntity<ReportResponse> response = factoryBean.getRestTemplate().postForEntity(adminServiceUrl, httpEntity, ReportResponse.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody().getStatus().equals(ReportResponse.SUCCESS)) {
            logger.debug("Report Request Logging Successfully To Admin.");
        } else {
            logger.error("Report Request Logging Error To Admin.");
        }
    }

    /**
     * Get After Format Admin URL
     *
     * @return ApiBoot Logging Admin URL
     */
    private String getAfterFormatAdminUrl() {
        // api boot admin service uri
        String adminServiceUri = factoryBean.getLoggingAdminDiscovery().lookup();
        // api boot admin service url
        return String.format("%s%s", adminServiceUri, REPORT_LOG_URI);
    }

    /**
     * Bean Destroy
     * When destroyed, report all request logs in the cache to admin
     *
     * @throws Exception exception
     */
    @Override
    public void destroy() throws Exception {
        // get all cache logs
        List<MinBoxLog> logs = factoryBean.getLoggingCache().getAll();
        // report to admin
        report(logs);
    }
}
