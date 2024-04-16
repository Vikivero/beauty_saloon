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
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
