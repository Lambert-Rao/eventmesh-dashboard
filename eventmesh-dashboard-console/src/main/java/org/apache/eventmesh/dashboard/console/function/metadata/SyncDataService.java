package org.apache.eventmesh.dashboard.console.function.metadata;

import java.util.List;

public interface SyncDataService<T> {


    public List<T> getData();

    public List<Long> insertData(List<T> toInsertList);

    /**
     * In design, update data to DB from service(eventmesh runtime and nacos) should not exist, but for now DB data is still writable, so we just let
     * it call insertData.
     *
     * @param toUpdateList
     * @return
     */
    public default List<Long> updateData(List<T> toUpdateList) {
        return insertData(toUpdateList);
    }

    /**
     * For the same reason as updateData, delete data from DB should not exist, this should be removed in the future.
     *
     * @param toDeleteList
     */
    public default void deleteData(List<T> toDeleteList) {
    }

    String getUnique(T t);

}
