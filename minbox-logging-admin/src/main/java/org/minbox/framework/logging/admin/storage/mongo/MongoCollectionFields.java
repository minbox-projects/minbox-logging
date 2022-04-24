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
 * The mongo collection fields
 *
 * @author 恒宇少年
 */
public interface MongoCollectionFields {
    String SERVICE_DETAIL_ID = "_id";
    String SERVICE_ID = "serviceId";
    String SERVICE_IP = "serviceIp";
    String SERVICE_PORT = "servicePort";
    String SERVICE_LAST_REPORT_TIME = "lastReportTime";

    String LOG_CREATE_TIME = "createTime";
}
