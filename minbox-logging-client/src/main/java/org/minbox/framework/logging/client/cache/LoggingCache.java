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

package org.minbox.framework.logging.client.cache;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.core.MinBoxLog;

import java.util.List;

/**
 * ApiBoot Logging Cache
 *
 * @author 恒宇少年
 */
public interface LoggingCache {
    /**
     * Cache Single MinBoxLog
     *
     * @param log MinBoxLog
     * @throws MinBoxLoggingException Logging Exception
     */
    void cache(MinBoxLog log) throws MinBoxLoggingException;

    /**
     * Get Any One MinBoxLog
     *
     * @return MinBoxLog
     * @throws MinBoxLoggingException Logging Exception
     */
    MinBoxLog getAnyOne() throws MinBoxLoggingException;

    /**
     * Gets the specified number of MinBoxLog
     *
     * @param count get count number
     * @return MinBoxLog Collection
     * @throws MinBoxLoggingException Logging Exception
     */
    List<MinBoxLog> getLogs(int count) throws MinBoxLoggingException;

    /**
     * Gets All Of MinBoxLog
     *
     * @return MinBoxLog Collection
     * @throws MinBoxLoggingException Logging Exception
     */
    List<MinBoxLog> getAll() throws MinBoxLoggingException;
}
