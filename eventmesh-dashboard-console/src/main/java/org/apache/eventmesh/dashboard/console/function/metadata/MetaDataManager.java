package org.apache.eventmesh.dashboard.console.function.metadata;

import org.apache.eventmesh.dashboard.console.function.metadata.MetaDataServiceWrapper.SingleMetaDataServiceWrapper;

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
        this.handlerDbToService(clazz, metaDataServiceWrapper);
        this.handlerServiceToDb(clazz, metaDataServiceWrapper);
    }

    public void handlerDbToService(Class<?> clazz, MetaDataServiceWrapper metaDataServiceWrapper) {
        SingleMetaDataServiceWrapper singleMetaDataServiceWrapper = metaDataServiceWrapper.getDbToService();
        try {
            // 得到数据 现在的数据库数据
            List<Object> newObjectList = singleMetaDataServiceWrapper.getSyncService().getData();
            if (newObjectList.isEmpty()) {
                return;
            }
            Map<String, Object> newObjectMap = getUniqueKeyMap(newObjectList, singleMetaDataServiceWrapper.getSyncService()::getUnique);

            // 得到之前的数据
            List<Object> cacheDataList = cacheData.get(clazz);
            if (Objects.isNull(cacheDataList)) {
                cacheDataList = new ArrayList<>();
                cacheData.put(clazz, cacheDataList);
            }
            Map<String, Object> oldObjectMap = getUniqueKeyMap(cacheDataList, singleMetaDataServiceWrapper.getSyncService()::getUnique);
            //update old cache
            cacheData.replace(clazz, newObjectList);

            for (Entry<String, Object> entry : oldObjectMap.entrySet()) {

                Object inDbObject = newObjectMap.remove(entry.getKey());
                //如果新数据中没有，则删除service中的这一项
                if (inDbObject == null) {
                    singleMetaDataServiceWrapper.getMetaService().deleteMetaData(entry.getValue());
                } else {
                    //如果新数据中有，则更新
                    //primary id, creat time and update time should not be compared
                    if (!inDbObject.equals(entry.getValue())) {
                        singleMetaDataServiceWrapper.getMetaService().updateMetaData(entry.getValue());
                    }
                }

            }
            //新增
            for (Object object : newObjectMap.values()) {
                //TODO convert object to metadata format
                singleMetaDataServiceWrapper.getMetaService().addMetaData(object);
            }
            //TODO add service to DB after create new service in db2service
            //如果以DB为主, 那么service2db的时候无法向db写入数据

            //TODO: register (service to db)
            //不同的metadata service 有不同的处理方式
            if (metaDataServiceWrapper.getServiceToDb() == null) {
                metaDataServiceWrapper.setDbToService(new SingleMetaDataServiceWrapper(
                    singleMetaDataServiceWrapper.getSyncService(), singleMetaDataServiceWrapper.getMetaService()));
            }
        } catch (Throwable e) {
            //TODO Exception
            log.error("metadata manager handler error", e);
        }
    }

    public void handlerServiceToDb(Class<?> clazz, MetaDataServiceWrapper metaDataServiceWrapper) {
        SingleMetaDataServiceWrapper singleMetaDataServiceWrapper = metaDataServiceWrapper.getServiceToDb();
        try {
            // Get metadata
            List<Object> serviceDataList = singleMetaDataServiceWrapper
                .getMetaService().getAllMetaData();
            if (serviceDataList.isEmpty()) {
                return;
            }
            Map<String, Object> serviceDataMap = getUniqueKeyMap(serviceDataList, singleMetaDataServiceWrapper.getSyncService()::getUnique);

            List<Object> cacheDataList = cacheData.get(clazz);
            if (Objects.isNull(cacheDataList)) {
                return;
            }

            // the cache should be just updated in the dbToService function
            Map<String, Object> dbDataMap = getUniqueKeyMap(cacheDataList, singleMetaDataServiceWrapper.getSyncService()::getUnique);

            List<Object> toInsert = new ArrayList<>();
            List<Object> toUpdate = new ArrayList<>();
            List<Object> toDelete = new ArrayList<>();

            for (Entry<String, Object> entry : dbDataMap.entrySet()) {

                Object serviceObject = serviceDataMap.remove(entry.getKey());
                //if service Data don't have a key in dbMap, service removed a piece of data.
                //we remove this data in db for now, but if db is only readable,
                //this should be replaced by send a notification to administrator
                // that a peace of data is removed in cluster service but not operated by dashboard.
                if (serviceObject == null) {
                    toDelete.add(entry.getValue());
                } else {
                    //see comment above
                    //primary id, creat time and update time should not be compared
                    if (!serviceObject.equals(entry.getValue())) {
                        toUpdate.add(entry.getValue());
                    }
                }

            }
            //see comment above
            toInsert.addAll(serviceDataMap.values());

            singleMetaDataServiceWrapper.getSyncService().insertData(toInsert);
            singleMetaDataServiceWrapper.getSyncService().updateData(toUpdate);
            singleMetaDataServiceWrapper.getSyncService().deleteData(toDelete);

        } catch (Throwable e) {
            //TODO Exception
            log.error("metadata manager handler error", e);
        }
    }

    /**
     * get the unique key which can be used to identify the object then assemble the key and the object into a map
     *
     * @param getUnique a function that can process the objects
     */
    private Map<String, Object> getUniqueKeyMap(List<Object> objects, Function<Object, String> getUnique) {
        Map<String, Object> map = new HashMap<>();
        for (Object entity : objects) {
            String key = getUnique.apply(entity);
            map.put(key, entity);
        }
        return map;
    }

}
