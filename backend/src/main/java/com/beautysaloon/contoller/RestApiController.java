package com.beautysaloon.contoller;

import com.beautysaloon.service.AppointmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {
    private AppointmentService appointmentService;
}
