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

 
package org.thingsboard.server.dao.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import org.thingsboard.server.common.data.DeviceShare;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.DeviceShareId;
import org.thingsboard.server.dao.model.BaseSqlEntity;
//import org.thingsboard.server.dao.model.BaseVersionedEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import java.util.UUID;
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AbstractDeviceShareEntity<T extends DeviceShare> extends BaseSqlEntity<DeviceShare>  {

   

    @Column(name = ModelConstants.DEVICE_SHARE_DEVICE_ID_PROPERTY, columnDefinition = "uuid")
    private UUID deviceId;

    @Column(name = ModelConstants.DEVICE_SHARE_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.DEVICE_SHARE_CUSTOMER_ID_PROPERTY, columnDefinition = "uuid")
    private UUID customerId;

    

    public abstract DeviceShare toData();
    
    public AbstractDeviceShareEntity() {
        super();
    }

    public AbstractDeviceShareEntity(T deviceShare) {
        if (deviceShare.getId() != null) {
            log.debug("AbstractDeviceShareEntity  deviceShare.getId().getId() = {}", deviceShare.getId().getId());
            this.id = deviceShare.getId().getId();
        } else {
            log.debug("AbstractDeviceShareEntity  deviceShare.getId() is null!");
        }
        this.deviceId = deviceShare.getDeviceId().getId();
        this.tenantId = deviceShare.getTenantId().getId();
        this.customerId = deviceShare.getCustomerId().getId();
    }

    public AbstractDeviceShareEntity(AbstractDeviceShareEntity<T> deviceShareEntity)
    {
        super(deviceShareEntity);
        this.tenantId=deviceShareEntity.getTenantId();
        this.customerId=deviceShareEntity.getCustomerId();
        this.deviceId= deviceShareEntity.getDeviceId();
    }

    public AbstractDeviceShareEntity(UUID id) {
        this.id = id;
    }

    public DeviceShare toDeviceShare() {
        DeviceShare deviceShare = new DeviceShare();
        deviceShare.setId(new DeviceShareId(this.id));
        deviceShare.setDeviceId(new DeviceId(deviceId));
        deviceShare.setTenantId(TenantId.fromUUID(tenantId));
        deviceShare.setCustomerId(new CustomerId(customerId));
       // deviceShare.setCreatedTime(createdTime);
       deviceShare.setCreatedTime(createdTime);
    //    this.createdTime = (deviceShare.getCreatedTime() != 0) ? deviceShare.getCreatedTime() : System.currentTimeMillis(); // Changed line
        return deviceShare;
    }

    public void setId(DeviceShareId deviceShareId) { // Sửa đổi kiểu tham số
        this.id = deviceShareId.getId();
    }
}