package com.fiap.hospitalar.agendamento.controller;

import com.fiap.hospitalar.agendamento.model.MedicalAppointment;
import com.fiap.hospitalar.agendamento.service.MedicalAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class MedicalAppointmentController {

    @Autowired
    private MedicalAppointmentService appointmentService;

/*    @PostMapping
    //@Operation(summary = "Criação de login", description = "Cria um novo login com os dados fornecidos.")
    public ResponseEntity<MedicalAppointment> createAppointment(@RequestBody MedicalAppointment appointment) {
        MedicalAppointment createdAppointment = appointmentService.save(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }*/

    @PostMapping
    @Operation(summary = "Criar uma nova consulta médica", description = "Cria uma nova consulta médica com os detalhes fornecidos.")
    public ResponseEntity<MedicalAppointment> createAppointment(@RequestBody MedicalAppointment appointment) {
        try {
            MedicalAppointment createdAppointment = appointmentService.save(appointment);
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error creating appointment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Obter todas as consultas médicas", description = "Recupera uma lista de todas as consultas médicas.")
    public ResponseEntity<List<MedicalAppointment>> getAllAppointments() {
        List<MedicalAppointment> appointments = appointmentService.findAll();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma consulta médica por ID", description = "Recupera uma consulta médica por seu ID.")
    public ResponseEntity<MedicalAppointment> getAppointmentById(@PathVariable Long id) {
        MedicalAppointment appointment = appointmentService.findById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

/*    @PutMapping("/{id}")
    public ResponseEntity<MedicalAppointment> updateAppointment(@PathVariable Long id, @RequestBody MedicalAppointment appointment) {
        appointment.setId(id);
        MedicalAppointment updatedAppointment = appointmentService.update(appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }*/

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma consulta médica", description = "Atualiza uma consulta médica existente")
    public ResponseEntity<MedicalAppointment> updateAppointment(@PathVariable Long id, @RequestBody MedicalAppointment appointment) {
        try {
            appointment.setId(id);
            MedicalAppointment updatedAppointment = appointmentService.update(appointment);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Log the exception
            System.err.println("Error updating appointment: " + e.getMessage());
            // Return 404 Not Found if the appointment is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Log any other exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            // Return 500 Internal Server Error for any other exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma consulta médica", description = "Exclui uma consulta médica pelo seu ID.")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
