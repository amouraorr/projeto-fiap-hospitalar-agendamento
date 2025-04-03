package com.fiap.hospitalar.agendamento.repository;

import com.fiap.hospitalar.agendamento.model.MedicalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {
    // Adicionar métodos de consulta personalizados
    List<MedicalAppointment> findByMedico(String medico);

    // Exemplo de consulta personalizada usando @Query
    // @Query("SELECT m FROM MedicalAppointment m WHERE m.status = :status")
    // List<MedicalAppointment> findByStatus(@Param("status") String status);
}