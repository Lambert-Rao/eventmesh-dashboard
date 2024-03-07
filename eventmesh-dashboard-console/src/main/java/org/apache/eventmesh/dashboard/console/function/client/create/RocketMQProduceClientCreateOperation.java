package org.apache.eventmesh.dashboard.console.function.client.create;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.eventmesh.dashboard.console.function.client.ClientOperation;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateRocketmqConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

@Slf4j
public class RocketMQProduceClientCreateOperation implements ClientOperation {

    @Override
    public Pair createClient(CreateClientConfig clientConfig) {
        DefaultMQProducer producer = null;
        try {
            CreateRocketmqConfig config = (CreateRocketmqConfig) clientConfig;
            producer = new DefaultMQProducer(config.getProducerGroup());
            producer.setNamesrvAddr(config.getNameServerUrl());
            producer.setCompressMsgBodyOverHowmuch(16);
            producer.start();
        } catch (MQClientException e) {
            log.error("create rocketmq producer failed", e);
        }
        return producer;
    }

    @Override
    public void close(Object client) {
        ((DefaultMQProducer) client).shutdown();
    }
}
