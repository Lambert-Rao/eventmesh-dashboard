package org.apache.eventmesh.dashboard.console.function.center;

import org.apache.eventmesh.dashboard.console.function.MetaDataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.MetaDataServiceTypeEnums;
import org.apache.eventmesh.dashboard.console.function.center.meta.EtcdCenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.center.meta.NacosCenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.metadata.MetaDataHandler;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetaData;
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

    private static final Map<MetaDataServiceTypeEnums, Class<?>> SERIVICE_TYPE_ENUMS_CLASS_MAP = new ConcurrentHashMap<>();

    static {
        SERIVICE_TYPE_ENUMS_CLASS_MAP.put(MetaDataServiceTypeEnums.CENTER_ETCD, EtcdCenterMonitorService.class);
        SERIVICE_TYPE_ENUMS_CLASS_MAP.put(MetaDataServiceTypeEnums.CENTER_NACOS, NacosCenterMonitorService.class);
    }

    /**
     * key: center address
     */
    @Getter
    private final Map<String, CenterMonitorService> centerMap = new ConcurrentHashMap<>();

    private final CenterMetaService centerMetaService = new CenterMetaService();

    @Setter
    private RuntimeMetaService runtimeMetaService;

    public MetaDataHandler<CenterMetaData> getCenterMetaService() {
        return centerMetaService;
    }


    public void startMonitor(MetaDataOperationConfig serviceConfig) {
        Class<?> clazz = SERIVICE_TYPE_ENUMS_CLASS_MAP.get(serviceConfig.getServiceTypeEnums());
        try {
            CenterMonitorService centerMonitorService = (CenterMonitorService) clazz.newInstance();
            centerMonitorService.init(runtimeMetaService, serviceConfig);
            centerMap.put(serviceConfig.getAddress(), centerMonitorService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void stopMonitor(MetaDataOperationConfig serviceConfig) {
        CenterMonitorService centerMonitorService = centerMap.get(serviceConfig.getAddress());
        if (centerMonitorService != null) {
            centerMonitorService.close();
            centerMap.remove(serviceConfig.getAddress());
        }
    }


    public class CenterMetaService implements MetaDataHandler<CenterMetaData> {

        @Setter
        private String address;

        @Override
        public List<CenterMetaData> getAllMetaData() {
            return Arrays.asList((CenterMetaData) CenterManager.this.getCenterMap().get(address).getCenterInfo());
        }

        @Override
        public void addMetaData(CenterMetaData meta) {
            CenterManager.this.startMonitor(meta);
            address = meta.getAddress();
        }

        @Override
        public void deleteMetaData(CenterMetaData meta) {
            CenterManager.this.stopMonitor(meta);
        }
    }


}
