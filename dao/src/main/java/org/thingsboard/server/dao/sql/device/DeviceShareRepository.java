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


package org.thingsboard.server.dao.sql.device; // Điều chỉnh package nếu cần

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; // Thêm @Repository
import org.thingsboard.server.dao.model.sql.DeviceShareEntity; // Import Entity

import java.util.List;
import java.util.UUID;

@Repository // Đánh dấu là Spring Data JPA Repository
public interface DeviceShareRepository extends JpaRepository<DeviceShareEntity, UUID> {

    @Query("SELECT ds FROM DeviceShareEntity ds WHERE ds.tenantId = :tenantId AND ds.customerId = :customerId")
    List<DeviceShareEntity> findByTenantIdAndCustomerId(
            @Param("tenantId") UUID tenantId,
            @Param("customerId") UUID customerId
    );

    @Query("SELECT ds FROM DeviceShareEntity ds WHERE ds.tenantId = :tenantId AND ds.deviceId = :deviceId")
    List<DeviceShareEntity> findByTenantIdAndDeviceId(
            @Param("tenantId") UUID tenantId,
            @Param("deviceId") UUID deviceId
    );

    @Query("DELETE FROM DeviceShareEntity ds WHERE ds.deviceId = :deviceId AND ds.customerId = :customerId")
    void deleteByDeviceIdAndCustomerId(
            @Param("deviceId") UUID deviceId,
            @Param("customerId") UUID customerId
    );

    @Query("SELECT ds FROM DeviceShareEntity ds WHERE ds.tenantId = :tenantId")
    List<DeviceShareEntity> findByTenantId(@Param("tenantId") UUID tenantId);

    @Query("SELECT ds FROM DeviceShareEntity ds WHERE ds.deviceId = :deviceId")
    List<DeviceShareEntity> findByDeviceId(@Param("deviceId") UUID deviceId); //Thêm
}
