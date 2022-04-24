/*
 *  Copyright 2022. [恒宇少年 - 于起宇]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.minbox.framework.logging.admin.storage.mongo;

/**
 * The mongo collection names with minbox logging
 *
 * @author 恒宇少年
 */
public interface MongoCollectionNames {
    /**
     * The {@link GlobalLogMongoEntity} storage collection name
     */
    String GLOBAL_LOG = "minbox-global-logs";
    /**
     * The {@link RequestLogMongoEntity} storage collection name
     */
    String REQUEST_LOG = "minbox-request-logs";
    /**
     * The {@link ServiceEntity} storage collection name
     */
    String SERVICE = "minbox-services";

}
