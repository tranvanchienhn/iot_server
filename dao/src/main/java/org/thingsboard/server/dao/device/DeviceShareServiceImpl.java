/**
 * Copyright © 2016-2025 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thingsboard.server.dao.device; // Chọn package thích hợp

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsboard.server.cache.device.DeviceCacheKey;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.DeviceShare;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.DeviceShareId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.HasId;
import org.thingsboard.server.common.data.id.TenantId;
//import org.thingsboard.server.dao.device.DeviceShareService;
//import org.thingsboard.server.dao.model.sql.DeviceShareEntity;
import org.thingsboard.server.dao.model.sql.DeviceShareEntity;
import lombok.extern.slf4j.Slf4j;
import static org.thingsboard.server.dao.service.Validator.validateId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service("DeviceShareDaoService")
public class DeviceShareServiceImpl implements DeviceShareService {

    public static final String INCORRECT_DEVICE_SHARE_ID = "Incorrect deviceShareId ";
    @Autowired
    private DeviceShareDao deviceShareDao;
    
    @Override
    public void shareDevice(TenantId tenantId, DeviceId deviceId, CustomerId customerId) {
        log.debug("shareDevice: tenantId = {}, deviceId = {}, customerId = {}", tenantId, deviceId, customerId);

        DeviceShare deviceShare = new DeviceShare();
        deviceShare.setTenantId(tenantId);
        deviceShare.setDeviceId(deviceId);
        deviceShare.setCustomerId(customerId);
        deviceShare.setCreatedTime(System.currentTimeMillis());
        //DeviceShareEntity deviceShareEntity = new DeviceShareEntity(deviceShare);
       
        DeviceShareEntity deviceShareEntity = new DeviceShareEntity(deviceShare);
        log.debug("shareDevice: deviceShareEntity = {}", deviceShareEntity);
        try{
            deviceShareDao.save(tenantId,deviceShare);
        }
        catch(Exception e)
        {
            log.error("Error in shareDevice", e); // Log the full exception
            throw e; // Re-throw the exception to prevent 200 OK
        }
    }

    @Override
    public void unshareDevice(TenantId tenantId, UUID Id) {
        deviceShareDao.removeById(tenantId, Id);
    }

    @Override
    public List<DeviceShare> getSharedDevicesByCustomerId(TenantId tenantId, CustomerId customerId) {
        return deviceShareDao.findByTenantIdAndCustomerId(tenantId, customerId);

    }

    @Override
    public List<DeviceShare> getSharedDevicesByTenantId(TenantId tenantId) {
        return deviceShareDao.findByTenantId(tenantId);
    }

    public DeviceShare findDeviceShareById(TenantId tenantId, DeviceShareId deviceShareId) {
        log.trace("Executing findDeviceShareById [{}]", deviceShareId);
        validateId(deviceShareId, id -> INCORRECT_DEVICE_SHARE_ID + id);
        if (TenantId.SYS_TENANT_ID.equals(tenantId)) {
            return deviceShareDao.findById(tenantId, deviceShareId.getId());
        } else {
            return null;//deviceShareDao.findByTenantId(tenantId);
        }
    }

    @Override
    public Optional<HasId<?>> findEntity(TenantId tenantId, EntityId entityId) {
        return Optional.ofNullable(findDeviceShareById(tenantId, new DeviceShareId(entityId.getId())));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.DEVICESHARE;
    }

   
}