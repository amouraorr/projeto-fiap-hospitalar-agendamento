package com.fiap.hospitalar.agendamento.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@Data
@RequiredArgsConstructor
public class KafkaProducerMessage {
    private final KafkaTemplate<String, KafkaProducerMessage> kafkaTemplate;
    private static final String KAFKA_TOPIC = "consultas-agendadas";

    public void sendMessage(KafkaProducerMessage message) {
        kafkaTemplate.send(KAFKA_TOPIC, message);
    }
}