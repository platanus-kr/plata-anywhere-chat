package org.platanus.platachat.message.broker.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.broker.kafka.KafkaListenerAdaptor;
import org.platanus.platachat.message.contants.SimpleConfigConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaAdminConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ListenerService listenerService() {
        return new KafkaListenerAdaptor();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic simpleTopic() {
        return new NewTopic(SimpleConfigConstant.TOPIC_NAME, 1, (short) 1);
    }

}
