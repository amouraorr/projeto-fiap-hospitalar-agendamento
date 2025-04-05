package com.fiap.hospitalar.agendamento.message;

import com.fiap.hospitalar.agendamento.dto.response.MedicalAppointmentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerMessage {

    private final KafkaTemplate<String, MedicalAppointmentResponseDTO> kafkaTemplate;

    @Autowired
    public KafkaProducerMessage(KafkaTemplate<String, MedicalAppointmentResponseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MedicalAppointmentResponseDTO message) {
        kafkaTemplate.send("consultas-agendadas", message);
    }
}