package org.apache.eventmesh.dashboard.console.function.metadata;

import org.apache.eventmesh.dashboard.console.entity.BaseEntity;

import java.util.List;

/**
 * 获取集群信息
 *
 * @param <T>
 */
public abstract class ClusterFactorSyncDataService<T> implements SyncDataService<T> {


    @Override
    public List<T> getData() {
        return null;
    }

    protected abstract void syncData(Object clusterFactor);

}
