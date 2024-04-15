package com.beautysaloon.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BeautyMaster {
    private Long id;
    private String name;
    private List<Appointment> appointments;
}
