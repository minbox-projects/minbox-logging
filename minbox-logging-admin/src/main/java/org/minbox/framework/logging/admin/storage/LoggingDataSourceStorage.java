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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link LoggingStorage} database mode
 *
 * @author 恒宇少年
 */
public class LoggingDataSourceStorage implements LoggingStorage {
    /**
     * The bean name of {@link LoggingDataSourceStorage}
     */
    public static final String BEAN_NAME = "loggingDataSourceStorage";

    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingDataSourceStorage.class);
    /**
     * Insert ServiceDetail SQL
     */
    private static final String SQL_INSERT_SERVICE_DETAILS = "insert into logging_service_details (lsd_id, lsd_service_id, lsd_service_ip, lsd_service_port) values (?,?,?,?)";
    /**
     * Select All Service SQL
     */
    private static final String SQL_SELECT_SERVICE_DETAILS = "select lsd_id, lsd_service_id, lsd_service_ip, lsd_service_port,lsd_last_report_time,lsd_create_time from logging_service_details";
    /**
     * Select ServiceDetails Id SQL
     */
    private static final String SQL_SELECT_SERVICE_DETAILS_ID = "select lsd_id from logging_service_details where lsd_service_id = ? and lsd_service_ip = ? and lsd_service_port = ? limit 1";
    /**
     * Update ServiceDetail Last Report Time SQL
     */
    private static final String SQL_UPDATE_LAST_REPORT_SERVICE_DETAILS = "update logging_service_details set lsd_last_report_time = ? where lsd_id = ?";
    /**
     * Select Trace Logs
     */
    private static final String SQL_SELECT_LOG = "select logging_request_logs.*, lsd_service_id,lsd_service_ip,lsd_service_port " +
            "from logging_request_logs left join logging_service_details on lsd_id = lrl_service_detail_id " +
            "where lrl_parent_span_id is null " +
            "order by lrl_create_time desc";
    /**
     * Insert Request Log SQL
     */
    private static final String SQL_INSERT_LOG = "insert into logging_request_logs (lrl_id, lrl_service_detail_id, lrl_trace_id, lrl_parent_span_id, lrl_span_id,\n" +
            "                                  lrl_start_time, lrl_end_time, lrl_http_status, lrl_request_body, lrl_request_headers,\n" +
            "                                  lrl_request_ip, lrl_request_method, lrl_request_uri, lrl_response_body,\n" +
            "                                  lrl_response_headers, lrl_time_consuming,lrl_request_params,lrl_exception_stack) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    /**
     * Insert Global Log SQL
     */
    private static final String SQL_INSERT_GLOBAL_LOG = "insert into logging_global_logs (lgl_id, lgl_request_log_id, lgl_level, lgl_content, lgl_caller_class,\n" +
            "                                 lgl_caller_method, lgl_caller_code_line_number, lgl_exception_stack, lgl_create_time) values (?,?,?,?,?,?,?,?,?);";
    /**
     * The Data Source {@link DataSource}
     * save the logs to database with dataSource
     */
    private DataSource dataSource;

    public LoggingDataSourceStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Insert Global log
     *
     * @param requestLogId request log id
     * @param log          {@link GlobalLog}
     * @return the global log id
     * @throws SQLException
     */
    @Override
    public String insertGlobalLog(String requestLogId, GlobalLog log) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_GLOBAL_LOG);
        String globalLogId = UUID.randomUUID().toString();
        ps.setString(1, globalLogId);
        ps.setString(2, requestLogId);
        ps.setString(3, log.getLevel().toString());
        ps.setString(4, log.getContent());
        ps.setString(5, log.getCallerClass());
        ps.setString(6, log.getCallerMethod());
        ps.setInt(7, log.getCallerCodeLineNumber());
        ps.setString(8, log.getExceptionStack());
        ps.setLong(9, log.getCreateTime());
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
        return globalLogId;
    }

    /**
     * Insert Request Log
     *
     * @param serviceDetailId ServiceDetail ID
     * @param log             MinBoxLog
     * @throws SQLException SqlException
     */
    @Override
    public String insertLog(String serviceDetailId, MinBoxLog log) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_LOG);
        String logId = UUID.randomUUID().toString();
        ps.setString(1, logId);
        ps.setString(2, serviceDetailId);
        ps.setString(3, log.getTraceId());
        ps.setString(4, log.getParentSpanId());
        ps.setString(5, log.getSpanId());
        ps.setLong(6, log.getStartTime());
        ps.setLong(7, log.getEndTime());
        ps.setInt(8, log.getHttpStatus());
        ps.setString(9, log.getRequestBody());
        ps.setString(10, log.getRequestHeader());
        ps.setString(11, log.getRequestIp());
        ps.setString(12, log.getRequestMethod());
        ps.setString(13, log.getRequestUri());
        ps.setString(14, log.getResponseBody());
        ps.setString(15, log.getResponseHeader());
        ps.setLong(16, log.getTimeConsuming());
        ps.setString(17, log.getRequestParam());
        ps.setString(18, log.getExceptionStack());
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
        return logId;
    }

    /**
     * find top count logs
     *
     * @param topCount top count
     * @return
     * @throws SQLException
     */
    @Override
    public List<LoggingResponse> findTopList(int topCount) throws SQLException {
        List<LoggingResponse> responses = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_SELECT_LOG);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LoggingResponse response = new LoggingResponse();
            response.setCreateTime(rs.getTimestamp("lrl_create_time").toLocalDateTime());
            response.setTraceId(rs.getString("lrl_trace_id"));
            response.setHttpStatus(rs.getInt("lrl_http_status"));
            response.setTimeConsuming(rs.getInt("lrl_time_consuming"));
            response.setExceptionStack(rs.getString("lrl_exception_stack"));
            response.setSpanId(rs.getString("lrl_span_id"));
            response.setParentSpanId(rs.getString("lrl_parent_span_id"));
            response.setServiceId(rs.getString("lsd_service_id"));
            response.setServiceIp(rs.getString("lsd_service_ip"));
            response.setServicePort(rs.getString("lsd_service_port"));
            response.setStartTime(rs.getLong("lrl_start_time"));
            response.setEndTime(rs.getLong("lrl_end_time"));

            response.setRequestUri(rs.getString("lrl_request_uri"));
            response.setRequestMethod(rs.getString("lrl_request_method"));
            response.setRequestBody(rs.getString("lrl_request_body"));
            response.setRequestHeader(rs.getString("lrl_request_headers"));
            response.setRequestIp(rs.getString("lrl_request_ip"));
            response.setRequestParam(rs.getString("lrl_request_params"));

            response.setResponseBody(rs.getString("lrl_response_body"));
            response.setResponseHeader(rs.getString("lrl_response_headers"));
            responses.add(response);
        }
        closeConnection(connection);
        return responses;
    }

    /**
     * Insert ServiceDetails
     *
     * @param serviceId   ServiceId
     * @param serviceIp   Service Ip Address
     * @param servicePort ServicePort
     * @return ServiceDetails Pk Value
     * @throws SQLException Sql Exception
     */
    @Override
    public String insertServiceDetail(String serviceId, String serviceIp, int servicePort) throws SQLException {
        String serviceDetailId = UUID.randomUUID().toString();
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_SERVICE_DETAILS);
        ps.setString(1, serviceDetailId);
        ps.setString(2, serviceId);
        ps.setString(3, serviceIp);
        ps.setInt(4, servicePort);
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
        return serviceDetailId;
    }

    /**
     * Select All Service List
     * {@link ServiceResponse}
     *
     * @return Service Response
     * @throws SQLException SqlException
     */
    @Override
    public List<ServiceResponse> findAllService() throws SQLException {
        List<ServiceResponse> responses = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_SELECT_SERVICE_DETAILS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ServiceResponse response = new ServiceResponse();
            response.setId(rs.getString("lsd_service_id"));
            response.setIp(rs.getString("lsd_service_ip"));
            response.setPort(rs.getInt("lsd_service_port"));
            Timestamp lastReportTime = rs.getTimestamp("lsd_last_report_time");
            response.setLastReportTime(!ObjectUtils.isEmpty(lastReportTime) ? lastReportTime.toLocalDateTime() : null);
            response.setCreateTime(rs.getTimestamp("lsd_create_time").toLocalDateTime());
            responses.add(response);
        }
        ps.close();
        closeConnection(connection);
        return responses;
    }

    /**
     * Select ServiceDetails Id
     *
     * @param serviceId   Service Id
     * @param serviceIp   Service Ip Address
     * @param servicePort Service Port
     * @return ServiceDetail Id
     * @throws SQLException Sql Exception
     */
    @Override
    public String selectServiceDetailId(String serviceId, String serviceIp, int servicePort) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_SELECT_SERVICE_DETAILS_ID);
        ps.setString(1, serviceId);
        ps.setString(2, serviceIp);
        ps.setInt(3, servicePort);
        ResultSet rs = ps.executeQuery();
        String serviceDetailId = null;
        while (rs.next()) {
            serviceDetailId = rs.getString(1);
            break;
        }
        rs.close();
        ps.close();
        closeConnection(connection);
        return serviceDetailId;
    }

    /**
     * Update ServiceDetails Last Report Time
     *
     * @param serviceDetailId ServiceDetail Pk Value
     * @throws SQLException Sql Exception
     */
    @Override
    public void updateLastReportTime(String serviceDetailId) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_LAST_REPORT_SERVICE_DETAILS);
        ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        ps.setString(2, serviceDetailId);
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
    }

    /**
     * Get DataSource Connection
     *
     * @return Connection
     * @throws SQLException Sql Exception
     */
    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Close DataSource Connection
     *
     * @param connection DataSource Connection
     * @throws SQLException Sql Exception
     */
    void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
