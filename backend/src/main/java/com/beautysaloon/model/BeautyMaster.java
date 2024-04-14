package com.beautysaloon.model;

import lombok.Data;

import java.util.List;

@Data
public class BeautyMaster {
    private Long id;
    private String name;
    private List<Appointment> appointments;
}
