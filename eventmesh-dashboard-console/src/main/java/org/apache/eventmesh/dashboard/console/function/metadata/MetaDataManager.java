package org.apache.eventmesh.dashboard.console.function.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetaDataManager {


    private final ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

    /**
     *
     */
    private final Map<Class<?>, MetaDataServiceWrapper> metaDataServiceWrapperMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, List<Object>> cacheData = new ConcurrentHashMap<>();

    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(this::run, 1000, 1000 * 10, TimeUnit.MINUTES);
    }


    //
    //注册中心 同步 dashboard
    // dashboard 同步  node
    // 单项同步，还是双向同步
    //1. topic 2. acl 3. user
    //service --> dashboard 有不做识别的
    //1. group 2. client 3. connection 4. topic
    //5. acl 6. user
    //dashboard --> service 1. topic 2. acl 3. user 4.
    //
    //@param clazz
    //@param metaDataServiceWrapper
    //
    public void addMetaDataService(Class<?> clazz, MetaDataServiceWrapper metaDataServiceWrapper) {
        metaDataServiceWrapperMap.put(clazz, metaDataServiceWrapper);

    }

    public void run() {
        metaDataServiceWrapperMap.forEach(this::handler);
    }

    public void handler(Class<?> clazz, MetaDataServiceWrapper metaDataServiceWrapper) {
        this.handlerDbToService(clazz, metaDataServiceWrapper.getDbToService());
        //TODO service to DB
    }

    public void handlerDbToService(Class<?> clazz, MetaDataServiceWrapper.SingleMetaDataServiceWrapper metaDataServiceWrapper) {
        try {
            // 得到数据 现在的数据
            List<Object> objectList = metaDataServiceWrapper.getSyncService().syncData();
            if (objectList.isEmpty()) {
                return;
            }
            Map<String, Object> newObjectMap = getUniqueKeyMap(objectList, metaDataServiceWrapper.getSyncService()::getUnique);

            // 得到之前的数据
            List<Object> cacheDataList = cacheData.get(clazz);
            if (Objects.isNull(cacheDataList)) {
                //TODO 创建数据
                cacheDataList = new ArrayList<>();
                cacheData.put(clazz, cacheDataList);
            }
            Map<String, Object> oldObjectMap = getUniqueKeyMap(cacheDataList, metaDataServiceWrapper.getSyncService()::getUnique);

            for (Entry<String, Object> entry : oldObjectMap.entrySet()) {

                Object inDbObject = newObjectMap.remove(entry.getKey());
                //如果新数据中没有，则删除
                if (inDbObject == null) {
                    metaDataServiceWrapper.getMetaService().deleteMetaData(entry.getValue());
                } else {
                    //如果新数据中有，则更新
                    //primary id, creat time and update time should not be compared
                    if (!inDbObject.equals(entry.getValue())) {
                        metaDataServiceWrapper.getMetaService().updateMetaData(entry.getValue());
                    }
                }

            }
            //新增
            for (Object object : newObjectMap.values()) {
                metaDataServiceWrapper.getMetaService().addMetaData(object);
            }
            //TODO add service to DB after create new service in db2service
            //如果以DB为主, 那么service2db的时候无法像db写入数据

        } catch (Throwable e) {
            //TODO Exception
            log.error("metadata manager handler error", e);
        }
    }

    public void handlerDbToService(Class<?> clazz, MetaDataServiceWrapper.SingleMetaDataServiceWrapper metaDataServiceWrapper)

    private Map<String, Object> getUniqueKeyMap(List<Object> entities, Function<Object, String> getUnique) {
        Map<String, Object> map = new HashMap<>();
        for (Object entity : entities) {
            String key = getUnique.apply(entity);
            map.put(key, entity);
        }
        return map;
    }

}
