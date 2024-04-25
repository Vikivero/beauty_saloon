package com.beautysaloon.repository.local;

import com.beautysaloon.model.Appointment;
import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@Primary
public class ListBeautyMasterRepository implements BeautyMasterRepository {

    private final static int BEAUTY_MASTERS_COUNT = 4;
    private final static int APPOINTMENTS_PER_MASTER = 8;
    private final static String DEFAULT_BEAUTY_MASTER_NAME_PATTERN = "Beauty Master %d";
    private final static String DEFAULT_PATIENT_NAME_PATTERN = "Patient %d";

    private final List<BeautyMaster> beautyMasters = constructBeautyMastersData();

    @Override
    public List<BeautyMaster> findAll() {
        return beautyMasters;
    }

    @Override
    public Optional<BeautyMaster> findById(long beautyMasterId) {
        return beautyMasters.stream()
                .filter(beautyMaster -> beautyMaster.getId() == beautyMasterId)
                .findAny();
    }

    @Override
    public BeautyMaster save(BeautyMaster master) {
        beautyMasters.remove(master);
        beautyMasters.add(master);

        return master;
    }

    private static List<BeautyMaster> constructBeautyMastersData() {
        return IntStream.range(0, BEAUTY_MASTERS_COUNT)
                .mapToObj(i -> {

                    var beautyMaster = BeautyMaster.builder()
                            .id((long) i)
                            .name(String.format(DEFAULT_BEAUTY_MASTER_NAME_PATTERN, i))
                            .build();

                    // Adds busy day
                    List<Appointment> appointments = new ArrayList<>();
                    for (int j = 0; j < APPOINTMENTS_PER_MASTER; j++) {
                        var sampleAppointment = Appointment.builder()
                                .id((long) j)
                                //LocalDate.now().toEpochDay() + "" + master.getId() + "" + appointment.getAppointmentWindow()
                                .appointmentWindow(j)
                                .patientName(String.format(DEFAULT_PATIENT_NAME_PATTERN, i))
                                .date(LocalDate.now().plusDays(i))
                                .masterId(beautyMaster.getId())
                                .masterName(beautyMaster.getName())
                                .build();
                        appointments.add(sampleAppointment);
                    }

                    // Adds a day with some free windows
                    for (int j = 0; j < 3; j++) {
                        var sampleAppointment = Appointment.builder()
                                .id((long) (j + appointments.size()))
                                .appointmentWindow(j)
                                .patientName(String.format(DEFAULT_PATIENT_NAME_PATTERN, i))
                                .date(LocalDate.now().plusDays(i + 1))
                                .masterId(beautyMaster.getId())
                                .masterName(beautyMaster.getName())
                                .build();
                        appointments.add(sampleAppointment);
                    }

                    beautyMaster.setAppointments(appointments);

                    return beautyMaster;
                })
                .collect(Collectors.toList());
    }
}
