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

package org.minbox.framework.logging.client;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.minbox.framework.logging.core.MinBoxLog;

/**
 * Using threadLocal to store log objects in multithreaded situations
 *
 * @author 恒宇少年
 */
public class LogThreadLocal {
    /**
     * The Request Logs
     * Solve the problem of the {@link MinBoxLog} object of the child parent thread
     * https://gitee.com/minbox-projects/minbox-logging/issues/I11QKJ
     *
     * @see TransmittableThreadLocal
     * @see MinBoxLog
     */
    private static final TransmittableThreadLocal<MinBoxLog> LOGS = new TransmittableThreadLocal<>();

    /**
     * Get This Thread ApiBoot Log Object Instance
     *
     * @return This Thread ApiBoot Log
     */
    public static MinBoxLog get() {
        return LOGS.get();
    }

    /**
     * Set This Thread ApiBoot Log Object Instance
     *
     * @param minBoxLog This Thread ApiBoot Log
     */
    public static void set(MinBoxLog minBoxLog) {
        LOGS.set(minBoxLog);
    }

    /**
     * Remove This Thread ApiBoot Log Object Instance
     */
    public static void remove() {
        LOGS.remove();
    }
}
