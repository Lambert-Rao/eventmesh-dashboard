package org.apache.eventmesh.dashboard.console.function.client;

import javafx.util.Pair;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;

public interface ClientOperation<T> {

    public Pair<String, T> createClient(CreateClientConfig clientConfig);

    public void close(T client);
}
