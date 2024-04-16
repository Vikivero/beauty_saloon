package com.beautysaloon.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Appointment {
    private Long id;
    private String patientName;
    private int appointmentWindow;
    private Long beautyMasterId;
}
