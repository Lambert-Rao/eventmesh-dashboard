package org.apache.eventmesh.dashboard.console.function.metadata;

import java.lang.reflect.Field;
import java.util.List;

public interface SyncDataService<T> {


    public List<T> syncData();



    String getUnique(T t);

}
