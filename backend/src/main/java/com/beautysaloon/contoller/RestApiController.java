package com.beautysaloon.contoller;

import com.beautysaloon.model.Appointment;
import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private BeautyMasterRepository beautyMasterRepository;

    @GetMapping("/show-busy")
    public ResponseEntity<Map<String, List<Instant>>> getAllBusyDatesForAllMasters() {
        List<BeautyMaster> masters = beautyMasterRepository.findAll();

        Map<String, List<Instant>> busyDates = masters.stream()
                .collect(Collectors.toMap(
                        BeautyMaster::getName,
                        master -> master.getAppointments().stream()
                                .map(Appointment::getTime)
                                .collect(Collectors.toList())));

        return new ResponseEntity<>(busyDates, HttpStatus.OK);
    }
}
