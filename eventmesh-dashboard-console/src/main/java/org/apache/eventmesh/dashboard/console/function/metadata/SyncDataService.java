package org.apache.eventmesh.dashboard.console.function.metadata;

import java.util.List;

public interface SyncDataService<T> {


    public List<T> getData();

    public List<Long> insertData(List<T> toInsertList);

    String getUnique(T t);

}
