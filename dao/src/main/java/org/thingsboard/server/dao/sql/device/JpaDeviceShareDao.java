/**
 * Copyright © 2016-2025 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package org.thingsboard.server.dao.sql.device;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.DeviceShare;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.device.DeviceShareDao;
import org.thingsboard.server.dao.model.sql.DeviceShareEntity;
import org.thingsboard.server.dao.sql.JpaAbstractDao;
import org.thingsboard.server.dao.util.SqlDao;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
 import java.util.UUID;
import java.util.stream.Collectors;
 
 @Component
 @SqlDao
 @Slf4j
 
 public class JpaDeviceShareDao extends JpaAbstractDao<DeviceShareEntity, DeviceShare> implements DeviceShareDao {

    @Autowired
    private DeviceShareRepository deviceShareRepository; // Inject JpaRepository

    @Override
    protected Class<DeviceShareEntity> getEntityClass() {
        return DeviceShareEntity.class;
    }

    @Override
    protected JpaRepository<DeviceShareEntity, UUID> getRepository() {
        return deviceShareRepository;
    }

    @Override
    public List<DeviceShare> findByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId) {
        return deviceShareRepository.findByTenantIdAndCustomerId(tenantId.getId(), customerId.getId())
                .stream()
                .map(DeviceShareEntity::toData)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceShare> findByTenantIdAndDeviceId(TenantId tenantId, DeviceId deviceId) {
        return deviceShareRepository.findByTenantIdAndDeviceId(tenantId.getId(), deviceId.getId())
                .stream()
                .map(DeviceShareEntity::toData)
                .collect(Collectors.toList());
    }

    @Override
    public void removeByDeviceIdAndCustomerId(DeviceId deviceId, CustomerId customerId) {
        deviceShareRepository.deleteByDeviceIdAndCustomerId(deviceId.getId(), customerId.getId());
    }

    @Override
    public List<DeviceShare> findByTenantId(TenantId tenantId) {
        return deviceShareRepository.findByTenantId(tenantId.getId())
                .stream()
                .map(DeviceShareEntity::toData)
                .collect(Collectors.toList());
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.DEVICESHARE;
    }


    // Thêm các phương thức CRUD từ JpaAbstractDao nếu cần
}
 