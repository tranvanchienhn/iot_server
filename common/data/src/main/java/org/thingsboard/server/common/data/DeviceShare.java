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

package org.thingsboard.server.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.TenantId;
import io.swagger.v3.oas.annotations.media.Schema;
import org.thingsboard.server.common.data.id.DeviceShareId;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema

public class DeviceShare extends BaseData<DeviceShareId> implements HasTenantId,HasCustomerId {

    private DeviceShareId deviceShareId;
    private DeviceId deviceId;
    private TenantId tenantId;
    private CustomerId customerId;
    private long createdTime;

    public DeviceShare() {
        super();
    }

    public DeviceShare(DeviceShareId id) {
        super(id);
    }

    public DeviceShare(DeviceShare deviceShare) {
        super(deviceShare);
        if(deviceShare.getDeviceShareId().getId()!=null)
        {
            this.deviceShareId = deviceShare.getDeviceShareId();
        }
        
        this.tenantId = deviceShare.getTenantId();
        this.customerId = deviceShare.getCustomerId();
        this.deviceId = deviceShare.getDeviceId();
        this.createdTime=deviceShare.getCreatedTime();
    }

    public DeviceShare updateDeviceShare(DeviceShare deviceShare) {
        this.deviceShareId = deviceShare.getDeviceShareId();
        this.tenantId = deviceShare.getTenantId();
        this.customerId = deviceShare.getCustomerId();
        this.deviceId = deviceShare.getDeviceId();
        this.createdTime = deviceShare.getCreatedTime();
        return this;
    }

    @Override
    public DeviceShareId getId() {
        return super.getId();
    }

    @Schema(description = "JSON object with Tenant Id. Use 'assignDeviceToTenant' to change the Tenant Id.", accessMode = Schema.AccessMode.READ_ONLY)
    public TenantId getTenantId() {
        return tenantId;
    }

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    @Schema(description = "JSON object with Customer Id. Use 'assignDeviceToCustomer' to change the Customer Id.", accessMode = Schema.AccessMode.READ_ONLY)
    public CustomerId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    @Schema(description = "JSON object with Customer Id. Use 'assignDeviceToCustomer' to change the Customer Id.", accessMode = Schema.AccessMode.READ_ONLY)
    public DeviceId getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(DeviceId devicerId) {
        this.deviceId = devicerId;
    }

    // public long getCreatedTime(){
    //     return createdTime;
    // }
    // public void setCreatedTime(long createdTime){
    //     this.createdTime = createdTime;
    // }
}