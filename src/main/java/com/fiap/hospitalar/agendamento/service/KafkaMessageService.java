package com.fiap.hospitalar.agendamento.service;

import com.fiap.hospitalar.agendamento.dto.response.MedicalAppointmentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService {

    private final KafkaTemplate<String, MedicalAppointmentResponseDTO> kafkaTemplate;

    @Autowired
    public KafkaMessageService(KafkaTemplate<String, MedicalAppointmentResponseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MedicalAppointmentResponseDTO message) {
        kafkaTemplate.send("consultas-agendadas", message);
    }
}