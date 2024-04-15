package com.beautysaloon.repository.local;

import com.beautysaloon.model.Appointment;
import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListBeautyMasterRepository implements BeautyMasterRepository {

    private List<BeautyMaster> beautyMasters = getBeautyMasters();

    @Override
    public List<BeautyMaster> findAll() {
        return beautyMasters;
    }

    private static List<BeautyMaster> getBeautyMasters() {
        return IntStream.range(0, 4)
                .mapToObj(i -> BeautyMaster.builder()
                        .id((long) i)
                        .name("Beauty Master " + i)
                        .appointments(List.of(
                                Appointment.
                        ))
                        .build())
                .collect(Collectors.toList());
    }
}
