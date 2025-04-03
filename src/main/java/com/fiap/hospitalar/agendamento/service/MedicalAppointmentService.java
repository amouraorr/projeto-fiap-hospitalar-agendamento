package com.fiap.hospitalar.agendamento.service;

import com.fiap.hospitalar.agendamento.model.MedicalAppointment;
import com.fiap.hospitalar.agendamento.repository.MedicalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalAppointmentService {

    @Autowired
    private MedicalAppointmentRepository appointmentRepository;

    public List<MedicalAppointment> findAll() {
        return appointmentRepository.findAll();
    }

    public MedicalAppointment findById(Long id) {
        Optional<MedicalAppointment> appointment = appointmentRepository.findById(id);
        return appointment.orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public MedicalAppointment save(MedicalAppointment appointment) {
        // Adicione lógica de validação ou transformação aqui, se necessário
        return appointmentRepository.save(appointment);
    }

    public MedicalAppointment update(MedicalAppointment appointment) {
        // Verifique se a consulta existe antes de atualizar
        if (!appointmentRepository.existsById(appointment.getId())) {
            throw new RuntimeException("Appointment not found");
        }
        return appointmentRepository.save(appointment);
    }

    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }
}