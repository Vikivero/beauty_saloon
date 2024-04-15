package com.beautysaloon.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Appointment {
    private Long id;
    private String patientName;
    private Instant time;
    private BeautyMaster beautyMaster;
}
