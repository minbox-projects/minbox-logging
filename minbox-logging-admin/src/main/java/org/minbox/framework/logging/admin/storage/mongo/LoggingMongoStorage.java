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

import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.minbox.framework.logging.core.GlobalLog;
import org.minbox.framework.logging.core.MinBoxLog;
import org.minbox.framework.logging.core.response.LoggingResponse;
import org.minbox.framework.logging.core.response.ServiceResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link LoggingStorage} mongo mode
 *
 * @author 恒宇少年
 */
public class LoggingMongoStorage implements LoggingStorage {
    private MongoTemplate mongoTemplate;

    public LoggingMongoStorage(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertGlobalLog(String requestLogId, GlobalLog log) throws SQLException {
        GlobalLogMongoEntity globalLogEntity = new GlobalLogMongoEntity();
        BeanUtils.copyProperties(log, globalLogEntity);
        globalLogEntity.setRequestLogId(requestLogId);
        this.mongoTemplate.insert(globalLogEntity, MongoCollectionNames.GLOBAL_LOG);
        return globalLogEntity.get_id();
    }

    @Override
    public String insertLog(String serviceDetailId, MinBoxLog log) throws SQLException {
        MinBoxLogMongoEntity minBoxLogEntity = new MinBoxLogMongoEntity();
        BeanUtils.copyProperties(log, minBoxLogEntity);
        minBoxLogEntity.setServiceDetailId(serviceDetailId);
        // ignore save global logs
        minBoxLogEntity.setGlobalLogs(null);
        this.mongoTemplate.insert(minBoxLogEntity, MongoCollectionNames.MINBOX_LOG);
        return minBoxLogEntity.get_id();
    }

    @Override
    public String insertServiceDetail(String serviceId, String serviceIp, int servicePort) throws SQLException {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceId(serviceId);
        serviceEntity.setServiceIp(serviceIp);
        serviceEntity.setServicePort(servicePort);
        this.mongoTemplate.insert(serviceEntity, MongoCollectionNames.SERVICE);
        return serviceEntity.get_id();
    }

    @Override
    public String selectServiceDetailId(String serviceId, String serviceIp, int servicePort) throws SQLException {
        Criteria criteria = Criteria
                .where(MongoCollectionFields.SERVICE_ID).is(serviceId)
                .and(MongoCollectionFields.SERVICE_IP).is(serviceIp)
                .and(MongoCollectionFields.SERVICE_PORT).is(servicePort);
        ServiceEntity serviceEntity = this.mongoTemplate.findOne(Query.query(criteria), ServiceEntity.class, MongoCollectionNames.SERVICE);
        return !ObjectUtils.isEmpty(serviceEntity) ? serviceEntity.get_id() : null;
    }

    @Override
    public List<ServiceResponse> findAllService() throws SQLException {
        List<ServiceEntity> serviceEntityList = this.mongoTemplate.findAll(ServiceEntity.class, MongoCollectionNames.SERVICE);
        return serviceEntityList.stream().map(entity ->
                        new ServiceResponse()
                                .setId(entity.get_id())
                                .setIp(entity.getServiceIp())
                                .setPort(entity.getServicePort())
                                .setLastReportTime(entity.getLastReportTime())
                                .setCreateTime(entity.getCreateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoggingResponse> findTopList(int topCount) throws SQLException {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, MongoCollectionFields.LOG_START_TIME))
                .limit(topCount);
        return this.mongoTemplate.find(query, MinBoxLogMongoEntity.class, MongoCollectionNames.MINBOX_LOG).stream()
                .map(entity -> {
                    LoggingResponse response = new LoggingResponse();
                    BeanUtils.copyProperties(entity, response);
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    public void updateLastReportTime(String serviceDetailId) throws SQLException {
        Query query = Query.query(Criteria.where(MongoCollectionFields.SERVICE_DETAIL_ID).is(serviceDetailId));
        Update update = new Update();
        update.set(MongoCollectionFields.SERVICE_LAST_REPORT_TIME, LocalDateTime.now());
        this.mongoTemplate.upsert(query, update, ServiceEntity.class, MongoCollectionNames.SERVICE);
    }
}
