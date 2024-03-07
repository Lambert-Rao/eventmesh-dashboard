package org.apache.eventmesh.dashboard.console.function.metadata;

import java.util.List;

public interface MetaDataHandler<T> {


    default void handler(List<T> addData, List<T> updateData, List<T> deleteData) {
    }

    //meta: topic, center, etc. add meta is to create a topic.
    void addMetaData(T meta);

    default void updateMetaData(T meta) {
        this.addMetaData(meta);
    }

    void deleteMetaData(T meta);

}
