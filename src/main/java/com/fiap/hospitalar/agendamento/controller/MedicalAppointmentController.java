package com.fiap.hospitalar.agendamento.controller;

import com.fiap.hospitalar.agendamento.dto.request.MedicalAppointmentRequestDTO;
import com.fiap.hospitalar.agendamento.dto.response.MedicalAppointmentResponseDTO;
import com.fiap.hospitalar.agendamento.mapper.MedicalAppointmentMapper;
import com.fiap.hospitalar.agendamento.model.MedicalAppointment;
import com.fiap.hospitalar.agendamento.message.KafkaProducerMessage;
import com.fiap.hospitalar.agendamento.service.MedicalAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class MedicalAppointmentController {

    @Autowired
    private MedicalAppointmentService appointmentService;

    @Autowired
    private KafkaProducerMessage kafkaMessageService; // Injetar o KafkaProducerMessage

    @PostMapping
    @Operation(summary = "Criar uma nova consulta médica", description = "Cria uma nova consulta médica com os detalhes fornecidos.")
    public ResponseEntity<MedicalAppointmentResponseDTO> createAppointment(@RequestBody MedicalAppointmentRequestDTO appointmentRequestDTO) {
        try {
            MedicalAppointment appointment = MedicalAppointmentMapper.INSTANCE.toEntity(appointmentRequestDTO);
            MedicalAppointment createdAppointment = appointmentService.save(appointment);

            // Enviar mensagem para o Kafka
            kafkaMessageService.sendMessage(MedicalAppointmentMapper.INSTANCE.toResponseDTO(createdAppointment));

            return new ResponseEntity<>(MedicalAppointmentMapper.INSTANCE.toResponseDTO(createdAppointment), HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma consulta médica", description = "Atualiza uma consulta médica existente")
    public ResponseEntity<MedicalAppointmentResponseDTO> updateAppointment(@PathVariable Long id, @RequestBody MedicalAppointmentRequestDTO appointmentRequestDTO) {
        MedicalAppointment appointment = MedicalAppointmentMapper.INSTANCE.toEntity(appointmentRequestDTO);
        appointment.setId(id); // Define o ID da consulta a ser atualizada
        MedicalAppointment updatedAppointment = appointmentService.update(appointment);

        // Enviar mensagem para o Kafka
        kafkaMessageService.sendMessage(MedicalAppointmentMapper.INSTANCE.toResponseDTO(updatedAppointment));

        return new ResponseEntity<>(MedicalAppointmentMapper.INSTANCE.toResponseDTO(updatedAppointment), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Obter todas as consultas médicas", description = "Recupera uma lista de todas as consultas médicas.")
    public ResponseEntity<List<MedicalAppointmentResponseDTO>> getAllAppointments() {
        List<MedicalAppointment> appointments = appointmentService.findAll();
        List<MedicalAppointmentResponseDTO> appointmentResponseDTOs = appointments.stream()
                .map(MedicalAppointmentMapper.INSTANCE::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(appointmentResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma consulta médica por ID", description = "Recupera uma consulta médica por seu ID.")
    public ResponseEntity<MedicalAppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
        MedicalAppointment appointment = appointmentService.findById(id);
        return new ResponseEntity<>(MedicalAppointmentMapper.INSTANCE.toResponseDTO(appointment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma consulta médica", description = "Exclui uma consulta médica pelo seu ID.")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}