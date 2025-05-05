
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

package org.thingsboard.server.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.DeviceShare;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.device.DeviceShareService;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deviceShare")
public class DeviceShareController {

    @Autowired
    private DeviceShareService deviceShareService;

    @PostMapping("/share")
    public void shareDevice(@RequestBody DeviceShareRequest deviceShareRequest) {
        deviceShareService.shareDevice(deviceShareRequest.getTenantId(), deviceShareRequest.getDeviceId(), deviceShareRequest.getCustomerId());
    }

    @DeleteMapping("/unshare")
    public void unshareDevice(@RequestBody DeviceShareRequest deviceShareRequest) {
        deviceShareService.unshareDevice(deviceShareRequest.getTenantId(),deviceShareRequest.getId());
    }

    @GetMapping("/customer/{customerId}")
    public List<DeviceShare> getSharedDevicesByCustomerId(@PathVariable UUID customerId) {
        return deviceShareService.getSharedDevicesByCustomerId(null, new CustomerId(customerId));
    }

    @GetMapping("/tenant/{tenantId}")
    public List<DeviceShare> getSharedDevicesByTenantId(@PathVariable UUID tenantId) {
        return deviceShareService.getSharedDevicesByTenantId(TenantId.fromUUID(tenantId));
    }
}

@Data
class DeviceShareRequest {
    private UUID id;
    private TenantId tenantId;
    private DeviceId deviceId;
    private CustomerId customerId;
}
