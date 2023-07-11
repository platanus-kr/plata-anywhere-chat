package org.platanus.platachat.message.broker.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.platanus.platachat.message.broker.ConsumerComponent;
import org.platanus.platachat.message.broker.kafka.KafkaConsumerAdaptor;
import org.platanus.platachat.message.contants.KafkaSimpleConfigConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


//@EnableKafka
//@Configuration
public class KafkaAdminConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerComponent listenerService() {
        return new KafkaConsumerAdaptor();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic simpleTopic() {
        return new NewTopic(KafkaSimpleConfigConstant.TOPIC_NAME, 1, (short) 1);
    }

}
