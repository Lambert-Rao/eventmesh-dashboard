package org.apache.eventmesh.dashboard.console.function.client.create;

import javafx.util.Pair;
import org.apache.eventmesh.dashboard.console.function.client.ClientOperation;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;

public class EtcdClientCreateOperation implements ClientOperation {
    @Override
    public Pair createClient(CreateClientConfig clientConfig) {
        return null;
    }

    @Override
    public void close(Object client) {

    }
}
