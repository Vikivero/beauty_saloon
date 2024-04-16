package com.beautysaloon.repository.local;

import com.beautysaloon.model.Appointment;
import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@Primary
public class ListBeautyMasterRepository implements BeautyMasterRepository {

    private final static int BEAUTY_MASTERS_COUNT = 4;
    private final static int APPOINTMENTS_PER_MASTER = 10;
    private final static String DEFAULT_BEAUTY_MASTER_NAME_PATTERN = "Beauty Master %d";
    private final static String DEFAULT_PATIENT_NAME_PATTERN = "Patient %d";

    private final List<BeautyMaster> beautyMasters = constructBeautyMasters();

    @Override
    public List<BeautyMaster> findAll() {
        return beautyMasters;
    }

    private static List<BeautyMaster> constructBeautyMasters() {
        return IntStream.range(0, BEAUTY_MASTERS_COUNT)
                .mapToObj(i -> {

                    var beautyMaster = BeautyMaster.builder()
                            .id((long) i)
                            .name(String.format(DEFAULT_BEAUTY_MASTER_NAME_PATTERN, i))
                            .build();

                    List<Appointment> appointments = new ArrayList<>();
                    for (int j = 0; j < APPOINTMENTS_PER_MASTER; j++) {
                        var counter = j * (i + 1);
                        var sampleAppointment = Appointment.builder()
                                .id((long) counter)
                                .appointmentWindow(counter)
                                .patientName(String.format(DEFAULT_PATIENT_NAME_PATTERN, i))
                                .date(LocalDate.now())
                                .beautyMasterId(beautyMaster.getId())
                                .build();
                        appointments.add(sampleAppointment);
                    }

                    beautyMaster.setAppointments(appointments);

                    return beautyMaster;
                })
                .collect(Collectors.toList());
    }
}
