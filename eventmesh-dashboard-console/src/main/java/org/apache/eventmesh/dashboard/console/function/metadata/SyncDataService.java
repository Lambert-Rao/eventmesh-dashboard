package org.apache.eventmesh.dashboard.console.function.metadata;

import java.util.List;

public interface SyncDataService<T> {

    /**
     * Whether the database is writable
     * @return
     */
    public default boolean writable() {
        return true;
    }

    public List<T> getData();

    public List<Long> insertData(List<T> toInsertList);

    /**
     * update data into db, this should use different method but insert.
     * TODO edit this after DAO service add batchUpdate
     * @param toUpdateList
     * @return
     */
    public default List<Long> updateData(List<T> toUpdateList) {
        return insertData(toUpdateList);
    }

    public default void deleteData(List<T> toDeleteList) {
    }

    String getUnique(T t);

}
