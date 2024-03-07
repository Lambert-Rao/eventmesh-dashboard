package org.apache.eventmesh.dashboard.console.function.client.create;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.eventmesh.dashboard.console.function.client.ClientOperation;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateRocketmqConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

@Slf4j
public class RocketMQPushConsumerClientCreateOperation implements ClientOperation {

    @Override
    public Pair createClient(CreateClientConfig clientConfig) {
        DefaultMQPushConsumer consumer = null;
        try {
            CreateRocketmqConfig config = (CreateRocketmqConfig) clientConfig;
            consumer = new DefaultMQPushConsumer(config.getConsumerGroup());
            consumer.setMessageModel(config.getMessageModel());
            consumer.setNamesrvAddr(config.getNameServerUrl());
            consumer.subscribe(config.getTopic(), config.getSubExpression());
            consumer.registerMessageListener(config.getMessageListener());
            consumer.start();
        } catch (MQClientException e) {
            log.error("create rocketmq push consumer failed", e);
        }
        return consumer;
    }

    @Override
    public void close(Object client) {
        ((DefaultMQPushConsumer) client).shutdown();
    }
}
