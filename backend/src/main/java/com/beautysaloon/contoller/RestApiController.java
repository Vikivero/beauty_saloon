package com.beautysaloon.contoller;

import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    private BeautyMasterRepository beautyMasterRepository;

    @GetMapping("/available-dates")
    public ResponseEntity<List<String>> getAllAvailableDatesForAllMasters() {
        List<String> masters = null;
        return new ResponseEntity<>(masters, HttpStatus.OK);
    }
}
