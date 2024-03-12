package org.apache.eventmesh.dashboard.console.function.center;

import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.MetadataServiceTypeEnums;
import org.apache.eventmesh.dashboard.console.function.center.meta.EtcdCenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.center.meta.NacosCenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.metadata.MetadataHandler;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetadata;
import org.apache.eventmesh.dashboard.console.function.metadata.service.meta.RuntimeMetaService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;

/**
 * Center Manager注册了monitor之后，才能从center获取数据
 */
public class CenterManager {

    private static final Map<MetadataServiceTypeEnums, Class<?>> SERIVICE_TYPE_ENUMS_CLASS_MAP = new ConcurrentHashMap<>();

    static {
        SERIVICE_TYPE_ENUMS_CLASS_MAP.put(MetadataServiceTypeEnums.CENTER_ETCD, EtcdCenterMonitorService.class);
        SERIVICE_TYPE_ENUMS_CLASS_MAP.put(MetadataServiceTypeEnums.CENTER_NACOS, NacosCenterMonitorService.class);
    }

    /**
     * key: center address
     */
    @Getter
    private final Map<String, CenterMonitorService> centerMap = new ConcurrentHashMap<>();

    private final CenterMetaService centerMetaService = new CenterMetaService();

    @Setter
    private RuntimeMetaService runtimeMetaService;

    public MetadataHandler<CenterMetadata> getCenterMetaService() {
        return centerMetaService;
    }


    public void startMonitor(MetadataOperationConfig serviceConfig) {
        if (centerMap.containsKey(serviceConfig.getRegisterAddress())) {
            return;
        }
        Class<?> clazz = SERIVICE_TYPE_ENUMS_CLASS_MAP.get(serviceConfig.getServiceTypeEnums());
        try {
            CenterMonitorService centerMonitorService = (CenterMonitorService) clazz.newInstance();
            centerMonitorService.init(runtimeMetaService, serviceConfig);
            centerMap.put(serviceConfig.getRegisterAddress(), centerMonitorService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void stopMonitor(MetadataOperationConfig serviceConfig) {
        CenterMonitorService centerMonitorService = centerMap.get(serviceConfig.getRegisterAddress());
        if (centerMonitorService != null) {
            centerMonitorService.close();
            centerMap.remove(serviceConfig.getRegisterAddress());
        }
    }


    public class CenterMetaService implements MetadataHandler<CenterMetadata> {

        @Setter
        private String address;

        @Override
        public List<CenterMetadata> getAllMetadata() {
            return Arrays.asList((CenterMetadata) CenterManager.this.getCenterMap().get(address).getCenterInfo());
        }

        @Override
        public void addMetadata(CenterMetadata meta) {
            CenterManager.this.startMonitor(meta);
            address = meta.getRegisterAddress();
        }

        @Override
        public void deleteMetadata(CenterMetadata meta) {
            CenterManager.this.stopMonitor(meta);
        }
    }


}
