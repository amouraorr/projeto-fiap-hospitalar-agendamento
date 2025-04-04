package com.fiap.hospitalar.agendamento.mapper;


import com.fiap.hospitalar.agendamento.dto.request.MedicalAppointmentRequestDTO;
import com.fiap.hospitalar.agendamento.dto.response.MedicalAppointmentResponseDTO;
import com.fiap.hospitalar.agendamento.model.MedicalAppointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalAppointmentMapper {
    MedicalAppointmentMapper INSTANCE = Mappers.getMapper(MedicalAppointmentMapper.class);

    MedicalAppointmentRequestDTO toDTO(MedicalAppointment appointment);
    MedicalAppointment toEntity(MedicalAppointmentRequestDTO dto); // Renomeado para toEntity

    MedicalAppointmentResponseDTO toResponseDTO(MedicalAppointment createdAppointment);
}