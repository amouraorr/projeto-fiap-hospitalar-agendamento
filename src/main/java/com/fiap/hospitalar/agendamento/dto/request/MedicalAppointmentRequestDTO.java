package com.fiap.hospitalar.agendamento.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MedicalAppointmentRequestDTO {

    private Long id;
    private String paciente;
    private String medico;
    private String enfermeiro;
    private LocalDateTime dataHora;
}
