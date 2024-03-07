package org.apache.eventmesh.dashboard.console.function.metadata;

import lombok.Data;

/**
 * 不同的MetaData处理方式不一样，center需要在dbToService注册了center信息之后，才能从ServiceToDb获取数据，而topic则可以在注册的时候就获取serviceToDB数据
 */
@Data
public class MetaDataServiceWrapper {

    private SingleMetaDataServiceWrapper dbToService;

    private SingleMetaDataServiceWrapper serviceToDb;

    private Boolean cache;

    private Class<?> clazz;


    @Data
    public static class SingleMetaDataServiceWrapper {

        private SyncDataService<Object> syncService;
        private MetaDataHandler<Object> metaService;

        public SingleMetaDataServiceWrapper(SyncDataService<Object> syncsService, MetaDataHandler<Object> metaService) {
            this.syncService = syncsService;
            this.metaService = metaService;
        }
    }
}
