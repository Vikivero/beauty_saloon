package com.beautysaloon.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Appointment {
    private Long id;
    private String patientName;
    private LocalDate date;
    private int appointmentWindow;
    private Long masterId;
    private String masterName;
}
