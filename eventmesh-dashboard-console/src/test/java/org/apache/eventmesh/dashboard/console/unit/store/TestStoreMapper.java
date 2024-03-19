package org.apache.eventmesh.dashboard.console.unit.store;

import org.apache.eventmesh.dashboard.console.EventMeshDashboardApplication;
import org.apache.eventmesh.dashboard.console.entity.storage.StoreEntity;
import org.apache.eventmesh.dashboard.console.mapper.storage.StoreMapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventMeshDashboardApplication.class)
public class TestStoreMapper {

    @Autowired
    private StoreMapper storeMapper;

    @Test
    public void testAddStore() {
        StoreEntity storeEntity =
            new StoreEntity(null, 1l, 2, "rocketmq", "run1", "n,j", (short) -1, 1098, 1099, "nothing", (short) 1, null, null, "nothing", 1l);
        StoreEntity storeEntity1 =
            new StoreEntity(null, 1l, 1, "rocketmq", "run1", "n,j", (short) -1, 1098, 1099, "nothing", (short) 1, null, null, "nothing", 1l);
        storeMapper.addStore(storeEntity);
        storeMapper.addStore(storeEntity1);
        StoreEntity storeEntities = storeMapper.selectStoreByCluster(storeEntity);

        storeEntities.setUpdateTime(null);
        storeEntities.setCreateTime(null);


    }

    @Test
    public void testDeleteStoreByUnique() {
        StoreEntity storeEntity =
            new StoreEntity(null, 1l, 2, "rocketmq", "run1", "n,j", (short) -1, 1098, 1099, "nothing", (short) 1, null, null, "nothing", 1l);
        storeMapper.addStore(storeEntity);
        storeMapper.deleteStoreByUnique(storeEntity);
        StoreEntity storeEntities = storeMapper.selectStoreByCluster(storeEntity);
        Assert.assertEquals(storeEntities, null);
    }

    @Test
    public void testUpdateStoreByUnique() {
        StoreEntity storeEntity =
            new StoreEntity(null, 1l, 2, "rocketmq", "run1", "n,j", (short) -1, 1098, 1099, "nothing", (short) 1, null, null, "nothing", 1l);
        storeMapper.addStore(storeEntity);
        storeEntity.setStatus((short) 5);
        storeMapper.updateStoreByUnique(storeEntity);
        StoreEntity storeEntities = storeMapper.selectStoreByCluster(storeEntity);

    }
}
