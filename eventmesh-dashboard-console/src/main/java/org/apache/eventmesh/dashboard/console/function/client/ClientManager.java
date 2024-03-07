package org.apache.eventmesh.dashboard.console.function.client;

import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;
import org.apache.eventmesh.dashboard.console.function.client.create.NacosClientCreateOperation;
import org.apache.eventmesh.dashboard.console.function.client.create.NacosConfigClientCreateOperation;
import org.apache.eventmesh.dashboard.console.function.client.create.NacosNamingClientCreateOperation;
import org.apache.eventmesh.dashboard.console.function.client.create.RedisClientCreateOperation;
import org.apache.eventmesh.dashboard.console.function.client.create.RocketMQProduceClientCreateOperation;
import org.apache.eventmesh.dashboard.console.function.client.create.RocketMQPushConsumerClientCreateOperation;
import org.apache.eventmesh.dashboard.console.function.client.create.RocketMQRemotingClientCreateOperation;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javafx.util.Pair;

public class ClientManager {

    private static final ClientManager INSTANCE = new ClientManager();


    public static ClientManager getInstance() {
        return INSTANCE;
    }

    //inner key is the unique key of a client, such as (ip + port)
    private final Map<ClientTypeEnum, Map<String, Object>> clientMap = new ConcurrentHashMap<>();

    private final Map<ClientTypeEnum, ClientOperation<?>> clientCreateOperationMap = new ConcurrentHashMap<>();

    {
        for (ClientTypeEnum clientTypeEnum : ClientTypeEnum.values()) {
            clientMap.put(clientTypeEnum, new ConcurrentHashMap<>());
        }

        clientCreateOperationMap.put(ClientTypeEnum.STORAGE_REDIS, new RedisClientCreateOperation());

        clientCreateOperationMap.put(ClientTypeEnum.STORAGE_ROCKETMQ_REMOTING, new RocketMQRemotingClientCreateOperation());
        clientCreateOperationMap.put(ClientTypeEnum.STORAGE_ROCKETMQ_PRODUCER, new RocketMQProduceClientCreateOperation());
        clientCreateOperationMap.put(ClientTypeEnum.STORAGE_ROCKETMQ_CONSUMER, new RocketMQPushConsumerClientCreateOperation());

        clientCreateOperationMap.put(ClientTypeEnum.CENTER_NACOS, new NacosClientCreateOperation());
        clientCreateOperationMap.put(ClientTypeEnum.CENTER_NACOS_CONFIG, new NacosConfigClientCreateOperation());
        clientCreateOperationMap.put(ClientTypeEnum.CENTER_NACOS_NAMING, new NacosNamingClientCreateOperation());

    }

    private ClientManager() {
    }


    public <T> Pair<String, T> createClient(String clientType, CreateClientConfig config) {
        ClientTypeEnum clientTypeEnum = ClientTypeEnum.valueOf(clientType);
        return createClient(clientTypeEnum, config);
    }

    public <T> Pair<String, T> createClient(ClientTypeEnum clientTypeEnum, CreateClientConfig config) {
        return createClient(clientTypeEnum, config.getUniqueKey(), config);
    }

    public <T> Pair<String, T> createClient(String clientType, String uniqueKey, CreateClientConfig config) {
        ClientTypeEnum clientTypeEnum = ClientTypeEnum.valueOf(clientType);
        return createClient(clientTypeEnum, uniqueKey, config);
    }

    public <T> Pair<String, T> createClient(ClientTypeEnum clientTypeEnum, String uniqueKey, CreateClientConfig config) {

        Map<String, Object> clients = this.clientMap.get(clientTypeEnum);

        Object client = clients.get(uniqueKey);
        Pair<String, ?> result = new Pair<>(uniqueKey, client);
        if (Objects.isNull(client)) {
            ClientOperation<?> clientCreateOperation = this.clientCreateOperationMap.get(clientTypeEnum);
            result = clientCreateOperation.createClient(config);
            clients.put(result.getKey(), result.getValue());
        }
        try {
            return (Pair<String, T>) result;
        } catch (Exception e) {
            throw new RuntimeException("create client error", e);
        }
    }

    public void deleteClient(ClientTypeEnum clientTypeEnum, String uniqueKey) {
        Map<String, Object> clients = this.clientMap.get(clientTypeEnum);
        ClientOperation operation = this.clientCreateOperationMap.get(clientTypeEnum);
        try {
            operation.close(clients.get(uniqueKey));
        } catch (Exception e) {
            throw new RuntimeException("close client error", e);
        }
        clients.remove(uniqueKey);
    }

    public Object getClient(ClientTypeEnum clientTypeEnum, String uniqueKey) {
        return this.clientMap.get(clientTypeEnum).get(uniqueKey);
    }
}
