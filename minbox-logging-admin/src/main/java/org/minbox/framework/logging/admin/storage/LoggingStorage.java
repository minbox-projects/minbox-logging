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

package org.minbox.framework.logging.admin.storage;


import org.minbox.framework.logging.core.GlobalLog;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.logging.core.response.LoggingResponse;
import org.minbox.framework.logging.core.response.ServiceResponse;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ApiBoot Logging Storage
 *
 * @author 恒宇少年
 */
public interface LoggingStorage {
    /**
     * Insert Global Log
     *
     * @param requestLogId request log id
     * @param log          {@link GlobalLog}
     * @return the global log id
     * @throws SQLException Sql Exception
     */
    String insertGlobalLog(String requestLogId, GlobalLog log) throws SQLException;

    /**
     * Clean up expired global logs
     *
     * @param effectiveDeadlineTime The effective deadline for cleaning data, the data before this time will be cleaned up
     * @return deleted count
     * @throws SQLException SQL Exception
     */
    long cleanupExpiredGlobalLogs(LocalDateTime effectiveDeadlineTime) throws SQLException;

    /**
     * Insert ApiBootLogs To DataBase
     *
     * @param serviceDetailId ServiceDetail ID
     * @param log             MinBoxLog
     * @return request log id
     * @throws SQLException Sql Exception
     */
    String insertLog(String serviceDetailId, MinBoxLog log) throws SQLException;

    /**
     * Clean up expired logs
     *
     * @param effectiveDeadlineTime The effective deadline for cleaning data, the data before this time will be cleaned up
     * @return deleted count
     * @throws SQLException SQL Exception
     */
    long cleanupExpiredRequestLogs(LocalDateTime effectiveDeadlineTime) throws SQLException;

    /**
     * Insert ServiceDetail To DataBase
     *
     * @param serviceId   ServiceId
     * @param serviceIp   Service Ip Address
     * @param servicePort ServicePort
     * @return ServiceDetail Pk Value
     * @throws SQLException Sql Exception
     */
    String insertServiceDetail(String serviceId, String serviceIp, int servicePort) throws SQLException;

    /**
     * Select ServiceDetails Id
     *
     * @param serviceId   Service Id
     * @param serviceIp   Service Ip Address
     * @param servicePort Service Port
     * @return ServiceDetail Id
     * @throws SQLException Sql Exception
     */
    String selectServiceDetailId(String serviceId, String serviceIp, int servicePort) throws SQLException;

    /**
     * select all service
     * {@link ServiceResponse}
     *
     * @return ServiceResponse
     * @throws SQLException Sql Exception
     */
    List<ServiceResponse> findAllService() throws SQLException;

    /**
     * select top logging list
     * {@link LoggingResponse}
     *
     * @return LoggingResponse
     * @throws SQLException Sql Exception
     */
    List<LoggingResponse> findTopList(int topCount) throws SQLException;

    /**
     * Update ServiceDetail Last Report Time
     *
     * @param serviceDetailId ServiceDetail Pk Value
     * @throws SQLException Sql Exception
     */
    void updateLastReportTime(String serviceDetailId) throws SQLException;
}
