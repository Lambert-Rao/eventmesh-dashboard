package org.apache.eventmesh.dashboard.console.function.metadata;

import java.util.List;

public interface MetadataHandler<T> {

    default boolean writable() {
        return false;
    }

    default void handler(List<T> addData, List<T> updateData, List<T> deleteData) {
    }

    //metaData: topic, center, etc. add meta is to create a topic.
    List<T> getAllMetadata();

    void addMetadata(T meta);

    default void updateMetadata(T meta) {
        this.addMetadata(meta);
    }

    void deleteMetadata(T meta);

}
