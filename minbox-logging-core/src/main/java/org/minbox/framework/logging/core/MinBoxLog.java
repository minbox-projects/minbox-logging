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

package org.minbox.framework.logging.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ApiBoot Log Object
 *
 * @author：恒宇少年 - 于起宇
 */
@Data
public class MinBoxLog implements Serializable {
    /**
     * The stored service id
     */
    private String serviceDetailId;
    /**
     * The service application name
     */
    private String serviceId;
    /**
     * the formatted service name
     */
    private String serviceName;
    /**
     * trace id
     */
    private String traceId;
    /**
     * span id
     */
    private String spanId;
    /**
     * parent span id
     */
    private String parentSpanId;
    /**
     * request uri
     */
    private String requestUri;
    /**
     * request method
     */
    private String requestMethod;
    /**
     * http status code
     */
    private int httpStatus;
    /**
     * request ip
     */
    private String requestIp;
    /**
     * service ip address
     */
    private String serviceIp;
    /**
     * service port
     */
    private String servicePort;
    /**
     * start time
     */
    private Long startTime;
    /**
     * end time
     */
    private Long endTime;
    /**
     * this request time consuming
     */
    private long timeConsuming;
    /**
     * request headers json value
     */
    private String requestHeader;
    /**
     * request param
     */
    private String requestParam;
    /**
     * request body
     */
    private String requestBody;
    /**
     * response headers json value
     */
    private String responseHeader;
    /**
     * response body
     */
    private String responseBody;
    /**
     * exception stack
     */
    private String exceptionStack;
    /**
     * Global method log list
     */
    private List<GlobalLog> globalLogs;
    /**
     * The request logs create time
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime = LocalDateTime.now();
}
