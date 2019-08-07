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

package org.minbox.framework.logging.client.cache.support;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.cache.LoggingCache;
import org.minbox.framework.logging.core.ApiBootLog;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ApiBoot Logging Memory Away Cache
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-21 13:50
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingMemoryCache implements LoggingCache {
    /**
     * Cache ApiBootLog Map
     * For Batch Report
     */
    private static final ConcurrentMap<String, ApiBootLog> CACHE_LOGS = new ConcurrentHashMap();

    /**
     * Cache Single ApiBootLog
     *
     * @param log ApiBootLog
     * @throws org.minbox.framework.logging.client.MinBoxLoggingException Logging Exception
     */
    @Override
    public void cache(ApiBootLog log) throws MinBoxLoggingException {
        if (!ObjectUtils.isEmpty(log)) {
            CACHE_LOGS.put(UUID.randomUUID().toString(), log);
        }
    }

    /**
     * Get Any One ApiBootLog
     *
     * @return ApiBootLog
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public ApiBootLog getAnyOne() throws MinBoxLoggingException {
        List<ApiBootLog> logs = get(0);
        return logs.size() > 0 ? logs.get(0) : null;
    }

    /**
     * Gets the specified number of ApiBootLog
     *
     * @param count get count number
     * @return ApiBootLog Collection
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public List<ApiBootLog> getLogs(int count) throws MinBoxLoggingException {
        if (CACHE_LOGS.size() >= count) {
            return get(count);
        }
        return null;
    }

    /**
     * Gets All Of ApiBootLog
     *
     * @return ApiBootLog Collection
     * @throws MinBoxLoggingException Logging Exception
     */
    @Override
    public List<ApiBootLog> getAll() throws MinBoxLoggingException {
        return get(null);
    }

    /**
     * Get ApiBootLogs
     *
     * @param count get count number
     * @return ApiBootLog Collection
     * @throws MinBoxLoggingException Logging Exception
     */
    private List<ApiBootLog> get(Integer count) throws MinBoxLoggingException {
        List<ApiBootLog> logs = new ArrayList();
        Iterator<String> iterator = CACHE_LOGS.keySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            logs.add(CACHE_LOGS.get(key));
            CACHE_LOGS.remove(key);
            if (count != null && index >= count - 1) {
                break;
            }
            index++;
        }
        return logs;
    }
}
