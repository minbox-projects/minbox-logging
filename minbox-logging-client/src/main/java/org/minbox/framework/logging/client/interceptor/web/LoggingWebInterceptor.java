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

package org.minbox.framework.logging.client.interceptor.web;

import org.minbox.framework.logging.client.LogThreadLocal;
import org.minbox.framework.logging.client.LoggingConstant;
import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.global.GlobalLoggingThreadLocal;
import org.minbox.framework.logging.client.interceptor.LoggingAbstractInterceptor;
import org.minbox.framework.logging.client.notice.LoggingNoticeEvent;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.util.JsonUtils;
import org.minbox.framework.util.StackTraceUtil;
import org.minbox.framework.util.UrlUtils;
import org.minbox.framework.web.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * ApiBoot Logging SpringBoot Web Interceptor
 * Start a log link
 *
 * @author 恒宇少年
 */
public class LoggingWebInterceptor
        extends LoggingAbstractInterceptor
        implements HandlerInterceptor {
    /**
     * the bean name of {@link LoggingWebInterceptor}
     */
    public static final String BEAN_NAME = "loggingInterceptor";
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingWebInterceptor.class);
    /**
     * {@link LoggingFactoryBean}
     */
    private LoggingFactoryBean factoryBean;

    public LoggingWebInterceptor(LoggingFactoryBean factoryBean) {
        super(factoryBean);
        this.factoryBean = factoryBean;
    }

    /**
     * Execution before controller method
     * If "traceId" is directly used in the request header information, otherwise a new one is created
     * If the request header information contains "spanId", bind the parent-subordinate relationship
     * Extract request IP, request parameters, request uri and set it to MinBoxLog
     *
     * @param request  http request
     * @param response http response
     * @param handler  execute object
     * @return true is success,false is Intercepted
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // check is matcher ignore url
        // check is matcher ignore httpStatus
        if (checkIgnore(HttpRequestUtil.getUri(request)) || checkIgnoreHttpStatus(response.getStatus())) {
            return true;
        }
        MinBoxLog log = new MinBoxLog();
        try {
            log.setRequestIp(HttpRequestUtil.getIp(request));
            log.setRequestUri(HttpRequestUtil.getUri(request));
            log.setRequestMethod(request.getMethod());
            Map requestParams = HttpRequestUtil.getPathParams(request);
            log.setRequestParam(!ObjectUtils.isEmpty(requestParams) ? JsonUtils.toJsonString(requestParams) : null);
            // see https://gitee.com/minbox-projects/minbox-logging/issues/I1JWSK
            if (!HttpRequestUtil.isMultipart(request)) {
                String requestBody = HttpRequestUtil.getRequestBody(request);
                log.setRequestBody(!ObjectUtils.isEmpty(requestBody) ? requestBody : null);
            }
            log.setRequestHeader(JsonUtils.toJsonString(HttpRequestUtil.getRequestHeaders(request)));
            log.setHttpStatus(response.getStatus());
            log.setStartTime(System.currentTimeMillis());
            log.setServiceId(factoryBean.getServiceId());
            log.setServicePort(String.valueOf(factoryBean.getServicePort()));
            InetAddress inetAddress = InetAddress.getLocalHost();
            log.setServiceIp(inetAddress.getHostAddress());
            String traceId = getOrCreateTraceId(request);
            log.setTraceId(traceId);
            String parentSpanId = extractParentSpanId(request);
            log.setParentSpanId(parentSpanId);
            String spanId = createSpanId();
            log.setSpanId(spanId);
            logger.debug("Request SpanId：{}", log.getSpanId());
        } catch (Exception e) {
            // set exception stack
            log.setExceptionStack(StackTraceUtil.getStackTrace(e));
        } finally {
            LogThreadLocal.set(log);
        }
        return true;
    }

    /**
     * Execution after controller method
     *
     * @param request  http request
     * @param response http response
     * @param handler  execute object
     * @param ex       have exception object
     * @throws Exception exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            // Get Current Thread ApiBoot Log Instance
            MinBoxLog log = LogThreadLocal.get();
            if (ObjectUtils.isEmpty(log) || this.checkIgnore(HttpRequestUtil.getUri(request))) {
                return;
            }
            log.setHttpStatus(response.getStatus());
            // set exception stack
            if (!ObjectUtils.isEmpty(ex)) {
                logger.debug("Request Have Exception，Execute Update HttpStatus.");
                log.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                log.setExceptionStack(StackTraceUtil.getStackTrace(ex));
            }
            if (!this.checkIgnoreHttpStatus(log.getHttpStatus())) {
                log.setEndTime(System.currentTimeMillis());
                log.setTimeConsuming(log.getEndTime() - log.getStartTime());
                Map responseHeaders = HttpRequestUtil.getResponseHeaders(response);
                log.setResponseHeader(!ObjectUtils.isEmpty(responseHeaders) ? JsonUtils.toJsonString(responseHeaders) : null);
                log.setResponseBody(HttpRequestUtil.getResponseBody(response));
                // set global logs
                log.setGlobalLogs(GlobalLoggingThreadLocal.getGlobalLogs());
                // publish logging event
                factoryBean.getApplicationContext().publishEvent(new LoggingNoticeEvent(this, log));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // Remove ApiBoot Log
            LogThreadLocal.remove();
            // Remove this request global logs
            GlobalLoggingThreadLocal.remove();
        }
    }

    /**
     * get or create traceId
     *
     * @param request http request
     * @return traceId
     */
    private String getOrCreateTraceId(HttpServletRequest request) {
        // get traceId from request header
        String traceId = extractTraceId(request);
        // if request header don't have traceId
        // create new traceId
        if (ObjectUtils.isEmpty(traceId)) {
            logger.debug("Request Header Don't Have TraceId，Create New TraceId Now.");
            traceId = factoryBean.getTraceGenerator().createTraceId();
        }
        logger.debug("Request TraceId：{}", traceId);
        return traceId;
    }

    /**
     * extract traceId from {@link HttpServletRequest} headers
     *
     * @param request {@link HttpServletRequest}
     * @return traceId
     */
    private String extractTraceId(HttpServletRequest request) {
        // get traceId from request header
        String traceId = HttpRequestUtil.getHeader(request, LoggingConstant.HEADER_NAME_TRACE_ID);
        return traceId;
    }

    /**
     * extract parent spanId from {@link HttpServletRequest} headers
     *
     * @param request {@link HttpServletRequest}
     * @return spanId
     */
    private String extractParentSpanId(HttpServletRequest request) {
        // get spanId from request header
        String spanId = HttpRequestUtil.getHeader(request, LoggingConstant.HEADER_NAME_PARENT_SPAN_ID);
        logger.debug("Request Parent SpanId：{}", spanId);
        return spanId;
    }

    /**
     * check ignore uri
     *
     * @param uri request uri
     * @return is have in ignore path list
     */
    private boolean checkIgnore(String uri) {
        return UrlUtils.isIgnore(factoryBean.getIgnorePaths(), uri);
    }

    /**
     * Check whether to filter the request of the specified {@link HttpStatus}
     *
     * @param httpStatusCode {@link HttpServletResponse#getStatus()}
     * @return
     */
    private boolean checkIgnoreHttpStatus(int httpStatusCode) {
        HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode);
        List<HttpStatus> ignoreHttpStatus = factoryBean.getIgnoreHttpStatus();
        return ignoreHttpStatus.contains(httpStatus);
    }
}
