package com.beautysaloon.contoller;

import com.beautysaloon.model.Appointment;
import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private BeautyMasterRepository beautyMasterRepository;

    @GetMapping("/masters")
    public ResponseEntity<List<BeautyMaster>> getAllBusyDatesForAllMasters() {
        List<BeautyMaster> masters = beautyMasterRepository.findAll();

        return new ResponseEntity<>(masters, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
