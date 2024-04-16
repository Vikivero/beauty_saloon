package com.beautysaloon.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class BeautyMasterAppointmentsDto {
    private Long masterId;
    private String masterName;
    private Map<LocalDate, List<Integer>> busyWindows;
}
