package com.beautysaloon.contoller;

import com.beautysaloon.dto.BeautyMasterAppointmentsDto;
import com.beautysaloon.model.Appointment;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private BeautyMasterRepository beautyMasterRepository;

    @GetMapping("/master-appointments")
    public ResponseEntity<List<BeautyMasterAppointmentsDto>> getAppointmentsForMasters() {
        List<BeautyMasterAppointmentsDto> masters = beautyMasterRepository.findAll().stream()
                .map(beautyMaster -> {
                    Map<LocalDate, List<Integer>> busyWindows = new HashMap<>();
                    beautyMaster.getAppointments()
                            .forEach(appointment -> {
                                if (!busyWindows.containsKey(appointment.getDate())) {
                                    busyWindows.put(appointment.getDate(), new ArrayList<>());
                                }

                                busyWindows.get(appointment.getDate()).add(appointment.getAppointmentWindow());
                            });
                    return BeautyMasterAppointmentsDto.builder()
                            .masterId(beautyMaster.getId())
                            .masterName(beautyMaster.getName())
                            .busyWindows(busyWindows)
                            .build();
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(masters, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAppointment(@RequestBody Appointment appointment) {
        var masterOptional = beautyMasterRepository.findById(appointment.getMasterId());

        if (masterOptional.isPresent()) {
            var master = masterOptional.get();

            var alreadyTakenWindows = master.getAppointments().stream()
                    .filter(existing -> existing.getDate().isEqual(appointment.getDate()))
                    .map(Appointment::getAppointmentWindow)
                    .toList();

            if (alreadyTakenWindows.contains(appointment.getAppointmentWindow())) {
                return new ResponseEntity<>("This appointment window is already taken.", HttpStatus.CONFLICT);
            }

            long generatedAppointmentId = Long.parseLong(LocalDate.now().toEpochDay() + "" + master.getId() + "" + appointment.getAppointmentWindow());
            appointment.setId(generatedAppointmentId);
            appointment.setMasterName(master.getName());

            master.getAppointments().add(appointment);

            beautyMasterRepository.save(master);

            return new ResponseEntity<>(String.valueOf(generatedAppointmentId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Master with given id is not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Appointment> checkAppointment(@PathVariable Long appointmentId) {
        var masters = beautyMasterRepository.findAll();
        var appointmentOptional = masters.stream()
                .flatMap(beautyMaster -> beautyMaster.getAppointments().stream())
                .filter(app -> app.getId().equals(appointmentId))
                .findAny();

        if (appointmentOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(appointmentOptional.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/appointment/{appointmentId}")
    public ResponseEntity<String> removeAppointment(@PathVariable Long appointmentId) {
        var masters = beautyMasterRepository.findAll();
        var masterOptional = masters.stream()
                .filter(master -> master.getAppointments().stream()
                        .map(Appointment::getId)
                        .anyMatch(aid -> aid.equals(appointmentId)))
                .findAny();

        if (masterOptional.isEmpty()) {
            return new ResponseEntity<>("Master not found or appointment does not exist", HttpStatus.NOT_FOUND);
        } else {
            masterOptional.get()
                    .getAppointments()
                    .removeIf(appointment -> appointment.getId().equals(appointmentId));

            beautyMasterRepository.save(masterOptional.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
