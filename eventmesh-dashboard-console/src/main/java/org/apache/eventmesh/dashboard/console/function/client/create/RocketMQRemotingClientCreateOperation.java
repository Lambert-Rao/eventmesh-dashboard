package org.apache.eventmesh.dashboard.console.function.client.create;

import javafx.util.Pair;
import org.apache.eventmesh.dashboard.console.function.client.ClientOperation;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;
import org.apache.rocketmq.remoting.RemotingClient;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.apache.rocketmq.remoting.netty.NettyRemotingClient;

public class RocketMQRemotingClientCreateOperation implements ClientOperation {

    @Override
    public Pair createClient(CreateClientConfig clientConfig) {
        NettyClientConfig config = new NettyClientConfig();
        config.setUseTLS(false);
        RemotingClient remotingClient = new NettyRemotingClient(config);
        remotingClient.start();
        return remotingClient;
    }

    @Override
    public void close(Object client) {
        ((RemotingClient) client).shutdown();
    }
}
