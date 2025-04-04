package com.fiap.hospitalar.agendamento.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MedicalAppointmentResponseDTO {

    private Long id;
    private String paciente;
    private String medico;
    private String enfermeiro;
    private LocalDateTime dataHora;
}
