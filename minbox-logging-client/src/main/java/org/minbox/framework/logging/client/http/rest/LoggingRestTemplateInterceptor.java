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

package org.minbox.framework.logging.client.http.rest;

import org.minbox.framework.logging.client.LoggingConstant;
import org.minbox.framework.logging.client.LogThreadLocal;
import org.minbox.framework.logging.core.MinBoxLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.ObjectUtils;

import java.io.IOException;


/**
 * ApiBoot Logging RestTemplate Interceptor
 * Pass-through traceId and spanId
 *
 * @author 恒宇少年
 */
public class LoggingRestTemplateInterceptor implements ClientHttpRequestInterceptor {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingRestTemplateInterceptor.class);

    /**
     * Request Exception
     *
     * @param request   {@link HttpRequest}
     * @param body      Request Body
     * @param execution Execute
     * @return {@link ClientHttpResponse}
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        MinBoxLog log = LogThreadLocal.get();
        if (!ObjectUtils.isEmpty(log)) {
            request.getHeaders().add(LoggingConstant.HEADER_NAME_TRACE_ID, log.getTraceId());
            request.getHeaders().add(LoggingConstant.HEADER_NAME_PARENT_SPAN_ID, log.getSpanId());
            logger.debug("RequestUri：{}, Method：{}，Setting Logging TraceId：{}，SpanId：{} With RestTemplate.",
                    request.getURI().getPath(), request.getMethod().toString(), log.getTraceId(), log.getSpanId());
        }
        return execution.execute(request, body);
    }
}
