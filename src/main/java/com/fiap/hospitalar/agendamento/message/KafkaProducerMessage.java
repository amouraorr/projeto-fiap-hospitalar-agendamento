package com.fiap.hospitalar.agendamento.message;

import com.fiap.hospitalar.agendamento.dto.response.MedicalAppointmentResponseDTO;
import com.fiap.hospitalar.agendamento.service.MedicalAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerMessage {

    private static final Logger logger = LoggerFactory.getLogger(MedicalAppointmentService.class);


    private final KafkaTemplate<String, MedicalAppointmentResponseDTO> kafkaTemplate;

    @Autowired
    public KafkaProducerMessage(KafkaTemplate<String, MedicalAppointmentResponseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MedicalAppointmentResponseDTO message) {
        try {
            kafkaTemplate.send("consultas-agendadas", message).get(); // Usando get() para esperar o resultado
            logger.info("Mensagem enviada com sucesso para o tópico 'consultas-agendadas': {}", message);
        } catch (Exception ex) {
            logger.error("Falha ao enviar mensagem para o tópico 'consultas-agendadas'", ex);
        }
    }
}